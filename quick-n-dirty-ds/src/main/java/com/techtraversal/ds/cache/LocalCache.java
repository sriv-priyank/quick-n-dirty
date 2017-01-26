package com.techtraversal.ds.cache;

import com.techtraversal.ds.cache.policy.DefaultLinkedPolicy;

import java.util.HashMap;
import java.util.Map;


public final class LocalCache<K, V> implements Cache<K, V> {

    private static final int MAX_SIZE = (1 << 30) - 1;

    private final Map<K, CacheNode<K, V>> cache;
    private final CachePolicy<K, V> policy;


    public LocalCache() {
        this(new DefaultLinkedPolicy<>());
    }

    public LocalCache(CachePolicy<K, V> policy) {
        if (policy == null) {
            policy = new DefaultLinkedPolicy<>();
        }
        this.policy = policy;
        validate();

        int initCapacity = policy.getInitialCapacity();
        if (initCapacity == 0 &&
                policy.getMaxSize() != CachePolicy.UNBOUNDED)
            initCapacity = policy.getMaxSize();

        this.cache = new HashMap<>(initCapacity);
    }

    private void validate() {
        if (policy.getMaxSize() > MAX_SIZE)
            throw new IllegalArgumentException("MaxSize too high");

        if (policy.evict() && policy.getMaxSize() == CachePolicy.UNBOUNDED)
            throw new IllegalArgumentException("Eviction cannot be applied for unbounded cache");

        if (!policy.evict() && policy.getMaxSize() != CachePolicy.UNBOUNDED)
            throw new IllegalArgumentException("MaxSize is redundant if eviction is not applied");
    }


    ////////// API :: interface Cache ///////////

    @Override
    public void set(K key, V val) {
        CacheNode<K, V> node = getNode(key);
        if (node == null) {
            node = policy.newNode(key, val);
            this.cache.put(key, node);

            doEvict();

        } else {
            node.setVal(val);
            policy.afterNodeAccess(node);
        }
    }

    @Override
    public V get(K key) {
        CacheNode<K, V> node = getNode(key);
        if (node == null)  return null;

        policy.afterNodeAccess(node);
        return node.val();
    }

    @Override
    public V remove(K key) {
        CacheNode<K, V> node = getNode(key);
        if (node == null)  return null;

        policy.afterNodeRemoval(node);
        return node.val();
    }


    //////////// internal methods /////////////

    private CacheNode<K, V> getNode(K key) {
        return this.cache.get(key);
    }

    private int size() {
        return this.cache.size();
    }

    private void doEvict() {
        if (policy.evict() && (size() > policy.getMaxSize())) {
            CacheNode<K, V> node = policy.getEvictionCandidate();
            if (node != null) {
                remove(node.key());
            }
        }
    }
}

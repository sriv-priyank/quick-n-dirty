package com.techtraversal.ds.cache.policy;

import com.techtraversal.ds.cache.CacheNode;
import com.techtraversal.ds.cache.CachePolicy;


public final class LRUCachePolicy<K, V> extends CachePolicy<K, V> {

    private LRUNode<K, V> head, tail;
    private final int maxSize;
    private final boolean evict;

    public LRUCachePolicy(int maxSize) {
        this.maxSize = maxSize;
        this.evict = true;
    }


    ///////// API :: CachePolicy methods ///////////

    @Override
    protected final CacheNode<K, V> newNode(K key, V val) {
        LRUNode<K, V> node = new LRUNode<>(key, val);
        linkLastNode(node);

        return node;
    }

    @Override
    protected int getMaxSize() {
        return this.maxSize;
    }

    @Override
    protected boolean evict() {
        return this.evict;
    }

    @Override
    protected final CacheNode<K, V> getEvictionCandidate() {
        return this.head;
    }

    @Override
    protected final void afterNodeAccess(CacheNode<K, V> node) {
        if (node != this.tail) {
            LRUNode<K, V> p = (LRUNode<K, V>) node;
            moveNodeToTail(p);
        }
    }

    @Override
    protected final void afterNodeRemoval(CacheNode<K, V> node) {
        LRUNode<K, V> p = (LRUNode<K, V>) node;
        unlinkNode(p);
    }


    //////////// internal methods /////////////

    private void moveNodeToTail(LRUNode<K, V> node) {
        LRUNode<K, V> last = this.tail;
        if (node.prev == null)
            this.head = node.next;
        else
            node.prev.next = node.next;

        node.next.prev = node.prev;

        node.prev = last;
        last.next = node;
        this.tail = node;
    }

    private void unlinkNode(LRUNode<K, V> node) {
        if (node.prev == null)
            this.head = node.next;
        else
            node.prev.next = node.next;

        if (node.next == null)
            this.tail = node.prev;
        else
            node.next.prev = node.prev;

        node.prev = node.next = null;
    }

    private void linkLastNode(LRUNode<K, V> node) {
        LRUNode<K, V> last = this.tail;
        this.tail = node;

        if (last == null) {
            this.head = node;
        } else {
            node.prev = last;
            last.next = node;
        }
    }


    //////////// lrunode definition /////////////

    static final class LRUNode<K, V> implements CacheNode<K, V> {
        private K key;
        private V val;
        private LRUNode<K, V> prev, next;

        public LRUNode(K key, V val) {
            this.key = key;
            this.val = val;
        }

        @Override
        public K key() {
            return this.key;
        }

        @Override
        public V val() {
            return this.val;
        }

        @Override
        public V setVal(V val) {
            V old = this.val;
            this.val = val;
            return old;
        }
    }
}

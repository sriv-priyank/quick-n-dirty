package com.techtraversal.ds.cache.policy;

import com.techtraversal.ds.cache.CacheNode;
import com.techtraversal.ds.cache.CachePolicy;


public class DefaultLinkedPolicy<K, V> extends CachePolicy<K, V> {

    private LinkedNode<K, V> head, tail;
    private final int initialCapacity;
    private final boolean evict = false;

    public DefaultLinkedPolicy() {
        this(0);
    }

    public DefaultLinkedPolicy(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }


    ///////// API :: CachePolicy methods ///////////

    @Override
    protected final CacheNode<K, V> newNode(K key, V val) {
        LinkedNode<K, V> node = new LinkedNode<>(key, val);
        linkLastNode(node);

        return node;
    }

    @Override
    protected int getInitialCapacity() {
        return this.initialCapacity;
    }

    @Override
    protected boolean evict() {
        return this.evict;
    }

    @Override
    protected final void afterNodeRemoval(CacheNode<K, V> node) {
        LinkedNode<K, V> p = (LinkedNode<K, V>) node;
        unlinkNode(p);
    }


    //////////// internal methods /////////////

    private void unlinkNode(LinkedNode<K, V> node) {
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

    private void linkLastNode(LinkedNode<K, V> node) {
        LinkedNode<K, V> last = this.tail;
        this.tail = node;

        if (last == null) {
            this.head = node;
        } else {
            node.prev = last;
            last.next = node;
        }
    }


    //////////// lrunode definition /////////////

    static final class LinkedNode<K, V> implements CacheNode<K, V> {
        private K key;
        private V val;
        private LinkedNode<K, V> prev, next;

        public LinkedNode(K key, V val) {
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

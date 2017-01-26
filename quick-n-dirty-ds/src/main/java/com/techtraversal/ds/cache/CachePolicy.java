package com.techtraversal.ds.cache;


public abstract class CachePolicy<K, V> {

    final static int UNBOUNDED = Integer.MIN_VALUE;

    protected abstract CacheNode<K, V> newNode(K key, V val);

    protected int getMaxSize() {
        return UNBOUNDED;
    }

    protected int getInitialCapacity() {
        return 0;
    }

    protected boolean evict() {
        return false;
    }

    protected CacheNode<K, V> getEvictionCandidate() {
        return null;
    }

    protected void afterNodeAccess(CacheNode<K, V> node)  {
            /*  no-ops  */
    }

    protected void afterNodeRemoval(CacheNode<K, V> node) {
            /*  no-ops  */
    }
}

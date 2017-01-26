package com.techtraversal.ds.cache;

public interface CacheNode<K, V> {

    K key();

    V val();

    V setVal(V val);
}

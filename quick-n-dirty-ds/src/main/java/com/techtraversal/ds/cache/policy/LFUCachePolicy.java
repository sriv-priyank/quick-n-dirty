package com.techtraversal.ds.cache.policy;

import com.techtraversal.ds.cache.CacheNode;
import com.techtraversal.ds.cache.CachePolicy;


public final class LFUCachePolicy<K, V> extends CachePolicy<K, V> {

    private FrequencyNode<K, V> head, tail;
    private final int maxSize;
    private final boolean evict;


    public LFUCachePolicy(int maxSize) {
        this.maxSize = maxSize;
        this.evict = true;
    }


    ///////// API :: CachePolicy methods ///////////

    @Override
    protected final CacheNode<K, V> newNode(K key, V val) {
        LFUNode<K, V> node = new LFUNode<>(key, val);
        FrequencyNode<K, V> parent = this.head;
        if (parent == null) {
            parent = new FrequencyNode<>();
        }
        linkLastNode(parent, node);

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
        FrequencyNode<K, V> first = this.head;
        if (first == null) return null;

        return first.head;   // first node of least freq.
    }

    @Override
    protected final void afterNodeAccess(CacheNode<K, V> node) {
        LFUNode<K, V> p = (LFUNode<K, V>) node;
        FrequencyNode<K, V> oldParent = p.parent;
        FrequencyNode<K, V> parent;

        int freq = oldParent.freq + 1;
        if (freq < maxSize) {           // capping freq at maxsize
            parent = oldParent.next;
            if (parent == null) {
                parent = new FrequencyNode<>(freq);
                appendFrequencyNode(parent);
            }
            unlinkNode(oldParent, p);   // unlink from oldparent
            linkLastNode(parent, p);    // link to new parent
        } else {
            //lru behavior, keep in the old parent but move to tail
            if (p != oldParent.tail) {
                moveNodeToTail(oldParent, p);
            }
        }
    }

    @Override
    protected final void afterNodeRemoval(CacheNode<K, V> node) {
        LFUNode<K, V> p = (LFUNode<K, V>) node;
        FrequencyNode<K, V> parent = p.parent;
        unlinkNode(parent, p);
    }


    ///////////////// internal methods //////////////////

    private void moveNodeToTail(FrequencyNode<K, V> parent, LFUNode<K, V> node) {
        LFUNode<K, V> last = parent.tail;
        if (node.prev == null)
            parent.head = node.next;
        else
            node.prev.next = node.next;

        node.next.prev = node.prev;

        node.prev = last;
        last.next = node;
        parent.tail = node;
    }

    private void unlinkNode(FrequencyNode<K, V> parent, LFUNode<K, V> node) {
        if (node.prev == null)
            parent.head = node.next;
        else
            node.prev.next = node.next;

        if (node.next == null)
            parent.tail = node.prev;
        else
            node.next.prev = node.prev;

        node.prev = node.next = null;
    }

    private void linkLastNode(FrequencyNode<K, V> parent, LFUNode<K, V> node) {
        LFUNode<K, V> last = parent.tail;
        parent.tail = node;

        if (last == null) {
            parent.head = node;
        } else {
            node.prev = last;
            last.next = node;
        }
    }


    private void appendFrequencyNode(FrequencyNode<K, V> node) {
        FrequencyNode<K, V> last = this.tail;
        this.tail = node;

        if (last == null) {
            this.head = node;
        } else {
            last.next = node;
        }
    }


    ///////////// node definitions //////////////

    static final class FrequencyNode<K, V> {
        private int freq;
        private FrequencyNode next;
        private LFUNode<K, V> head, tail;

        public FrequencyNode() {
            this(0);
        }

        public FrequencyNode(int freq) {
            this.freq = freq;
        }
    }


    static final class LFUNode<K, V> implements CacheNode<K, V> {
        private K key;
        private V val;
        private LFUNode<K, V> prev, next;
        private FrequencyNode<K, V> parent;

        public LFUNode(K key, V val) {
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
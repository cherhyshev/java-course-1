package ru.spbau.mit;

import org.jetbrains.annotations.Nullable;

public final class DictionaryImpl implements Dictionary {
    private static final int DEFAULT_CAPACITY = 11;
    private static final double MAX_LOAD_FACTOR = 0.75;
    private static final double MIN_LOAD_FACTOR = 0.25;
    private static final int GROW_MEASURE = 2;

    private LinkedDictionaryNodeList[] buckets;
    private int notNullKeysQuantity;
    private int bucketsQuantity;

    DictionaryImpl() {
        clear();
    }


    @Override
    public int size() {
        return notNullKeysQuantity;
    }

    int getBucketsQuantity() {
        return bucketsQuantity;
    }

    @Override
    public boolean contains(@Nullable String key) {
        if (key == null){
            return false;
        }
        int bucketIndex = getBucketIndex(key, getBucketsQuantity());

        return buckets[bucketIndex].getNodeByKey(key) != null;
    }

    @Override
    public String get(@Nullable String key) {
        if (key == null){
            return null;
        }

        int bucketIndex = getBucketIndex(key, getBucketsQuantity());
        DictionaryNode foundedNode;

        foundedNode = buckets[bucketIndex].getNodeByKey(key);

        return foundedNode == null ? null : foundedNode.getValue();
    }

    @Override
    public String put(@Nullable String key, @Nullable String value) {
        if (key == null || value == null) {
            return null;
        }

        int bucketIndex = getBucketIndex(key, getBucketsQuantity());
        DictionaryNode containedNode = buckets[bucketIndex].getNodeByKey(key);
        if (containedNode != null) {
            return containedNode.setValue(value);
        }

        buckets[bucketIndex].addFirst(new DictionaryNode(key, value));
        notNullKeysQuantity++;
        if (calcLoadFactor() > MAX_LOAD_FACTOR) {
            rehash(getBucketsQuantity() * GROW_MEASURE);
        }
        return null;

    }

    @Override
    public String remove(@Nullable String key) {
        if (key == null){
            return null;
        }
        String removedValue = null;
        int bucketIndex = getBucketIndex(key, getBucketsQuantity());

        DictionaryNode containedNode = buckets[bucketIndex].getNodeByKey(key);
        if (containedNode != null) {
            removedValue = buckets[bucketIndex].removeByKey(key);
            notNullKeysQuantity--;

            if (calcLoadFactor() < MIN_LOAD_FACTOR) {
                int nextNumberOfBuckets = getBucketsQuantity() / GROW_MEASURE;
                if (DEFAULT_CAPACITY < nextNumberOfBuckets) {
                    rehash(nextNumberOfBuckets);
                }
            }
        }
        return removedValue;
    }

    @Override
    public void clear() {
        bucketsQuantity = DEFAULT_CAPACITY;
        buckets = new LinkedDictionaryNodeList[getBucketsQuantity()];
        for (int i = 0; i < getBucketsQuantity(); i++) {
            buckets[i] = new LinkedDictionaryNodeList();
        }
        notNullKeysQuantity = 0;
    }

    private void rehash(int nextNumberOfBuckets) {
        LinkedDictionaryNodeList[] newBuckets = new LinkedDictionaryNodeList[nextNumberOfBuckets];
        for (int i = 0; i < nextNumberOfBuckets; i++) {
            newBuckets[i] = new LinkedDictionaryNodeList();
        }

        for (int i = 0; i < getBucketsQuantity(); i++) {
            for (DictionaryNode curNode : buckets[i].listOfNodes) {
                if (curNode == null) {
                    continue;
                }
                String key = curNode.getKey();
                String value = curNode.getValue();
                int newBucketIdx = getBucketIndex(key, nextNumberOfBuckets);
                newBuckets[newBucketIdx].addFirst(new DictionaryNode(key, value));
                buckets[i].listOfNodes.remove(curNode);
            }
        }

        bucketsQuantity = nextNumberOfBuckets;
        buckets = newBuckets;
    }


    private int getBucketIndex(String key, int bucketsSummary) {
        return key == null ? 0 : Math.abs(key.hashCode()) % bucketsSummary;
    }

    private double calcLoadFactor() {
        return notNullKeysQuantity / (double) getBucketsQuantity();
    }
}
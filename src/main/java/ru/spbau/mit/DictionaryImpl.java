package ru.spbau.mit;

import org.jetbrains.annotations.Nullable;

public final class DictionaryImpl implements Dictionary {
    private static final int DEFAULT_CAPACITY = 5;
    private static final double MAX_LOAD_FACTOR = 0.75;
    private static final double MIN_LOAD_FACTOR = 0.25;
    private static final int GROW_MEASURE = 2;

    private DictionaryNode[] buckets = new DictionaryNode[DEFAULT_CAPACITY];
    private int notNullKeysSummary;
    private int bucketsSummary;

    DictionaryImpl() {
        clear();
    }


    @Override
    public int size() {
        return notNullKeysSummary;
    }

    public int getBucketsSummary() {
        return bucketsSummary;
    }

    private void setBucketsSummary(int newBucketSummary) {
        bucketsSummary = newBucketSummary;
    }

    @Override
    public boolean contains(@Nullable String key) {
        int bucketIndex = getBucketIndex(key, getBucketsSummary());
        return DictionaryNode.getNodeByKey(buckets[bucketIndex], key) != null;
    }

    @Override
    public String get(@Nullable String key) {
        int bucketIndex = getBucketIndex(key, getBucketsSummary());
        DictionaryNode foundedNode = DictionaryNode.getNodeByKey(buckets[bucketIndex], key);
        return (foundedNode == null) ? null : foundedNode.getValue();
    }

    @Override
    public String put(@Nullable String key, @Nullable String value) {
        if (key != null) {
            int bucketIndex = getBucketIndex(key, getBucketsSummary());
            DictionaryNode containedNode = DictionaryNode.getNodeByKey(buckets[bucketIndex], key);
            if (containedNode != null) {
                return containedNode.setValue(value);
            }
            buckets[bucketIndex] = DictionaryNode.addToHeadOfBucket(buckets[bucketIndex], new DictionaryNode(key, value));
            incNotNullKeysSummary();
            if (calcLoadFactor() > MAX_LOAD_FACTOR) {
                rehash(getBucketsSummary() * GROW_MEASURE);
            }
        }
        return null;

    }

    @Override
    public String remove(@Nullable String key) {
        int bucketIndex = getBucketIndex(key, getBucketsSummary());
        DictionaryNode containedNode = DictionaryNode.getNodeByKey(buckets[bucketIndex], key);
        if (containedNode != null) {
            buckets[bucketIndex] = DictionaryNode.removeByKey(buckets[bucketIndex], key);
            decNotNullKeysSummary();

            if (calcLoadFactor() < MIN_LOAD_FACTOR) {
                int nextNumberOfBuckets = getBucketsSummary() / GROW_MEASURE;
                if (DEFAULT_CAPACITY < nextNumberOfBuckets){
                    rehash(nextNumberOfBuckets);
                }
            }
        }
        return (containedNode == null) ? null : containedNode.getValue();
    }

    @Override
    public void clear() {
        setBucketsSummary(DEFAULT_CAPACITY);
        buckets = new DictionaryNode[getBucketsSummary()];
        notNullKeysSummary = 0;
    }

    private void rehash(int nextNumberOfBuckets) {
        DictionaryNode[] newBuckets = new DictionaryNode[nextNumberOfBuckets];

        for (int i = 0; i < getBucketsSummary(); i++) {
            while (buckets[i] != null) {
                DictionaryNode nextNode = buckets[i].getNext();
                DictionaryNode.setNext(null, buckets[i]);

                String key = buckets[i].getKey();
                int newBucketIdx = getBucketIndex(key, nextNumberOfBuckets);
                newBuckets[newBucketIdx] = DictionaryNode.addToHeadOfBucket(newBuckets[newBucketIdx], buckets[i]);

                buckets[i] = nextNode;
            }
        }

        setBucketsSummary(nextNumberOfBuckets);
        buckets = newBuckets;
    }


    private int getBucketIndex(@Nullable String key, int bucketsSummary) {
        return (key == null) ? 0 : Math.abs(key.hashCode()) % bucketsSummary;
    }

    private void incNotNullKeysSummary() {
        notNullKeysSummary++;
    }

    private void decNotNullKeysSummary() {
        notNullKeysSummary--;
    }

    private double calcLoadFactor() {
        return notNullKeysSummary / (double) getBucketsSummary();
    }
}
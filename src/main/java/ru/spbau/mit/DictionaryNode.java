package ru.spbau.mit;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class DictionaryNode {
    private final String key;
    private String value;
    private DictionaryNode next;

    public DictionaryNode(@Nullable String key, @Nullable String value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }


    public String getValue() {
        return value;
    }

    public String setValue(@Nullable String value) {
        String oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    public String getKey() {
        return key;
    }

    public DictionaryNode getNext() {
        return next;
    }

    public static void setNext(@Nullable DictionaryNode head, @Nullable DictionaryNode newHead) {
        assert newHead != null;
        newHead.next = head;
    }

    public static DictionaryNode getNodeByKey(@Nullable DictionaryNode head, @Nullable String key) {
        if (key != null) {
            for (DictionaryNode currentNode = head; currentNode != null; currentNode = currentNode.getNext()) {
                if (currentNode.getKey().equals(key)) {
                    return currentNode;
                }
            }
        }
        return null;
    }

    public static DictionaryNode addToHeadOfBucket(@Nullable DictionaryNode head, @Nullable DictionaryNode newHead) {
        assert newHead != null;
        setNext(head, newHead);
        return newHead;
    }

    public static DictionaryNode removeByKey(@Nullable DictionaryNode head, @Nullable String key) {
        if (head == null) {
            return null;
        }

        if (head.getKey().equals(key)) {
            return head.getNext();
        }

        DictionaryNode prevNode = head;
        DictionaryNode curNode = head.getNext();

        while (curNode != null) {
            if (curNode.getKey().equals(key)) {
                setNext(prevNode, curNode.getNext());
                break;
            }

            prevNode = curNode;
            curNode = curNode.getNext();
        }

        return head;
    }
}

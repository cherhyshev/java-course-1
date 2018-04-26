package ru.spbau.mit;

import java.util.LinkedList;

class LinkedDictionaryNodeList {
    LinkedList<DictionaryNode> listOfNodes;

    LinkedDictionaryNodeList() {
        listOfNodes = new LinkedList<DictionaryNode>();
    }


    void addFirst(DictionaryNode newHead) {
        listOfNodes.addFirst(newHead);
    }

    DictionaryNode getNodeByKey(String key) {
        if (key == null) {
            return null;
        }
        for (DictionaryNode currentNode : listOfNodes) {
            if (currentNode.getKey().equals(key)) {
                return currentNode;
            }
        }
        return null;
    }


    String removeByKey(String key) {
        if (key == null) {
            return null;
        }
        if (listOfNodes.getFirst() == null) {
            return null;
        }

        DictionaryNode foundedNode = getNodeByKey(key);

        if (foundedNode == null) {
            return null;
        }
        String removedValue = foundedNode.getValue();
        listOfNodes.remove(foundedNode);
        return removedValue;
    }

}

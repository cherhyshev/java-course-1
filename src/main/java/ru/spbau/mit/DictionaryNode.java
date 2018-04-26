package ru.spbau.mit;

class DictionaryNode {
    private final String key;
    private String value;

    DictionaryNode(String key, String value) {
        this.key = key;
        this.value = value;
    }


    String getValue() {
        return value;
    }

    String setValue(String value) {
        String oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    String getKey() {
        return key;
    }

}

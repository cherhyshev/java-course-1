package ru.spbau.mit;

import java.util.HashMap;
import java.util.Map;

class TrieVertex {
    private boolean isTerminal;
    private int sumTerminal;
    private final Map<Character, TrieVertex> hashMap;

    TrieVertex() {
        isTerminal = false;
        sumTerminal = 0;
        hashMap = new HashMap<>();
    }

    TrieVertex addAndIter(char letter) {
        TrieVertex newVertex = new TrieVertex();
        hashMap.put(letter, newVertex);
        return newVertex;
    }

    Map<Character, TrieVertex> getHashMap() {
        return hashMap;
    }

    boolean isTerminal() {
        return isTerminal;
    }

    void setTerminal(boolean state) {
        isTerminal = state;
    }

    void sumTerminalInc() {
        sumTerminal += 1;
    }

    void sumTerminalDec() {
        sumTerminal -= 1;
    }

    int getSumTerminal() {
        return sumTerminal;
    }

    void removeVertex(char letter) {
        hashMap.remove(letter);
    }

}
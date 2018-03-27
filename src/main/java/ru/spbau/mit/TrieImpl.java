package ru.spbau.mit;

import java.util.Map;

import org.jetbrains.annotations.Nullable;

public final class TrieImpl implements Trie {

    private final TrieVertex root;

    TrieImpl() {
        root = new TrieVertex();
    }

    public TrieImpl(@Nullable String element) {
        root = new TrieVertex();
        add(element);
    }

    public TrieImpl(Iterable<String> elements) {
        root = new TrieVertex();
        for (String word : elements) {
            add(word);
        }
    }

    @Override
    public boolean add(@Nullable String element) {
        TrieVertex currentVertex = root;
        if (element == null || element.equals("") || contains(element)) {
            return false;
        }
        for (char symb : element.toCharArray()) {
            currentVertex.sumTerminalInc();
            Map<Character, TrieVertex> hashMap = currentVertex.getHashMap();
            if (hashMap.containsKey(symb)) {
                currentVertex = hashMap.get(symb);
            } else {
                currentVertex = currentVertex.addAndIter(symb);
            }
        }
        currentVertex.setTerminal(true);
        currentVertex.sumTerminalInc();
        return true;
    }

    @Override
    public boolean contains(@Nullable String element) {
        if (element == null || element.equals("")) {
            return false;
        }

        TrieVertex currentVertex = root;
        for (char symb : element.toCharArray()) {
            Map<Character, TrieVertex> hashMap = currentVertex.getHashMap();
            if (!hashMap.containsKey(symb)) {
                return false;
            }
            currentVertex = hashMap.get(symb);
        }
        return currentVertex.isTerminal();
    }

    @Override
    public boolean remove(@Nullable String element) {
        if (!contains(element)) {
            return false;
        }

        TrieVertex currentVertex = root;
        for (char symb : element.toCharArray()) {
            currentVertex.sumTerminalDec();
            Map<Character, TrieVertex> hashMap = currentVertex.getHashMap();
            currentVertex = hashMap.get(symb);
        }
        currentVertex.setTerminal(false);
        currentVertex.sumTerminalDec();
        if (currentVertex.getSumTerminal() == 0) {
            for (char key : currentVertex.getHashMap().keySet()) {
                currentVertex.removeVertex(key);
            }
        }
        return true;
    }

    @Override
    public int size() {
        return root.getSumTerminal();
    }

    @Override
    public int howManyStartsWithPrefix(@Nullable String prefix) {
        if (size() == 0 || prefix == null) {
            return 0;
        }
        if (prefix.equals("")) {
            return size(); // or maybe 0? I think this is wright way
        }
        TrieVertex currentVertex = root;
        for (char symb : prefix.toCharArray()) {
            Map<Character, TrieVertex> hashMap = currentVertex.getHashMap();
            currentVertex = hashMap.get(symb);
        }
        return currentVertex.getSumTerminal();
    }
}
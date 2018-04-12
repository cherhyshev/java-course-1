package ru.spbau.mit;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jetbrains.annotations.Nullable;

public final class TrieImpl implements Trie, StreamSerializable {

    private final TrieVertex root;
    private static final Pattern englishAlphabet = Pattern.compile("[a-zA-Z]+$");


    @Override
    public void serialize(OutputStream out) throws IOException {
        root.serialize(out);
    }

    @Override
    public void deserialize(InputStream in) throws IOException {
        root.deserialize(in);
    }

    private class TrieVertex implements StreamSerializable {

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

        @Override
        public void serialize(OutputStream out) throws IOException {
            DataOutputStream dataOut = new DataOutputStream(out);
            dataOut.writeBoolean(isTerminal());
            dataOut.writeInt(getSumTerminal());
            dataOut.writeInt(getHashMap().size());

            for (Map.Entry<Character, TrieVertex> entry : getHashMap().entrySet()) {
                dataOut.writeChar(entry.getKey());
                entry.getValue().serialize(out);
            }
            dataOut.flush();
        }

        @Override
        public void deserialize(InputStream in) throws IOException {
            DataInputStream dataIn = new DataInputStream(in);
            setTerminal(dataIn.readBoolean());

            int sumTerm = dataIn.readInt();
            for (int i = 0; i < sumTerm; i++) {
                sumTerminalInc();
            }

            int hashMapSize = dataIn.readInt();
            for (int i = 0; i < hashMapSize; i++) {
                char readChar = dataIn.readChar();
                TrieVertex newChild = new TrieVertex();
                newChild.deserialize(in);
                getHashMap().put(readChar, newChild);
            }
        }
    }

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

    private Map<Character, TrieVertex> getHashMap(TrieVertex currentVertex) {
        return currentVertex.getHashMap();
    }

    @Override
    public boolean add(@Nullable String element) {
        TrieVertex currentVertex = root;
        if (element == null || element.equals("") || contains(element)) {
            return false;
        }
        for (char symb : element.toCharArray()) {
            currentVertex.sumTerminalInc();
            if (getHashMap(currentVertex).containsKey(symb)) {
                currentVertex = getHashMap(currentVertex).get(symb);
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
            if (!getHashMap(currentVertex).containsKey(symb)) {
                return false;
            }
            currentVertex = getHashMap(currentVertex).get(symb);
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
            currentVertex = getHashMap(currentVertex).get(symb);
        }
        currentVertex.setTerminal(false);
        currentVertex.sumTerminalDec();

        currentVertex = root;
        for (char symb : element.toCharArray()) {
            if (currentVertex.getSumTerminal() == 0) {
                for (char key : getHashMap(currentVertex).keySet()) {
                    currentVertex.removeVertex(key);
                }
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
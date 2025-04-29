package edu.ntnu.idatt2003.idatt2003boardgame.util;

import java.util.Iterator;
import java.util.List;

public class LoopingIterator<Item> implements Iterator<Item> {
    private final List<Item> list;
    private int index = 0;

    public LoopingIterator(List<Item> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List must not be null or empty");
        }
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return true; 
    }

    @Override
    public Item next() {
        Item element = list.get(index);
        index = (index + 1) % list.size(); 
        return element;
    }
}

package edu.ntnu.idatt2003.idatt2003boardgame.util;

import java.util.Iterator;
import java.util.List;

/**
 * An iterator that loops indefinitely over a given list of items.
 * When the end of the list is reached, the iterator wraps around to the beginning.
 * This iterator always reports that there is a next element.
 *
 * @param <Item> The type of items this iterator will iterate over.
 * 
 * @author Hector Mendana Morales
 */
public class LoopingIterator<Item> implements Iterator<Item> {
    private final List<Item> list;
    private int index = 0;

    /**
     * Constructs a new LoopingIterator for the specified list.
     * The list must not be null or empty.
     *
     * @param list The list of items to iterate over in a loop.
     * @throws IllegalArgumentException if the provided list is null or empty.
     */
    public LoopingIterator(List<Item> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List must not be null or empty");
        }
        this.list = list;
    }

    /**
     * Checks if there is another element in the iteration.
     * For a {@code LoopingIterator}, this method will always return {@code true}
     * as the iteration is circular and endless.
     *
     * @return always {@code true}.
     */
    @Override
    public boolean hasNext() {
        return true;
    }

    /**
     * Returns the next element in the iteration.
     * If the end of the list is reached, it wraps around to the first element.
     *
     * @return the next element in the list.
     */
    @Override
    public Item next() {
        Item element = list.get(index);
        index = (index + 1) % list.size();
        return element;
    }
}
package org.cis120;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Produces deterministic numbers by iterating repeatedly over a list or array
 * of integers argued in the constructors.
 */
public class ListNumberGenerator implements NumberGenerator {

    final List<Integer> list;
    int index = 0;
    int smallestUpperBound = Integer.MAX_VALUE;

    /**
     * Creates a ListNumberGenerator which generates numbers based off of a list
     * of possible integers.
     *
     * @param list - an list of integers to step through and output in the
     *             generator.
     */
    public ListNumberGenerator(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException(
                    "list must be non-null and " +
                            "non-empty"
            );
        }
        this.list = new LinkedList<Integer>();
        for (int i : list) {
            if (i < smallestUpperBound) {
                smallestUpperBound = i;
            }
        }
        this.list.addAll(list);
    }

    /**
     * Creates a ListNumberGenerator which generates numbers based off of an
     * array of possible integers.
     *
     * @param arr - an array of integers to step through and output in the
     *            generator.
     */
    public ListNumberGenerator(Integer[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("array must be non-null");
        }
        this.list = new LinkedList<Integer>();
        for (int i : arr) {
            if (i < smallestUpperBound) {
                smallestUpperBound = i;
            }
        }
        this.list.addAll(Arrays.asList(arr));
    }

    /**
     * Returns the next integer available in the list.
     *
     * @return an unbounded number from the list.
     */
    public int next() {
        int next = list.get(index++);
        if (index == list.size()) {
            index = 0;
        }
        return next;
    }

    /**
     * Returns the next integer available in the list that is less than the
     * specified bound.
     *
     * @param bound - the max value that can be returned by this call to next.
     * @return a number between 0 and bound (inclusive)
     */
    public int next(int bound) {
        if (bound <= smallestUpperBound) {
            throw new NoSuchElementException(
                    "The list contains no elements "
                            + "greater than or equal to the argued bound"
            );
        }

        int next;
        while ((next = next()) >= bound) {
            continue;
        }
        return next;
    }

}

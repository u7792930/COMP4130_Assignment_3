package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.ValuesIterator;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestValuesIterator_next_1 {

    @Test
    public void test_next_iteratorHasNext() {
        ValuesIterator<Integer> iterator = new ValuesIterator<>(/* initialize with a suitable map */);
        // Assume the iterator has elements
        Assert.assertTrue(iterator.hasNext());
        Integer value = iterator.next();
        // Add assertions to verify the returned value
    }

    @Test
    public void test_next_noElements() {
        ValuesIterator<Integer> iterator = new ValuesIterator<>(/* initialize with an empty map */);
        try {
            iterator.next();
            Assert.fail("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }

    @Test
    public void test_next_afterExhaustingIterator() {
        ValuesIterator<Integer> iterator = new ValuesIterator<>(/* initialize with a suitable map */);
        while (iterator.hasNext()) {
            iterator.next(); // Consume all elements
        }
        try {
            iterator.next();
            Assert.fail("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
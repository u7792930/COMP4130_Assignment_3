package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.util.Iterator;
import java.util.Collections;
import static org.junit.Assert.*;

public class TestValuesIterator_next_NullEmpty_1 {

    private static class TestValuesIterator<V> {
        private Iterator<V> iterator;

        public TestValuesIterator(Iterator<V> iterator) {
            this.iterator = iterator;
        }

        public V next() {
            return iterator.next();
        }
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void test_next_emptyIterator() {
        TestValuesIterator<Object> emptyIterator = new TestValuesIterator<>(Collections.emptyIterator());
        emptyIterator.next(); // This should throw NoSuchElementException
    }

    @Test
    public void test_next_singleElementIterator() {
        TestValuesIterator<String> singleElementIterator = new TestValuesIterator<>(Collections.singleton("value").iterator());
        String result = singleElementIterator.next();
        Assert.assertEquals("value", result); // Validate the returned value
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void test_next_afterEndOfIterator() {
        TestValuesIterator<String> singleElementIterator = new TestValuesIterator<>(Collections.singleton("value").iterator());
        singleElementIterator.next(); // Move to the first element
        singleElementIterator.next(); // This should throw NoSuchElementException
    }
}
package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.junit.Assert.*;

public class TestValuesIterator_next_NormalFlow_1 {
    
    private class TestValuesIterator<V> implements Iterator<V> {
        private final Iterator<V> iterator;

        public TestValuesIterator(Iterator<V> iterator) {
            this.iterator = iterator;
        }
        
        public boolean hasNext() {
            return iterator.hasNext();
        }

        public V next() {
            return iterator.next();
        }
    }

    @Test
    public void test_next_validElement() {
        Iterator<String> stringIterator = java.util.Arrays.asList("one", "two", "three").iterator();
        TestValuesIterator<String> valuesIterator = new TestValuesIterator<>(stringIterator);
        
        Assert.assertEquals("one", valuesIterator.next());
        Assert.assertEquals("two", valuesIterator.next());
    }

    @Test
    public void test_next_multipleCalls() {
        Iterator<Integer> integerIterator = java.util.Arrays.asList(1, 2, 3, 4).iterator();
        TestValuesIterator<Integer> valuesIterator = new TestValuesIterator<>(integerIterator);

        Assert.assertEquals(Integer.valueOf(1), valuesIterator.next());
        Assert.assertEquals(Integer.valueOf(2), valuesIterator.next());
        Assert.assertEquals(Integer.valueOf(3), valuesIterator.next());
    }

    @Test
    public void test_next_noMoreElements() {
        Iterator<String> stringIterator = java.util.Collections.<String>emptyList().iterator();
        TestValuesIterator<String> valuesIterator = new TestValuesIterator<>(stringIterator);

        try {
            valuesIterator.next();
            Assert.fail("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
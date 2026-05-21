package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;
import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TestValuesIterator_next_BoundaryValues_1 {

    private class TestValuesIterator<V> implements Iterator<V> {
        private final Iterator<V> iterator;

        public TestValuesIterator(Iterator<V> iterator) {
            this.iterator = iterator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public V next() {
            return iterator.next();
        }
    }

    @Test
    public void test_next_singleElement() {
        Iterator<String> singleElementIterator = Collections.singletonList("A").iterator();
        TestValuesIterator<String> valuesIterator = new TestValuesIterator<>(singleElementIterator);
        
        String result = valuesIterator.next();
        Assert.assertEquals("A", result);
    }

    @Test
    public void test_next_emptyIterator() {
        Iterator<String> emptyIterator = Collections.<String>emptyList().iterator();
        TestValuesIterator<String> valuesIterator = new TestValuesIterator<>(emptyIterator);
        
        try {
            valuesIterator.next();
            Assert.fail("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }

    @Test
    public void test_next_boundaryValue() {
        Iterator<Integer> boundaryValueIterator = Collections.singletonList(Integer.MAX_VALUE).iterator();
        TestValuesIterator<Integer> valuesIterator = new TestValuesIterator<>(boundaryValueIterator);
        
        Integer result = valuesIterator.next();
        Assert.assertEquals(Integer.MAX_VALUE, result.intValue());
    }
}
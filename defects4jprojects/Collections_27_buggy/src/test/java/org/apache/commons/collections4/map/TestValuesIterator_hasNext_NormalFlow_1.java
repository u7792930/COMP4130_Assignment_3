package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;
import java.util.Iterator;
import org.apache.commons.collections4.IteratorUtils;

public class TestValuesIterator_hasNext_NormalFlow_1 {

    private final Iterator<Object> valuesIterator = createMockIteratorWithElements();

    @Test
    public void test_hasNext_whenIteratorHasNext_returnsTrue() {
        // Arrange
        // Act
        boolean result = valuesIterator.hasNext();

        // Assert
        Assert.assertTrue(result);
    }

    @Test
    public void test_hasNext_whenIteratorIsEmpty_returnsFalse() {
        // Arrange
        Iterator<Object> emptyIterator = createMockEmptyIterator();
        // Act
        boolean result = emptyIterator.hasNext();

        // Assert
        Assert.assertFalse(result);
    }

    @Test
    public void test_hasNext_whenIteratorIsModified_returnsExpectedValue() {
        // Arrange
        Iterator<Object> multipleElementsIterator = createMockIteratorWithMultipleElements();
        multipleElementsIterator.next(); // Move to the first element

        // Act
        boolean result = multipleElementsIterator.hasNext(); // Should return true as elements still exist

        // Assert
        Assert.assertTrue(result);
    }

    // Mock methods to create iterators
    private Iterator<Object> createMockIteratorWithElements() {
        // Return an iterator with elements
        return new Iterator<Object>() {
            private int count = 0;
            private final int max = 3;

            @Override
            public boolean hasNext() {
                return count < max;
            }

            @Override
            public Object next() {
                return count++;
            }
        };
    }

    private Iterator<Object> createMockEmptyIterator() {
        // Return an empty iterator
        return new Iterator<Object>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Object next() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private Iterator<Object> createMockIteratorWithMultipleElements() {
        // Return an iterator with multiple elements
        return new Iterator<Object>() {
            private int count = 0;
            private final int max = 5;

            @Override
            public boolean hasNext() {
                return count < max;
            }

            @Override
            public Object next() {
                return count++;
            }
        };
    }
}
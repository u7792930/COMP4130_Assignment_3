package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.map.MultiValueMap;

public class TestValuesIterator_remove_BoundaryValues_1 {

    private MultiValueMap<String, String> multiValueMap;

    public TestValuesIterator_remove_BoundaryValues_1() {
        multiValueMap = new MultiValueMap<>();
        multiValueMap.put("key", "value");
    }

    @Test(expected = IllegalStateException.class)
    public void test_remove_noPreviousCallToNext() {
        Iterator<?> iterator = multiValueMap.values().iterator();
        iterator.remove(); // Should throw IllegalStateException as next() was not called
    }

    @Test
    public void test_remove_singleElementCollection() {
        Iterator<?> iterator = multiValueMap.values().iterator();
        iterator.next(); // Move to the first element
        iterator.remove(); // Removes the only element
        Assert.assertTrue(multiValueMap.isEmpty()); // After removal, the map should be empty
    }

    @Test
    public void test_remove_nonEmptyValuesList() {
        multiValueMap.put("key2", "value2");
        Iterator<?> iterator = multiValueMap.values().iterator();
        iterator.next(); // Move to the first element
        iterator.remove(); // Removes the first element
        Assert.assertFalse(multiValueMap.isEmpty()); // The map should still contain elements
    }

    @Test
    public void test_remove_lastElementCollection() {
        multiValueMap.put("key3", "value3");
        Iterator<?> iterator = multiValueMap.values().iterator();
        while (iterator.hasNext()) {
            iterator.next(); // Move to the last element
        }
        iterator.remove(); // Removes the last element
        Assert.assertFalse(multiValueMap.isEmpty()); // The map should still contain elements
    }

    @Test
    public void test_remove_emptyValuesList() {
        multiValueMap.clear(); // Ensure the map is empty
        Iterator<?> iterator = multiValueMap.values().iterator();
        Assert.assertFalse(iterator.hasNext()); // Ensure iterator is empty
        try {
            iterator.remove(); // Should throw IllegalStateException as next() was not called
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
package org.apache.commons.collections4.map;

import org.apache.commons.collections4.IteratorUtils;
import org.junit.Assert;
import org.junit.Test;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class TestValuesIterator_remove_1 {

    @Test
    public void test_remove_emptyValuesIterator() {
        Map<String, String> map = new HashMap<>();
        Iterator<String> valuesIterator = IteratorUtils.emptyIterator();
        try {
            valuesIterator.remove();
            Assert.fail("Expected an IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }

    @Test
    public void test_remove_nonEmptyValuesIterator() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        Iterator<String> valuesIterator = map.values().iterator();
        valuesIterator.next(); // Move to the first element
        valuesIterator.remove(); // Should remove the current element
        Assert.assertFalse(map.containsValue("value1")); // Verify the expected state after removal
    }

    @Test
    public void test_remove_afterNext() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        Iterator<String> valuesIterator = map.values().iterator();
        valuesIterator.next(); // Move to the first element
        valuesIterator.remove(); // Should remove the current element
        Assert.assertTrue(valuesIterator.hasNext()); // Should still have more elements
        Assert.assertFalse(map.containsValue("value1")); // Verify the state of the underlying collection
    }
}
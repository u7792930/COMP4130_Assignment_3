package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.MultiValueMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;
import static org.junit.Assert.*;

public class TestMultiValueMap_iterator_2 {

    private final MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_iterator_emptyMap() {
        Iterator<Map.Entry<String, String>> iterator = multiValueMap.iterator();
        Assert.assertFalse("Iterator should be empty for an empty map", iterator.hasNext());
    }

    @Test
    public void test_iterator_singleEntry() {
        multiValueMap.put("key1", "value1");
        Iterator<Map.Entry<String, String>> iterator = multiValueMap.iterator();
        Assert.assertTrue("Iterator should have elements after adding an entry", iterator.hasNext());
        Map.Entry<String, String> entry = iterator.next();
        Assert.assertEquals("key1", entry.getKey());
        Assert.assertEquals("value1", entry.getValue());
    }

    @Test
    public void test_iterator_multipleEntries() {
        multiValueMap.put("key1", "value1");
        multiValueMap.put("key1", "value2"); // Adding multiple values for the same key
        multiValueMap.put("key2", "value3");

        Iterator<Map.Entry<String, String>> iterator = multiValueMap.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        Assert.assertEquals("Iterator should return three entries", 3, count);
    }
}
package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Collection;
import static org.junit.Assert.*;

public class TestMultiValueMap_iterator_BoundaryValues_2 {

    private MultiValueMap<String, String> multiValueMap;

    @org.junit.Before
    public void setUp() {
        multiValueMap = new MultiValueMap<>();
    }

    @Test
    public void test_iterator_singleElement() {
        multiValueMap.put("key1", "value1");
        Iterator<Entry<String, String>> iterator = multiValueMap.iterator();
        Assert.assertTrue(iterator.hasNext());
        Entry<String, String> entry = iterator.next();
        Assert.assertEquals("key1", entry.getKey());
        Assert.assertEquals("value1", entry.getValue());
    }

    @Test
    public void test_iterator_multipleValuesForSameKey() {
        multiValueMap.put("key1", "value1");
        multiValueMap.put("key1", "value2");
        Iterator<Entry<String, String>> iterator = multiValueMap.iterator();
        Assert.assertTrue(iterator.hasNext());
        Entry<String, String> entry1 = iterator.next();
        Assert.assertEquals("key1", entry1.getKey());
        Assert.assertTrue(iterator.hasNext());
        Entry<String, String> entry2 = iterator.next();
        Assert.assertEquals("key1", entry2.getKey());
        Assert.assertTrue(entry2.getValue().equals("value1") || entry2.getValue().equals("value2"));
    }

    @Test
    public void test_iterator_emptyMap() {
        Iterator<Entry<String, String>> iterator = multiValueMap.iterator();
        Assert.assertFalse(iterator.hasNext());
    }
}
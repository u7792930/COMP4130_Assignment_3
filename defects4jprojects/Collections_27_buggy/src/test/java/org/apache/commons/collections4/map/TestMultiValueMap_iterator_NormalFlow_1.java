package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class TestMultiValueMap_iterator_NormalFlow_1 {

    @Test
    public void test_iterator_validKey() {
        MultiValueMap<Object, String> map = new MultiValueMap<>();
        List<String> values = new ArrayList<>();
        values.add("value1");
        values.add("value2");
        map.put("key1", values);

        Iterator<String> iterator = map.iterator("key1");
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("value1", iterator.next());
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("value2", iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void test_iterator_keyWithEmptyCollection() {
        MultiValueMap<Object, String> map = new MultiValueMap<>();
        map.put("key2", new ArrayList<String>()); // key with empty collection

        Iterator<String> iterator = map.iterator("key2");
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void test_iterator_keyNotInMap() {
        MultiValueMap<Object, String> map = new MultiValueMap<>();

        Iterator<String> iterator = map.iterator("key3"); // key not in map
        Assert.assertFalse(iterator.hasNext());
    }
}
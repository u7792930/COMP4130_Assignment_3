package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Collection;

public class TestMultiValueMap_containsValue_BoundaryValues_2 {

    @Test
    public void test_containsValue_singleElementCollection() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        Collection<String> values = new ArrayList<>();
        values.add("test");
        map.put("key", values);
        
        Assert.assertTrue(map.containsValue("key", "test"));
        Assert.assertFalse(map.containsValue("key", "notPresent"));
    }

    @Test
    public void test_containsValue_emptyCollection() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key", new ArrayList<>());
        
        Assert.assertFalse(map.containsValue("key", "test"));
    }

    @Test
    public void test_containsValue_nullKey() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        Collection<String> values = new ArrayList<>();
        values.add("test");
        map.put(null, values);
        
        Assert.assertTrue(map.containsValue(null, "test"));
        Assert.assertFalse(map.containsValue(null, "notPresent"));
    }

    @Test
    public void test_containsValue_keyNotPresent() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key", new ArrayList<>());
        
        Assert.assertFalse(map.containsValue("notPresent", "test"));
    }

    @Test
    public void test_containsValue_nullValue() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        Collection<String> values = new ArrayList<>();
        values.add(null);
        map.put("key", values);
        
        Assert.assertTrue(map.containsValue("key", null));
        Assert.assertFalse(map.containsValue("key", "test"));
    }
}
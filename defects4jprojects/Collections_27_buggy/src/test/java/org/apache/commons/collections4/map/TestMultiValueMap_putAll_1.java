package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;
import org.apache.commons.collections4.map.MultiValueMap;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import static org.junit.Assert.*;

public class TestMultiValueMap_putAll_1 {

    @Test
    public void test_putAll_withNormalMap() {
        MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();
        Map<String, String> normalMap = new HashMap<>();
        normalMap.put("key1", "value1");
        normalMap.put("key2", "value2");
        
        multiValueMap.putAll(normalMap);
        
        Assert.assertEquals(2, multiValueMap.size());
        Assert.assertTrue(multiValueMap.containsKey("key1"));
        Assert.assertTrue(multiValueMap.containsKey("key2"));
        Assert.assertEquals("value1", multiValueMap.get("key1").iterator().next());
        Assert.assertEquals("value2", multiValueMap.get("key2").iterator().next());
    }

    @Test
    public void test_putAll_withMultiMap() {
        MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();
        MultiValueMap<String, String> anotherMultiMap = new MultiValueMap<>();
        anotherMultiMap.put("key1", "value1");
        anotherMultiMap.put("key1", "value2");
        
        multiValueMap.putAll(anotherMultiMap);
        
        Assert.assertEquals(1, multiValueMap.size());
        Assert.assertTrue(multiValueMap.containsKey("key1"));
        Collection<String> values = multiValueMap.get("key1");
        Assert.assertEquals(2, values.size());
        Assert.assertTrue(values.contains("value1"));
        Assert.assertTrue(values.contains("value2"));
    }

    @Test
    public void test_putAll_withNullMap() {
        MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();
        
        try {
            multiValueMap.putAll(null);
            Assert.fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("The map must not be null", e.getMessage());
        }
    }
}
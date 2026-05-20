package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.MultiValueMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TestMultiValueMap_multiValueMap_1 {

    @Test
    public void test_multiValueMap_nonNullMap() {
        Map<String, ? super Collection<String>> inputMap = new HashMap<>();
        MultiValueMap<String, String> result = MultiValueMap.multiValueMap(inputMap);
        Assert.assertNotNull(result);
    }

    @Test
    public void test_multiValueMap_emptyMap() {
        Map<String, ? super Collection<String>> inputMap = new HashMap<>();
        MultiValueMap<String, String> result = MultiValueMap.multiValueMap(inputMap);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void test_multiValueMap_nullMap() {
        try {
            MultiValueMap.multiValueMap(null);
            Assert.fail("Expected NullPointerException for null map");
        } catch (NullPointerException e) {
            // expected
        }
    }

    @Test
    public void test_multiValueMap_withValues() {
        Map<String, Collection<String>> inputMap = new HashMap<>();
        inputMap.put("key1", new ArrayList<String>());
        MultiValueMap<String, String> result = MultiValueMap.multiValueMap(inputMap);
        
        result.put("key1", "value1");
        result.put("key1", "value2");
        
        Assert.assertEquals(2, result.getCollection("key1").size());
        Assert.assertTrue(result.getCollection("key1").contains("value1"));
        Assert.assertTrue(result.getCollection("key1").contains("value2"));
    }

    @Test
    public void test_multiValueMap_withMultipleKeys() {
        Map<String, Collection<String>> inputMap = new HashMap<>();
        inputMap.put("key1", new ArrayList<String>());
        inputMap.put("key2", new ArrayList<String>());
        MultiValueMap<String, String> result = MultiValueMap.multiValueMap(inputMap);
        
        result.put("key1", "value1");
        result.put("key2", "value2");
        
        Assert.assertEquals(1, result.getCollection("key1").size());
        Assert.assertEquals(1, result.getCollection("key2").size());
        Assert.assertTrue(result.getCollection("key1").contains("value1"));
        Assert.assertTrue(result.getCollection("key2").contains("value2"));
    }
}
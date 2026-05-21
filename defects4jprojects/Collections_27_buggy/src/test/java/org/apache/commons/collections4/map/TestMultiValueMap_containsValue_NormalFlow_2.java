package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;
import org.apache.commons.collections4.map.MultiValueMap;

public class TestMultiValueMap_containsValue_NormalFlow_2 {

    private MultiValueMap<String, String> multiValueMap;

    public TestMultiValueMap_containsValue_NormalFlow_2() {
        multiValueMap = new MultiValueMap<>();
        multiValueMap.put("key1", "value1");
        multiValueMap.put("key1", "value2");
        multiValueMap.put("key2", "value3");
    }

    @Test
    public void test_containsValue_valueExists() {
        boolean result = multiValueMap.containsValue("key1", "value1");
        Assert.assertTrue(result);
    }

    @Test
    public void test_containsValue_valueDoesNotExist() {
        boolean result = multiValueMap.containsValue("key1", "value3");
        Assert.assertFalse(result);
    }

    @Test
    public void test_containsValue_multipleValues() {
        boolean result = multiValueMap.containsValue("key1", "value2");
        Assert.assertTrue(result);
    }
}
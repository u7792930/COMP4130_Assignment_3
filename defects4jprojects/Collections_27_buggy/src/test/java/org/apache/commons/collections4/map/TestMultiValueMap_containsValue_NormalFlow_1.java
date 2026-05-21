package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.map.MultiValueMap;

public class TestMultiValueMap_containsValue_NormalFlow_1 {

    private MultiValueMap<String, String> multiValueMap;

    public TestMultiValueMap_containsValue_NormalFlow_1() {
        multiValueMap = new MultiValueMap<>();
        multiValueMap.put("key1", "value1");
        multiValueMap.put("key2", "value2");
        multiValueMap.put("key2", "value3");
    }

    @Test
    public void test_containsValue_valueExists() {
        Assert.assertTrue(multiValueMap.containsValue("value1"));
    }

    @Test
    public void test_containsValue_multipleValuesForKey() {
        Assert.assertTrue(multiValueMap.containsValue("value3"));
    }

    @Test
    public void test_containsValue_valueDoesNotExist() {
        Assert.assertFalse(multiValueMap.containsValue("value4"));
    }
}
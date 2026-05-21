package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Arrays;

public class TestMultiValueMap_containsValue_BoundaryValues_1 {

    private MultiValueMap<String, String> multiValueMap;

    public TestMultiValueMap_containsValue_BoundaryValues_1() {
        multiValueMap = new MultiValueMap<>();
    }

    @Test
    public void test_containsValue_singleCharacterString() {
        multiValueMap.put("a", Arrays.asList("value1", "value2"));
        Assert.assertTrue(multiValueMap.containsValue("value1"));
        Assert.assertFalse(multiValueMap.containsValue("v"));
    }

    @Test
    public void test_containsValue_emptyCollection() {
        multiValueMap.put("key1", Collections.emptyList());
        Assert.assertFalse(multiValueMap.containsValue("value"));
    }

    @Test
    public void test_containsValue_multipleValues() {
        multiValueMap.put("key1", Arrays.asList("value1", "value2"));
        multiValueMap.put("key2", Arrays.asList("value3", "value4"));
        Assert.assertTrue(multiValueMap.containsValue("value2"));
        Assert.assertFalse(multiValueMap.containsValue("value5"));
    }

    @Test
    public void test_containsValue_maximumIntegerValue() {
        multiValueMap.put("maxInt", Collections.singletonList(String.valueOf(Integer.MAX_VALUE)));
        Assert.assertTrue(multiValueMap.containsValue(String.valueOf(Integer.MAX_VALUE)));
        Assert.assertFalse(multiValueMap.containsValue(String.valueOf(Integer.MAX_VALUE - 1)));
    }
}
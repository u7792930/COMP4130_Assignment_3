package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.MultiValueMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.*;

public class TestMultiValueMap_containsValue_1 {

    private final MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_containsValue_valuePresent() {
        multiValueMap.put("key1", "value1");
        multiValueMap.put("key2", "value2");

        boolean result = multiValueMap.containsValue("value1");

        Assert.assertTrue(result);
    }

    @Test
    public void test_containsValue_valueNotPresent() {
        multiValueMap.put("key1", "value1");
        multiValueMap.put("key2", "value2");

        boolean result = multiValueMap.containsValue("value3");

        Assert.assertFalse(result);
    }

    @Test
    public void test_containsValue_nullValue() {
        multiValueMap.put("key1", "value1");
        multiValueMap.put("key2", null);

        boolean result = multiValueMap.containsValue(null);

        Assert.assertTrue(result);
    }
}
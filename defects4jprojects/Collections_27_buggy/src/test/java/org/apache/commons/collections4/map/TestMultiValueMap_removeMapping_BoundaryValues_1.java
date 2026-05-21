package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class TestMultiValueMap_removeMapping_BoundaryValues_1 {

    private final MultiValueMap<Object, Object> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_removeMapping_keyNotPresent() {
        boolean result = multiValueMap.removeMapping("nonexistentKey", "value");
        Assert.assertFalse(result);
    }

    @Test
    public void test_removeMapping_valueNotPresent() {
        multiValueMap.put("key", "value1");
        boolean result = multiValueMap.removeMapping("key", "value2");
        Assert.assertFalse(result);
    }

    @Test
    public void test_removeMapping_singleValueRemoved() {
        multiValueMap.put("key", "value");
        boolean result = multiValueMap.removeMapping("key", "value");
        Assert.assertTrue(result);
        Assert.assertFalse(multiValueMap.containsKey("key"));
    }
}
package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import static org.junit.Assert.*;

public class TestMultiValueMap_removeMapping_NullEmpty_1 {

    private final MultiValueMap<Object, Object> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_removeMapping_nullKey() {
        boolean result = multiValueMap.removeMapping(null, "value");
        Assert.assertFalse(result);
    }

    @Test
    public void test_removeMapping_nullValue() {
        multiValueMap.put("key", "value");
        boolean result = multiValueMap.removeMapping("key", null);
        Assert.assertFalse(result);
    }

    @Test
    public void test_removeMapping_emptyCollection() {
        multiValueMap.put("key", new HashSet<>()); // Adding an empty collection
        boolean result = multiValueMap.removeMapping("key", "value");
        Assert.assertFalse(result);
    }
}
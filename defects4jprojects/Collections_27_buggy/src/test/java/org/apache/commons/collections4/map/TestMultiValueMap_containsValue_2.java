package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.MultiValueMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import static org.junit.Assert.*;

public class TestMultiValueMap_containsValue_2 {

    private final MultiValueMap<Object, Object> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_containsValue_withNullKey() {
        multiValueMap.put(null, "value");
        Assert.assertTrue(multiValueMap.containsValue(null, "value"));
    }

    @Test
    public void test_containsValue_withNonExistentKey() {
        Assert.assertFalse(multiValueMap.containsValue("nonExistentKey", "value"));
    }

    @Test
    public void test_containsValue_withNullCollection() {
        Assert.assertFalse(multiValueMap.containsValue("keyWithNullCollection", "value"));
    }
}
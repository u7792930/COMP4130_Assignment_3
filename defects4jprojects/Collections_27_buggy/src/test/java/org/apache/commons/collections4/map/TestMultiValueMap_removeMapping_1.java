package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.MultiValueMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.*;

public class TestMultiValueMap_removeMapping_1 {

    @Test
    public void test_removeMapping_nonExistentKey() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        boolean result = map.removeMapping("nonexistentKey", "value");
        Assert.assertFalse(result);
    }

    @Test
    public void test_removeMapping_nonExistentValue() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key", "value1");
        boolean result = map.removeMapping("key", "nonExistentValue");
        Assert.assertFalse(result);
    }

    @Test
    public void test_removeMapping_lastValueRemoved() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key", "value1");
        map.put("key", "value2");
        boolean result = map.removeMapping("key", "value1");
        Assert.assertTrue(result);
        // Verify that the key still has values
        Collection<String> values = map.getCollection("key");
        Assert.assertNotNull(values);
        Assert.assertTrue(values.contains("value2"));
        result = map.removeMapping("key", "value2");
        Assert.assertTrue(result);
        // Verify that the key is now removed
        Assert.assertNull(map.getCollection("key"));
    }
}
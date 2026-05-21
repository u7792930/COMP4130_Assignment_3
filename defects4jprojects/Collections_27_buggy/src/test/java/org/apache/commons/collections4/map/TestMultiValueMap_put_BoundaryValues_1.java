package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class TestMultiValueMap_put_BoundaryValues_1 {
    
    private final MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_put_singleCharacterKeyAndValue() {
        String key = "a";
        String value = "b";
        Object result = multiValueMap.put(key, value);
        Assert.assertEquals(value, result);
        Collection<String> collection = multiValueMap.getCollection(key);
        Assert.assertNotNull(collection);
        Assert.assertTrue(collection.contains(value));
    }

    @Test
    public void test_put_integerMinValueKey() {
        String key = String.valueOf(Integer.MIN_VALUE);
        String value = "value";
        Object result = multiValueMap.put(key, value);
        Assert.assertEquals(value, result);
        Collection<String> collection = multiValueMap.getCollection(key);
        Assert.assertNotNull(collection);
        Assert.assertTrue(collection.contains(value));
    }

    @Test
    public void test_put_integerMaxValueKey() {
        String key = String.valueOf(Integer.MAX_VALUE);
        String value = "value";
        Object result = multiValueMap.put(key, value);
        Assert.assertEquals(value, result);
        Collection<String> collection = multiValueMap.getCollection(key);
        Assert.assertNotNull(collection);
        Assert.assertTrue(collection.contains(value));
    }
}
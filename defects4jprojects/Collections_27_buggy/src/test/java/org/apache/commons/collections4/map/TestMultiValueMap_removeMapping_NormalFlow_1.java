package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import static org.junit.Assert.*;

public class TestMultiValueMap_removeMapping_NormalFlow_1 {

    private final MultiValueMap<Object, Object> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_removeMapping_existingValue() {
        // Arrange
        Object key = "key1";
        Object value = "value1";
        multiValueMap.put(key, value);
        multiValueMap.put(key, "value2");

        // Act
        boolean result = multiValueMap.removeMapping(key, value);

        // Assert
        Assert.assertTrue(result);
        Assert.assertEquals(1, multiValueMap.getCollection(key).size());
        Assert.assertFalse(multiValueMap.getCollection(key).contains(value));
    }

    @Test
    public void test_removeMapping_nonExistingValue() {
        // Arrange
        Object key = "key2";
        Object value = "value3";
        multiValueMap.put(key, "value4");

        // Act
        boolean result = multiValueMap.removeMapping(key, value);

        // Assert
        Assert.assertFalse(result);
        Assert.assertEquals(1, multiValueMap.getCollection(key).size());
    }

    @Test
    public void test_removeMapping_lastValue() {
        // Arrange
        Object key = "key3";
        Object value = "value5";
        multiValueMap.put(key, value);

        // Act
        boolean result = multiValueMap.removeMapping(key, value);

        // Assert
        Assert.assertTrue(result);
        Assert.assertNull(multiValueMap.getCollection(key));
    }
}
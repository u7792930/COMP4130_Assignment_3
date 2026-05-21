package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.util.Collections;
import java.util.HashSet;

public class TestMultiValueMap_size_BoundaryValues_1 {

    private MultiValueMap<Object, Object> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_size_keyNotInMap() {
        int size = multiValueMap.size("nonexistentKey");
        Assert.assertEquals(0, size);
    }

    @Test
    public void test_size_singleElementCollection() {
        multiValueMap.put("key1", "value1");
        int size = multiValueMap.size("key1");
        Assert.assertEquals(1, size);
    }

    @Test
    public void test_size_emptyCollection() {
        multiValueMap.put("key2", Collections.emptyList());
        int size = multiValueMap.size("key2");
        Assert.assertEquals(0, size);
    }

    @Test
    public void test_size_multipleElementsCollection() {
        multiValueMap.put("key3", "value1");
        multiValueMap.put("key3", "value2");
        int size = multiValueMap.size("key3");
        Assert.assertEquals(2, size);
    }

    @Test
    public void test_size_nullKey() {
        multiValueMap.put(null, "value1");
        int size = multiValueMap.size(null);
        Assert.assertEquals(1, size);
    }

    @Test
    public void test_size_keyWithNullValue() {
        multiValueMap.put("key4", null);
        int size = multiValueMap.size("key4");
        Assert.assertEquals(1, size);
    }

    @Test
    public void test_size_keyWithEmptySet() {
        multiValueMap.put("key5", new HashSet<>());
        int size = multiValueMap.size("key5");
        Assert.assertEquals(0, size);
    }
}
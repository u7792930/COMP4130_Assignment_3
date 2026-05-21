package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class TestMultiValueMap_totalSize_NullEmpty_1 {

    @Test
    public void test_totalSize_emptyMap() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        Assert.assertEquals(0, map.totalSize());
    }

    @Test
    public void test_totalSize_nullValues() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        map.put("key1", null);
        map.put("key2", null);
        Assert.assertEquals(0, map.totalSize());
    }

    @Test
    public void test_totalSize_emptyCollections() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        map.put("key1", Collections.emptyList());
        map.put("key2", Collections.emptySet());
        Assert.assertEquals(0, map.totalSize());
    }

    @Test
    public void test_totalSize_singleValue() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        map.put("key1", Collections.singletonList("value1"));
        Assert.assertEquals(1, map.totalSize());
    }

    @Test
    public void test_totalSize_multipleValues() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        map.put("key1", Arrays.asList("value1", "value2"));
        map.put("key2", Collections.singleton("value3"));
        Assert.assertEquals(3, map.totalSize());
    }

    @Test
    public void test_totalSize_mixedValues() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        map.put("key1", Arrays.asList("value1", "value2"));
        map.put("key2", null);
        map.put("key3", Collections.emptyList());
        map.put("key4", Collections.singleton("value3"));
        Assert.assertEquals(3, map.totalSize());
    }
}
package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;
import org.apache.commons.collections4.CollectionUtils;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.Assert.*;

public class TestMultiValueMap_totalSize_BoundaryValues_1 {

    @Test
    public void test_totalSize_emptyMap() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        Assert.assertEquals(0, map.totalSize());
    }

    @Test
    public void test_totalSize_singleElementCollection() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", Collections.singletonList("value1"));
        Assert.assertEquals(1, map.totalSize());
    }

    @Test
    public void test_totalSize_multipleCollections() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", Arrays.asList("value1", "value2"));
        map.put("key2", Collections.singletonList("value3"));
        Assert.assertEquals(3, map.totalSize());
    }

    @Test
    public void test_totalSize_emptyCollections() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", Collections.emptyList());
        map.put("key2", Collections.emptyList());
        Assert.assertEquals(0, map.totalSize());
    }

    @Test
    public void test_totalSize_nullValues() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", null);
        map.put("key2", Arrays.asList("value1", "value2"));
        Assert.assertEquals(2, map.totalSize());
    }
}
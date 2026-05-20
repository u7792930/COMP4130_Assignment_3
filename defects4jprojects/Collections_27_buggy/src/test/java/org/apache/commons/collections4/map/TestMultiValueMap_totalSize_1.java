package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.MultiValueMap;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestMultiValueMap_totalSize_1 {

    @Test
    public void test_totalSize_emptyMap() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        Assert.assertEquals(0, map.totalSize());
    }

    @Test
    public void test_totalSize_singleValue() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        Assert.assertEquals(1, map.totalSize());
    }

    @Test
    public void test_totalSize_multipleValues() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key1", "value2");
        map.put("key2", "value3");
        Assert.assertEquals(3, map.totalSize());
    }
}
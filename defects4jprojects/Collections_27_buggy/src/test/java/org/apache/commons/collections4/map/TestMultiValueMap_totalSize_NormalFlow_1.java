package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.map.MultiValueMap;

public class TestMultiValueMap_totalSize_NormalFlow_1 {

    @Test
    public void test_totalSize_multipleValueEntries() {
        MultiValueMap<String, String> map = new MultiValueMap<String, String>();
        map.put("key1", "value1");
        map.put("key1", "value2");
        map.put("key2", "value3");

        int result = map.totalSize();
        Assert.assertEquals(3, result);
    }

    @Test
    public void test_totalSize_singleValueEntries() {
        MultiValueMap<String, String> map = new MultiValueMap<String, String>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        int result = map.totalSize();
        Assert.assertEquals(2, result);
    }

    @Test
    public void test_totalSize_noEntries() {
        MultiValueMap<String, String> map = new MultiValueMap<String, String>();

        int result = map.totalSize();
        Assert.assertEquals(0, result);
    }
}
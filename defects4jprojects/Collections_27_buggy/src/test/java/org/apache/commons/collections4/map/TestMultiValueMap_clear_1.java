package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;
import org.apache.commons.collections4.map.MultiValueMap;
import static org.junit.Assert.*;

public class TestMultiValueMap_clear_1 {

    @Test
    public void test_clear_emptyMap() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.clear();
        Assert.assertTrue(map.isEmpty());
    }

    @Test
    public void test_clear_nonEmptyMap() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.clear();
        Assert.assertTrue(map.isEmpty());
    }

    @Test
    public void test_clear_mapWithMultipleValues() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key1", "value2");
        map.clear();
        Assert.assertTrue(map.isEmpty());
    }
}
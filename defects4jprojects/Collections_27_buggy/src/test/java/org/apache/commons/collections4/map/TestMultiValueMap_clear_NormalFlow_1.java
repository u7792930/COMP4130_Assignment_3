package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestMultiValueMap_clear_NormalFlow_1 {

    @Test
    public void test_clear_emptyMap() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.clear();
        Assert.assertEquals(0, map.size());
    }

    @Test
    public void test_clear_nonEmptyMap() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.clear();
        Assert.assertEquals(0, map.size());
    }

    @Test
    public void test_clear_afterAddingMultipleValues() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key1", "value2");
        map.clear();
        Assert.assertEquals(0, map.size());
    }
}
package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestMultiValueMap_clear_BoundaryValues_1 {

    @Test
    public void test_clear_emptyMap() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.clear();
        Assert.assertTrue(map.isEmpty());
    }

    @Test
    public void test_clear_singleElementMap() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.clear();
        Assert.assertTrue(map.isEmpty());
    }

    @Test
    public void test_clear_largeMap() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        for (int i = 0; i < 1000; i++) {
            map.put("key" + i, "value" + i);
        }
        map.clear();
        Assert.assertTrue(map.isEmpty());
    }
}
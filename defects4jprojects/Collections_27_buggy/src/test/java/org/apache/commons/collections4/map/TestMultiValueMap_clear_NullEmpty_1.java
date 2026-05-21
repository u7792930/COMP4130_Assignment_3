package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestMultiValueMap_clear_NullEmpty_1 {

    @Test
    public void test_clear_emptyMap() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
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
    public void test_clear_onEmptyMap() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        map.put(null, null);
        map.clear();
        Assert.assertTrue(map.isEmpty());
    }
}
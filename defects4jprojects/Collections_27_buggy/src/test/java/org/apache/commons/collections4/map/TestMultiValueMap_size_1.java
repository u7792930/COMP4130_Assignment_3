package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;
import org.apache.commons.collections4.map.MultiValueMap;
import static org.junit.Assert.*;

public class TestMultiValueMap_size_1 {

    @Test
    public void test_size_keyNotInMap() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        int size = map.size("nonExistentKey");
        Assert.assertEquals(0, size);
    }

    @Test
    public void test_size_keyWithEmptyCollection() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("emptyKey", null);
        int size = map.size("emptyKey");
        Assert.assertEquals(0, size);
    }

    @Test
    public void test_size_keyWithCollection() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("keyWithValues", "value1");
        map.put("keyWithValues", "value2");
        int size = map.size("keyWithValues");
        Assert.assertEquals(2, size);
    }
}
package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import static org.junit.Assert.*;

public class TestMultiValueMap_size_NormalFlow_1 {

    @Test
    public void test_size_nonExistentKey() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        int size = map.size("nonExistentKey");
        Assert.assertEquals(0, size);
    }

    @Test
    public void test_size_existingKeyWithValues() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key1", "value2");
        int size = map.size("key1");
        Assert.assertEquals(2, size);
    }

    @Test
    public void test_size_existingKeyWithNoValues() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key2", "value1");
        map.remove("key2");
        int size = map.size("key2");
        Assert.assertEquals(0, size);
    }
}
package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

public class TestMultiValueMap_put_NormalFlow_1 {

    @Test
    public void test_put_newKey_newValue() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        Object result = map.put("key1", "value1");
        Assert.assertEquals("value1", result);
        Assert.assertTrue(map.getCollection("key1").contains("value1"));
    }

    @Test
    public void test_put_existingKey_newValue() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        Object result = map.put("key1", "value2");
        Assert.assertEquals("value2", result);
        Assert.assertTrue(map.getCollection("key1").contains("value1"));
        Assert.assertTrue(map.getCollection("key1").contains("value2"));
    }

    @Test
    public void test_put_newKey_multipleValues() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key1", "value2");
        Object result = map.put("key1", "value3");
        Assert.assertEquals("value3", result);
        Collection<String> values = map.getCollection("key1");
        Assert.assertTrue(values.contains("value1"));
        Assert.assertTrue(values.contains("value2"));
        Assert.assertTrue(values.contains("value3"));
    }
}
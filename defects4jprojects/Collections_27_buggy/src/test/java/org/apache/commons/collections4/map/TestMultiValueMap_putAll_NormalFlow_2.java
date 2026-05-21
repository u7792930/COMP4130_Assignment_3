package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import static org.junit.Assert.*;

public class TestMultiValueMap_putAll_NormalFlow_2 {

    @Test
    public void test_putAll_nonExistentKey() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        Collection<String> values = new HashSet<>(Arrays.asList("value1", "value2"));
        boolean result = map.putAll("key1", values);
        Assert.assertTrue(result);
        Assert.assertEquals(2, map.getCollection("key1").size());
    }

    @Test
    public void test_putAll_existingKey() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.putAll("key1", new HashSet<>(Arrays.asList("value1")));
        Collection<String> newValues = new HashSet<>(Arrays.asList("value2", "value3"));
        boolean result = map.putAll("key1", newValues);
        Assert.assertTrue(result);
        Assert.assertEquals(3, map.getCollection("key1").size());
    }

    @Test
    public void test_putAll_withDuplicateValues() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        Collection<String> values = new HashSet<>(Arrays.asList("value1", "value1"));
        boolean result = map.putAll("key1", values);
        Assert.assertTrue(result);
        Assert.assertEquals(1, map.getCollection("key1").size());
    }
}
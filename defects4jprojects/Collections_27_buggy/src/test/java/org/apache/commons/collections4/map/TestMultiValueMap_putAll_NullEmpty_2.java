package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import static org.junit.Assert.*;

public class TestMultiValueMap_putAll_NullEmpty_2 {

    @Test
    public void test_putAll_nullValues() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        boolean result = map.putAll("key", null);
        Assert.assertFalse(result);
    }

    @Test
    public void test_putAll_emptyCollection() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        Collection<String> emptyCollection = new ArrayList<>();
        boolean result = map.putAll("key", emptyCollection);
        Assert.assertFalse(result);
    }

    @Test
    public void test_putAll_nonExistingKey() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        Collection<String> values = new ArrayList<>();
        values.add("value1");
        values.add("value2");
        boolean result = map.putAll("newKey", values);
        Assert.assertTrue(result);
    }
}
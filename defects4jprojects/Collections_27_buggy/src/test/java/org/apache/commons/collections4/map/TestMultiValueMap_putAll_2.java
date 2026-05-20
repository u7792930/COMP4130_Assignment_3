package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.MultiValueMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.*;

public class TestMultiValueMap_putAll_2 {

    private final MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_putAll_nullValues() {
        boolean result = multiValueMap.putAll("key1", null);
        Assert.assertFalse(result);
    }

    @Test
    public void test_putAll_emptyCollection() {
        boolean result = multiValueMap.putAll("key2", Arrays.asList());
        Assert.assertFalse(result);
    }

    @Test
    public void test_putAll_nonEmptyCollection() {
        Collection<String> values = Arrays.asList("value1", "value2");
        boolean result = multiValueMap.putAll("key3", values);
        Assert.assertTrue(result);
        Assert.assertEquals(2, multiValueMap.getCollection("key3").size());
    }
}
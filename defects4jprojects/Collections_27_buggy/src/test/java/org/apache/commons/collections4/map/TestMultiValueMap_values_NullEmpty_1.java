package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

public class TestMultiValueMap_values_NullEmpty_1 {

    @Test
    public void test_values_emptyCollection() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        Collection<Object> values = map.values();
        Assert.assertNotNull(values);
        Assert.assertTrue(values.isEmpty());
    }

    @Test
    public void test_values_withNullKey() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        map.put(null, "value1");
        Collection<Object> values = map.values();
        Assert.assertNotNull(values);
        Assert.assertEquals(1, values.size());
        Assert.assertTrue(values.contains("value1"));
    }

    @Test
    public void test_values_withEmptyMap() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        Collection<Object> values = map.values();
        Assert.assertNotNull(values);
        Assert.assertTrue(values.isEmpty());
    }
}
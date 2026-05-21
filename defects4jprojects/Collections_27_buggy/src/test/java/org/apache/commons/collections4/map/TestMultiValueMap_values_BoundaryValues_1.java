package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import static org.junit.Assert.*;

public class TestMultiValueMap_values_BoundaryValues_1 {

    @Test
    public void test_values_emptyMap() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        Collection<Object> values = map.values();
        Assert.assertNotNull(values);
        Assert.assertTrue(values.isEmpty());
    }

    @Test
    public void test_values_singleElement() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        map.put("key1", "value1");
        Collection<Object> values = map.values();
        Assert.assertNotNull(values);
        Assert.assertEquals(1, values.size());
        Assert.assertTrue(values.contains("value1"));
    }

    @Test
    public void test_values_multipleElements() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key1", "value3");
        Collection<Object> values = map.values();
        Assert.assertNotNull(values);
        Assert.assertEquals(3, values.size());
        Assert.assertTrue(values.contains("value1"));
        Assert.assertTrue(values.contains("value2"));
        Assert.assertTrue(values.contains("value3"));
    }
}
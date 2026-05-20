package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.MultiValueMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import static org.junit.Assert.*;

public class TestMultiValueMap_put_1 {

    private final MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_put_keyIsNull_valueIsNotNull() {
        Object result = multiValueMap.put(null, "value");
        Assert.assertEquals("value", result);
        Collection<String> values = multiValueMap.getCollection(null);
        Assert.assertNotNull(values);
        Assert.assertEquals(1, values.size());
    }

    @Test
    public void test_put_keyExists_valueIsAdded() {
        multiValueMap.put("key", "value1");
        Object result = multiValueMap.put("key", "value2");
        Assert.assertEquals("value2", result);
        Collection<String> values = multiValueMap.getCollection("key");
        Assert.assertNotNull(values);
        Assert.assertEquals(2, values.size());
    }

    @Test
    public void test_put_keyExists_valueIsNull() {
        multiValueMap.put("key", "value1");
        Object result = multiValueMap.put("key", null);
        Assert.assertNull(result);
        Collection<String> values = multiValueMap.getCollection("key");
        Assert.assertNotNull(values);
        Assert.assertEquals(1, values.size());
    }
}
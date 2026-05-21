package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import static org.junit.Assert.*;

public class TestMultiValueMap_put_NullEmpty_1 {

    private final MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_put_nullKey() {
        Object result = multiValueMap.put(null, "value");
        Assert.assertEquals("value", result);
        Collection<String> values = multiValueMap.getCollection(null);
        assertNotNull(values);
        assertTrue(values.contains("value"));
    }

    @Test
    public void test_put_emptyKey() {
        Object result = multiValueMap.put("", "value");
        Assert.assertEquals("value", result);
        Collection<String> values = multiValueMap.getCollection("");
        assertNotNull(values);
        assertTrue(values.contains("value"));
    }

    @Test
    public void test_put_nullValue() {
        Object result = multiValueMap.put("key", null);
        Assert.assertNull(result);
        Collection<String> values = multiValueMap.getCollection("key");
        assertNotNull(values);
        assertFalse(values.contains(null));
    }

    @Test
    public void test_put_nonNullKeyAndValue() {
        Object result = multiValueMap.put("key", "value1");
        Assert.assertEquals("value1", result);
        result = multiValueMap.put("key", "value2");
        Assert.assertEquals("value2", result);
        Collection<String> values = multiValueMap.getCollection("key");
        assertNotNull(values);
        assertTrue(values.contains("value1"));
        assertTrue(values.contains("value2"));
    }
}
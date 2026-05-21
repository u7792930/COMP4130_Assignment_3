package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import static org.junit.Assert.*;

public class TestMultiValueMap_containsValue_NullEmpty_2 {

    private final MultiValueMap<Object, Object> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_containsValue_nullKey() {
        boolean result = multiValueMap.containsValue(null, "value");
        Assert.assertFalse(result);
    }

    @Test
    public void test_containsValue_nullValue() {
        boolean result = multiValueMap.containsValue("key", null);
        Assert.assertFalse(result);
    }

    @Test
    public void test_containsValue_emptyCollection() {
        multiValueMap.put("key", Collections.emptyList());
        boolean result = multiValueMap.containsValue("key", "value");
        Assert.assertFalse(result);
    }
}
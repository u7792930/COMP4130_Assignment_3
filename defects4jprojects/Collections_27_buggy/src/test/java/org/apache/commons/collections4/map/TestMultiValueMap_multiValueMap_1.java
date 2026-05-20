package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.MultiValueMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class TestMultiValueMap_multiValueMap_1 {

    @Test
    public void test_multiValueMap_nonNullMap() {
        Map<String, ? super Collection<String>> inputMap = new HashMap<>();
        MultiValueMap<String, String> result = MultiValueMap.multiValueMap(inputMap);
        Assert.assertNotNull(result);
    }

    @Test
    public void test_multiValueMap_emptyMap() {
        Map<String, ? super Collection<String>> inputMap = new HashMap<>();
        MultiValueMap<String, String> result = MultiValueMap.multiValueMap(inputMap);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void test_multiValueMap_nullMap() {
        try {
            MultiValueMap.multiValueMap(null);
            Assert.fail("Expected IllegalArgumentException for null map");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
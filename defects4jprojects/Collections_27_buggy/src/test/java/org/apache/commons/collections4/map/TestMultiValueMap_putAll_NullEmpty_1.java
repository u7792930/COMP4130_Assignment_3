package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class TestMultiValueMap_putAll_NullEmpty_1 {

    @Test
    public void test_putAll_nullMap() {
        MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();
        try {
            multiValueMap.putAll(null);
            Assert.fail("Expected an NullPointerException to be thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }

    @Test
    public void test_putAll_emptyMap() {
        MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();
        Map<String, String> emptyMap = Collections.emptyMap();
        multiValueMap.putAll(emptyMap);
        Assert.assertTrue("The map should be empty after putting an empty map", multiValueMap.isEmpty());
    }

    @Test
    public void test_putAll_emptyMultiMap() {
        MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();
        MultiValueMap<String, String> emptyMultiMap = new MultiValueMap<>();
        multiValueMap.putAll(emptyMultiMap);
        Assert.assertTrue("The map should be empty after putting an empty MultiMap", multiValueMap.isEmpty());
    }
}
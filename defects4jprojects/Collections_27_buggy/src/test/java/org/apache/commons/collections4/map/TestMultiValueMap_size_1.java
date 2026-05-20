package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;
import org.apache.commons.collections4.map.MultiValueMap;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class TestMultiValueMap_size_1 {

    @Test
    public void test_size_keyNotInMap() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        int size = map.size("nonExistentKey");
        Assert.assertEquals(0, size);
    }

    @Test
    public void test_size_keyWithEmptyCollection() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("emptyKey", new ArrayList<String>()); // Using an empty collection
        int size = map.size("emptyKey");
        Assert.assertEquals(0, size);
    }

    @Test
    public void test_size_keyWithCollection() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("keyWithValues", "value1");
        map.put("keyWithValues", "value2");
        int size = map.size("keyWithValues");
        Assert.assertEquals(2, size);
    }

    @Test
    public void test_size_keyWithNullValue() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("keyWithNull", null); // Adding a null value
        int size = map.size("keyWithNull");
        Assert.assertEquals(1, size); // Expecting size to be 1 since null is a valid entry
    }

    @Test
    public void test_size_multipleKeys() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        int size1 = map.size("key1");
        int size2 = map.size("key2");
        Assert.assertEquals(1, size1); // Each key should have size 1
        Assert.assertEquals(1, size2);
    }
}
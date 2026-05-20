package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.MultiValueMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import static org.junit.Assert.*;

public class TestMultiValueMap_getCollection_1 {

    @Test
    public void test_getCollection_keyMappedToCollection() {
        MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();
        multiValueMap.put("key1", "value1");
        multiValueMap.put("key1", "value2");

        Collection<String> result = multiValueMap.getCollection("key1");
        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.size());
    }

    @Test
    public void test_getCollection_keyNotMapped() {
        MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();

        Collection<String> result = multiValueMap.getCollection("key2");
        Assert.assertNull(result);
    }

    @Test
    public void test_getCollection_nullKey() {
        MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();
        multiValueMap.put(null, "value");

        Collection<String> result = multiValueMap.getCollection(null);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
    }
}
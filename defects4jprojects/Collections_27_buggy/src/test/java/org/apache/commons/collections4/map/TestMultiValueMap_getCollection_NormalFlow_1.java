package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.util.Collection;
import java.util.Arrays;
import static org.junit.Assert.*;

public class TestMultiValueMap_getCollection_NormalFlow_1 {

    private final MultiValueMap<Object, String> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_getCollection_existingKey() {
        // Setting up a key with a collection
        multiValueMap.put("key1", "value1");
        multiValueMap.put("key1", "value2");

        Collection<String> result = multiValueMap.getCollection("key1");

        // Verify the returned collection contains the expected values
        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.size());
        Assert.assertTrue(result.contains("value1"));
        Assert.assertTrue(result.contains("value2"));
    }

    @Test
    public void test_getCollection_differentKey() {
        // Setting up a different key
        multiValueMap.put("key2", "valueA");

        Collection<String> result = multiValueMap.getCollection("key2");

        // Verify the returned collection contains the expected value
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.contains("valueA"));
    }

    @Test
    public void test_getCollection_noMapping() {
        // Retrieving a collection for a key that does not exist
        Collection<String> result = multiValueMap.getCollection("nonExistentKey");

        // Verify that the returned collection is null
        Assert.assertNull(result);
    }
}
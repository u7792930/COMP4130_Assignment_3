package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.MultiValueMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import static org.junit.Assert.*;

public class TestMultiValueMap_createCollection_1 {

    private final MultiValueMap<String, String> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_createCollection_zeroSize() {
        Collection<String> collection = multiValueMap.createCollection(0);
        Assert.assertNotNull(collection);
        Assert.assertTrue(collection.isEmpty());
    }

    @Test
    public void test_createCollection_positiveSize() {
        Collection<String> collection = multiValueMap.createCollection(5);
        Assert.assertNotNull(collection);
        // Since the specific type of Collection is not defined,
        // we assume it can hold elements and check size.
        Assert.assertTrue(collection.isEmpty());
    }

    @Test
    public void test_createCollection_negativeSize() {
        try {
            multiValueMap.createCollection(-1);
            Assert.fail("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
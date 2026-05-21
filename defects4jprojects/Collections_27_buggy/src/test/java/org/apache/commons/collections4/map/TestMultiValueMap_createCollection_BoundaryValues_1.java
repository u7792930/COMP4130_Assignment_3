package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

public class TestMultiValueMap_createCollection_BoundaryValues_1 {

    private MultiValueMap<Object, Object> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_createCollection_zeroSize() {
        Collection<Object> result = multiValueMap.createCollection(0);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
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

    @Test
    public void test_createCollection_positiveSize() {
        Collection<Object> result = multiValueMap.createCollection(10);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
    }
}
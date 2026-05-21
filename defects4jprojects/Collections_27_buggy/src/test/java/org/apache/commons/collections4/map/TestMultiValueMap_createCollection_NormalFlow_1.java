package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.util.Collection;
import static org.junit.Assert.*;

public class TestMultiValueMap_createCollection_NormalFlow_1 {

    private final MultiValueMap<Object, Object> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_createCollection_validSize() {
        int size = 10;
        Collection<Object> collection = multiValueMap.createCollection(size);
        Assert.assertNotNull(collection);
    }

    @Test
    public void test_createCollection_nonZeroSize() {
        int size = 5;
        Collection<Object> collection = multiValueMap.createCollection(size);
        Assert.assertNotNull(collection);
        Assert.assertTrue(collection.isEmpty());
    }

    @Test
    public void test_createCollection_largeSize() {
        int size = 1000;
        Collection<Object> collection = multiValueMap.createCollection(size);
        Assert.assertNotNull(collection);
    }
}
package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import static org.junit.Assert.*;

public class TestMultiValueMap_createCollection_NullEmpty_1 {

    private final MultiValueMap<Object, Object> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_createCollection_zeroSize() {
        Collection<Object> result = multiValueMap.createCollection(0);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void test_createCollection_negativeSize() {
        Collection<Object> result = multiValueMap.createCollection(-1);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void test_createCollection_largeSize() {
        Collection<Object> result = multiValueMap.createCollection(1000);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
    }
}
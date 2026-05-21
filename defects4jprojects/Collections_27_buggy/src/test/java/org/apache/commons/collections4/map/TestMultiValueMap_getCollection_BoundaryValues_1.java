package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;
import static org.junit.Assert.*;

public class TestMultiValueMap_getCollection_BoundaryValues_1 {

    private MultiValueMap<Object, String> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_getCollection_keyIntegerMinValue() {
        Collection<String> result = multiValueMap.getCollection(Integer.MIN_VALUE);
        Assert.assertNull(result);
    }

    @Test
    public void test_getCollection_keyIntegerMaxValue() {
        Collection<String> result = multiValueMap.getCollection(Integer.MAX_VALUE);
        Assert.assertNull(result);
    }

    @Test
    public void test_getCollection_keyZero() {
        Collection<String> result = multiValueMap.getCollection(0);
        Assert.assertNull(result);
    }
}
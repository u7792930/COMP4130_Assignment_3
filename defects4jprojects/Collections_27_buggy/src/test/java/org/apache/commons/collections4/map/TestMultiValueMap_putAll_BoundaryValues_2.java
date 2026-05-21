package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.*;

public class TestMultiValueMap_putAll_BoundaryValues_2 {

    private final MultiValueMap<Integer, String> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_putAll_singleElementCollection() {
        List<String> values = new ArrayList<>();
        values.add("singleValue");
        boolean result = multiValueMap.putAll(1, values);
        Assert.assertTrue(result);
        Assert.assertEquals(1, multiValueMap.getCollection(1).size());
    }

    @Test
    public void test_putAll_zeroSizeCollection() {
        List<String> values = Collections.emptyList();
        boolean result = multiValueMap.putAll(2, values);
        Assert.assertFalse(result);
    }

    @Test
    public void test_putAll_nullCollection() {
        boolean result = multiValueMap.putAll(3, null);
        Assert.assertFalse(result);
    }

    @Test
    public void test_putAll_multipleElementsCollection() {
        List<String> values = new ArrayList<>();
        values.add("value1");
        values.add("value2");
        boolean result = multiValueMap.putAll(4, values);
        Assert.assertTrue(result);
        Assert.assertEquals(2, multiValueMap.getCollection(4).size());
    }

    @Test
    public void test_putAll_existingKey() {
        List<String> initialValues = new ArrayList<>();
        initialValues.add("value1");
        multiValueMap.putAll(5, initialValues);
        
        List<String> newValues = new ArrayList<>();
        newValues.add("value2");
        boolean result = multiValueMap.putAll(5, newValues);
        Assert.assertTrue(result);
        Assert.assertEquals(2, multiValueMap.getCollection(5).size());
    }

    @Test
    public void test_putAll_largeCollection() {
        List<String> values = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            values.add("value" + i);
        }
        boolean result = multiValueMap.putAll(6, values);
        Assert.assertTrue(result);
        Assert.assertEquals(1000, multiValueMap.getCollection(6).size());
    }
}
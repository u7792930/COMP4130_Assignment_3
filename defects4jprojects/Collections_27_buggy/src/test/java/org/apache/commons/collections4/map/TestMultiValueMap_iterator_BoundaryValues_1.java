package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.util.Iterator;
import java.util.Collections;
import static org.junit.Assert.*;

public class TestMultiValueMap_iterator_BoundaryValues_1 {

    private MultiValueMap<Object, String> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_iterator_keyIsIntegerMinValue() {
        multiValueMap.put(Integer.MIN_VALUE, "value");
        Iterator<String> iterator = multiValueMap.iterator(Integer.MIN_VALUE);
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("value", iterator.next());
    }

    @Test
    public void test_iterator_keyIsIntegerMaxValue() {
        multiValueMap.put(Integer.MAX_VALUE, "value");
        Iterator<String> iterator = multiValueMap.iterator(Integer.MAX_VALUE);
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("value", iterator.next());
    }

    @Test
    public void test_iterator_keyIsZero() {
        multiValueMap.put(0, "value");
        Iterator<String> iterator = multiValueMap.iterator(0);
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("value", iterator.next());
    }

    @Test
    public void test_iterator_keyIsNegativeOne() {
        multiValueMap.put(-1, "value");
        Iterator<String> iterator = multiValueMap.iterator(-1);
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("value", iterator.next());
    }

    @Test
    public void test_iterator_keyIsSingleCharacterString() {
        multiValueMap.put("A", "value");
        Iterator<String> iterator = multiValueMap.iterator("A");
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("value", iterator.next());
    }

    @Test
    public void test_iterator_keyIsSingleElementCollection() {
        multiValueMap.put(Collections.singletonList("key"), "value");
        Iterator<String> iterator = multiValueMap.iterator(Collections.singletonList("key"));
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("value", iterator.next());
    }
}
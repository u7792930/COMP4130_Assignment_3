package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.util.Iterator;
import static org.junit.Assert.*;

public class TestMultiValueMap_iterator_NullEmpty_1 {

    @Test
    public void test_iterator_nullKey() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        Iterator<Object> iterator = map.iterator(null);
        Assert.assertNotNull(iterator);
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void test_iterator_emptyKey() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        Iterator<Object> iterator = map.iterator("");
        Assert.assertNotNull(iterator);
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void test_iterator_nonExistentKey() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        Iterator<Object> iterator = map.iterator(new Object());
        Assert.assertNotNull(iterator);
        Assert.assertFalse(iterator.hasNext());
    }
}
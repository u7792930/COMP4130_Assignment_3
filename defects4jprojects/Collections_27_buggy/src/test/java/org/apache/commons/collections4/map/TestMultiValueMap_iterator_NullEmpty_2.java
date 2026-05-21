package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map.Entry;
import static org.junit.Assert.*;

public class TestMultiValueMap_iterator_NullEmpty_2 {

    @Test
    public void test_iterator_emptyMap() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        Iterator<Entry<String, String>> iterator = map.iterator();
        Assert.assertNotNull(iterator);
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void test_iterator_singleValue() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        Iterator<Entry<String, String>> iterator = map.iterator();
        Assert.assertNotNull(iterator);
        Assert.assertTrue(iterator.hasNext());
        Entry<String, String> entry = iterator.next();
        Assert.assertEquals("key1", entry.getKey());
        Assert.assertEquals("value1", entry.getValue());
    }

    @Test
    public void test_iterator_multipleValues() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key1", "value2");
        Iterator<Entry<String, String>> iterator = map.iterator();
        Assert.assertNotNull(iterator);
        int count = 0;
        while (iterator.hasNext()) {
            Entry<String, String> entry = iterator.next();
            Assert.assertEquals("key1", entry.getKey());
            count++;
        }
        Assert.assertEquals(2, count);
    }
}
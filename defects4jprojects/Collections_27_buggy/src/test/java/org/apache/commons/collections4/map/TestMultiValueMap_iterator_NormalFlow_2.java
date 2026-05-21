package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;
import java.util.Iterator;
import java.util.Map.Entry;
import static org.junit.Assert.*;

public class TestMultiValueMap_iterator_NormalFlow_2 {

    @Test
    public void test_iterator_multipleValuesForSameKey() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key1", "value2");

        Iterator<Entry<String, String>> iterator = map.iterator();
        Assert.assertTrue(iterator.hasNext());

        Entry<String, String> entry1 = iterator.next();
        Assert.assertEquals("key1", entry1.getKey());
        Assert.assertTrue(entry1.getValue().equals("value1") || entry1.getValue().equals("value2"));

        Entry<String, String> entry2 = iterator.next();
        Assert.assertTrue(entry2.getKey().equals("key1") && !entry2.getValue().equals(entry1.getValue()));
    }

    @Test
    public void test_iterator_emptyMap() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        Iterator<Entry<String, String>> iterator = map.iterator();
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void test_iterator_singleEntry() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");

        Iterator<Entry<String, String>> iterator = map.iterator();
        Assert.assertTrue(iterator.hasNext());

        Entry<String, String> entry = iterator.next();
        Assert.assertEquals("key1", entry.getKey());
        Assert.assertEquals("value1", entry.getValue());
        Assert.assertFalse(iterator.hasNext());
    }
}
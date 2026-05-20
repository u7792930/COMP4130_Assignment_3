package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.Values;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import static org.junit.Assert.*;

public class TestValues_iterator_1 {

    @Test
    public void test_iterator_nonEmpty() {
        Values<String, String> values = new Values<>();
        // Assuming the class has a method to add key-value pairs
        values.put("key1", "value1");
        values.put("key2", "value2");
        
        Iterator<String> iterator = values.iterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("value1", iterator.next());
        Assert.assertEquals("value2", iterator.next());
    }

    @Test
    public void test_iterator_empty() {
        Values<String, String> values = new Values<>();
        
        Iterator<String> iterator = values.iterator();
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void test_iterator_singleValue() {
        Values<String, String> values = new Values<>();
        // Assuming the class has a method to add key-value pairs
        values.put("key1", "value1");
        
        Iterator<String> iterator = values.iterator();
        Assert.assertTrue(iterator.hasNext());
        Assert.assertEquals("value1", iterator.next());
        Assert.assertFalse(iterator.hasNext());
    }
}
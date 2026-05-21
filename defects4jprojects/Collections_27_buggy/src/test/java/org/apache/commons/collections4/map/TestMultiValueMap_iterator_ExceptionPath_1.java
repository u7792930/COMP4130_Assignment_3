package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Map.Entry;
import static org.junit.Assert.*;

public class TestMultiValueMap_iterator_ExceptionPath_1 {

    @Test
    public void test_iterator_setValueUnsupportedOperationException() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        Iterator<Entry<String, String>> iterator = map.iterator();
        
        // Advance to the first element
        if (iterator.hasNext()) {
            Entry<String, String> entry = iterator.next();
            try {
                entry.setValue("newValue");
                Assert.fail("Expected UnsupportedOperationException to be thrown");
            } catch (UnsupportedOperationException e) {
                // Expected exception
            }
        }
    }

    @Test
    public void test_iterator_noNextElement_NoSuchElementException() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        Iterator<Entry<String, String>> iterator = map.iterator();
        
        try {
            iterator.next(); // This should throw NoSuchElementException
            Assert.fail("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
    
    @Test
    public void test_iterator_emptyMap_NoSuchElementException() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        Iterator<Entry<String, String>> iterator = map.iterator();
        
        try {
            iterator.next(); // This should throw NoSuchElementException
            Assert.fail("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // Expected exception
        }
    }
}
package org.apache.commons.collections4.map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import java.util.Set;
import java.util.Map.Entry;
import static org.junit.Assert.*;

public class TestMultiValueMap_entrySet_NormalFlow_1 {

    @Test
    public void test_entrySet_nonEmptyMap() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key1", "value2");

        Set<Entry<String, Object>> entrySet = map.entrySet();
        
        assertNotNull("EntrySet should not be null", entrySet);
        assertEquals("EntrySet size should be 1", 1, entrySet.size());
    }

    @Test
    public void test_entrySet_multipleKeys() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        Set<Entry<String, Object>> entrySet = map.entrySet();
        
        assertNotNull("EntrySet should not be null", entrySet);
        assertEquals("EntrySet size should be 2", 2, entrySet.size());
    }

    @Test
    public void test_entrySet_singleKeyMultipleValues() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key1", "value2");
        map.put("key2", "value3");

        Set<Entry<String, Object>> entrySet = map.entrySet();
        
        assertNotNull("EntrySet should not be null", entrySet);
        assertEquals("EntrySet size should be 2", 2, entrySet.size());
    }
}
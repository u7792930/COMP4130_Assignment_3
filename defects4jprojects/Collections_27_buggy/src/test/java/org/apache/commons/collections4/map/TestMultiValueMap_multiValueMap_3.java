package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.MultiValueMap;
import org.apache.commons.collections4.Factory;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

public class TestMultiValueMap_multiValueMap_3 {

    @Test
    public void test_multiValueMap_validInput() {
        Map<String, Collection<String>> map = new HashMap<>();
        Factory<Collection<String>> factory = ArrayList::new;
        
        MultiValueMap<String, String> result = MultiValueMap.multiValueMap(map, factory);
        
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void test_multiValueMap_emptyMap() {
        Map<String, Collection<String>> map = new HashMap<>();
        Factory<Collection<String>> factory = ArrayList::new;
        
        MultiValueMap<String, String> result = MultiValueMap.multiValueMap(map, factory);
        
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void test_multiValueMap_nullFactory() {
        Map<String, Collection<String>> map = new HashMap<>();
        Factory<Collection<String>> factory = null;
        
        try {
            MultiValueMap.multiValueMap(map, factory);
            fail("Expected an exception to be thrown due to null factory");
        } catch (IllegalArgumentException e) {
            // Exception expected
        }
    }
}
package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.MultiValueMap;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

public class TestMultiValueMap_multiValueMap_2 {

    @Test
    public void test_multiValueMap_emptyMap() {
        Map<String, Collection<String>> map = new HashMap<>();
        MultiValueMap<String, String> result = MultiValueMap.multiValueMap(map, ArrayList.class);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void test_multiValueMap_singleEntry() {
        Map<String, Collection<String>> map = new HashMap<>();
        map.put("key1", new ArrayList<>(Arrays.asList("value1")));
        MultiValueMap<String, String> result = MultiValueMap.multiValueMap(map, ArrayList.class);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.getCollection("key1").size());
    }

    @Test
    public void test_multiValueMap_multipleEntries() {
        Map<String, Collection<String>> map = new HashMap<>();
        map.put("key1", new ArrayList<>(Arrays.asList("value1", "value2")));
        map.put("key2", new ArrayList<>(Arrays.asList("value3")));
        MultiValueMap<String, String> result = MultiValueMap.multiValueMap(map, ArrayList.class);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(2, result.getCollection("key1").size());
        assertEquals(1, result.getCollection("key2").size());
    }
}
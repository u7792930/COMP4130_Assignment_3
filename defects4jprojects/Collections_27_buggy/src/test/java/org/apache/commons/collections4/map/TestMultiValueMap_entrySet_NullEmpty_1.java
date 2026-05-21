package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class TestMultiValueMap_entrySet_NullEmpty_1 {

    @Test
    public void test_entrySet_emptyMap() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        Set<Map.Entry<Object, Object>> entries = map.entrySet();
        Assert.assertNotNull(entries);
        Assert.assertTrue(entries.isEmpty());
    }

    @Test
    public void test_entrySet_nullValues() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        map.put("key1", null);
        Set<Map.Entry<Object, Object>> entries = map.entrySet();
        Assert.assertNotNull(entries);
        Assert.assertEquals(1, entries.size());
        for (Map.Entry<Object, Object> entry : entries) {
            Assert.assertEquals("key1", entry.getKey());
            Assert.assertTrue(((Collection<Object>) entry.getValue()).isEmpty());
        }
    }

    @Test
    public void test_entrySet_nonEmptyMap() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key1", "value2");
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        Assert.assertNotNull(entries);
        Assert.assertEquals(1, entries.size());
        for (Map.Entry<String, Object> entry : entries) {
            Assert.assertEquals("key1", entry.getKey());
            Assert.assertEquals(2, ((Collection<Object>) entry.getValue()).size());
        }
    }
}
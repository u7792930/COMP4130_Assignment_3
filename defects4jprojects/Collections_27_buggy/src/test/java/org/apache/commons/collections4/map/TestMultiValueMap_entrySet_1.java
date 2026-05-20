package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.MultiValueMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.Set;
import static org.junit.Assert.*;

public class TestMultiValueMap_entrySet_1 {

    @Test
    public void test_entrySet_nonEmptyMap() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key1", "value2");
        Set<Map.Entry<String, Collection<String>>> entrySet = map.entrySet();
        Assert.assertNotNull(entrySet);
        Assert.assertEquals(1, entrySet.size());
    }

    @Test
    public void test_entrySet_emptyMap() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        Set<Map.Entry<String, Collection<String>>> entrySet = map.entrySet();
        Assert.assertNotNull(entrySet);
        Assert.assertEquals(0, entrySet.size());
    }

    @Test
    public void test_entrySet_singleEntry() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        Set<Map.Entry<String, Collection<String>>> entrySet = map.entrySet();
        Assert.assertNotNull(entrySet);
        Assert.assertEquals(1, entrySet.size());
        for (Map.Entry<String, Collection<String>> entry : entrySet) {
            Assert.assertEquals("key1", entry.getKey());
            Assert.assertEquals(1, entry.getValue().size());
            Assert.assertTrue(entry.getValue().contains("value1"));
        }
    }
}
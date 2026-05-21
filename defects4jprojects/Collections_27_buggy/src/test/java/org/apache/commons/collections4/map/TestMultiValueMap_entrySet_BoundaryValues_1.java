package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.util.Set;
import java.util.Map.Entry;
import static org.junit.Assert.*;

public class TestMultiValueMap_entrySet_BoundaryValues_1 {

    @Test
    public void test_entrySet_nonEmptyMap() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        Set<Entry<String, Object>> entrySet = map.entrySet();
        Assert.assertNotNull(entrySet);
        Assert.assertEquals(1, entrySet.size());
    }

    @Test
    public void test_entrySet_emptyMap() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        Set<Entry<String, Object>> entrySet = map.entrySet();
        Assert.assertNotNull(entrySet);
        Assert.assertEquals(0, entrySet.size());
    }

    @Test
    public void test_entrySet_singleElement() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key1", "value2");
        Set<Entry<String, Object>> entrySet = map.entrySet();
        Assert.assertNotNull(entrySet);
        Assert.assertEquals(1, entrySet.size());
    }
}
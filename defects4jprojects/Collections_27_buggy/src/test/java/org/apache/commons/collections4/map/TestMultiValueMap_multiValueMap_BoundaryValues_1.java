package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class TestMultiValueMap_multiValueMap_BoundaryValues_1 {

    @Test
    public void test_multiValueMap_emptyMap() {
        Map<Integer, ? super Collection<String>> emptyMap = Collections.emptyMap();
        MultiValueMap<Integer, String> result = MultiValueMap.multiValueMap(emptyMap);
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void test_multiValueMap_singleElementCollection() {
        Map<Integer, Collection<String>> singleElementMap = new HashMap<>();
        singleElementMap.put(1, Collections.singleton("A"));
        MultiValueMap<Integer, String> result = MultiValueMap.multiValueMap(singleElementMap);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.containsKey(1));
    }

    @Test
    public void test_multiValueMap_largeIntegerKey() {
        Map<Long, Collection<String>> largeKeyMap = new HashMap<>();
        largeKeyMap.put(Long.MAX_VALUE, Collections.singleton("MaxValue"));
        MultiValueMap<Long, String> result = MultiValueMap.multiValueMap(largeKeyMap);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.containsKey(Long.MAX_VALUE));
    }
}
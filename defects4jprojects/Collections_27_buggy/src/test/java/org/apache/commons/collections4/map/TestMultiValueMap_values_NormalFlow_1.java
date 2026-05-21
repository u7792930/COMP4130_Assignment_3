package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.util.Collection;
import static org.junit.Assert.*;

public class TestMultiValueMap_values_NormalFlow_1 {

    @Test
    public void test_values_nonEmptyMap() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        
        Collection<Object> values = map.values();
        Assert.assertNotNull(values);
        Assert.assertEquals(2, values.size());
    }

    @Test
    public void test_values_withMultipleValuesForKey() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        map.put("key", "value1");
        map.put("key", "value2");
        
        Collection<Object> values = map.values();
        Assert.assertNotNull(values);
        Assert.assertEquals(2, values.size());
    }

    @Test
    public void test_values_emptyMap() {
        MultiValueMap<String, String> map = new MultiValueMap<>();
        
        Collection<Object> values = map.values();
        Assert.assertNotNull(values);
        Assert.assertEquals(0, values.size());
    }
}
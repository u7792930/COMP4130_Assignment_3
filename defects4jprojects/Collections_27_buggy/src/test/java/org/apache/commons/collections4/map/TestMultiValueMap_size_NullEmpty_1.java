package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;
import java.util.Collections;
import static org.junit.Assert.*;

public class TestMultiValueMap_size_NullEmpty_1 {

    private MultiValueMap<Object, Object> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_size_nullKey() {
        // Test size with a null key
        int result = multiValueMap.size(null);
        Assert.assertEquals(0, result);
    }

    @Test
    public void test_size_emptyKey() {
        // Test size with an empty key (which is treated as a regular Object)
        int result = multiValueMap.size("");
        Assert.assertEquals(0, result);
    }

    @Test
    public void test_size_nonExistingKey() {
        // Test size with a key that does not exist in the map
        int result = multiValueMap.size(new Object());
        Assert.assertEquals(0, result);
    }
}
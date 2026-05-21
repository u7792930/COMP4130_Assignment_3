package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import static org.junit.Assert.*;

public class TestMultiValueMap_getCollection_NullEmpty_1 {

    private MultiValueMap<Object, Object> multiValueMap = new MultiValueMap<>();

    @Test
    public void test_getCollection_nullKey() {
        Collection<Object> result = multiValueMap.getCollection(null);
        Assert.assertNull(result);
    }

    @Test
    public void test_getCollection_emptyKey() {
        Collection<Object> result = multiValueMap.getCollection("");
        Assert.assertNull(result);
    }

    @Test
    public void test_getCollection_nonExistentKey() {
        Collection<Object> result = multiValueMap.getCollection(new Object());
        Assert.assertNull(result);
    }
}
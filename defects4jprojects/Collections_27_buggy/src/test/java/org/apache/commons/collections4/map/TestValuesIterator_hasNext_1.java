package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class TestValuesIterator_hasNext_1 {

    @Test
    public void test_hasNext_iteratorHasNextReturnsTrue() {
        Iterator<Integer> iterator = new HashMap<Integer, Integer>() {{
            put(1, 1);
        }}.values().iterator();
        Assert.assertTrue(iterator.hasNext());
    }

    @Test
    public void test_hasNext_iteratorHasNextReturnsFalse() {
        Iterator<Integer> iterator = new HashMap<Integer, Integer>().values().iterator();
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void test_hasNext_emptyIterator() {
        Iterator<Object> iterator = Collections.emptyList().iterator();
        Assert.assertFalse(iterator.hasNext());
    }
}
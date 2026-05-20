package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.ValuesIterator;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestValuesIterator_hasNext_1 {

    @Test
    public void test_hasNext_iteratorHasNextReturnsTrue() {
        ValuesIterator<Integer> iterator = new ValuesIterator<>(new HashMap<Integer, Integer>() {{
            put(1, 1);
        }}.values().iterator());
        Assert.assertTrue(iterator.hasNext());
    }

    @Test
    public void test_hasNext_iteratorHasNextReturnsFalse() {
        ValuesIterator<Integer> iterator = new ValuesIterator<>(new HashMap<Integer, Integer>().values().iterator());
        Assert.assertFalse(iterator.hasNext());
    }

    @Test
    public void test_hasNext_emptyIterator() {
        ValuesIterator<Integer> iterator = new ValuesIterator<>(Collections.emptyList().iterator());
        Assert.assertFalse(iterator.hasNext());
    }
}
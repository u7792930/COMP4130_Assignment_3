package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.ListOrderedMap;
import java.util.Collection;

public class TestValues_clear_1 {

    private static class Values {
        private Collection<Object> collection;

        public Values() {
            collection = CollectionUtils.emptyCollection();
        }

        public void add(Object value) {
            collection.add(value);
        }

        public void clear() {
            collection.clear();
        }

        public boolean isEmpty() {
            return collection.isEmpty();
        }
    }

    @Test
    public void test_clear_emptyValues() {
        Values values = new Values();
        values.clear();
        Assert.assertTrue("Values should be empty after clear", values.isEmpty());
    }

    @Test
    public void test_clear_nonEmptyValues() {
        Values values = new Values();
        values.add("value1");
        values.add("value2");
        values.clear();
        Assert.assertTrue("Values should be empty after clear", values.isEmpty());
    }

    @Test
    public void test_clear_calledMultipleTimes() {
        Values values = new Values();
        values.add("value1");
        values.clear();
        values.clear(); // Call clear again
        Assert.assertTrue("Values should be empty after multiple clear calls", values.isEmpty());
    }
}
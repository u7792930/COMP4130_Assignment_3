package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;
import org.apache.commons.collections4.map.Values;
import static org.junit.Assert.*;

public class TestValues_clear_1 {

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
package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_equals_1 {

    @Test
    public void test_equals_bothNull() {
        assertTrue(StringUtils.equals(null, null));
    }

    @Test
    public void test_equals_firstNull() {
        assertFalse(StringUtils.equals(null, "abc"));
    }

    @Test
    public void test_equals_secondNull() {
        assertFalse(StringUtils.equals("abc", null));
    }
}
package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_equals_NormalFlow_1 {

    @Test
    public void test_equals_bothNonNullEqual() {
        CharSequence cs1 = "abc";
        CharSequence cs2 = "abc";
        assertTrue(StringUtils.equals(cs1, cs2));
    }

    @Test
    public void test_equals_bothNonNullNotEqual() {
        CharSequence cs1 = "abc";
        CharSequence cs2 = "ABC";
        assertFalse(StringUtils.equals(cs1, cs2));
    }

    @Test
    public void test_equals_bothNonNullDifferentType() {
        CharSequence cs1 = new StringBuilder("abc");
        CharSequence cs2 = new StringBuilder("abc");
        assertTrue(StringUtils.equals(cs1, cs2));
    }
}
package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestStringUtils_newStringUtf16Le_BoundaryValues_1 {

    @Test
    public void test_newStringUtf16Le_emptyArray() {
        byte[] input = new byte[0]; // Empty byte array
        String result = StringUtils.newStringUtf16Le(input);
        assertEquals("", result); // Expecting an empty string
    }

    @Test
    public void test_newStringUtf16Le_singleCharacter() {
        byte[] input = new byte[] { 'H', 0, 'i', 0 }; // UTF-16LE for "Hi"
        String result = StringUtils.newStringUtf16Le(input);
        assertEquals("Hi", result); // Expecting "Hi"
    }

    @Test
    public void test_newStringUtf16Le_nullArray() {
        byte[] input = null; // Null byte array
        String result = StringUtils.newStringUtf16Le(input);
        assertNull(result); // Expecting null
    }

    @Test
    public void test_newStringUtf16Le_maxValueArray() {
        byte[] input = new byte[Character.MAX_VALUE * 2]; // Maximum size for characters
        String result = StringUtils.newStringUtf16Le(input);
        assertEquals("", result); // Expecting empty string since it's initialized to zeros
    }

    @Test
    public void test_newStringUtf16Le_invalidUtf16() {
        byte[] input = new byte[] { -1, 0 }; // Invalid UTF-16LE
        String result = StringUtils.newStringUtf16Le(input);
        assertEquals("\uFFFD", result); // Expecting replacement character for invalid input
    }
}
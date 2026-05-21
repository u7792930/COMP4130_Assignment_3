package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

public class TestStringUtils_newStringUtf16_NullEmpty_1 {

    @Test
    public void test_newStringUtf16_nullInput() {
        String result = StringUtils.newStringUtf16(null);
        assertNull(result);
    }

    @Test
    public void test_newStringUtf16_emptyArray() {
        String result = StringUtils.newStringUtf16(new byte[0]);
        assertEquals("", result);
    }

    @Test
    public void test_newStringUtf16_nonEmptyArray() {
        byte[] input = new byte[] { 72, 0, 101, 0, 108, 0, 108, 0, 111, 0 }; // UTF-16 for "Hello"
        String result = StringUtils.newStringUtf16(input);
        assertEquals("Hello", result);
    }

    @Test
    public void test_newStringUtf16_withSpecialCharacters() {
        byte[] input = new byte[] { -28, -72, -83, 0, -28, -70, -91, 0 }; // UTF-16 for "你好"
        String result = StringUtils.newStringUtf16(input);
        assertEquals("你好", result);
    }
}
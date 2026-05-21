package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

public class TestStringUtils_getBytesUtf16_NullEmpty_1 {

    @Test
    public void test_getBytesUtf16_nullString() {
        byte[] result = StringUtils.getBytesUtf16(null);
        assertNull(result);
    }

    @Test
    public void test_getBytesUtf16_emptyString() {
        byte[] result = StringUtils.getBytesUtf16("");
        assertArrayEquals(new byte[0], result);
    }

    @Test
    public void test_getBytesUtf16_nonEmptyString() {
        byte[] result = StringUtils.getBytesUtf16("Hello");
        byte[] expected = new byte[] { 'H', 0, 'e', 0, 'l', 0, 'l', 0, 'o', 0 }; // UTF-16 encoding
        assertArrayEquals(expected, result);
    }

    @Test
    public void test_getBytesUtf16_specialCharacters() {
        byte[] result = StringUtils.getBytesUtf16("你好");
        byte[] expected = new byte[] { -48, -28, 0, 0, -48, -27, 0, 0 }; // UTF-16 encoding for "你好"
        assertArrayEquals(expected, result);
    }

    @Test
    public void test_getBytesUtf16_singleCharacter() {
        byte[] result = StringUtils.getBytesUtf16("A");
        byte[] expected = new byte[] { 'A', 0 }; // UTF-16 encoding for "A"
        assertArrayEquals(expected, result);
    }
}
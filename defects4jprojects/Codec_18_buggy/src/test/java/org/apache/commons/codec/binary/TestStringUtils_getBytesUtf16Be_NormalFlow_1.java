package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUtf16Be_NormalFlow_1 {

    @Test
    public void test_getBytesUtf16Be_validString() {
        String input = "Hello";
        byte[] expected = new byte[] { 0, 'H', 0, 'e', 0, 'l', 0, 'l', 0, 'o' }; // UTF-16BE encoding
        byte[] result = StringUtils.getBytesUtf16Be(input);
        assertArrayEquals(expected, result);
    }

    @Test
    public void test_getBytesUtf16Be_anotherValidString() {
        String input = "Test";
        byte[] expected = new byte[] { 0, 'T', 0, 'e', 0, 's', 0, 't' }; // UTF-16BE encoding
        byte[] result = StringUtils.getBytesUtf16Be(input);
        assertArrayEquals(expected, result);
    }

    @Test
    public void test_getBytesUtf16Be_emptyString() {
        String input = "";
        byte[] expected = new byte[] {}; // UTF-16BE encoding of an empty string
        byte[] result = StringUtils.getBytesUtf16Be(input);
        assertArrayEquals(expected, result);
    }
}
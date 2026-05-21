package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUtf16Le_NormalFlow_1 {

    @Test
    public void test_getBytesUtf16Le_validString() {
        String input = "Hello";
        byte[] expected = new byte[] { 'H', 0, 'e', 0, 'l', 0, 'l', 0, 'o', 0 }; // UTF-16LE encoding of "Hello"
        byte[] result = StringUtils.getBytesUtf16Le(input);
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void test_getBytesUtf16Le_emptyString() {
        String input = "";
        byte[] expected = new byte[] { 0, 0 }; // UTF-16LE encoding of empty string
        byte[] result = StringUtils.getBytesUtf16Le(input);
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void test_getBytesUtf16Le_nullString() {
        String input = null;
        byte[] result = StringUtils.getBytesUtf16Le(input);
        Assert.assertNull(result); // Expecting null for null input
    }

    @Test
    public void test_getBytesUtf16Le_specialCharacters() {
        String input = "你好"; // "Hello" in Chinese
        byte[] expected = new byte[] { -28, -72, -83, 0, -27, -101, -67, 0 }; // UTF-16LE encoding of "你好"
        byte[] result = StringUtils.getBytesUtf16Le(input);
        Assert.assertArrayEquals(expected, result);
    }
}
package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUtf16_NormalFlow_1 {

    @Test
    public void test_getBytesUtf16_validString() {
        String input = "Hello";
        byte[] expected = new byte[]{72, 0, 101, 0, 108, 0, 108, 0, 111, 0}; // UTF-16 encoding of "Hello"
        byte[] result = StringUtils.getBytesUtf16(input);
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void test_getBytesUtf16_anotherValidString() {
        String input = "Test";
        byte[] expected = new byte[]{84, 0, 101, 0, 115, 0, 116, 0}; // UTF-16 encoding of "Test"
        byte[] result = StringUtils.getBytesUtf16(input);
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void test_getBytesUtf16_specialCharacters() {
        String input = "Δ";
        byte[] expected = new byte[]{(byte) 206, 0, (byte) 144, 0}; // UTF-16 encoding of "Δ"
        byte[] result = StringUtils.getBytesUtf16(input);
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void test_getBytesUtf16_nullInput() {
        String input = null;
        byte[] result = StringUtils.getBytesUtf16(input);
        Assert.assertNull(result);
    }

    @Test
    public void test_getBytesUtf16_emptyString() {
        String input = "";
        byte[] expected = new byte[]{0, 0}; // UTF-16 encoding of an empty string
        byte[] result = StringUtils.getBytesUtf16(input);
        Assert.assertArrayEquals(expected, result);
    }
}
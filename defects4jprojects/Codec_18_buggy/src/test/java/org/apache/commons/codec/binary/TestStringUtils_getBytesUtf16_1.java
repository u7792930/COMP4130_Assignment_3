package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;
import org.apache.commons.codec.binary.StringUtils;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUtf16_1 {

    @Test
    public void test_getBytesUtf16_nullInput() {
        byte[] result = StringUtils.getBytesUtf16(null);
        Assert.assertNull(result);
    }

    @Test
    public void test_getBytesUtf16_emptyString() {
        byte[] result = StringUtils.getBytesUtf16("");
        Assert.assertArrayEquals(new byte[]{}, result);
    }

    @Test
    public void test_getBytesUtf16_validString() {
        String input = "Hello";
        byte[] expected = new byte[]{72, 0, 101, 0, 108, 0, 108, 0, 111, 0}; // UTF-16 encoding of "Hello"
        byte[] result = StringUtils.getBytesUtf16(input);
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void test_getBytesUtf16_specialCharacters() {
        String input = "你好"; // UTF-16 encoding of "你好"
        byte[] expected = new byte[]{-24, -100, -100, 0, -22, -70, -98, 0}; 
        byte[] result = StringUtils.getBytesUtf16(input);
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void test_getBytesUtf16_singleCharacter() {
        String input = "A"; // UTF-16 encoding of "A"
        byte[] expected = new byte[]{65, 0}; 
        byte[] result = StringUtils.getBytesUtf16(input);
        Assert.assertArrayEquals(expected, result);
    }
}
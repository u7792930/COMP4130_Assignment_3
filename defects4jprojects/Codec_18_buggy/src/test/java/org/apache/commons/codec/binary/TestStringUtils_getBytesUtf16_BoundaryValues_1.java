package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUtf16_BoundaryValues_1 {

    @Test
    public void test_getBytesUtf16_emptyString() {
        byte[] result = StringUtils.getBytesUtf16("");
        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.length); // UTF-16 encoding of an empty string is 2 bytes (0x0000)
        Assert.assertArrayEquals(new byte[]{0x00, 0x00}, result); // Correctly assert the content
    }

    @Test
    public void test_getBytesUtf16_singleCharacter() {
        byte[] result = StringUtils.getBytesUtf16("A");
        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.length); // UTF-16 encoding of 'A' is 2 bytes (0x0041)
        Assert.assertArrayEquals(new byte[]{0x41, 0x00}, result);
    }

    @Test
    public void test_getBytesUtf16_nullInput() {
        byte[] result = StringUtils.getBytesUtf16(null);
        Assert.assertNull(result); // Should return null for null input
    }

    @Test
    public void test_getBytesUtf16_maxCharacter() {
        byte[] result = StringUtils.getBytesUtf16(Character.toString((char) Character.MAX_VALUE));
        Assert.assertNotNull(result);
        Assert.assertEquals(2, result.length); // UTF-16 encoding of max char value
        Assert.assertArrayEquals(new byte[]{(byte) 0xFF, (byte) 0x7F}, result);
    }

    @Test
    public void test_getBytesUtf16_specialCharacters() {
        byte[] result = StringUtils.getBytesUtf16("€"); // Euro sign
        Assert.assertNotNull(result);
        Assert.assertEquals(4, result.length); // UTF-16 encoding of '€' is 4 bytes
        Assert.assertArrayEquals(new byte[]{(byte) 0x20, (byte) 0xAC, 0x00, 0x00}, result);
    }
}
package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUtf8_NormalFlow_1 {

    @Test
    public void test_getBytesUtf8_validString() {
        String input = "Hello, World!";
        byte[] expected = new byte[] { 72, 101, 108, 108, 111, 44, 32, 87, 111, 114, 108, 100, 33 };
        byte[] result = StringUtils.getBytesUtf8(input);
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void test_getBytesUtf8_anotherValidString() {
        String input = "Apache Commons Codec";
        byte[] expected = new byte[] { 65, 112, 97, 99, 104, 101, 32, 67, 111, 109, 109, 111, 110, 115, 32, 67, 111, 100, 101 };
        byte[] result = StringUtils.getBytesUtf8(input);
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void test_getBytesUtf8_emptyString() {
        String input = "";
        byte[] expected = new byte[0];
        byte[] result = StringUtils.getBytesUtf8(input);
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void test_getBytesUtf8_nullString() {
        String input = null;
        byte[] result = StringUtils.getBytesUtf8(input);
        Assert.assertNull(result);
    }
}
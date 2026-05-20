package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestStringUtils_newStringUtf16_1 {

    @Test
    public void test_newStringUtf16_nullInput() {
        String result = StringUtils.newStringUtf16(null);
        Assert.assertNull(result);
    }

    @Test
    public void test_newStringUtf16_emptyInput() {
        String result = StringUtils.newStringUtf16(new byte[0]);
        Assert.assertEquals("", result);
    }

    @Test
    public void test_newStringUtf16_validInput() {
        byte[] input = new byte[]{72, 0, 101, 0}; // "He" in UTF-16
        String result = StringUtils.newStringUtf16(input);
        Assert.assertEquals("He", result);
    }

    @Test
    public void test_newStringUtf16_validInput_withBOM() {
        byte[] input = new byte[]{(byte)0xFE, (byte)0xFF, 72, 0, 101, 0}; // "He" with BOM in UTF-16
        String result = StringUtils.newStringUtf16(input);
        Assert.assertEquals("He", result);
    }

    @Test
    public void test_newStringUtf16_validInput_withSpecialChars() {
        byte[] input = new byte[]{(byte)0xD8, (byte)0x00, (byte)0xDC, (byte)0x00}; // U+10000 in UTF-16
        String result = StringUtils.newStringUtf16(input);
        Assert.assertEquals("\uD800\uDC00", result);
    }
}
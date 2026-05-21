package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_newStringUtf8_NormalFlow_1 {

    @Test
    public void test_newStringUtf8_validBytes() {
        byte[] input = {72, 101, 108, 108, 111}; // "Hello" in UTF-8
        String expected = "Hello";
        String result = StringUtils.newStringUtf8(input);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void test_newStringUtf8_differentCharacters() {
        byte[] input = new byte[]{(byte) 0xE2, (byte) 0x82, (byte) 0xAC}; // Euro sign (€) in UTF-8
        String expected = "€";
        String result = StringUtils.newStringUtf8(input);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void test_newStringUtf8_anotherValidBytes() {
        byte[] input = {87, 111, 114, 108, 100}; // "World" in UTF-8
        String expected = "World";
        String result = StringUtils.newStringUtf8(input);
        Assert.assertEquals(expected, result);
    }
}
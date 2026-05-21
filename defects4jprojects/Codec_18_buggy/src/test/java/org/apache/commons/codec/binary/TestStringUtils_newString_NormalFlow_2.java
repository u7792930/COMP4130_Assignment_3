package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_newString_NormalFlow_2 {

    @Test
    public void test_newString_validInput() {
        byte[] input = {72, 101, 108, 108, 111}; // "Hello" in bytes
        String charsetName = "UTF-8";
        String result = StringUtils.newString(input, charsetName);
        Assert.assertEquals("Hello", result);
    }

    @Test
    public void test_newString_differentCharset() {
        byte[] input = new byte[]{(byte) 0xC3, (byte) 0xA9}; // "é" in UTF-8
        String charsetName = "UTF-8";
        String result = StringUtils.newString(input, charsetName);
        Assert.assertEquals("é", result);
    }

    @Test
    public void test_newString_withDifferentByteArray() {
        byte[] input = {65, 66, 67}; // "ABC" in bytes
        String charsetName = "UTF-8";
        String result = StringUtils.newString(input, charsetName);
        Assert.assertEquals("ABC", result);
    }
}
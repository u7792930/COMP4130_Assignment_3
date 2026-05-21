package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestStringUtils_newStringUtf16_NormalFlow_1 {

    @Test
    public void test_newStringUtf16_validInput() {
        byte[] input = new byte[]{(byte) 0xFF, (byte) 0xFE, 0x48, 0x00, 0x65, 0x00, 0x6C, 0x00, 0x6C, 0x00, 0x6F, 0x00}; // "Hello" in UTF-16
        String expected = "Hello";
        String result = StringUtils.newStringUtf16(input);
        assertEquals(expected, result);
    }

    @Test
    public void test_newStringUtf16_anotherValidInput() {
        byte[] input = new byte[]{(byte) 0xFF, (byte) 0xFE, 0x57, 0x00, 0x6F, 0x00, 0x72, 0x00, 0x6C, 0x00, 0x64, 0x00}; // "World" in UTF-16
        String expected = "World";
        String result = StringUtils.newStringUtf16(input);
        assertEquals(expected, result);
    }

    @Test
    public void test_newStringUtf16_nullInput() {
        byte[] input = null; 
        String result = StringUtils.newStringUtf16(input);
        assertNull(result);
    }

    @Test
    public void test_newStringUtf16_emptyInput() {
        byte[] input = new byte[]{(byte) 0xFF, (byte) 0xFE}; // Empty string in UTF-16
        String expected = "";
        String result = StringUtils.newStringUtf16(input);
        assertEquals(expected, result);
    }
}
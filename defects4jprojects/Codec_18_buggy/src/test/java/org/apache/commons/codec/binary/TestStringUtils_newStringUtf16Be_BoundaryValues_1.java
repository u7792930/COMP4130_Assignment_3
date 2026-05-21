package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_newStringUtf16Be_BoundaryValues_1 {

    @Test
    public void test_newStringUtf16Be_emptyArray() {
        byte[] input = new byte[0]; // boundary value: empty array
        String result = StringUtils.newStringUtf16Be(input);
        Assert.assertEquals("", result); // expecting an empty string
    }

    @Test
    public void test_newStringUtf16Be_singleCharacter() {
        byte[] input = new byte[] { 0x41, 0x00 }; // boundary value: single character 'A' in UTF-16BE
        String result = StringUtils.newStringUtf16Be(input);
        Assert.assertEquals("A", result); // expecting "A"
    }

    @Test
    public void test_newStringUtf16Be_nullArray() {
        byte[] input = null; // boundary value: null array
        String result = StringUtils.newStringUtf16Be(input);
        Assert.assertNull(result); // expecting null
    }

    @Test
    public void test_newStringUtf16Be_twoCharacters() {
        byte[] input = new byte[] { 0x41, 0x00, 0x42, 0x00 }; // boundary value: two characters 'AB' in UTF-16BE
        String result = StringUtils.newStringUtf16Be(input);
        Assert.assertEquals("AB", result); // expecting "AB"
    }

    @Test
    public void test_newStringUtf16Be_specialCharacters() {
        byte[] input = new byte[] { (byte) 0xE2, (byte) 0x82, (byte) 0xAC, 0x00 }; // boundary value: Euro sign '€' in UTF-16BE
        String result = StringUtils.newStringUtf16Be(input);
        Assert.assertEquals("€", result); // expecting "€"
    }
}
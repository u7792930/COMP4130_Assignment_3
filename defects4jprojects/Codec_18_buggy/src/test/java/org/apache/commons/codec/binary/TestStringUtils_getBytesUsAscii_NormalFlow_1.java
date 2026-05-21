package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUsAscii_NormalFlow_1 {

    @Test
    public void test_getBytesUsAscii_validInput() {
        String input = "Hello";
        byte[] expected = new byte[]{72, 101, 108, 108, 111}; // ASCII values for 'H', 'e', 'l', 'l', 'o'
        byte[] actual = StringUtils.getBytesUsAscii(input);
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void test_getBytesUsAscii_emptyString() {
        String input = "";
        byte[] expected = new byte[0]; // An empty string should return an empty byte array
        byte[] actual = StringUtils.getBytesUsAscii(input);
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void test_getBytesUsAscii_specialCharacters() {
        String input = "!@#$%^&*()"; // Special characters
        byte[] expected = new byte[]{33, 64, 35, 36, 37, 94, 38, 42, 40, 41}; // ASCII values for the special characters
        byte[] actual = StringUtils.getBytesUsAscii(input);
        Assert.assertArrayEquals(expected, actual);
    }
}
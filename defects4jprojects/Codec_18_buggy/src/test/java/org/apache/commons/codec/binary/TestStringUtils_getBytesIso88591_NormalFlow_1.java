package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

public class TestStringUtils_getBytesIso88591_NormalFlow_1 {

    @Test
    public void test_getBytesIso8859_1_validString() {
        String input = "Hello";
        byte[] expected = new byte[]{72, 101, 108, 108, 111}; // ISO-8859-1 encoded bytes for "Hello"
        byte[] actual = StringUtils.getBytesIso8859_1(input);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void test_getBytesIso8859_1_anotherValidString() {
        String input = "ISO-8859-1 Test";
        byte[] expected = new byte[]{73, 83, 79, 45, 98, 97, 115, 101, 109, 101, 45, 49, 32, 84, 101, 115, 116}; // ISO-8859-1 encoded bytes
        byte[] actual = StringUtils.getBytesIso8859_1(input);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void test_getBytesIso8859_1_specialCharacters() {
        String input = "éèêë";
        byte[] expected = new byte[]{(byte) 0xE9, (byte) 0xE8, (byte) 0xEA, (byte) 0xEB}; // ISO-8859-1 encoded bytes for "éèêë"
        byte[] actual = StringUtils.getBytesIso8859_1(input);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void test_getBytesIso8859_1_nullInput() {
        String input = null;
        byte[] actual = StringUtils.getBytesIso8859_1(input);
        assertNull(actual); // Expecting null for null input
    }
}
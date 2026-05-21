package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_newStringIso88591_BoundaryValues_1 {

    @Test
    public void test_newStringIso8859_1_emptyArray() {
        byte[] input = {};
        String result = StringUtils.newStringIso8859_1(input);
        assertEquals("", result);
    }

    @Test
    public void test_newStringIso8859_1_singleCharacter() {
        byte[] input = {65}; // ASCII for 'A'
        String result = StringUtils.newStringIso8859_1(input);
        assertEquals("A", result);
    }

    @Test
    public void test_newStringIso8859_1_nullArray() {
        byte[] input = null;
        String result = StringUtils.newStringIso8859_1(input);
        assertNull(result);
    }

    @Test
    public void test_newStringIso8859_1_largeArray() {
        byte[] input = new byte[256]; // create a byte array with 256 elements
        for (int i = 0; i < input.length; i++) {
            input[i] = (byte) i; // fill with values from 0 to 255
        }
        String result = StringUtils.newStringIso8859_1(input);
        assertEquals(new String(input, java.nio.charset.StandardCharsets.ISO_8859_1), result);
    }
}
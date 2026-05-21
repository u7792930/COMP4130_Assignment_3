package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.*;

public class TestStringUtils_newStringUtf8_BoundaryValues_1 {

    @Test
    public void test_newStringUtf8_emptyArray() {
        byte[] input = new byte[0];
        String result = StringUtils.newStringUtf8(input);
        assertEquals("", result);
    }

    @Test
    public void test_newStringUtf8_singleCharacter() {
        byte[] input = new byte[]{65}; // ASCII for 'A'
        String result = StringUtils.newStringUtf8(input);
        assertEquals("A", result);
    }

    @Test
    public void test_newStringUtf8_nullArray() {
        byte[] input = null;
        String result = StringUtils.newStringUtf8(input);
        assertNull(result);
    }
}
package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.*;

public class TestStringUtils_newStringUtf16_BoundaryValues_1 {

    @Test
    public void test_newStringUtf16_emptyArray() {
        String result = StringUtils.newStringUtf16(new byte[0]);
        assertEquals("", result);
    }

    @Test
    public void test_newStringUtf16_singleCharacter() {
        byte[] input = new byte[] { 0x00, 0x61 }; // UTF-16 for 'a'
        String result = StringUtils.newStringUtf16(input);
        assertEquals("a", result);
    }

    @Test
    public void test_newStringUtf16_nullArray() {
        String result = StringUtils.newStringUtf16(null);
        assertNull(result);
    }
}
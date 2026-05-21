package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUnchecked_BoundaryValues_1 {

    @Test
    public void test_getBytesUnchecked_nullString() {
        byte[] result = StringUtils.getBytesUnchecked(null, "UTF-8");
        assertNull(result);
    }

    @Test
    public void test_getBytesUnchecked_singleCharacterString() {
        byte[] result = StringUtils.getBytesUnchecked("A", "UTF-8");
        byte[] expected = new byte[]{65}; // ASCII for 'A'
        assertArrayEquals(expected, result);
    }

    @Test
    public void test_getBytesUnchecked_emptyString() {
        byte[] result = StringUtils.getBytesUnchecked("", "UTF-8");
        byte[] expected = new byte[]{}; // Empty byte array
        assertArrayEquals(expected, result);
    }
}
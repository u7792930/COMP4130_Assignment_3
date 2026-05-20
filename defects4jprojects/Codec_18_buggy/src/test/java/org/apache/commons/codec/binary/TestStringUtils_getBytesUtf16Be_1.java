package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUtf16Be_1 {

    @Test
    public void test_getBytesUtf16Be_nullInput() {
        byte[] result = StringUtils.getBytesUtf16Be(null);
        assertNull(result);
    }

    @Test
    public void test_getBytesUtf16Be_emptyString() {
        byte[] expected = new byte[]{};
        byte[] result = StringUtils.getBytesUtf16Be("");
        assertArrayEquals(expected, result);
    }

    @Test
    public void test_getBytesUtf16Be_validString() {
        String input = "Hello";
        byte[] expected = new byte[]{0, 'H', 0, 'e', 0, 'l', 0, 'l', 0, 'o'};
        byte[] result = StringUtils.getBytesUtf16Be(input);
        assertArrayEquals(expected, result);
    }
}
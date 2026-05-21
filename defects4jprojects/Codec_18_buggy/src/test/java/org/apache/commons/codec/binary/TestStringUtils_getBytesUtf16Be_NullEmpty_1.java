package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUtf16Be_NullEmpty_1 {

    @Test
    public void test_getBytesUtf16Be_nullInput() {
        byte[] result = StringUtils.getBytesUtf16Be(null);
        assertNull("Expected null when input is null", result);
    }

    @Test
    public void test_getBytesUtf16Be_emptyString() {
        byte[] result = StringUtils.getBytesUtf16Be("");
        assertArrayEquals("Expected empty byte array for empty string", new byte[0], result);
    }

    @Test
    public void test_getBytesUtf16Be_nonEmptyString() {
        byte[] result = StringUtils.getBytesUtf16Be("Hello");
        byte[] expected = new byte[] { 0, 'H', 0, 'e', 0, 'l', 0, 'l', 0, 'o' };
        assertArrayEquals("Expected UTF-16BE encoded byte array for 'Hello'", expected, result);
    }
}
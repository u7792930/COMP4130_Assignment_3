package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;
import org.apache.commons.codec.binary.StringUtils;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUtf16Le_1 {

    @Test
    public void test_getBytesUtf16Le_nullString() {
        byte[] result = StringUtils.getBytesUtf16Le(null);
        Assert.assertNull(result);
    }

    @Test
    public void test_getBytesUtf16Le_emptyString() {
        byte[] result = StringUtils.getBytesUtf16Le("");
        byte[] expected = new byte[]{0, 0}; // UTF-16LE encoding of an empty string
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void test_getBytesUtf16Le_nonEmptyString() {
        String input = "Hello";
        byte[] result = StringUtils.getBytesUtf16Le(input);
        byte[] expected = new byte[]{72, 0, 101, 0, 108, 0, 108, 0, 111, 0}; // UTF-16LE encoding of "Hello"
        Assert.assertArrayEquals(expected, result);
    }
}
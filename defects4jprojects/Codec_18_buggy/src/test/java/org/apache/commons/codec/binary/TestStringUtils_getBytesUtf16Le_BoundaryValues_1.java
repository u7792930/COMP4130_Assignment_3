package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUtf16Le_BoundaryValues_1 {

    @Test
    public void test_getBytesUtf16Le_singleCharacterString() {
        byte[] result = StringUtils.getBytesUtf16Le("A");
        byte[] expected = new byte[] {65, 0}; // UTF-16LE encoding for 'A'
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void test_getBytesUtf16Le_emptyString() {
        byte[] result = StringUtils.getBytesUtf16Le("");
        byte[] expected = new byte[] {}; // UTF-16LE encoding for empty string
        Assert.assertArrayEquals(expected, result);
    }

    @Test
    public void test_getBytesUtf16Le_nullString() {
        byte[] result = StringUtils.getBytesUtf16Le(null);
        Assert.assertNull(result); // Expecting null for null input
    }
}
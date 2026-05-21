package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUtf16Le_NullEmpty_1 {

    @Test
    public void test_getBytesUtf16Le_nullInput() {
        byte[] result = StringUtils.getBytesUtf16Le(null);
        Assert.assertNull("Expected null for null input", result);
    }

    @Test
    public void test_getBytesUtf16Le_emptyString() {
        byte[] result = StringUtils.getBytesUtf16Le("");
        Assert.assertArrayEquals("Expected empty byte array for empty input", new byte[0], result);
    }

    @Test
    public void test_getBytesUtf16Le_nonEmptyString() {
        byte[] result = StringUtils.getBytesUtf16Le("test");
        byte[] expected = new byte[] { 't', 0, 'e', 0, 's', 0, 't', 0 }; // UTF-16LE encoding
        Assert.assertArrayEquals("Expected byte array for non-empty input", expected, result);
    }
}
package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUtf16Be_BoundaryValues_1 {

    @Test
    public void test_getBytesUtf16Be_emptyString() {
        byte[] result = StringUtils.getBytesUtf16Be("");
        Assert.assertArrayEquals(new byte[]{0, 0}, result);
    }

    @Test
    public void test_getBytesUtf16Be_singleCharacter() {
        byte[] result = StringUtils.getBytesUtf16Be("A");
        Assert.assertArrayEquals(new byte[]{0, 65, 0, 0}, result);
    }

    @Test
    public void test_getBytesUtf16Be_nullString() {
        byte[] result = StringUtils.getBytesUtf16Be(null);
        Assert.assertNull(result);
    }

    @Test
    public void test_getBytesUtf16Be_multipleCharacters() {
        byte[] result = StringUtils.getBytesUtf16Be("AB");
        Assert.assertArrayEquals(new byte[]{0, 65, 0, 66, 0, 0}, result);
    }

    @Test
    public void test_getBytesUtf16Be_specialCharacters() {
        byte[] result = StringUtils.getBytesUtf16Be("你好");
        Assert.assertArrayEquals(new byte[]{-48, -28, -96, -27, 0, 0}, result);
    }
}
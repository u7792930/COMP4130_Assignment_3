package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUtf8_NullEmpty_1 {

    @Test
    public void test_getBytesUtf8_nullInput() {
        byte[] result = StringUtils.getBytesUtf8(null);
        Assert.assertNull(result);
    }

    @Test
    public void test_getBytesUtf8_emptyString() {
        byte[] result = StringUtils.getBytesUtf8("");
        Assert.assertArrayEquals(new byte[0], result);
    }

    @Test
    public void test_getBytesUtf8_nonEmptyString() {
        byte[] result = StringUtils.getBytesUtf8("Hello");
        Assert.assertArrayEquals(new byte[] { 72, 101, 108, 108, 111 }, result);
    }
}
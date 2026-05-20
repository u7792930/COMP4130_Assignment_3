package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;

import org.apache.commons.codec.binary.StringUtils;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUsAscii_1 {

    @Test
    public void test_getBytesUsAscii_nullString() {
        byte[] result = StringUtils.getBytesUsAscii(null);
        Assert.assertNull(result);
    }

    @Test
    public void test_getBytesUsAscii_emptyString() {
        byte[] result = StringUtils.getBytesUsAscii("");
        Assert.assertArrayEquals(new byte[0], result);
    }

    @Test
    public void test_getBytesUsAscii_validString() {
        byte[] result = StringUtils.getBytesUsAscii("Hello");
        Assert.assertArrayEquals(new byte[]{72, 101, 108, 108, 111}, result);
    }
}
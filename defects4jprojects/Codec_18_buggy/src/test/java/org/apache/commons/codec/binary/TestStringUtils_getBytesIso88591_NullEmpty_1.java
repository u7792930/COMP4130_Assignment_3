package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesIso88591_NullEmpty_1 {

    @Test
    public void test_getBytesIso8859_1_nullInput() {
        byte[] result = StringUtils.getBytesIso8859_1(null);
        Assert.assertNull(result);
    }

    @Test
    public void test_getBytesIso8859_1_emptyString() {
        byte[] result = StringUtils.getBytesIso8859_1("");
        Assert.assertArrayEquals(new byte[0], result);
    }

    @Test
    public void test_getBytesIso8859_1_nonEmptyString() {
        byte[] result = StringUtils.getBytesIso8859_1("Hello");
        Assert.assertArrayEquals(new byte[]{72, 101, 108, 108, 111}, result); // ASCII values for "Hello"
    }
}
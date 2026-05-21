package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUtf8_BoundaryValues_1 {

    @Test
    public void test_getBytesUtf8_emptyString() {
        byte[] result = StringUtils.getBytesUtf8("");
        Assert.assertArrayEquals(new byte[0], result);
    }

    @Test
    public void test_getBytesUtf8_singleCharacter() {
        byte[] result = StringUtils.getBytesUtf8("A");
        Assert.assertArrayEquals(new byte[]{65}, result); // ASCII for 'A'
    }

    @Test
    public void test_getBytesUtf8_nullString() {
        byte[] result = StringUtils.getBytesUtf8(null);
        Assert.assertNull(result);
    }
}
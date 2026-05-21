package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUsAscii_BoundaryValues_1 {

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
    public void test_getBytesUsAscii_singleCharacterString() {
        byte[] result = StringUtils.getBytesUsAscii("A");
        Assert.assertArrayEquals(new byte[]{65}, result); // ASCII value of 'A' is 65
    }
}
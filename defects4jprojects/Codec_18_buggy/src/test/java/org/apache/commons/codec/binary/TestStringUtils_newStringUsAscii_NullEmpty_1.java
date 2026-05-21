package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_newStringUsAscii_NullEmpty_1 {

    @Test
    public void test_newStringUsAscii_nullInput() {
        String result = StringUtils.newStringUsAscii(null);
        Assert.assertNull(result);
    }

    @Test
    public void test_newStringUsAscii_emptyArray() {
        String result = StringUtils.newStringUsAscii(new byte[0]);
        Assert.assertEquals("", result);
    }

    @Test
    public void test_newStringUsAscii_nonEmptyArray() {
        byte[] input = new byte[]{65, 66, 67}; // ASCII for 'ABC'
        String result = StringUtils.newStringUsAscii(input);
        Assert.assertEquals("ABC", result);
    }
}
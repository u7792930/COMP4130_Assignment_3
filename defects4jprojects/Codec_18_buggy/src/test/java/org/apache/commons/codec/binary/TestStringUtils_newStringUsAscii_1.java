package org.apache.commons.codec.binary;

import org.apache.commons.codec.binary.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_newStringUsAscii_1 {

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
    public void test_newStringUsAscii_validInput() {
        byte[] input = new byte[]{72, 101, 108, 108, 111}; // ASCII for "Hello"
        String result = StringUtils.newStringUsAscii(input);
        Assert.assertEquals("Hello", result);
    }
}
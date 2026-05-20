package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;
import org.apache.commons.codec.binary.StringUtils;
import static org.junit.Assert.*;

public class TestStringUtils_newStringUtf16Be_1 {

    @Test
    public void test_newStringUtf16Be_nullInput() {
        String result = StringUtils.newStringUtf16Be(null);
        Assert.assertNull(result);
    }

    @Test
    public void test_newStringUtf16Be_emptyInput() {
        String result = StringUtils.newStringUtf16Be(new byte[0]);
        Assert.assertEquals("", result);
    }

    @Test
    public void test_newStringUtf16Be_validInput() {
        byte[] input = new byte[]{0, 65}; // Represents "A" in UTF-16BE
        String result = StringUtils.newStringUtf16Be(input);
        Assert.assertEquals("A", result);
    }
}
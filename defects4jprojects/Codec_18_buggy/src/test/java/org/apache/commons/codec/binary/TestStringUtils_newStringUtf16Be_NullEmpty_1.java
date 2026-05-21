package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_newStringUtf16Be_NullEmpty_1 {

    @Test
    public void test_newStringUtf16Be_nullInput() {
        String result = StringUtils.newStringUtf16Be(null);
        Assert.assertNull("Expected null when input is null", result);
    }

    @Test
    public void test_newStringUtf16Be_emptyArray() {
        String result = StringUtils.newStringUtf16Be(new byte[0]);
        Assert.assertEquals("Expected empty string when input is empty array", "", result);
    }

    @Test
    public void test_newStringUtf16Be_emptyString() {
        String result = StringUtils.newStringUtf16Be(new byte[]{});
        Assert.assertEquals("Expected empty string when input is empty byte array", "", result);
    }
}
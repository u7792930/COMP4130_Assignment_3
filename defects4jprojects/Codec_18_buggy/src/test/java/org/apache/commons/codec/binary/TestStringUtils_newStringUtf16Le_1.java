package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestStringUtils_newStringUtf16Le_1 {

    @Test
    public void test_newStringUtf16Le_nullInput() {
        String result = StringUtils.newStringUtf16Le(null);
        Assert.assertNull(result);
    }

    @Test
    public void test_newStringUtf16Le_emptyInput() {
        String result = StringUtils.newStringUtf16Le(new byte[0]);
        Assert.assertEquals("", result);
    }

    @Test
    public void test_newStringUtf16Le_validInput() {
        byte[] input = new byte[]{67, 0, 111, 0, 100, 0, 101, 0}; // "Code" in UTF-16LE
        String result = StringUtils.newStringUtf16Le(input);
        Assert.assertEquals("Code", result);
    }
}
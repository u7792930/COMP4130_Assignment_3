package org.apache.commons.codec.binary;

import org.apache.commons.codec.binary.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_newStringIso88591_1 {

    @Test
    public void test_newStringIso8859_1_nullInput() {
        byte[] input = null;
        String result = StringUtils.newStringIso8859_1(input);
        Assert.assertNull(result);
    }

    @Test
    public void test_newStringIso8859_1_emptyArray() {
        byte[] input = new byte[0];
        String result = StringUtils.newStringIso8859_1(input);
        Assert.assertEquals("", result);
    }

    @Test
    public void test_newStringIso8859_1_validInput() {
        byte[] input = new byte[]{65, 66, 67}; // Corresponds to "ABC" in ISO-8859-1
        String result = StringUtils.newStringIso8859_1(input);
        Assert.assertEquals("ABC", result);
    }
}
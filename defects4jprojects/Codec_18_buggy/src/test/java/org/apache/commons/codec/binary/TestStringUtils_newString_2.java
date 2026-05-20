package org.apache.commons.codec.binary;

import org.apache.commons.codec.binary.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_newString_2 {

    @Test
    public void test_newString_nullBytes() {
        String result = StringUtils.newString(null, "UTF-8");
        Assert.assertNull(result);
    }

    @Test
    public void test_newString_validInput() {
        byte[] bytes = "Hello".getBytes();
        String result = StringUtils.newString(bytes, "UTF-8");
        Assert.assertEquals("Hello", result);
    }

    @Test
    public void test_newString_invalidCharset() {
        byte[] bytes = "Hello".getBytes();
        try {
            StringUtils.newString(bytes, "invalid-charset");
            Assert.fail("Expected IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
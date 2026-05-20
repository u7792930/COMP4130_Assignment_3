package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.Charset;

public class TestStringUtils_getBytes_1 {

    @Test
    public void test_getBytes_nullInput() {
        byte[] result = StringUtilsTestHelper.getBytes(null, Charset.forName("UTF-8"));
        Assert.assertNull(result);
    }

    @Test
    public void test_getBytes_emptyString() {
        byte[] result = StringUtilsTestHelper.getBytes("", Charset.forName("UTF-8"));
        Assert.assertArrayEquals(new byte[0], result);
    }

    @Test
    public void test_getBytes_validString() {
        byte[] result = StringUtilsTestHelper.getBytes("Hello", Charset.forName("UTF-8"));
        Assert.assertArrayEquals(new byte[]{72, 101, 108, 108, 111}, result);
    }
}

class StringUtilsTestHelper {
    static byte[] getBytes(final String string, final Charset charset) {
        // Call the private method using reflection
        try {
            java.lang.reflect.Method method = org.apache.commons.codec.binary.StringUtils.class.getDeclaredMethod("getBytes", String.class, Charset.class);
            method.setAccessible(true);
            return (byte[]) method.invoke(null, string, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
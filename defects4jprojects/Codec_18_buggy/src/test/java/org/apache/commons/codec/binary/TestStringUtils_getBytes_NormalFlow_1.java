package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.Charset;
import java.lang.reflect.Method;

public class TestStringUtils_getBytes_NormalFlow_1 {

    private byte[] invokeGetBytes(String input, Charset charset) throws Exception {
        Method method = StringUtils.class.getDeclaredMethod("getBytes", String.class, Charset.class);
        method.setAccessible(true);
        return (byte[]) method.invoke(null, input, charset);
    }

    @Test
    public void test_getBytes_validString_withUTF8Charset() throws Exception {
        String input = "Hello, World!";
        Charset charset = Charset.forName("UTF-8");
        byte[] expected = input.getBytes(charset);
        byte[] actual = invokeGetBytes(input, charset);
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void test_getBytes_validString_withISOCharset() throws Exception {
        String input = "Hello, World!";
        Charset charset = Charset.forName("ISO-8859-1");
        byte[] expected = input.getBytes(charset);
        byte[] actual = invokeGetBytes(input, charset);
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void test_getBytes_validString_withAsciiCharset() throws Exception {
        String input = "Hello, World!";
        Charset charset = Charset.forName("US-ASCII");
        byte[] expected = input.getBytes(charset);
        byte[] actual = invokeGetBytes(input, charset);
        Assert.assertArrayEquals(expected, actual);
    }
}
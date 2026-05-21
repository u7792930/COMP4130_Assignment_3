package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.lang.reflect.Method;

public class TestStringUtils_getByteBuffer_NormalFlow_1 {

    private ByteBuffer invokeGetByteBuffer(String string, Charset charset) throws Exception {
        Method method = StringUtils.class.getDeclaredMethod("getByteBuffer", String.class, Charset.class);
        method.setAccessible(true);
        return (ByteBuffer) method.invoke(null, string, charset);
    }

    @Test
    public void test_getByteBuffer_validStringAndCharset() throws Exception {
        String input = "Hello, World!";
        Charset charset = Charset.forName("UTF-8");
        ByteBuffer result = invokeGetByteBuffer(input, charset);
        byte[] expectedBytes = input.getBytes(charset);
        Assert.assertNotNull(result);
        Assert.assertArrayEquals(expectedBytes, result.array());
    }

    @Test
    public void test_getByteBuffer_anotherValidStringAndCharset() throws Exception {
        String input = "Test String";
        Charset charset = Charset.forName("UTF-16");
        ByteBuffer result = invokeGetByteBuffer(input, charset);
        byte[] expectedBytes = input.getBytes(charset);
        Assert.assertNotNull(result);
        Assert.assertArrayEquals(expectedBytes, result.array());
    }

    @Test
    public void test_getByteBuffer_emptyStringAndCharset() throws Exception {
        String input = "";
        Charset charset = Charset.forName("UTF-8");
        ByteBuffer result = invokeGetByteBuffer(input, charset);
        byte[] expectedBytes = input.getBytes(charset);
        Assert.assertNotNull(result);
        Assert.assertArrayEquals(expectedBytes, result.array());
    }
}
package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class TestStringUtils_getByteBuffer_1 {

    @Test
    public void test_getByteBuffer_nullString() {
        // Test for null input
        ByteBuffer result = invokeGetByteBuffer(null, Charset.defaultCharset());
        Assert.assertNull(result);
    }

    @Test
    public void test_getByteBuffer_emptyString() {
        // Test for empty string
        ByteBuffer result = invokeGetByteBuffer("", Charset.defaultCharset());
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.remaining());
    }

    @Test
    public void test_getByteBuffer_nonEmptyString() {
        // Test for a non-empty string
        String input = "Hello";
        ByteBuffer result = invokeGetByteBuffer(input, Charset.defaultCharset());
        Assert.assertNotNull(result);
        Assert.assertEquals(5, result.remaining());
    }

    private ByteBuffer invokeGetByteBuffer(String string, Charset charset) {
        try {
            java.lang.reflect.Method method = StringUtils.class.getDeclaredMethod("getByteBuffer", String.class, Charset.class);
            method.setAccessible(true);
            return (ByteBuffer) method.invoke(null, string, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
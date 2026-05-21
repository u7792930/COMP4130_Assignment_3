package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class TestStringUtils_getByteBuffer_NullEmpty_1 {

    @Test
    public void test_getByteBuffer_nullString() throws Exception {
        ByteBuffer result = invokeGetByteBuffer(null, Charset.defaultCharset());
        Assert.assertNull(result);
    }

    @Test
    public void test_getByteBuffer_emptyString() throws Exception {
        ByteBuffer result = invokeGetByteBuffer("", Charset.defaultCharset());
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.remaining());
    }

    @Test
    public void test_getByteBuffer_emptyCharset() throws Exception {
        ByteBuffer result = invokeGetByteBuffer("test", Charset.forName("UTF-8"));
        Assert.assertNotNull(result);
        Assert.assertEquals(4, result.remaining());
    }

    private ByteBuffer invokeGetByteBuffer(String string, Charset charset) throws Exception {
        java.lang.reflect.Method method = StringUtils.class.getDeclaredMethod("getByteBuffer", String.class, Charset.class);
        method.setAccessible(true);
        return (ByteBuffer) method.invoke(null, string, charset);
    }
}
package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.lang.reflect.Method;

public class TestStringUtils_getByteBuffer_BoundaryValues_1 {

    private ByteBuffer invokeGetByteBuffer(String input, Charset charset) throws Exception {
        Method method = StringUtils.class.getDeclaredMethod("getByteBuffer", String.class, Charset.class);
        method.setAccessible(true);
        return (ByteBuffer) method.invoke(null, input, charset);
    }

    @Test
    public void test_getByteBuffer_singleCharacterString() throws Exception {
        String input = "A";
        Charset charset = Charset.forName("UTF-8");
        ByteBuffer result = invokeGetByteBuffer(input, charset);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.remaining());
    }

    @Test
    public void test_getByteBuffer_emptyString() throws Exception {
        String input = "";
        Charset charset = Charset.forName("UTF-8");
        ByteBuffer result = invokeGetByteBuffer(input, charset);
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.remaining());
    }

    @Test
    public void test_getByteBuffer_nullString() throws Exception {
        String input = null;
        Charset charset = Charset.forName("UTF-8");
        ByteBuffer result = invokeGetByteBuffer(input, charset);
        Assert.assertNull(result);
    }
}
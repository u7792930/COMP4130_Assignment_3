package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.Charset;
import java.lang.reflect.Method;

public class TestStringUtils_getBytes_BoundaryValues_1 {

    private byte[] invokeGetBytes(String string, Charset charset) throws Exception {
        Method method = StringUtils.class.getDeclaredMethod("getBytes", String.class, Charset.class);
        method.setAccessible(true);
        return (byte[]) method.invoke(null, string, charset);
    }

    @Test
    public void test_getBytes_singleCharacterString() throws Exception {
        String string = "a";
        Charset charset = Charset.forName("UTF-8");
        byte[] result = invokeGetBytes(string, charset);
        Assert.assertArrayEquals(new byte[]{97}, result);
    }

    @Test
    public void test_getBytes_nullString() throws Exception {
        String string = null;
        Charset charset = Charset.forName("UTF-8");
        byte[] result = invokeGetBytes(string, charset);
        Assert.assertNull(result);
    }

    @Test
    public void test_getBytes_emptyString() throws Exception {
        String string = "";
        Charset charset = Charset.forName("UTF-8");
        byte[] result = invokeGetBytes(string, charset);
        Assert.assertArrayEquals(new byte[]{}, result);
    }
}
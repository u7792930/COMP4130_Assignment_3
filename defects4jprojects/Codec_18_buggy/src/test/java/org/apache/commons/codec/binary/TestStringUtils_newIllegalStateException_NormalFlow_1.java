package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

public class TestStringUtils_newIllegalStateException_NormalFlow_1 {

    @Test
    public void test_newIllegalStateException_validInputs() throws Exception {
        String charsetName = "UTF-8";
        UnsupportedEncodingException exception = new UnsupportedEncodingException("Unsupported Charset");

        Method method = StringUtils.class.getDeclaredMethod("newIllegalStateException", String.class, UnsupportedEncodingException.class);
        method.setAccessible(true);
        IllegalStateException result = (IllegalStateException) method.invoke(null, charsetName, exception);

        Assert.assertNotNull("Expected IllegalStateException to be not null", result);
        Assert.assertEquals("Expected exception message to match", "UTF-8: Unsupported Charset", result.getMessage());
    }

    @Test
    public void test_newIllegalStateException_differentCharset() throws Exception {
        String charsetName = "ISO-8859-1";
        UnsupportedEncodingException exception = new UnsupportedEncodingException("Unsupported Charset");

        Method method = StringUtils.class.getDeclaredMethod("newIllegalStateException", String.class, UnsupportedEncodingException.class);
        method.setAccessible(true);
        IllegalStateException result = (IllegalStateException) method.invoke(null, charsetName, exception);

        Assert.assertNotNull("Expected IllegalStateException to be not null", result);
        Assert.assertEquals("Expected exception message to match", "ISO-8859-1: Unsupported Charset", result.getMessage());
    }

    @Test
    public void test_newIllegalStateException_otherExceptionMessage() throws Exception {
        String charsetName = "windows-1252";
        UnsupportedEncodingException exception = new UnsupportedEncodingException("Another issue");

        Method method = StringUtils.class.getDeclaredMethod("newIllegalStateException", String.class, UnsupportedEncodingException.class);
        method.setAccessible(true);
        IllegalStateException result = (IllegalStateException) method.invoke(null, charsetName, exception);

        Assert.assertNotNull("Expected IllegalStateException to be not null", result);
        Assert.assertEquals("Expected exception message to match", "windows-1252: Another issue", result.getMessage());
    }
}
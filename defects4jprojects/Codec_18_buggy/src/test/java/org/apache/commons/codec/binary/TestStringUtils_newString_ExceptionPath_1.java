package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestStringUtils_newString_ExceptionPath_1 {

    @Test
    public void test_newString_unsupportedEncodingException() {
        byte[] bytes = "test".getBytes();
        String charsetName = "unsupportedCharset"; // This should trigger the exception

        try {
            StringUtils.newString(bytes, charsetName);
            Assert.fail("Expected IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
    
    @Test
    public void test_newString_nullByteArray() {
        byte[] bytes = null;
        String charsetName = "UTF-8";
        
        String result = StringUtils.newString(bytes, charsetName);
        Assert.assertNull(result);
    }
    
    @Test
    public void test_newString_validInputs() {
        byte[] bytes = "Hello".getBytes();
        String charsetName = "UTF-8";
        
        String result = StringUtils.newString(bytes, charsetName);
        Assert.assertEquals("Hello", result);
    }
}
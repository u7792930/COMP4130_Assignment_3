package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

public class TestStringUtils_newIllegalStateException_NullEmpty_1 {

    @Test
    public void test_newIllegalStateException_nullCharsetName() {
        UnsupportedEncodingException exception = new UnsupportedEncodingException("UTF-8");
        try {
            Method method = StringUtils.class.getDeclaredMethod("newIllegalStateException", String.class, UnsupportedEncodingException.class);
            method.setAccessible(true);
            method.invoke(null, null, exception);
            Assert.fail("Expected IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            Assert.assertEquals("null: UTF-8", e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void test_newIllegalStateException_emptyCharsetName() {
        UnsupportedEncodingException exception = new UnsupportedEncodingException("UTF-8");
        try {
            Method method = StringUtils.class.getDeclaredMethod("newIllegalStateException", String.class, UnsupportedEncodingException.class);
            method.setAccessible(true);
            method.invoke(null, "", exception);
            Assert.fail("Expected IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            Assert.assertEquals(": UTF-8", e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void test_newIllegalStateException_nullException() {
        try {
            Method method = StringUtils.class.getDeclaredMethod("newIllegalStateException", String.class, UnsupportedEncodingException.class);
            method.setAccessible(true);
            method.invoke(null, "UTF-8", null);
            Assert.fail("Expected IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            Assert.assertEquals("UTF-8: null", e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }
}
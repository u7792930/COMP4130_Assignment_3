package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;

import java.io.UnsupportedEncodingException;

public class TestStringUtils_newIllegalStateException_1 {

    @Test
    public void test_newIllegalStateException_nullCharsetName() {
        UnsupportedEncodingException e = new UnsupportedEncodingException("UTF-8");
        IllegalStateException exception = new IllegalStateException("null: " + e.getMessage());
        Assert.assertEquals("null: UTF-8", exception.getMessage());
    }

    @Test
    public void test_newIllegalStateException_emptyCharsetName() {
        UnsupportedEncodingException e = new UnsupportedEncodingException("UTF-8");
        IllegalStateException exception = new IllegalStateException(": " + e.getMessage());
        Assert.assertEquals(": UTF-8", exception.getMessage());
    }

    @Test
    public void test_newIllegalStateException_validCharsetName() {
        UnsupportedEncodingException e = new UnsupportedEncodingException("UTF-8");
        IllegalStateException exception = new IllegalStateException("UTF-8: " + e.getMessage());
        Assert.assertEquals("UTF-8: UTF-8", exception.getMessage());
    }
}
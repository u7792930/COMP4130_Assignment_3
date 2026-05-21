package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;

import java.io.UnsupportedEncodingException;

public class TestStringUtils_newIllegalStateException_BoundaryValues_1 {

    @Test
    public void test_newIllegalStateException_emptyString() {
        String charsetName = "";
        UnsupportedEncodingException e = new UnsupportedEncodingException("Unsupported encoding");
        IllegalStateException exception = new IllegalStateException(charsetName + ": " + e.getMessage());
        Assert.assertEquals(": Unsupported encoding", exception.getMessage());
    }

    @Test
    public void test_newIllegalStateException_singleCharacterString() {
        String charsetName = "A";
        UnsupportedEncodingException e = new UnsupportedEncodingException("Unsupported encoding");
        IllegalStateException exception = new IllegalStateException(charsetName + ": " + e.getMessage());
        Assert.assertEquals("A: Unsupported encoding", exception.getMessage());
    }

    @Test
    public void test_newIllegalStateException_longString() {
        StringBuilder charsetNameBuilder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            charsetNameBuilder.append("A");
        }
        String charsetName = charsetNameBuilder.toString();
        UnsupportedEncodingException e = new UnsupportedEncodingException("Unsupported encoding");
        IllegalStateException exception = new IllegalStateException(charsetName + ": " + e.getMessage());
        Assert.assertEquals(charsetName + ": Unsupported encoding", exception.getMessage());
    }
}
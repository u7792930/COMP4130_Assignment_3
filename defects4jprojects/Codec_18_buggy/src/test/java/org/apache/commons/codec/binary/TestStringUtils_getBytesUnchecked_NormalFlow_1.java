package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;

public class TestStringUtils_getBytesUnchecked_NormalFlow_1 {

    @Test
    public void test_getBytesUnchecked_validStringWithValidCharset() {
        String input = "Hello, World!";
        String charsetName = "UTF-8";
        byte[] expected = null;
        try {
            expected = input.getBytes(charsetName);
        } catch (java.io.UnsupportedEncodingException e) {
            // This should not happen for UTF-8
            throw new RuntimeException(e);
        }
        byte[] actual = StringUtils.getBytesUnchecked(input, charsetName);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void test_getBytesUnchecked_validStringWithAnotherValidCharset() {
        String input = "こんにちは";
        String charsetName = "UTF-8";
        byte[] expected = null;
        try {
            expected = input.getBytes(charsetName);
        } catch (java.io.UnsupportedEncodingException e) {
            // This should not happen for UTF-8
            throw new RuntimeException(e);
        }
        byte[] actual = StringUtils.getBytesUnchecked(input, charsetName);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void test_getBytesUnchecked_nullString() {
        String input = null;
        String charsetName = "UTF-8";
        byte[] actual = StringUtils.getBytesUnchecked(input, charsetName);
        assertNull(actual);
    }
}
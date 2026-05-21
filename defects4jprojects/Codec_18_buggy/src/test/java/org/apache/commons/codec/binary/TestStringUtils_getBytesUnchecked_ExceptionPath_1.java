package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_getBytesUnchecked_ExceptionPath_1 {

    @Test
    public void test_getBytesUnchecked_unsupportedEncodingException() {
        String invalidCharset = "invalidCharset";
        String inputString = "test";

        try {
            StringUtils.getBytesUnchecked(inputString, invalidCharset);
            fail("Expected IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }

    @Test
    public void test_getBytesUnchecked_nullString() {
        String inputString = null;
        String charsetName = "UTF-8";

        byte[] result = StringUtils.getBytesUnchecked(inputString, charsetName);
        assertNull("Expected result to be null when input string is null", result);
    }

    @Test
    public void test_getBytesUnchecked_validInput() {
        String inputString = "Hello, World!";
        String charsetName = "UTF-8";

        byte[] result = StringUtils.getBytesUnchecked(inputString, charsetName);
        assertNotNull("Expected result to be non-null for valid input", result);
        assertArrayEquals("Expected byte array to match the expected encoding", 
                          inputString.getBytes(), result);
    }
}
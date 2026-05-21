package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_newString_BoundaryValues_2 {

    @Test
    public void test_newString_emptyByteArray() {
        byte[] bytes = {};
        String charsetName = "UTF-8";
        String result = StringUtils.newString(bytes, charsetName);
        assertEquals("", result);
    }

    @Test
    public void test_newString_singleCharacterByteArray() {
        byte[] bytes = {65}; // ASCII for 'A'
        String charsetName = "UTF-8";
        String result = StringUtils.newString(bytes, charsetName);
        assertEquals("A", result);
    }

    @Test
    public void test_newString_nullByteArray() {
        byte[] bytes = null;
        String charsetName = "UTF-8";
        String result = StringUtils.newString(bytes, charsetName);
        assertNull(result);
    }
}
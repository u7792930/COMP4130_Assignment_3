package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_newStringUtf8_NullEmpty_1 {

    @Test
    public void test_newStringUtf8_nullInput() {
        String result = StringUtils.newStringUtf8(null);
        assertNull(result);
    }

    @Test
    public void test_newStringUtf8_emptyArray() {
        String result = StringUtils.newStringUtf8(new byte[0]);
        assertEquals("", result);
    }

    @Test
    public void test_newStringUtf8_emptyString() {
        String result = StringUtils.newStringUtf8("".getBytes());
        assertEquals("", result);
    }
}
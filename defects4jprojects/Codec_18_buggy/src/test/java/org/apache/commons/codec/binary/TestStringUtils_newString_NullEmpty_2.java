package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_newString_NullEmpty_2 {

    @Test
    public void test_newString_nullBytes() {
        String result = StringUtils.newString(null, "UTF-8");
        assertNull(result);
    }

    @Test
    public void test_newString_emptyBytes() {
        String result = StringUtils.newString(new byte[0], "UTF-8");
        assertEquals("", result);
    }

    @Test
    public void test_newString_validBytes() {
        String result = StringUtils.newString(new byte[] { 97, 98, 99 }, "UTF-8");
        assertEquals("abc", result);
    }

    @Test
    public void test_newString_invalidCharset() {
        try {
            StringUtils.newString(new byte[] { 97, 98, 99 }, "INVALID_CHARSET");
            fail("Expected IllegalStateException for invalid charset");
        } catch (IllegalStateException e) {
            // Expected exception
        }
    }
}
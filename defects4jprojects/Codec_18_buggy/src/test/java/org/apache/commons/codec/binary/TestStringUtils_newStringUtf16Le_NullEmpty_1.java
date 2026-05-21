package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_newStringUtf16Le_NullEmpty_1 {

    @Test
    public void test_newStringUtf16Le_nullInput() {
        String result = StringUtils.newStringUtf16Le(null);
        assertNull(result);
    }

    @Test
    public void test_newStringUtf16Le_emptyArray() {
        String result = StringUtils.newStringUtf16Le(new byte[0]);
        assertEquals("", result);
    }

    @Test
    public void test_newStringUtf16Le_emptyString() {
        String result = StringUtils.newStringUtf16Le("".getBytes(java.nio.charset.StandardCharsets.UTF_16LE));
        assertEquals("", result);
    }
}
package org.apache.commons.codec.binary;

import org.apache.commons.codec.binary.StringUtils;
import org.junit.Test;
import java.nio.charset.Charset;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;

public class TestStringUtils_newString_1 {

    @Test
    public void test_newString_nullByteArray() {
        byte[] bytes = null;
        Charset charset = Charset.forName("UTF-8");
        String result = StringUtils.newString(bytes, charset);
        assertNull(result);
    }

    @Test
    public void test_newString_emptyByteArray() {
        byte[] bytes = new byte[0];
        Charset charset = Charset.forName("UTF-8");
        String result = StringUtils.newString(bytes, charset);
        assertEquals("", result);
    }

    @Test
    public void test_newString_validByteArray() {
        byte[] bytes = "Hello".getBytes(Charset.forName("UTF-8"));
        Charset charset = Charset.forName("UTF-8");
        String result = StringUtils.newString(bytes, charset);
        assertEquals("Hello", result);
    }
}
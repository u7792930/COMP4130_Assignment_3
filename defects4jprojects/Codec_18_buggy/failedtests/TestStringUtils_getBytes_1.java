package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.Charset;
import static org.junit.Assert.*;

public class TestStringUtils_getBytes_1 {

    @Test
    public void test_getBytes_nullInput() {
        byte[] result = org.apache.commons.codec.binary.StringUtils.getBytes(null, Charset.forName("UTF-8"));
        Assert.assertNull(result);
    }

    @Test
    public void test_getBytes_emptyString() {
        byte[] result = org.apache.commons.codec.binary.StringUtils.getBytes("", Charset.forName("UTF-8"));
        Assert.assertArrayEquals(new byte[0], result);
    }

    @Test
    public void test_getBytes_validString() {
        byte[] result = org.apache.commons.codec.binary.StringUtils.getBytes("Hello", Charset.forName("UTF-8"));
        Assert.assertArrayEquals(new byte[]{72, 101, 108, 108, 111}, result);
    }
}
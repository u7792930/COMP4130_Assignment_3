package org.apache.commons.codec.binary;

import org.apache.commons.codec.binary.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import static org.junit.Assert.*;

public class TestStringUtils_getByteBuffer_1 {

    @Test
    public void test_getByteBuffer_nullString() {
        // Test for null input
        ByteBuffer result = StringUtils.getByteBuffer(null, Charset.defaultCharset());
        Assert.assertNull(result);
    }

    @Test
    public void test_getByteBuffer_emptyString() {
        // Test for empty string
        ByteBuffer result = StringUtils.getByteBuffer("", Charset.defaultCharset());
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.remaining());
    }

    @Test
    public void test_getByteBuffer_nonEmptyString() {
        // Test for a non-empty string
        String input = "Hello";
        ByteBuffer result = StringUtils.getByteBuffer(input, Charset.defaultCharset());
        Assert.assertNotNull(result);
        Assert.assertEquals(5, result.remaining());
    }
}
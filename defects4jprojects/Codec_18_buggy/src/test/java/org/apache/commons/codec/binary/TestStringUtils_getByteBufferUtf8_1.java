package org.apache.commons.codec.binary;

import org.apache.commons.codec.binary.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import static org.junit.Assert.*;

public class TestStringUtils_getByteBufferUtf8_1 {

    @Test
    public void test_getByteBufferUtf8_nullInput() {
        ByteBuffer result = StringUtils.getByteBufferUtf8(null);
        Assert.assertNull(result);
    }

    @Test
    public void test_getByteBufferUtf8_emptyInput() {
        ByteBuffer result = StringUtils.getByteBufferUtf8("");
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.remaining());
    }

    @Test
    public void test_getByteBufferUtf8_validInput() {
        String input = "Hello";
        ByteBuffer result = StringUtils.getByteBufferUtf8(input);
        Assert.assertNotNull(result);
        byte[] expectedBytes = input.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        Assert.assertArrayEquals(expectedBytes, result.array());
    }
}
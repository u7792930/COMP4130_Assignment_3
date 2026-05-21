package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;

import java.nio.ByteBuffer;
import static org.junit.Assert.*;

public class TestStringUtils_getByteBufferUtf8_NormalFlow_1 {

    @Test
    public void test_getByteBufferUtf8_nonNullString() {
        String input = "Hello, World!";
        ByteBuffer result = StringUtils.getByteBufferUtf8(input);
        Assert.assertNotNull(result);
        byte[] expectedBytes = input.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        byte[] actualBytes = new byte[result.remaining()];
        result.get(actualBytes);
        Assert.assertArrayEquals(expectedBytes, actualBytes);
    }

    @Test
    public void test_getByteBufferUtf8_anotherNonNullString() {
        String input = "JUnit testing";
        ByteBuffer result = StringUtils.getByteBufferUtf8(input);
        Assert.assertNotNull(result);
        byte[] expectedBytes = input.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        byte[] actualBytes = new byte[result.remaining()];
        result.get(actualBytes);
        Assert.assertArrayEquals(expectedBytes, actualBytes);
    }

    @Test
    public void test_getByteBufferUtf8_emptyString() {
        String input = "";
        ByteBuffer result = StringUtils.getByteBufferUtf8(input);
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.remaining());
    }
}
package org.apache.commons.codec.binary;

import org.junit.Test;
import org.junit.Assert;

import java.nio.ByteBuffer;
import static org.junit.Assert.*;

public class TestStringUtils_getByteBufferUtf8_NullEmpty_1 {

    @Test
    public void test_getByteBufferUtf8_nullInput() {
        ByteBuffer result = StringUtils.getByteBufferUtf8(null);
        Assert.assertNull(result);
    }

    @Test
    public void test_getByteBufferUtf8_emptyString() {
        ByteBuffer result = StringUtils.getByteBufferUtf8("");
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.remaining());
    }

    @Test
    public void test_getByteBufferUtf8_nonEmptyString() {
        String input = "Hello";
        ByteBuffer result = StringUtils.getByteBufferUtf8(input);
        Assert.assertNotNull(result);
        Assert.assertEquals(5, result.remaining());
    }
}
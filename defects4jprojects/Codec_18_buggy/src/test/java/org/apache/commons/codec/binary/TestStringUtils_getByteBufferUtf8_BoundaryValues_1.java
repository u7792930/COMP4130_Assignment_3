package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import static org.junit.Assert.*;

public class TestStringUtils_getByteBufferUtf8_BoundaryValues_1 {

    @Test
    public void test_getByteBufferUtf8_emptyString() {
        ByteBuffer result = StringUtils.getByteBufferUtf8("");
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.remaining());
    }

    @Test
    public void test_getByteBufferUtf8_singleCharacter() {
        ByteBuffer result = StringUtils.getByteBufferUtf8("A");
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.remaining());
        Assert.assertEquals((byte) 'A', result.get());
    }

    @Test
    public void test_getByteBufferUtf8_nullString() {
        ByteBuffer result = StringUtils.getByteBufferUtf8(null);
        Assert.assertNull(result);
    }
}
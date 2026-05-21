package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatNameBytes_BoundaryValues_1 {

    @Test
    public void test_formatNameBytes_singleCharacterString() {
        String name = "A";
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 1;
        int result = TarUtils.formatNameBytes(name, buf, offset, length);
        Assert.assertEquals(1, result);
        Assert.assertEquals('A', buf[0]);
        Assert.assertEquals(0, buf[1]);
    }

    @Test
    public void test_formatNameBytes_emptyString() {
        String name = "";
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        int result = TarUtils.formatNameBytes(name, buf, offset, length);
        Assert.assertEquals(0, result);
        for (byte b : buf) {
            Assert.assertEquals(0, b);
        }
    }

    @Test
    public void test_formatNameBytes_bufferTooSmall() {
        String name = "HelloWorld";
        byte[] buf = new byte[5];
        int offset = 0;
        int length = 10;
        int result = TarUtils.formatNameBytes(name, buf, offset, length);
        Assert.assertEquals(5, result);
        Assert.assertArrayEquals(new byte[]{'H', 'e', 'l', 'l', 'o'}, buf);
    }

    @Test
    public void test_formatNameBytes_exactBufferSize() {
        String name = "Hello";
        byte[] buf = new byte[5];
        int offset = 0;
        int length = 5;
        int result = TarUtils.formatNameBytes(name, buf, offset, length);
        Assert.assertEquals(5, result);
        Assert.assertArrayEquals(new byte[]{'H', 'e', 'l', 'l', 'o'}, buf);
    }

    @Test
    public void test_formatNameBytes_bufferLargerThanName() {
        String name = "Hi";
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        int result = TarUtils.formatNameBytes(name, buf, offset, length);
        Assert.assertEquals(10, result);
        Assert.assertArrayEquals(new byte[]{'H', 'i', 0, 0, 0, 0, 0, 0, 0, 0}, buf);
    }

    @Test
    public void test_formatNameBytes_negativeOffset() {
        String name = "Test";
        byte[] buf = new byte[10];
        int offset = -1;
        int length = 10;
        try {
            TarUtils.formatNameBytes(name, buf, offset, length);
            fail("Expected an ArrayIndexOutOfBoundsException to be thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    @Test
    public void test_formatNameBytes_offsetExceedsBuffer() {
        String name = "Test";
        byte[] buf = new byte[10];
        int offset = 10;
        int length = 5;
        int result = TarUtils.formatNameBytes(name, buf, offset, length);
        Assert.assertEquals(10, result);
        for (int i = 0; i < buf.length; i++) {
            Assert.assertEquals(0, buf[i]);
        }
    }
}
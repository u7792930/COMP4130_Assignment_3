package org.apache.commons.compress.archivers.tar;

import org.apache.commons.compress.archivers.tar.TarUtils;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatLongOctalOrBinaryBytes_1 {

    @Test
    public void test_formatLongOctalOrBinaryBytes_positiveValueFitsAsOctal() {
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        long value = 5; // A positive value that fits as octal
        int result = TarUtils.formatLongOctalOrBinaryBytes(value, buf, offset, length);
        Assert.assertEquals(offset + length, result);
        String expectedOctal = String.format("%0" + (length - 1) + "o ", value);
        Assert.assertArrayEquals(expectedOctal.getBytes(), buf);
    }

    @Test
    public void test_formatLongOctalOrBinaryBytes_negativeValue() {
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        long value = -5; // A negative value
        int result = TarUtils.formatLongOctalOrBinaryBytes(value, buf, offset, length);
        Assert.assertEquals(offset + length, result);
        Assert.assertEquals((byte) 0xff, buf[offset + length - 1]); // Check the negative marker at the end
    }

    @Test
    public void test_formatLongOctalOrBinaryBytes_valueExceedsBuffer() {
        byte[] buf = new byte[8]; // Buffer too small
        int offset = 0;
        int length = 8;
        long value = 10000; // A value that exceeds buffer limits
        try {
            TarUtils.formatLongOctalOrBinaryBytes(value, buf, offset, length);
            Assert.fail("Expected IllegalArgumentException for buffer overflow");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_formatLongOctalOrBinaryBytes_valueFitsAsBinary() {
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        long value = 10000; // A value that fits as binary
        int result = TarUtils.formatLongOctalOrBinaryBytes(value, buf, offset, length);
        Assert.assertEquals(offset + length, result);
        Assert.assertEquals((byte) 0x80, buf[offset + length - 1]); // Check the binary marker at the end
    }

    @Test
    public void test_formatLongOctalOrBinaryBytes_valueNegativeFitsAsBinary() {
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        long value = -10000; // A negative value that fits as binary
        int result = TarUtils.formatLongOctalOrBinaryBytes(value, buf, offset, length);
        Assert.assertEquals(offset + length, result);
        Assert.assertEquals((byte) 0xff, buf[offset + length - 1]); // Check the negative marker at the end
    }
}
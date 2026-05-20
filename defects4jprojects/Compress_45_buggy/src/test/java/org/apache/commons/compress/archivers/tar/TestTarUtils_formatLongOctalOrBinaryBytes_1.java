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
        String expectedOctal = String.format("%0" + length + "o ", value);
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
        Assert.assertEquals((byte) 0xff, buf[offset]); // Check the negative marker
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
}
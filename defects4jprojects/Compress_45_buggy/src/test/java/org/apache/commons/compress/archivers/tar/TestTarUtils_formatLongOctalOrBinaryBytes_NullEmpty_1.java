package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestTarUtils_formatLongOctalOrBinaryBytes_NullEmpty_1 {

    @Test
    public void test_formatLongOctalOrBinaryBytes_nullBuffer() {
        try {
            TarUtils.formatLongOctalOrBinaryBytes(12345L, null, 0, 10);
            Assert.fail("Expected IllegalArgumentException for null buffer");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_formatLongOctalOrBinaryBytes_emptyBuffer() {
        byte[] buf = new byte[0];
        try {
            TarUtils.formatLongOctalOrBinaryBytes(12345L, buf, 0, 0);
            Assert.fail("Expected IllegalArgumentException for empty buffer");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_formatLongOctalOrBinaryBytes_negativeValue() {
        byte[] buf = new byte[10];
        int result = TarUtils.formatLongOctalOrBinaryBytes(-12345L, buf, 0, 10);
        Assert.assertEquals(10, result);
        Assert.assertEquals((byte) 0xff, buf[0]); // Check negative marker
    }

    @Test
    public void test_formatLongOctalOrBinaryBytes_positiveValue_fitsAsOctal() {
        byte[] buf = new byte[10];
        int result = TarUtils.formatLongOctalOrBinaryBytes(1234L, buf, 0, 10);
        Assert.assertEquals(10, result);
        Assert.assertArrayEquals(new byte[] { '0', '0', '0', '0', '0', '0', '2', '4', '2', ' ' }, buf);
    }

    @Test
    public void test_formatLongOctalOrBinaryBytes_positiveValue_fitsAsBinary() {
        byte[] buf = new byte[10];
        int result = TarUtils.formatLongOctalOrBinaryBytes(123456789L, buf, 0, 10);
        Assert.assertEquals(10, result);
        Assert.assertEquals((byte) 0x80, buf[9]); // Check binary marker
        // Additional checks can be added to verify the binary representation
    }

    @Test
    public void test_formatLongOctalOrBinaryBytes_valueTooLarge() {
        byte[] buf = new byte[10];
        try {
            TarUtils.formatLongOctalOrBinaryBytes(Long.MAX_VALUE, buf, 0, 10);
            Assert.fail("Expected IllegalArgumentException for value too large");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
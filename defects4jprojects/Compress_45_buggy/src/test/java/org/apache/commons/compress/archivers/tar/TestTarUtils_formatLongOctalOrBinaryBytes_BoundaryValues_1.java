package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatLongOctalOrBinaryBytes_BoundaryValues_1 {

    @Test
    public void test_formatLongOctalOrBinaryBytes_zeroValue() {
        byte[] buf = new byte[10];
        int result = TarUtils.formatLongOctalOrBinaryBytes(0, buf, 0, buf.length);
        Assert.assertEquals(10, result);
        Assert.assertEquals('0', buf[0]);
        Assert.assertEquals(' ', buf[1]); // Check for space after octal representation
    }

    @Test
    public void test_formatLongOctalOrBinaryBytes_longMaxValue() {
        byte[] buf = new byte[10];
        int result = TarUtils.formatLongOctalOrBinaryBytes(Long.MAX_VALUE, buf, 0, buf.length);
        Assert.assertEquals(10, result);
        Assert.assertEquals(0x80, buf[9]); // Check the negative byte set
    }

    @Test
    public void test_formatLongOctalOrBinaryBytes_uidMaxValue() {
        byte[] buf = new byte[10];
        int result = TarUtils.formatLongOctalOrBinaryBytes(TarConstants.MAXID, buf, 0, buf.length);
        Assert.assertEquals(10, result);
        Assert.assertEquals('7', buf[0]); // Check for correct octal representation
        Assert.assertEquals(' ', buf[1]); // Check for space after octal representation
    }

    @Test
    public void test_formatLongOctalOrBinaryBytes_sizeMaxValue() {
        byte[] buf = new byte[10];
        int result = TarUtils.formatLongOctalOrBinaryBytes(TarConstants.MAXSIZE, buf, 0, buf.length);
        Assert.assertEquals(10, result);
        Assert.assertEquals('7', buf[0]); // Check for correct octal representation
        Assert.assertEquals(' ', buf[1]); // Check for space after octal representation
    }

    @Test
    public void test_formatLongOctalOrBinaryBytes_lengthTooSmall() {
        byte[] buf = new byte[8];
        try {
            TarUtils.formatLongOctalOrBinaryBytes(1, buf, 0, buf.length);
            Assert.fail("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_formatLongOctalOrBinaryBytes_negativeValue() {
        byte[] buf = new byte[10];
        int result = TarUtils.formatLongOctalOrBinaryBytes(-1, buf, 0, buf.length);
        Assert.assertEquals(10, result);
        Assert.assertEquals(0xff, buf[9]); // Check the negative byte set
    }
}
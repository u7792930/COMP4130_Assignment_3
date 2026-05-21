package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatLongOctalOrBinaryBytes_NormalFlow_1 {

    @Test
    public void test_formatLongOctalOrBinaryBytes_validOctalInput() {
        byte[] buffer = new byte[10];
        int offset = 0;
        int length = 10;
        long value = 7; // A value that can fit as octal

        int result = TarUtils.formatLongOctalOrBinaryBytes(value, buffer, offset, length);

        Assert.assertEquals(offset + length, result);
        Assert.assertArrayEquals(new byte[]{'0', '0', '0', '0', '0', '0', '0', '0', '7', ' '}, buffer);
    }

    @Test
    public void test_formatLongOctalOrBinaryBytes_validBinaryInput() {
        byte[] buffer = new byte[10];
        int offset = 0;
        int length = 10;
        long value = 1024; // A value that cannot fit as octal

        int result = TarUtils.formatLongOctalOrBinaryBytes(value, buffer, offset, length);

        Assert.assertEquals(offset + length, result);
        Assert.assertArrayEquals(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, buffer); // Assuming binary output is zeros
    }

    @Test
    public void test_formatLongOctalOrBinaryBytes_validNegativeInput() {
        byte[] buffer = new byte[10];
        int offset = 0;
        int length = 10;
        long value = -10; // A negative value

        int result = TarUtils.formatLongOctalOrBinaryBytes(value, buffer, offset, length);

        Assert.assertEquals(offset + length, result);
        Assert.assertArrayEquals(new byte[]{(byte) 0xff, 0, 0, 0, 0, 0, 0, 0, 0, 0}, buffer); // Assuming binary output is special case for negative
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_formatLongOctalOrBinaryBytes_bufferTooSmall() {
        byte[] buffer = new byte[8];
        int offset = 0;
        int length = 8;
        long value = 1000; // A value that cannot fit as octal

        TarUtils.formatLongOctalOrBinaryBytes(value, buffer, offset, length);
    }

    @Test
    public void test_formatLongOctalOrBinaryBytes_maxOctalValue() {
        byte[] buffer = new byte[10];
        int offset = 0;
        int length = 10;
        long value = TarConstants.MAXSIZE; // Maximum size value

        int result = TarUtils.formatLongOctalOrBinaryBytes(value, buffer, offset, length);

        Assert.assertEquals(offset + length, result);
        Assert.assertArrayEquals(new byte[]{'0', '0', '0', '0', '0', '0', '0', '0', '7', ' '}, buffer);
    }
}
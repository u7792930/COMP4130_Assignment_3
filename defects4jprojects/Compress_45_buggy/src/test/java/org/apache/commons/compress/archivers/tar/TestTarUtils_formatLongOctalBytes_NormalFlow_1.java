package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatLongOctalBytes_NormalFlow_1 {

    @Test
    public void test_formatLongOctalBytes_validInput() {
        byte[] buffer = new byte[20];
        long value = 123456789L;
        int offset = 0;
        int length = 12;

        int result = TarUtils.formatLongOctalBytes(value, buffer, offset, length);

        Assert.assertEquals(12, result);
        Assert.assertArrayEquals(new byte[]{'0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '1', ' ', 0, 0, 0, 0, 0, 0, 0, 0}, buffer);
    }

    @Test
    public void test_formatLongOctalBytes_largeValue() {
        byte[] buffer = new byte[20];
        long value = Long.MAX_VALUE;
        int offset = 0;
        int length = 12;

        int result = TarUtils.formatLongOctalBytes(value, buffer, offset, length);

        Assert.assertEquals(12, result);
        Assert.assertArrayEquals(new byte[]{'7', '3', '2', '3', '3', '2', '7', '0', '0', '0', '0', ' ', 0, 0, 0, 0, 0, 0, 0, 0}, buffer);
    }

    @Test
    public void test_formatLongOctalBytes_zeroValue() {
        byte[] buffer = new byte[20];
        long value = 0L;
        int offset = 0;
        int length = 12;

        int result = TarUtils.formatLongOctalBytes(value, buffer, offset, length);

        Assert.assertEquals(12, result);
        Assert.assertArrayEquals(new byte[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', ' ', 0, 0, 0, 0, 0, 0, 0, 0}, buffer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_formatLongOctalBytes_insufficientBuffer() {
        byte[] buffer = new byte[10];
        long value = 123456789L;
        int offset = 0;
        int length = 12;

        TarUtils.formatLongOctalBytes(value, buffer, offset, length);
    }
}
package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatCheckSumOctalBytes_NormalFlow_1 {

    @Test
    public void test_formatCheckSumOctalBytes_validInput() {
        byte[] buffer = new byte[20];
        long value = 12345;
        int offset = 0;
        int length = 20;

        int result = TarUtils.formatCheckSumOctalBytes(value, buffer, offset, length);

        Assert.assertEquals(offset + length, result);
        Assert.assertEquals((byte) 0, buffer[offset + length - 2]); // NUL byte
        Assert.assertEquals((byte) ' ', buffer[offset + length - 1]); // Space byte
    }

    @Test
    public void test_formatCheckSumOctalBytes_largeValue() {
        byte[] buffer = new byte[20];
        long value = Long.MAX_VALUE;
        int offset = 0;
        int length = 20;

        int result = TarUtils.formatCheckSumOctalBytes(value, buffer, offset, length);

        Assert.assertEquals(offset + length, result);
        Assert.assertEquals((byte) 0, buffer[offset + length - 2]); // NUL byte
        Assert.assertEquals((byte) ' ', buffer[offset + length - 1]); // Space byte
    }

    @Test
    public void test_formatCheckSumOctalBytes_zeroValue() {
        byte[] buffer = new byte[20];
        long value = 0;
        int offset = 0;
        int length = 20;

        int result = TarUtils.formatCheckSumOctalBytes(value, buffer, offset, length);

        Assert.assertEquals(offset + length, result);
        Assert.assertEquals((byte) 0, buffer[offset + length - 2]); // NUL byte
        Assert.assertEquals((byte) ' ', buffer[offset + length - 1]); // Space byte
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_formatCheckSumOctalBytes_insufficientBuffer() {
        byte[] buffer = new byte[10];
        long value = 12345;
        int offset = 0;
        int length = 10;

        TarUtils.formatCheckSumOctalBytes(value, buffer, offset, length);
    }
}
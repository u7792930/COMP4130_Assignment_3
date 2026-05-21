package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatCheckSumOctalBytes_BoundaryValues_1 {

    @Test
    public void test_formatCheckSumOctalBytes_zeroValue() {
        byte[] buf = new byte[10];
        int result = TarUtils.formatCheckSumOctalBytes(0L, buf, 0, 10);
        Assert.assertEquals(10, result);
        Assert.assertArrayEquals(new byte[]{48, 48, 48, 48, 48, 48, 48, 48, 0, 32}, buf);
    }

    @Test
    public void test_formatCheckSumOctalBytes_maxValue() {
        byte[] buf = new byte[10];
        int result = TarUtils.formatCheckSumOctalBytes(2047L, buf, 0, 10); // 2047 is the max octal value that fits in 8 characters
        Assert.assertEquals(10, result);
        Assert.assertArrayEquals(new byte[]{55, 55, 55, 55, 55, 55, 55, 55, 0, 32}, buf); // 2047 in octal is 3777
    }

    @Test
    public void test_formatCheckSumOctalBytes_negativeValue() {
        byte[] buf = new byte[10];
        try {
            TarUtils.formatCheckSumOctalBytes(-1L, buf, 0, 10);
            Assert.fail("Expected IllegalArgumentException for negative value");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_formatCheckSumOctalBytes_insufficientBuffer() {
        byte[] buf = new byte[8];
        try {
            TarUtils.formatCheckSumOctalBytes(0L, buf, 0, 8);
            Assert.fail("Expected IllegalArgumentException for insufficient buffer size");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestTarUtils_formatCheckSumOctalBytes_NullEmpty_1 {

    @Test
    public void test_formatCheckSumOctalBytes_nullBuffer() {
        try {
            TarUtils.formatCheckSumOctalBytes(12345L, null, 0, 10);
            Assert.fail("Expected IllegalArgumentException for null buffer");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void test_formatCheckSumOctalBytes_emptyBuffer() {
        byte[] buf = new byte[0];
        try {
            TarUtils.formatCheckSumOctalBytes(12345L, buf, 0, buf.length);
            Assert.fail("Expected IllegalArgumentException for empty buffer");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void test_formatCheckSumOctalBytes_offsetOutOfBounds() {
        byte[] buf = new byte[10];
        try {
            TarUtils.formatCheckSumOctalBytes(12345L, buf, 10, 5);
            Assert.fail("Expected IllegalArgumentException for offset out of bounds");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void test_formatCheckSumOctalBytes_validInput() {
        byte[] buf = new byte[12];
        int result = TarUtils.formatCheckSumOctalBytes(12345L, buf, 0, buf.length);
        Assert.assertEquals(12, result);
        Assert.assertArrayEquals(new byte[] { '3', '0', '0', '0', '0', '0', '0', '0', 0, ' ' }, buf);
    }

    @Test
    public void test_formatCheckSumOctalBytes_largeValue() {
        byte[] buf = new byte[12];
        int result = TarUtils.formatCheckSumOctalBytes(123456789L, buf, 0, buf.length);
        Assert.assertEquals(12, result);
        Assert.assertArrayEquals(new byte[] { '3', '7', '1', '5', '0', '0', '0', '1', 0, ' ' }, buf);
    }

    @Test
    public void test_formatCheckSumOctalBytes_valueZero() {
        byte[] buf = new byte[12];
        int result = TarUtils.formatCheckSumOctalBytes(0L, buf, 0, buf.length);
        Assert.assertEquals(12, result);
        Assert.assertArrayEquals(new byte[] { '0', '0', '0', '0', '0', '0', '0', '0', 0, ' ' }, buf);
    }
}
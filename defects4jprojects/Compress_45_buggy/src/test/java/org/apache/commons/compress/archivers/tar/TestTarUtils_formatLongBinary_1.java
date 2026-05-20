package org.apache.commons.compress.archivers.tar;

import org.apache.commons.compress.archivers.tar.TarUtils;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatLongBinary_1 {

    @Test
    public void test_formatLongBinary_valueTooLarge() {
        long value = Long.MAX_VALUE;
        byte[] buf = new byte[8];
        int offset = 0;
        int length = 8;
        boolean negative = false;

        try {
            TarUtils.formatLongBinary(value, buf, offset, length, negative);
            Assert.fail("Expected IllegalArgumentException for value too large");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Value " + value + " is too large for " + length + " byte field.", e.getMessage());
        }
    }

    @Test
    public void test_formatLongBinary_negativeValue() {
        long value = -1;
        byte[] buf = new byte[8];
        int offset = 0;
        int length = 8;
        boolean negative = true;

        try {
            TarUtils.formatLongBinary(value, buf, offset, length, negative);
            Assert.fail("Expected IllegalArgumentException for negative value");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Value " + value + " is too large for " + length + " byte field.", e.getMessage());
        }
    }

    @Test
    public void test_formatLongBinary_correctBuffer() {
        long value = 10;
        byte[] buf = new byte[8];
        int offset = 0;
        int length = 8;
        boolean negative = false;

        TarUtils.formatLongBinary(value, buf, offset, length, negative);
        byte[] expected = new byte[]{0, 0, 0, 0, 0, 0, 0, 10};
        Assert.assertArrayEquals(expected, buf);
    }
}
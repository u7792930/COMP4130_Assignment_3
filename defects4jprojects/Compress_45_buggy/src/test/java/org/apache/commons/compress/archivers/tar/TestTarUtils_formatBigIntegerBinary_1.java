package org.apache.commons.compress.archivers.tar;

import org.apache.commons.compress.archivers.tar.TarUtils;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatBigIntegerBinary_1 {

    @Test
    public void test_formatBigIntegerBinary_valueTooLarge() {
        long value = Long.MAX_VALUE;
        byte[] buf = new byte[4];
        int offset = 0;
        int length = 4;
        boolean negative = false;

        try {
            TarUtils.formatBigIntegerBinary(value, buf, offset, length, negative);
            fail("Expected IllegalArgumentException for value too large");
        } catch (IllegalArgumentException e) {
            assertEquals("Value " + value + " is too large for " + length + " byte field.", e.getMessage());
        }
    }

    @Test
    public void test_formatBigIntegerBinary_negativeValue() {
        long value = -1;
        byte[] buf = new byte[4];
        int offset = 0;
        int length = 4;
        boolean negative = true;

        TarUtils.formatBigIntegerBinary(value, buf, offset, length, negative);
        assertArrayEquals(new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff}, buf);
    }

    @Test
    public void test_formatBigIntegerBinary_zeroValue() {
        long value = 0;
        byte[] buf = new byte[4];
        int offset = 0;
        int length = 4;
        boolean negative = false;

        TarUtils.formatBigIntegerBinary(value, buf, offset, length, negative);
        assertArrayEquals(new byte[]{0, 0, 0, 0}, buf);
    }
}
package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatUnsignedOctalString_NullEmpty_1 {

    @Test
    public void test_formatUnsignedOctalString_nullBuffer() {
        try {
            TarUtils.formatUnsignedOctalString(10L, null, 0, 10);
            Assert.fail("Expected IllegalArgumentException for null buffer");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_formatUnsignedOctalString_emptyBuffer() {
        byte[] buffer = new byte[0];
        try {
            TarUtils.formatUnsignedOctalString(10L, buffer, 0, 0);
            Assert.fail("Expected IllegalArgumentException for empty buffer");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_formatUnsignedOctalString_valueFitsInBuffer() {
        byte[] buffer = new byte[3];
        TarUtils.formatUnsignedOctalString(10L, buffer, 0, 3);
        assertArrayEquals(new byte[] { '0', '1', '2' }, buffer);
    }

    @Test
    public void test_formatUnsignedOctalString_valueTooLarge() {
        byte[] buffer = new byte[5];
        try {
            TarUtils.formatUnsignedOctalString(Long.MAX_VALUE, buffer, 0, 5);
            Assert.fail("Expected IllegalArgumentException for value too large");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_formatUnsignedOctalString_zeroValue() {
        byte[] buffer = new byte[3];
        TarUtils.formatUnsignedOctalString(0L, buffer, 0, 3);
        assertArrayEquals(new byte[] { '0', '0', '0' }, buffer);
    }

    @Test
    public void test_formatUnsignedOctalString_leadingZeros() {
        byte[] buffer = new byte[5];
        TarUtils.formatUnsignedOctalString(8L, buffer, 0, 5);
        assertArrayEquals(new byte[] { '0', '0', '0', '1', '0' }, buffer);
    }
}
package org.apache.commons.compress.archivers.tar;

import org.apache.commons.compress.archivers.tar.TarUtils;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

public class TestTarUtils_formatUnsignedOctalString_1 {

    @Test
    public void test_formatUnsignedOctalString_zeroValue() {
        byte[] buffer = new byte[5];
        TarUtils.formatUnsignedOctalString(0, buffer, 0, 5);
        byte[] expected = new byte[]{'0', '0', '0', '0', '0'};
        assertArrayEquals(expected, buffer);
    }

    @Test
    public void test_formatUnsignedOctalString_smallValue() {
        byte[] buffer = new byte[5];
        TarUtils.formatUnsignedOctalString(7, buffer, 0, 5);
        byte[] expected = new byte[]{'0', '0', '0', '0', '7'};
        assertArrayEquals(expected, buffer);
    }

    @Test
    public void test_formatUnsignedOctalString_mediumValue() {
        byte[] buffer = new byte[5];
        TarUtils.formatUnsignedOctalString(63, buffer, 0, 5);
        byte[] expected = new byte[]{'0', '0', '0', '7', '7'};
        assertArrayEquals(expected, buffer);
    }

    @Test
    public void test_formatUnsignedOctalString_largeValue() {
        byte[] buffer = new byte[5];
        TarUtils.formatUnsignedOctalString(255, buffer, 0, 5);
        byte[] expected = new byte[]{'0', '0', '3', '7', '7'};
        assertArrayEquals(expected, buffer);
    }

    @Test
    public void test_formatUnsignedOctalString_valueTooLarge() {
        byte[] buffer = new byte[5];
        try {
            TarUtils.formatUnsignedOctalString(2048, buffer, 0, 5);
            fail("Expected IllegalArgumentException for value too large");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_formatUnsignedOctalString_exactFit() {
        byte[] buffer = new byte[5];
        TarUtils.formatUnsignedOctalString(2047, buffer, 0, 5);
        byte[] expected = new byte[]{'0', '3', '7', '7', '7'};
        assertArrayEquals(expected, buffer);
    }

    @Test
    public void test_formatUnsignedOctalString_leadingZeros() {
        byte[] buffer = new byte[5];
        TarUtils.formatUnsignedOctalString(1, buffer, 0, 5);
        byte[] expected = new byte[]{'0', '0', '0', '0', '1'};
        assertArrayEquals(expected, buffer);
    }
}
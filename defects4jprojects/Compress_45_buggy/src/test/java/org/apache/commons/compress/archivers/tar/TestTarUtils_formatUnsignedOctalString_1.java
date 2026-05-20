package org.apache.commons.compress.archivers.tar;

import org.apache.commons.compress.archivers.tar.TarUtils;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;

public class TestTarUtils_formatUnsignedOctalString_1 {

    @Test
    public void test_formatUnsignedOctalString_zeroValue() {
        byte[] buffer = new byte[5];
        TarUtils.formatUnsignedOctalString(0, buffer, 0, 5);
        byte[] expected = new byte[]{'0', '0', '0', '0', '0'};
        assertArrayEquals(expected, buffer);
    }

    @Test
    public void test_formatUnsignedOctalString_largeValue() {
        byte[] buffer = new byte[5];
        TarUtils.formatUnsignedOctalString(255, buffer, 0, 5);
        byte[] expected = new byte[]{'0', '0', '0', '3', '7'};
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
}
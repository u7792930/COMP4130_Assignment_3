package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatOctalBytes_BoundaryValues_1 {

    @Test
    public void test_formatOctalBytes_zeroValue() {
        byte[] buffer = new byte[10];
        int result = TarUtils.formatOctalBytes(0, buffer, 0, 10);
        assertEquals(10, result);
        assertArrayEquals(new byte[] { '0', ' ', 0, 0, 0, 0, 0, 0, 0, 0 }, buffer);
    }

    @Test
    public void test_formatOctalBytes_negativeValue() {
        byte[] buffer = new byte[10];
        try {
            TarUtils.formatOctalBytes(-1, buffer, 0, 10);
            fail("Expected IllegalArgumentException for negative value");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_formatOctalBytes_longMaxValue() {
        byte[] buffer = new byte[10];
        int result = TarUtils.formatOctalBytes(Long.MAX_VALUE, buffer, 0, 10);
        assertEquals(10, result);
        assertArrayEquals(new byte[] { '7', '3', '2', '0', '3', '7', '6', '5', ' ', 0 }, buffer);
    }

    @Test
    public void test_formatOctalBytes_bufferTooSmall() {
        byte[] buffer = new byte[8];
        try {
            TarUtils.formatOctalBytes(1, buffer, 0, 8);
            fail("Expected IllegalArgumentException for buffer too small");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
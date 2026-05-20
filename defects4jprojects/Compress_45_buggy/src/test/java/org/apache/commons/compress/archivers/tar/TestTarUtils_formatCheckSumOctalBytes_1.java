package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatCheckSumOctalBytes_1 {

    @Test
    public void test_formatCheckSumOctalBytes_validInput() {
        byte[] buffer = new byte[10];
        int offset = 0;
        int length = 10;
        long value = 1234;

        int result = TarUtils.formatCheckSumOctalBytes(value, buffer, offset, length);

        assertEquals(10, result);
        assertEquals(0, buffer[8]); // Check for NUL
        assertEquals((byte) ' ', buffer[9]); // Check for space
        // Check the octal representation
        assertArrayEquals(new byte[] {'0', '0', '0', '0', '0', '2', '3', '2', 0, ' '}, buffer);
    }

    @Test
    public void test_formatCheckSumOctalBytes_insufficientBuffer() {
        byte[] buffer = new byte[8]; // Insufficient buffer size
        int offset = 0;
        int length = 8;
        long value = 1234;

        try {
            TarUtils.formatCheckSumOctalBytes(value, buffer, offset, length);
            fail("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_formatCheckSumOctalBytes_negativeValue() {
        byte[] buffer = new byte[10];
        int offset = 0;
        int length = 10;
        long value = -1234;

        int result = TarUtils.formatCheckSumOctalBytes(value, buffer, offset, length);

        assertEquals(10, result);
        assertEquals(0, buffer[8]); // Check for NUL
        assertEquals((byte) ' ', buffer[9]); // Check for space
        // Check the octal representation of the negative value
        assertArrayEquals(new byte[] {'-', '0', '0', '0', '0', '0', '2', '3', 0, ' '}, buffer);
    }
}
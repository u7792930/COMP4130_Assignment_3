package org.apache.commons.compress.archivers.tar;

import org.apache.commons.compress.archivers.tar.TarUtils;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_parseBinaryBigInteger_1 {

    @Test
    public void test_parseBinaryBigInteger_negativeFlagTrue_exceedsMaxSignedLong() {
        byte[] buffer = new byte[] {0, 1, 2}; // Example buffer
        int offset = 0;
        int length = 3;
        boolean negative = true;

        try {
            TarUtils.parseBinaryBigInteger(buffer, offset, length, negative);
            fail("Expected IllegalArgumentException for exceeding maximum signed long value");
        } catch (IllegalArgumentException e) {
            assertEquals("At offset 0, 3 byte binary number exceeds maximum signed long value", e.getMessage());
        }
    }

    @Test
    public void test_parseBinaryBigInteger_negativeFlagFalse_exceedsMaxSignedLong() {
        byte[] buffer = new byte[] {0, 1, 2}; // Example buffer
        int offset = 0;
        int length = 3;
        boolean negative = false;

        try {
            TarUtils.parseBinaryBigInteger(buffer, offset, length, negative);
            fail("Expected IllegalArgumentException for exceeding maximum signed long value");
        } catch (IllegalArgumentException e) {
            assertEquals("At offset 0, 3 byte binary number exceeds maximum signed long value", e.getMessage());
        }
    }

    @Test
    public void test_parseBinaryBigInteger_validInput() {
        byte[] buffer = new byte[] {0, 0, 1}; // Represents the number 1
        int offset = 0;
        int length = 3;
        boolean negative = false;

        long result = TarUtils.parseBinaryBigInteger(buffer, offset, length, negative);
        assertEquals(1L, result);
    }
}
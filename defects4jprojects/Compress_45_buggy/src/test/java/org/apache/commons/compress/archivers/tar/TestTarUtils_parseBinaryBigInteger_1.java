package org.apache.commons.compress.archivers.tar;

import org.apache.commons.compress.archivers.tar.TarUtils;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_parseBinaryBigInteger_1 {

    @Test
    public void test_parseBinaryBigInteger_negativeFlagTrue_exceedsMaxSignedLong() {
        byte[] buffer = new byte[] {0, (byte) 0xFF, (byte) 0xFF}; // Example buffer for -1
        int offset = 0;
        int length = 3;
        boolean negative = true;

        try {
            invokeParseBinaryBigInteger(buffer, offset, length, negative);
            fail("Expected IllegalArgumentException for exceeding maximum signed long value");
        } catch (IllegalArgumentException e) {
            assertEquals("At offset 0, 3 byte binary number exceeds maximum signed long value", e.getMessage());
        }
    }

    @Test
    public void test_parseBinaryBigInteger_negativeFlagFalse_exceedsMaxSignedLong() {
        byte[] buffer = new byte[] {0, (byte) 0x80, (byte) 0x00}; // Example buffer for 2^63
        int offset = 0;
        int length = 3;
        boolean negative = false;

        try {
            invokeParseBinaryBigInteger(buffer, offset, length, negative);
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

        long result = invokeParseBinaryBigInteger(buffer, offset, length, negative);
        assertEquals(1L, result);
    }

    @Test
    public void test_parseBinaryBigInteger_negativeValidInput() {
        byte[] buffer = new byte[] {0, 0, 1}; // Represents the number 1
        int offset = 0;
        int length = 3;
        boolean negative = true;

        long result = invokeParseBinaryBigInteger(buffer, offset, length, negative);
        assertEquals(-1L, result);
    }

    @Test
    public void test_parseBinaryBigInteger_zeroInput() {
        byte[] buffer = new byte[] {0, 0, 0}; // Represents the number 0
        int offset = 0;
        int length = 3;
        boolean negative = false;

        long result = invokeParseBinaryBigInteger(buffer, offset, length, negative);
        assertEquals(0L, result);
    }

    private long invokeParseBinaryBigInteger(byte[] buffer, int offset, int length, boolean negative) {
        try {
            java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("parseBinaryBigInteger", byte[].class, int.class, int.class, boolean.class);
            method.setAccessible(true);
            return (long) method.invoke(null, buffer, offset, length, negative);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_parseBinaryBigInteger_NullEmpty_1 {

    @Test
    public void test_parseBinaryBigInteger_nullBuffer() {
        try {
            invokeParseBinaryBigInteger(null, 0, 1, false);
            fail("Expected IllegalArgumentException for null buffer");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_parseBinaryBigInteger_emptyBuffer() {
        byte[] buffer = new byte[0];
        try {
            invokeParseBinaryBigInteger(buffer, 0, 1, false);
            fail("Expected IllegalArgumentException for empty buffer");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_parseBinaryBigInteger_negativeValueWithSingleByte() {
        byte[] buffer = new byte[1];
        buffer[0] = 0; // Valid single byte
        long result = invokeParseBinaryBigInteger(buffer, 0, 1, true);
        assertEquals(-0L, result);
    }

    @Test
    public void test_parseBinaryBigInteger_positiveValue() {
        byte[] buffer = new byte[]{0, 1, 0, 0}; // Represents the value 256
        long result = invokeParseBinaryBigInteger(buffer, 0, 4, false);
        assertEquals(256L, result);
    }

    @Test
    public void test_parseBinaryBigInteger_negativeValue() {
        byte[] buffer = new byte[]{0, 1, 0, 0}; // Represents the value 256
        long result = invokeParseBinaryBigInteger(buffer, 0, 4, true);
        assertEquals(-256L, result);
    }

    @Test
    public void test_parseBinaryBigInteger_exceedsMaxLong() {
        byte[] buffer = new byte[9]; // 8 bytes + 1 for length
        for (int i = 0; i < 8; i++) {
            buffer[i] = (byte) 0xFF; // Fill with 255
        }
        try {
            invokeParseBinaryBigInteger(buffer, 0, 9, false);
            fail("Expected IllegalArgumentException for exceeding max signed long value");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
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
package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestTarUtils_parseBinaryBigInteger_BoundaryValues_1 {

    @Test
    public void test_parseBinaryBigInteger_zero() {
        byte[] buffer = new byte[]{0, 0, 0, 0, 0}; // Represents a binary big integer of 0
        long result = invokeParseBinaryBigInteger(buffer, 0, 5, false);
        assertEquals(0, result);
    }

    @Test
    public void test_parseBinaryBigInteger_negativeOne() {
        byte[] buffer = new byte[]{0, 0, 0, 0, 1}; // Represents a binary big integer of 1
        long result = invokeParseBinaryBigInteger(buffer, 0, 5, true);
        assertEquals(-1, result);
    }

    @Test
    public void test_parseBinaryBigInteger_maxLongValue() {
        byte[] buffer = new byte[]{0, 127, -1, -1, -1, -1, -1, -1}; // Represents a binary big integer of Long.MAX_VALUE
        long result = invokeParseBinaryBigInteger(buffer, 0, 8, false);
        assertEquals(Long.MAX_VALUE, result);
    }

    @Test
    public void test_parseBinaryBigInteger_tooLarge() {
        byte[] buffer = new byte[]{0, 127, -1, -1, -1, -1, -1, -1, -1}; // Exceeds Long.MAX_VALUE
        try {
            invokeParseBinaryBigInteger(buffer, 0, 9, false);
            fail("Expected IllegalArgumentException for exceeding maximum signed long value");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_parseBinaryBigInteger_negativeMaxLongValue() {
        byte[] buffer = new byte[]{0, 127, -1, -1, -1, -1, -1, -1}; // Represents a binary big integer of Long.MAX_VALUE
        long result = invokeParseBinaryBigInteger(buffer, 0, 8, true);
        assertEquals(Long.MIN_VALUE + 1, result); // Should return -Long.MAX_VALUE - 1
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
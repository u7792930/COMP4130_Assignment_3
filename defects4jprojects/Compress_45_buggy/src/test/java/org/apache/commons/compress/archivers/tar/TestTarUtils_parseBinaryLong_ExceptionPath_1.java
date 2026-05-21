package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_parseBinaryLong_ExceptionPath_1 {

    @Test
    public void test_parseBinaryLong_lengthExceedsMaximumSignedLong() {
        byte[] buffer = new byte[10]; // length > 9
        int offset = 0;
        int length = 10;
        boolean negative = false;

        try {
            invokeParseBinaryLong(buffer, offset, length, negative);
            fail("Expected IllegalArgumentException for length exceeding maximum signed long");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
    
    @Test
    public void test_parseBinaryLong_negativeValue() {
        byte[] buffer = new byte[]{0, 1, 2}; // Example binary number
        int offset = 0;
        int length = 3; // Valid length
        boolean negative = true;

        long result = invokeParseBinaryLong(buffer, offset, length, negative);
        assertEquals(-258, result); // 0x0102 in 2's complement
    }

    @Test
    public void test_parseBinaryLong_zeroLength() {
        byte[] buffer = new byte[1]; // Just one byte
        int offset = 0;
        int length = 0; // Invalid length
        boolean negative = false;

        try {
            invokeParseBinaryLong(buffer, offset, length, negative);
            fail("Expected IllegalArgumentException for zero length");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_parseBinaryLong_positiveValue() {
        byte[] buffer = new byte[]{0, 1, 2}; // Example binary number
        int offset = 0;
        int length = 3; // Valid length
        boolean negative = false;

        long result = invokeParseBinaryLong(buffer, offset, length, negative);
        assertEquals(258, result); // 0x0102
    }

    private long invokeParseBinaryLong(byte[] buffer, int offset, int length, boolean negative) {
        try {
            java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("parseBinaryLong", byte[].class, int.class, int.class, boolean.class);
            method.setAccessible(true);
            return (long) method.invoke(null, buffer, offset, length, negative);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
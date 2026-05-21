package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestTarUtils_parseBinaryLong_NullEmpty_1 {

    @Test
    public void test_parseBinaryLong_nullBuffer() {
        try {
            invokeParseBinaryLong(null, 0, 1, false);
            Assert.fail("Expected IllegalArgumentException for null buffer");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void test_parseBinaryLong_emptyBuffer() {
        try {
            invokeParseBinaryLong(new byte[0], 0, 0, false);
            Assert.fail("Expected IllegalArgumentException for empty buffer");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void test_parseBinaryLong_exceedsMaxLength() {
        try {
            invokeParseBinaryLong(new byte[10], 0, 10, false);
            Assert.fail("Expected IllegalArgumentException for length exceeding max signed long");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void test_parseBinaryLong_positiveValue() {
        byte[] buffer = new byte[] {0, 0, 0, 1}; // Represents the number 1
        long result = invokeParseBinaryLong(buffer, 0, 4, false);
        assertEquals(1, result);
    }

    @Test
    public void test_parseBinaryLong_negativeValue() {
        byte[] buffer = new byte[] {0, 0, 0, 1}; // Represents the number 1
        long result = invokeParseBinaryLong(buffer, 0, 4, true);
        assertEquals(-1, result);
    }

    private long invokeParseBinaryLong(byte[] buffer, int offset, int length, boolean negative) {
        // Use reflection to access the private method
        try {
            java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("parseBinaryLong", byte[].class, int.class, int.class, boolean.class);
            method.setAccessible(true);
            return (long) method.invoke(null, buffer, offset, length, negative);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

public class TestTarUtils_formatBigIntegerBinary_NormalFlow_1 {

    @Test
    public void test_formatBigIntegerBinary_positiveValue_fillsBufferCorrectly() {
        byte[] buffer = new byte[10];
        long value = 12345L;
        int offset = 0;
        int length = 10;
        boolean negative = false;

        // Use reflection to access the private method
        try {
            java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class);
            method.setAccessible(true);
            method.invoke(null, value, buffer, offset, length, negative);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        byte[] expected = new byte[10];
        expected[5] = 0x30; // '0' in ASCII
        expected[6] = 0x39; // '9' in ASCII
        expected[7] = 0x30; // '0' in ASCII
        expected[8] = 0x39; // '9' in ASCII
        assertArrayEquals(expected, buffer);
    }

    @Test
    public void test_formatBigIntegerBinary_negativeValue_fillsBufferCorrectly() {
        byte[] buffer = new byte[10];
        long value = -12345L;
        int offset = 0;
        int length = 10;
        boolean negative = true;

        // Use reflection to access the private method
        try {
            java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class);
            method.setAccessible(true);
            method.invoke(null, value, buffer, offset, length, negative);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        byte[] expected = new byte[10];
        expected[5] = (byte) 0xff; // Fill with 0xff for negative
        expected[6] = (byte) 0xff; // Fill with 0xff for negative
        expected[7] = (byte) 0xff; // Fill with 0xff for negative
        expected[8] = (byte) 0xff; // Fill with 0xff for negative
        assertArrayEquals(expected, buffer);
    }

    @Test
    public void test_formatBigIntegerBinary_zeroValue_fillsBufferCorrectly() {
        byte[] buffer = new byte[10];
        long value = 0L;
        int offset = 0;
        int length = 10;
        boolean negative = false;

        // Use reflection to access the private method
        try {
            java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class);
            method.setAccessible(true);
            method.invoke(null, value, buffer, offset, length, negative);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        byte[] expected = new byte[10];
        expected[5] = 0x30; // '0' in ASCII
        expected[6] = 0x30; // '0' in ASCII
        expected[7] = 0x30; // '0' in ASCII
        expected[8] = 0x30; // '0' in ASCII
        assertArrayEquals(expected, buffer);
    }
}
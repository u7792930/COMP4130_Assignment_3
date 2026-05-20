package org.apache.commons.compress.archivers.tar;

import org.apache.commons.compress.archivers.tar.TarUtils;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatBigIntegerBinary_1 {

    @Test
    public void test_formatBigIntegerBinary_valueTooLarge() {
        long value = Long.MAX_VALUE;
        byte[] buf = new byte[4];
        int offset = 0;
        int length = 4;
        boolean negative = false;

        try {
            java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class);
            method.setAccessible(true);
            method.invoke(null, value, buf, offset, length, negative);
            fail("Expected IllegalArgumentException for value too large");
        } catch (IllegalArgumentException e) {
            assertEquals("Value " + value + " is too large for " + length + " byte field.", e.getMessage());
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void test_formatBigIntegerBinary_negativeValue() {
        long value = -1;
        byte[] buf = new byte[4];
        int offset = 0;
        int length = 4;
        boolean negative = true;

        try {
            java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class);
            method.setAccessible(true);
            method.invoke(null, value, buf, offset, length, negative);
            assertArrayEquals(new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff}, buf);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void test_formatBigIntegerBinary_zeroValue() {
        long value = 0;
        byte[] buf = new byte[4];
        int offset = 0;
        int length = 4;
        boolean negative = false;

        try {
            java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class);
            method.setAccessible(true);
            method.invoke(null, value, buf, offset, length, negative);
            assertArrayEquals(new byte[]{0, 0, 0, 0}, buf);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void test_formatBigIntegerBinary_positiveValue() {
        long value = 1;
        byte[] buf = new byte[4];
        int offset = 0;
        int length = 4;
        boolean negative = false;

        try {
            java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class);
            method.setAccessible(true);
            method.invoke(null, value, buf, offset, length, negative);
            assertArrayEquals(new byte[]{0, 0, 0, 1}, buf);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void test_formatBigIntegerBinary_fillBytes() {
        long value = 5;
        byte[] buf = new byte[8];
        int offset = 0;
        int length = 8;
        boolean negative = true;

        try {
            java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class);
            method.setAccessible(true);
            method.invoke(null, value, buf, offset, length, negative);
            assertArrayEquals(new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, 5}, buf);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}
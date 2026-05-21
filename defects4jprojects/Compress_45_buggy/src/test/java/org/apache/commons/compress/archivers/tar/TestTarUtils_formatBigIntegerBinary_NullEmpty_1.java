package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestTarUtils_formatBigIntegerBinary_NullEmpty_1 {

    @Test
    public void test_formatBigIntegerBinary_nullBuffer() {
        try {
            TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class)
                    .setAccessible(true);
            TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class)
                    .invoke(null, 12345L, null, 0, 4, false);
            Assert.fail("Expected NullPointerException for null buffer");
        } catch (NullPointerException e) {
            // Expected exception
        } catch (NoSuchMethodException e) {
            Assert.fail("Unexpected NoSuchMethodException: " + e.getMessage());
        } catch (IllegalAccessException e) {
            Assert.fail("Unexpected IllegalAccessException: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void test_formatBigIntegerBinary_emptyBuffer() {
        byte[] buf = new byte[0];
        try {
            TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class)
                    .setAccessible(true);
            TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class)
                    .invoke(null, 12345L, buf, 0, 0, false);
            Assert.fail("Expected IllegalArgumentException for empty buffer");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (NoSuchMethodException e) {
            Assert.fail("Unexpected NoSuchMethodException: " + e.getMessage());
        } catch (IllegalAccessException e) {
            Assert.fail("Unexpected IllegalAccessException: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void test_formatBigIntegerBinary_valueTooLarge() {
        byte[] buf = new byte[4];
        try {
            TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class)
                    .setAccessible(true);
            TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class)
                    .invoke(null, Long.MAX_VALUE, buf, 0, 4, false);
            Assert.fail("Expected IllegalArgumentException for value too large for buffer");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (NoSuchMethodException e) {
            Assert.fail("Unexpected NoSuchMethodException: " + e.getMessage());
        } catch (IllegalAccessException e) {
            Assert.fail("Unexpected IllegalAccessException: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void test_formatBigIntegerBinary_negativeValue() {
        byte[] buf = new byte[4];
        try {
            TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class)
                    .setAccessible(true);
            TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class)
                    .invoke(null, -12345L, buf, 0, 4, true);
            assertArrayEquals(new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xd7}, buf);
        } catch (NoSuchMethodException e) {
            Assert.fail("Unexpected NoSuchMethodException: " + e.getMessage());
        } catch (IllegalAccessException e) {
            Assert.fail("Unexpected IllegalAccessException: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void test_formatBigIntegerBinary_positiveValue() {
        byte[] buf = new byte[4];
        try {
            TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class)
                    .setAccessible(true);
            TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class)
                    .invoke(null, 12345L, buf, 0, 4, false);
            assertArrayEquals(new byte[]{0, 0, 0, (byte) 0x30}, buf);
        } catch (NoSuchMethodException e) {
            Assert.fail("Unexpected NoSuchMethodException: " + e.getMessage());
        } catch (IllegalAccessException e) {
            Assert.fail("Unexpected IllegalAccessException: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }
}
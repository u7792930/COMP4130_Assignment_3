package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatLongBinary_NullEmpty_1 {

    @Test
    public void test_formatLongBinary_nullBuffer() {
        try {
            TarUtils.class.getDeclaredMethod("formatLongBinary", long.class, byte[].class, int.class, int.class, boolean.class)
                .setAccessible(true);
            TarUtils.class.getDeclaredMethod("formatLongBinary", long.class, byte[].class, int.class, int.class, boolean.class)
                .invoke(null, 5, null, 0, 8, false);
            Assert.fail("Expected IllegalArgumentException for null buffer");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void test_formatLongBinary_emptyBuffer() {
        byte[] buf = new byte[0];
        try {
            TarUtils.class.getDeclaredMethod("formatLongBinary", long.class, byte[].class, int.class, int.class, boolean.class)
                .setAccessible(true);
            TarUtils.class.getDeclaredMethod("formatLongBinary", long.class, byte[].class, int.class, int.class, boolean.class)
                .invoke(null, 5, buf, 0, 0, false);
            Assert.fail("Expected IllegalArgumentException for empty buffer");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void test_formatLongBinary_negativeValueWithTrue() {
        byte[] buf = new byte[8];
        try {
            TarUtils.class.getDeclaredMethod("formatLongBinary", long.class, byte[].class, int.class, int.class, boolean.class)
                .setAccessible(true);
            TarUtils.class.getDeclaredMethod("formatLongBinary", long.class, byte[].class, int.class, int.class, boolean.class)
                .invoke(null, -5, buf, 0, 8, true);
            Assert.assertArrayEquals(new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xfb}, buf);
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void test_formatLongBinary_positiveValue() {
        byte[] buf = new byte[8];
        try {
            TarUtils.class.getDeclaredMethod("formatLongBinary", long.class, byte[].class, int.class, int.class, boolean.class)
                .setAccessible(true);
            TarUtils.class.getDeclaredMethod("formatLongBinary", long.class, byte[].class, int.class, int.class, boolean.class)
                .invoke(null, 5, buf, 0, 8, false);
            Assert.assertArrayEquals(new byte[]{0, 0, 0, 0, 0, 0, 0, 5}, buf);
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void test_formatLongBinary_largeValue() {
        byte[] buf = new byte[8];
        try {
            TarUtils.class.getDeclaredMethod("formatLongBinary", long.class, byte[].class, int.class, int.class, boolean.class)
                .setAccessible(true);
            TarUtils.class.getDeclaredMethod("formatLongBinary", long.class, byte[].class, int.class, int.class, boolean.class)
                .invoke(null, Long.MAX_VALUE, buf, 0, 8, false);
            Assert.assertArrayEquals(new byte[]{0, 0, 0, 0, 0, 0, 0, (byte) 0x7f}, buf);
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }
}
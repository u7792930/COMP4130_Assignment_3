package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import org.junit.Assert;

public class TestTarUtils_formatBigIntegerBinary_BoundaryValues_1 {

    @Test
    public void test_formatBigIntegerBinary_valueZero() {
        byte[] buf = new byte[10];
        TestHelper.invokeFormatBigIntegerBinary(0, buf, 0, 10, false);
        Assert.assertArrayEquals(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, buf);
    }

    @Test
    public void test_formatBigIntegerBinary_valueNegativeOne() {
        byte[] buf = new byte[10];
        TestHelper.invokeFormatBigIntegerBinary(-1, buf, 0, 10, true);
        Assert.assertArrayEquals(new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff}, buf);
    }

    @Test
    public void test_formatBigIntegerBinary_valueLongMax() {
        byte[] buf = new byte[10];
        TestHelper.invokeFormatBigIntegerBinary(Long.MAX_VALUE, buf, 0, 10, false);
        byte[] expected = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Adjust based on actual expected byte array
        Assert.assertArrayEquals(expected, buf);
    }

    @Test
    public void test_formatBigIntegerBinary_valueTooLarge() {
        byte[] buf = new byte[10];
        try {
            TestHelper.invokeFormatBigIntegerBinary(Long.MAX_VALUE, buf, 0, 9, false);
            Assert.fail("Expected IllegalArgumentException for value too large");
        } catch (RuntimeException e) {
            Assert.assertTrue(e.getCause() instanceof IllegalArgumentException);
        }
    }

    @Test
    public void test_formatBigIntegerBinary_valueNegativeMax() {
        byte[] buf = new byte[10];
        TestHelper.invokeFormatBigIntegerBinary(Long.MIN_VALUE, buf, 0, 10, true);
        byte[] expected = new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff}; // Adjust based on actual expected byte array
        Assert.assertArrayEquals(expected, buf);
    }
}

class TestHelper {
    static void invokeFormatBigIntegerBinary(long value, byte[] buf, int offset, int length, boolean negative) {
        try {
            java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class);
            method.setAccessible(true);
            method.invoke(null, value, buf, offset, length, negative);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatLongBinary_BoundaryValues_1 {

    @Test
    public void test_formatLongBinary_longZero() throws Exception {
        byte[] buffer = new byte[8];
        invokeFormatLongBinary(0L, buffer, 0, 8, false);
        Assert.assertArrayEquals(new byte[]{0, 0, 0, 0, 0, 0, 0, 0}, buffer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_formatLongBinary_longMaxValue() throws Exception {
        byte[] buffer = new byte[8];
        invokeFormatLongBinary(Long.MAX_VALUE, buffer, 0, 8, false);
    }

    @Test
    public void test_formatLongBinary_negativeValue() throws Exception {
        byte[] buffer = new byte[8];
        invokeFormatLongBinary(-1L, buffer, 0, 8, true);
        Assert.assertArrayEquals(new byte[]{-1, -1, -1, -1, -1, -1, -1, -1}, buffer);
    }

    @Test
    public void test_formatLongBinary_negativeMaxValue() throws Exception {
        byte[] buffer = new byte[8];
        invokeFormatLongBinary(Long.MAX_VALUE, buffer, 0, 8, true);
        Assert.assertArrayEquals(new byte[]{-128, 0, 0, 0, 0, 0, 0, 0}, buffer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_formatLongBinary_tooLargeValue() throws Exception {
        byte[] buffer = new byte[8];
        invokeFormatLongBinary(Long.MAX_VALUE + 1, buffer, 0, 8, false);
    }

    private void invokeFormatLongBinary(long value, byte[] buf, int offset, int length, boolean negative) throws Exception {
        java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("formatLongBinary", long.class, byte[].class, int.class, int.class, boolean.class);
        method.setAccessible(true);
        method.invoke(null, value, buf, offset, length, negative);
    }
}
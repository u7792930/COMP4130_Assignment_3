package org.apache.commons.compress.archivers.tar;

import org.apache.commons.compress.archivers.tar.TarUtils;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatLongBinary_1 {

    @Test
    public void test_formatLongBinary_valueTooLarge() {
        long value = Long.MAX_VALUE;
        byte[] buf = new byte[8];
        int offset = 0;
        int length = 8;
        boolean negative = false;

        try {
            invokeFormatLongBinary(value, buf, offset, length, negative);
            Assert.fail("Expected IllegalArgumentException for value too large");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Value " + value + " is too large for " + length + " byte field.", e.getMessage());
        }
    }

    @Test
    public void test_formatLongBinary_negativeValue() {
        long value = Long.MIN_VALUE; // Use Long.MIN_VALUE to test negative value
        byte[] buf = new byte[8];
        int offset = 0;
        int length = 8;
        boolean negative = true;

        invokeFormatLongBinary(value, buf, offset, length, negative);
        byte[] expected = new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x80};
        Assert.assertArrayEquals(expected, buf);
    }

    @Test
    public void test_formatLongBinary_correctBuffer() {
        long value = 10;
        byte[] buf = new byte[8];
        int offset = 0;
        int length = 8;
        boolean negative = false;

        invokeFormatLongBinary(value, buf, offset, length, negative);
        byte[] expected = new byte[]{0, 0, 0, 0, 0, 0, 0, 10};
        Assert.assertArrayEquals(expected, buf);
    }

    @Test
    public void test_formatLongBinary_negativeValueCorrectBuffer() {
        long value = 10;
        byte[] buf = new byte[8];
        int offset = 0;
        int length = 8;
        boolean negative = true;

        invokeFormatLongBinary(value, buf, offset, length, negative);
        byte[] expected = new byte[]{(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x0a};
        Assert.assertArrayEquals(expected, buf);
    }

    private void invokeFormatLongBinary(long value, byte[] buf, int offset, int length, boolean negative) {
        try {
            java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("formatLongBinary", long.class, byte[].class, int.class, int.class, boolean.class);
            method.setAccessible(true);
            method.invoke(null, value, buf, offset, length, negative);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
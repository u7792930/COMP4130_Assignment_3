package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestTarUtils_formatLongBinary_NormalFlow_1 {

    @Test
    public void test_formatLongBinary_positiveValue() throws Exception {
        byte[] buf = new byte[8];
        long value = 123456789L;
        int offset = 0;
        int length = 8;
        boolean negative = false;

        invokeFormatLongBinary(value, buf, offset, length, negative);
        
        byte[] expected = new byte[] {
            0x15, (byte)0xCD, 0x5B, 0x07, 0x00, 0x00, 0x00, 0x00
        };
        Assert.assertArrayEquals(expected, buf);
    }

    @Test
    public void test_formatLongBinary_negativeValue() throws Exception {
        byte[] buf = new byte[8];
        long value = -123456789L;
        int offset = 0;
        int length = 8;
        boolean negative = true;

        invokeFormatLongBinary(value, buf, offset, length, negative);
        
        byte[] expected = new byte[] {
            (byte)0xEA, (byte)0x32, (byte)0xA4, (byte)0xF8, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF
        };
        Assert.assertArrayEquals(expected, buf);
    }

    @Test
    public void test_formatLongBinary_zeroValue() throws Exception {
        byte[] buf = new byte[8];
        long value = 0L;
        int offset = 0;
        int length = 8;
        boolean negative = false;

        invokeFormatLongBinary(value, buf, offset, length, negative);
        
        byte[] expected = new byte[] {
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        };
        Assert.assertArrayEquals(expected, buf);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_formatLongBinary_tooLargeValue() throws Exception {
        byte[] buf = new byte[8];
        long value = Long.MAX_VALUE; // This value is too large for 8 bytes
        int offset = 0;
        int length = 8;
        boolean negative = false;

        invokeFormatLongBinary(value, buf, offset, length, negative);
    }

    private void invokeFormatLongBinary(long value, byte[] buf, int offset, int length, boolean negative) throws Exception {
        java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("formatLongBinary", long.class, byte[].class, int.class, int.class, boolean.class);
        method.setAccessible(true);
        method.invoke(null, value, buf, offset, length, negative);
    }
}
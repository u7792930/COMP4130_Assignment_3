package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestTarUtils_formatLongBinary_ExceptionPath_1 {

    @Test
    public void test_formatLongBinary_valueTooLarge() {
        long value = Long.MAX_VALUE; // Value that exceeds the limit
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
        long value = Long.MIN_VALUE; // Negative value
        byte[] buf = new byte[8];
        int offset = 0;
        int length = 8;
        boolean negative = false;
        
        try {
            invokeFormatLongBinary(value, buf, offset, length, negative);
            Assert.fail("Expected IllegalArgumentException for negative value");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Value " + value + " is too large for " + length + " byte field.", e.getMessage());
        }
    }

    @Test
    public void test_formatLongBinary_valueNegativeWithBuffer() {
        long value = 1; // Valid positive value
        byte[] buf = new byte[8];
        int offset = 0;
        int length = 8;
        boolean negative = true; // This will trigger the negative handling
        
        invokeFormatLongBinary(value, buf, offset, length, negative);
        Assert.assertArrayEquals(new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x00}, buf);
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
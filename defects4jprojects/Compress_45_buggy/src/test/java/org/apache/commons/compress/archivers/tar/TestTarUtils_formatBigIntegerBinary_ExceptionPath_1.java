package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestTarUtils_formatBigIntegerBinary_ExceptionPath_1 {

    @Test
    public void test_formatBigIntegerBinary_valueTooLarge() {
        long value = Long.MAX_VALUE;
        byte[] buf = new byte[5];
        int offset = 0;
        int length = 4; // length too small to hold the value
        boolean negative = false;

        try {
            java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class);
            method.setAccessible(true);
            method.invoke(null, value, buf, offset, length, negative);
            Assert.fail("Expected IllegalArgumentException for value too large");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals("Value " + value + " is too large for " + length + " byte field.", e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void test_formatBigIntegerBinary_negativeValue() {
        long value = -1; // testing a negative value
        byte[] buf = new byte[5];
        int offset = 0;
        int length = 5; // length is sufficient
        boolean negative = true;

        try {
            java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class);
            method.setAccessible(true);
            method.invoke(null, value, buf, offset, length, negative);
            // Check if the buffer is filled with -1 (0xff)
            Assert.assertEquals((byte) 0xff, buf[0]);
            Assert.assertEquals((byte) 0xff, buf[1]);
            Assert.assertEquals((byte) 0xff, buf[2]);
            Assert.assertEquals((byte) 0xff, buf[3]);
            Assert.assertEquals((byte) 0xff, buf[4]);
        } catch (IllegalArgumentException e) {
            Assert.fail("Did not expect IllegalArgumentException for negative value");
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void test_formatBigIntegerBinary_zeroValue() {
        long value = 0; // testing the zero value
        byte[] buf = new byte[5];
        int offset = 0;
        int length = 5; // length is sufficient
        boolean negative = false;

        try {
            java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("formatBigIntegerBinary", long.class, byte[].class, int.class, int.class, boolean.class);
            method.setAccessible(true);
            method.invoke(null, value, buf, offset, length, negative);
            // Check if the buffer is filled with 0
            Assert.assertEquals((byte) 0, buf[0]);
            Assert.assertEquals((byte) 0, buf[1]);
            Assert.assertEquals((byte) 0, buf[2]);
            Assert.assertEquals((byte) 0, buf[3]);
            Assert.assertEquals((byte) 0, buf[4]);
        } catch (IllegalArgumentException e) {
            Assert.fail("Did not expect IllegalArgumentException for zero value");
        } catch (Exception e) {
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }
}
package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import java.lang.reflect.Method;

public class TestTarUtils_parseBinaryBigInteger_ExceptionPath_1 {

    private long invokeParseBinaryBigInteger(byte[] buffer, int offset, int length, boolean negative) throws Exception {
        Method method = TarUtils.class.getDeclaredMethod("parseBinaryBigInteger", byte[].class, int.class, int.class, boolean.class);
        method.setAccessible(true);
        return (long) method.invoke(null, buffer, offset, length, negative);
    }

    @Test
    public void test_parseBinaryBigInteger_exceedsMaximumSignedLong() {
        byte[] buffer = new byte[]{0, 127, 127, 127, 127, 127, 127, 127, 127, 127}; // arbitrary values that will exceed the limit
        int offset = 0;
        int length = buffer.length;
        boolean negative = false;
        
        try {
            invokeParseBinaryBigInteger(buffer, offset, length, negative);
            Assert.fail("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(e.getMessage().contains("exceeds maximum signed long value"));
        } catch (Exception e) {
            Assert.fail("Unexpected exception was thrown: " + e.getMessage());
        }
    }

    @Test
    public void test_parseBinaryBigInteger_negativeValue() {
        byte[] buffer = new byte[]{0, 1, 0}; // represents a small positive number
        int offset = 0;
        int length = buffer.length;
        boolean negative = true; // should trigger the negative logic
        
        long result;
        try {
            result = invokeParseBinaryBigInteger(buffer, offset, length, negative);
            Assert.assertEquals(-1L, result); // 1 as negative should be -1
        } catch (IllegalArgumentException e) {
            Assert.fail("Unexpected IllegalArgumentException was thrown.");
        } catch (Exception e) {
            Assert.fail("Unexpected exception was thrown: " + e.getMessage());
        }
    }

    @Test
    public void test_parseBinaryBigInteger_zeroValue() {
        byte[] buffer = new byte[]{0, 0}; // represents 0
        int offset = 0;
        int length = buffer.length;
        boolean negative = false;
        
        long result;
        try {
            result = invokeParseBinaryBigInteger(buffer, offset, length, negative);
            Assert.assertEquals(0L, result); // 0 should return 0
        } catch (IllegalArgumentException e) {
            Assert.fail("Unexpected IllegalArgumentException was thrown.");
        } catch (Exception e) {
            Assert.fail("Unexpected exception was thrown: " + e.getMessage());
        }
    }

    @Test
    public void test_parseBinaryBigInteger_negativeLargeValue() {
        byte[] buffer = new byte[]{0, 0, 0, 1}; // represents a small positive number
        int offset = 0;
        int length = buffer.length;
        boolean negative = true; // should trigger the negative logic
        
        long result;
        try {
            result = invokeParseBinaryBigInteger(buffer, offset, length, negative);
            Assert.assertEquals(-1L, result); // 1 as negative should be -1
        } catch (IllegalArgumentException e) {
            Assert.fail("Unexpected IllegalArgumentException was thrown.");
        } catch (Exception e) {
            Assert.fail("Unexpected exception was thrown: " + e.getMessage());
        }
    }
}
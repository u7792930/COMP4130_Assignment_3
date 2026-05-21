package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;

public class TestTarUtils_parseBinaryBigInteger_NormalFlow_1 {

    @Test
    public void test_parseBinaryBigInteger_positiveValue() throws Exception {
        byte[] buffer = new byte[] {0, 0, 0, 5}; // Represents the value 5
        long result = invokeParseBinaryBigInteger(buffer, 0, 4, false);
        Assert.assertEquals(5L, result);
    }

    @Test
    public void test_parseBinaryBigInteger_negativeValue() throws Exception {
        byte[] buffer = new byte[] {0, 0, 0, 5}; // Represents the value 5
        long result = invokeParseBinaryBigInteger(buffer, 0, 4, true);
        Assert.assertEquals(-5L, result);
    }

    @Test
    public void test_parseBinaryBigInteger_largeValue() throws Exception {
        byte[] buffer = new byte[] {0, 0, 0, 0, 0, 0, 0, 1}; // Represents a large positive value (2^63)
        long result = invokeParseBinaryBigInteger(buffer, 0, 8, false);
        Assert.assertEquals(Long.MAX_VALUE, result); // 2^63 - 1
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_parseBinaryBigInteger_exceedingLongValue() throws Exception {
        byte[] buffer = new byte[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2}; // Represents a value exceeding Long.MAX_VALUE
        invokeParseBinaryBigInteger(buffer, 0, 12, false);
    }

    private long invokeParseBinaryBigInteger(byte[] buffer, int offset, int length, boolean negative) throws Exception {
        java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("parseBinaryBigInteger", byte[].class, int.class, int.class, boolean.class);
        method.setAccessible(true);
        return (long) method.invoke(null, buffer, offset, length, negative);
    }
}
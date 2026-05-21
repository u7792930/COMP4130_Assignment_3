package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestTarUtils_parseBinaryLong_NormalFlow_1 {

    @Test
    public void test_parseBinaryLong_positiveValue() throws Exception {
        byte[] buffer = {0, 0, 0, 1}; // 1 in binary
        long result = invokeParseBinaryLong(buffer, 0, 4, false);
        assertEquals(1L, result);
    }

    @Test
    public void test_parseBinaryLong_negativeValue() throws Exception {
        byte[] buffer = {0, 0, 0, 1}; // 1 in binary
        long result = invokeParseBinaryLong(buffer, 0, 4, true);
        assertEquals(-1L, result);
    }

    @Test
    public void test_parseBinaryLong_largeValue() throws Exception {
        byte[] buffer = {0, 0, 0, 127}; // 127 in binary
        long result = invokeParseBinaryLong(buffer, 0, 4, false);
        assertEquals(127L, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_parseBinaryLong_exceedsMaxLength() throws Exception {
        byte[] buffer = new byte[9]; // Length exceeds maximum
        invokeParseBinaryLong(buffer, 0, 9, false);
    }

    @Test
    public void test_parseBinaryLong_twoComplement() throws Exception {
        byte[] buffer = {0, 0, 0, 1}; // 1 in binary
        long result = invokeParseBinaryLong(buffer, 0, 4, true);
        assertEquals(-1L, result);
    }

    private long invokeParseBinaryLong(byte[] buffer, int offset, int length, boolean negative) throws Exception {
        java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("parseBinaryLong", byte[].class, int.class, int.class, boolean.class);
        method.setAccessible(true);
        return (long) method.invoke(null, buffer, offset, length, negative);
    }
}
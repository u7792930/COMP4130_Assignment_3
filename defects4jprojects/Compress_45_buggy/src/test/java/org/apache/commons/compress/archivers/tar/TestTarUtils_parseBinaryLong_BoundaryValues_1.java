package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_parseBinaryLong_BoundaryValues_1 {

    @Test(expected = IllegalArgumentException.class)
    public void test_parseBinaryLong_exceedsMaxSignedLong() throws Exception {
        byte[] buffer = new byte[] {0, 127, -1, -1, -1, -1, -1, -1, -1, 0}; // 9 bytes should throw exception
        invokeParseBinaryLong(buffer, 0, 9, false);
    }

    @Test
    public void test_parseBinaryLong_maxSignedLong() throws Exception {
        byte[] buffer = new byte[] {0, 127, -1, -1, -1, -1, -1, -1}; // 8 bytes representing Long.MAX_VALUE
        long result = invokeParseBinaryLong(buffer, 0, 8, false);
        Assert.assertEquals(Long.MAX_VALUE, result);
    }

    @Test
    public void test_parseBinaryLong_minSignedLong() throws Exception {
        byte[] buffer = new byte[] {0, -128, 0, 0, 0, 0, 0, 0}; // 8 bytes representing Long.MIN_VALUE
        long result = invokeParseBinaryLong(buffer, 0, 8, true);
        Assert.assertEquals(Long.MIN_VALUE, result);
    }

    @Test
    public void test_parseBinaryLong_zeroValue() throws Exception {
        byte[] buffer = new byte[] {0, 0, 0, 0, 0, 0, 0, 0}; // 8 bytes representing 0
        long result = invokeParseBinaryLong(buffer, 0, 8, false);
        Assert.assertEquals(0, result);
    }

    @Test
    public void test_parseBinaryLong_negativeValue() throws Exception {
        byte[] buffer = new byte[] {0, 1, 0, 0, 0, 0, 0, 0}; // 8 bytes representing -1
        long result = invokeParseBinaryLong(buffer, 0, 8, true);
        Assert.assertEquals(-1, result);
    }

    @Test
    public void test_parseBinaryLong_smallPositiveValue() throws Exception {
        byte[] buffer = new byte[] {0, 0, 0, 0, 0, 0, 0, 1}; // 8 bytes representing 1
        long result = invokeParseBinaryLong(buffer, 0, 8, false);
        Assert.assertEquals(1, result);
    }

    @Test
    public void test_parseBinaryLong_smallNegativeValue() throws Exception {
        byte[] buffer = new byte[] {0, 0, 0, 0, 0, 0, 0, 1}; // 8 bytes representing -1
        long result = invokeParseBinaryLong(buffer, 0, 8, true);
        Assert.assertEquals(-1, result);
    }

    private long invokeParseBinaryLong(byte[] buffer, int offset, int length, boolean negative) throws Exception {
        java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("parseBinaryLong", byte[].class, int.class, int.class, boolean.class);
        method.setAccessible(true);
        return (long) method.invoke(null, buffer, offset, length, negative);
    }
}
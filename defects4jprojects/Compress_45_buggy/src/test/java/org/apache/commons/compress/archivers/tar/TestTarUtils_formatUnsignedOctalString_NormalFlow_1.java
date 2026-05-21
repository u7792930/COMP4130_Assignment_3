package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestTarUtils_formatUnsignedOctalString_NormalFlow_1 {

    @Test
    public void test_formatUnsignedOctalString_valueZero() {
        byte[] buffer = new byte[10];
        TarUtils.formatUnsignedOctalString(0, buffer, 0, 10);
        Assert.assertArrayEquals(new byte[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0'}, buffer);
    }

    @Test
    public void test_formatUnsignedOctalString_valueEight() {
        byte[] buffer = new byte[10];
        TarUtils.formatUnsignedOctalString(8, buffer, 0, 10);
        Assert.assertArrayEquals(new byte[]{'0', '0', '0', '0', '0', '0', '0', '0', '1', '0'}, buffer);
    }

    @Test
    public void test_formatUnsignedOctalString_valueSixtyThree() {
        byte[] buffer = new byte[10];
        TarUtils.formatUnsignedOctalString(63, buffer, 0, 10);
        Assert.assertArrayEquals(new byte[]{'0', '0', '0', '0', '0', '0', '0', '0', '7', '7'}, buffer);
    }
}
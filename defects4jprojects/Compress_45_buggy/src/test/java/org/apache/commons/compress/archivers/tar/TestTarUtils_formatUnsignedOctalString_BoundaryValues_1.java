package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatUnsignedOctalString_BoundaryValues_1 {

    @Test
    public void test_formatUnsignedOctalString_zeroValue() {
        long value = 0;
        byte[] buffer = new byte[5];
        int offset = 0;
        int length = buffer.length;

        TarUtils.formatUnsignedOctalString(value, buffer, offset, length);
        Assert.assertEquals('0', (char) buffer[4]);
        Assert.assertEquals('0', (char) buffer[3]);
        Assert.assertEquals('0', (char) buffer[2]);
        Assert.assertEquals('0', (char) buffer[1]);
        Assert.assertEquals('0', (char) buffer[0]);
    }

    @Test
    public void test_formatUnsignedOctalString_maxValue() {
        long value = Long.MAX_VALUE;
        byte[] buffer = new byte[12];
        int offset = 0;
        int length = buffer.length;

        TarUtils.formatUnsignedOctalString(value, buffer, offset, length);
        String expected = Long.toOctalString(value);
        for (int i = 0; i < expected.length(); i++) {
            Assert.assertEquals((byte) expected.charAt(expected.length() - 1 - i), buffer[11 - i]);
        }
        for (int i = expected.length(); i < length; i++) {
            Assert.assertEquals((byte) '0', buffer[11 - i]);
        }
    }

    @Test
    public void test_formatUnsignedOctalString_valueTooLarge() {
        long value = Long.MAX_VALUE;
        byte[] buffer = new byte[10]; // Insufficient buffer size
        int offset = 0;
        int length = buffer.length;

        try {
            TarUtils.formatUnsignedOctalString(value, buffer, offset, length);
            Assert.fail("Expected IllegalArgumentException for value too large");
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(e.getMessage().contains("will not fit in octal number buffer"));
        }
    }

    @Test
    public void test_formatUnsignedOctalString_smallValue() {
        long value = 10; // Octal 12
        byte[] buffer = new byte[5];
        int offset = 0;
        int length = buffer.length;

        TarUtils.formatUnsignedOctalString(value, buffer, offset, length);
        Assert.assertEquals('1', (char) buffer[3]);
        Assert.assertEquals('2', (char) buffer[4]);
        Assert.assertEquals('0', (char) buffer[2]);
        Assert.assertEquals('0', (char) buffer[1]);
        Assert.assertEquals('0', (char) buffer[0]);
    }
}
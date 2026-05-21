package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatUnsignedOctalString_ExceptionPath_1 {

    @Test
    public void test_formatUnsignedOctalString_valueTooLarge() {
        long value = Long.MAX_VALUE;
        byte[] buffer = new byte[5];
        int offset = 0;
        int length = buffer.length;

        try {
            TarUtils.formatUnsignedOctalString(value, buffer, offset, length);
            Assert.fail("Expected IllegalArgumentException for value too large");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_formatUnsignedOctalString_bufferTooSmall() {
        long value = 8; // This will not fit in a buffer of length 1
        byte[] buffer = new byte[1];
        int offset = 0;
        int length = buffer.length;

        try {
            TarUtils.formatUnsignedOctalString(value, buffer, offset, length);
            Assert.fail("Expected IllegalArgumentException for buffer too small");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_formatUnsignedOctalString_negativeValue() {
        long value = -1; // Negative value should also trigger the exception
        byte[] buffer = new byte[5];
        int offset = 0;
        int length = buffer.length;

        try {
            TarUtils.formatUnsignedOctalString(value, buffer, offset, length);
            Assert.fail("Expected IllegalArgumentException for negative value");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
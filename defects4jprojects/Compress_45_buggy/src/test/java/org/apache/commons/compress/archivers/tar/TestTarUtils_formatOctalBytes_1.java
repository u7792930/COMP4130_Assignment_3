package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatOctalBytes_1 {

    @Test
    public void test_formatOctalBytes_valueTooLarge() {
        byte[] buf = new byte[10];
        int offset = 0;
        int length = buf.length;

        try {
            // Assuming the value 1234567890123456789 is too large to fit
            TarUtils.formatOctalBytes(1234567890123456789L, buf, offset, length);
            Assert.fail("Expected IllegalArgumentException for value too large");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_formatOctalBytes_negativeValue() {
        byte[] buf = new byte[10];
        int offset = 0;
        int length = buf.length;

        try {
            // Negative value should also throw exception
            TarUtils.formatOctalBytes(-1L, buf, offset, length);
            Assert.fail("Expected IllegalArgumentException for negative value");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_formatOctalBytes_bufferTooSmall() {
        byte[] buf = new byte[5]; // Buffer too small for the output
        int offset = 0;
        int length = buf.length;

        try {
            TarUtils.formatOctalBytes(100L, buf, offset, length);
            Assert.fail("Expected IllegalArgumentException for buffer too small");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
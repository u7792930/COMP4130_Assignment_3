package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatLongOctalBytes_1 {

    @Test
    public void test_formatLongOctalBytes_negativeValue() {
        long value = -1L;
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        try {
            TarUtils.formatLongOctalBytes(value, buf, offset, length);
            fail("Expected IllegalArgumentException for negative value");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_formatLongOctalBytes_bufferTooSmall() {
        long value = 123L;
        byte[] buf = new byte[6]; // Adjusted size for octal representation and space
        int offset = 0;
        int length = 6;
        try {
            TarUtils.formatLongOctalBytes(value, buf, offset, length);
            fail("Expected IllegalArgumentException for insufficient buffer size");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_formatLongOctalBytes_success() {
        long value = 83L;
        byte[] buf = new byte[12]; // Sufficient size for octal representation and space
        int offset = 0;
        int length = 11; // 11 to accommodate octal representation and space
        int updatedOffset = TarUtils.formatLongOctalBytes(value, buf, offset, length);
        
        assertEquals(11, updatedOffset);
        assertEquals('1', buf[0]);
        assertEquals('2', buf[1]);
        assertEquals('3', buf[2]);
        assertEquals(' ', buf[10]); // Check trailing space
    }

    @Test
    public void test_formatLongOctalBytes_zeroValue() {
        long value = 0L;
        byte[] buf = new byte[12]; // Sufficient size for octal representation and space
        int offset = 0;
        int length = 11; // 11 to accommodate octal representation and space
        int updatedOffset = TarUtils.formatLongOctalBytes(value, buf, offset, length);
        
        assertEquals(11, updatedOffset);
        assertEquals('0', buf[0]);
        assertEquals(' ', buf[10]); // Check trailing space
    }

    @Test
    public void test_formatLongOctalBytes_largeValue() {
        long value = 512L;
        byte[] buf = new byte[12]; // Sufficient size for octal representation and space
        int offset = 0;
        int length = 11; // 11 to accommodate octal representation and space
        int updatedOffset = TarUtils.formatLongOctalBytes(value, buf, offset, length);
        
        assertEquals(11, updatedOffset);
        assertEquals('1', buf[0]);
        assertEquals('0', buf[1]);
        assertEquals('0', buf[2]);
        assertEquals(' ', buf[10]); // Check trailing space
    }
}
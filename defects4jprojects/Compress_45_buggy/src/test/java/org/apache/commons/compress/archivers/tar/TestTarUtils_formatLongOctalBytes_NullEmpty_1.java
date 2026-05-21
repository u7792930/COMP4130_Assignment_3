package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestTarUtils_formatLongOctalBytes_NullEmpty_1 {

    @Test(expected = IllegalArgumentException.class)
    public void test_formatLongOctalBytes_nullBuffer() {
        long value = 12345L;
        byte[] buf = null;
        int offset = 0;
        int length = 10;
        
        TarUtils.formatLongOctalBytes(value, buf, offset, length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_formatLongOctalBytes_emptyBuffer() {
        long value = 12345L;
        byte[] buf = new byte[0];
        int offset = 0;
        int length = 0;
        
        TarUtils.formatLongOctalBytes(value, buf, offset, length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_formatLongOctalBytes_negativeOffset() {
        long value = 12345L;
        byte[] buf = new byte[10];
        int offset = -1;
        int length = 10;
        
        TarUtils.formatLongOctalBytes(value, buf, offset, length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_formatLongOctalBytes_insufficientBufferLength() {
        long value = 12345L;
        byte[] buf = new byte[5];
        int offset = 0;
        int length = 10; // Length exceeds buffer size
        
        TarUtils.formatLongOctalBytes(value, buf, offset, length);
    }

    @Test
    public void test_formatLongOctalBytes_validInput() {
        long value = 12345L;
        byte[] buf = new byte[12]; // Sufficient size for octal representation and space
        int offset = 0;
        int length = 11; // 11 to accommodate octal representation and space
        
        int resultOffset = TarUtils.formatLongOctalBytes(value, buf, offset, length);
        
        // Check the content of the buffer
        String expectedOutput = "30071 "; // Octal representation of 12345 followed by a space
        String actualOutput = new String(buf, offset, length);
        
        assertEquals(expectedOutput, actualOutput);
        assertEquals(offset + length, resultOffset);
    }
}
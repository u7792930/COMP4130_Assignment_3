package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestTarUtils_formatOctalBytes_NormalFlow_1 {

    @Test
    public void test_formatOctalBytes_normalCase() {
        byte[] buffer = new byte[12];
        long value = 12345;
        int offset = 0;
        int length = 12;
        int result = TarUtils.formatOctalBytes(value, buffer, offset, length);
        
        // Verify the updated offset
        assertEquals(12, result);
        
        // Verify the content of the buffer
        String expectedString = String.format("%011o ", value); // Octal format with space
        for (int i = 0; i < expectedString.length(); i++) {
            assertEquals((byte) expectedString.charAt(i), buffer[offset + i]);
        }
        assertEquals((byte) 0, buffer[offset + expectedString.length()]); // Trailing null
    }

    @Test
    public void test_formatOctalBytes_largeValue() {
        byte[] buffer = new byte[12];
        long value = 123456789;
        int offset = 0;
        int length = 12;
        int result = TarUtils.formatOctalBytes(value, buffer, offset, length);
        
        // Verify the updated offset
        assertEquals(12, result);
        
        // Verify the content of the buffer
        String expectedString = String.format("%011o ", value); // Octal format with space
        for (int i = 0; i < expectedString.length(); i++) {
            assertEquals((byte) expectedString.charAt(i), buffer[offset + i]);
        }
        assertEquals((byte) 0, buffer[offset + expectedString.length()]); // Trailing null
    }

    @Test
    public void test_formatOctalBytes_zeroValue() {
        byte[] buffer = new byte[12];
        long value = 0;
        int offset = 0;
        int length = 12;
        int result = TarUtils.formatOctalBytes(value, buffer, offset, length);
        
        // Verify the updated offset
        assertEquals(12, result);
        
        // Verify the content of the buffer
        String expectedString = String.format("%011o ", value); // Octal format with space
        for (int i = 0; i < expectedString.length(); i++) {
            assertEquals((byte) expectedString.charAt(i), buffer[offset + i]);
        }
        assertEquals((byte) 0, buffer[offset + expectedString.length()]); // Trailing null
    }
}
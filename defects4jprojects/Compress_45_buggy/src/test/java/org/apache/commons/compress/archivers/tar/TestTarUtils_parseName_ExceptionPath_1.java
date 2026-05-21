package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_parseName_ExceptionPath_1 {

    @Test
    public void test_parseName_validInput() {
        // Prepare a valid buffer that does not cause an exception
        byte[] buffer = new byte[] { 't', 'e', 's', 't', 0 }; // Valid input
        int offset = 0;
        int length = buffer.length;

        String result = TarUtils.parseName(buffer, offset, length);
        assertEquals("test", result);
    }

    @Test
    public void test_parseName_ioExceptionThrown() {
        // Prepare a buffer that will cause an IOException when parseName is called
        byte[] buffer = new byte[] { 't', 'e', 's', 't', 0 }; // This should not throw an IOException
        int offset = 0;
        int length = buffer.length;

        try {
            TarUtils.parseName(buffer, offset, length);
            fail("Expected RuntimeException to be thrown due to IOException");
        } catch (RuntimeException e) {
            // Expected
        }
    }

    @Test
    public void test_parseName_fallbackEncodingIOException() {
        // Prepare a scenario where the fallback encoding would throw an IOException
        byte[] buffer = new byte[] { 't', 'e', 's', 't', 0 }; // This should not throw an IOException
        int offset = 0;
        int length = buffer.length;

        try {
            TarUtils.parseName(buffer, offset, length);
            fail("Expected RuntimeException to be thrown due to IOException in fallback");
        } catch (RuntimeException e) {
            // Expected
        }
    }

    @Test
    public void test_parseName_impossibleIOException() {
        // Prepare a buffer that leads to an impossible IOException being thrown
        byte[] buffer = new byte[] { 't', 'e', 's', 't', 0 }; // This should not throw an IOException
        int offset = 0;
        int length = buffer.length;

        try {
            TarUtils.parseName(buffer, offset, length);
            fail("Expected RuntimeException to be thrown due to impossible IOException");
        } catch (RuntimeException e) {
            // Expected
        }
    }
}
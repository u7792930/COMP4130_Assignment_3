package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_parseOctal_ExceptionPath_1 {

    @Test
    public void test_parseOctal_invalidLength() {
        byte[] buffer = new byte[2];
        int offset = 0;
        int length = 1; // Invalid length should trigger exception
        try {
            TarUtils.parseOctal(buffer, offset, length);
            fail("Expected IllegalArgumentException for invalid length");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_parseOctal_leadingNull() {
        byte[] buffer = new byte[]{0, '1', '2', '3', 0}; // Leading null should return 0
        int offset = 0;
        int length = buffer.length;
        long result = TarUtils.parseOctal(buffer, offset, length);
        assertEquals(0L, result);
    }

    @Test
    public void test_parseOctal_invalidByte() {
        byte[] buffer = new byte[]{'8', ' '}; // Invalid byte '8' should trigger exception
        int offset = 0;
        int length = buffer.length;
        try {
            TarUtils.parseOctal(buffer, offset, length);
            fail("Expected IllegalArgumentException for invalid byte");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
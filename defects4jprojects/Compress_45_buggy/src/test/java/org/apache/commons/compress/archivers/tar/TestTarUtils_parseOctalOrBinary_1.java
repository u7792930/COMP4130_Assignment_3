package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_parseOctalOrBinary_1 {

    @Test
    public void test_parseOctalOrBinary_validOctal() {
        byte[] buffer = { '7', '5', '0', ' ' }; // Octal representation of 500
        long result = TarUtils.parseOctalOrBinary(buffer, 0, buffer.length);
        assertEquals(500L, result);
    }

    @Test
    public void test_parseOctalOrBinary_validBinary() {
        byte[] buffer = { (byte) 0x80, 0b00000001, 0b00000000 }; // Binary representation for 256
        long result = TarUtils.parseOctalOrBinary(buffer, 0, buffer.length);
        assertEquals(256L, result);
    }

    @Test
    public void test_parseOctalOrBinary_invalidOctal() {
        byte[] buffer = { '8', '0', ' ' }; // Invalid octal number
        try {
            TarUtils.parseOctalOrBinary(buffer, 0, buffer.length);
            fail("Expected IllegalArgumentException for invalid octal input");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
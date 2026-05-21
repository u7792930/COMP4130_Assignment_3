package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_parseOctal_NullEmpty_1 {

    @Test
    public void test_parseOctal_nullBuffer() {
        try {
            TarUtils.parseOctal(null, 0, 2);
            fail("Expected IllegalArgumentException for null buffer.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void test_parseOctal_emptyBuffer() {
        byte[] buffer = new byte[0];
        try {
            TarUtils.parseOctal(buffer, 0, 2);
            fail("Expected IllegalArgumentException for empty buffer.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void test_parseOctal_bufferWithLeadingNUL() {
        byte[] buffer = new byte[] { 0, '1', '2', '3', ' ' };
        long result = TarUtils.parseOctal(buffer, 0, buffer.length);
        assertEquals(0L, result);
    }

    @Test
    public void test_parseOctal_validOctal() {
        byte[] buffer = new byte[] { '1', '2', '3', ' ' };
        long result = TarUtils.parseOctal(buffer, 0, buffer.length);
        assertEquals(123L, result);
    }

    @Test
    public void test_parseOctal_withLeadingSpaces() {
        byte[] buffer = new byte[] { ' ', ' ', '1', '2', '3', ' ' };
        long result = TarUtils.parseOctal(buffer, 0, buffer.length);
        assertEquals(123L, result);
    }

    @Test
    public void test_parseOctal_withTrailingSpaces() {
        byte[] buffer = new byte[] { '1', '2', '3', ' ', ' ' };
        long result = TarUtils.parseOctal(buffer, 0, buffer.length);
        assertEquals(123L, result);
    }

    @Test
    public void test_parseOctal_allNULs() {
        byte[] buffer = new byte[] { 0, 0, 0, 0 };
        long result = TarUtils.parseOctal(buffer, 0, buffer.length);
        assertEquals(0L, result);
    }

    @Test
    public void test_parseOctal_invalidCharacter() {
        byte[] buffer = new byte[] { '1', '2', '8', ' ' };
        try {
            TarUtils.parseOctal(buffer, 0, buffer.length);
            fail("Expected IllegalArgumentException for invalid octal character.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    @Test
    public void test_parseOctal_lengthTooShort() {
        byte[] buffer = new byte[] { '1', '2' };
        try {
            TarUtils.parseOctal(buffer, 0, 1);
            fail("Expected IllegalArgumentException for length less than 2.");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
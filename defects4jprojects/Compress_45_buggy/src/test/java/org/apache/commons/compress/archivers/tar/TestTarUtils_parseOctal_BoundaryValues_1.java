package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

public class TestTarUtils_parseOctal_BoundaryValues_1 {

    @Test
    public void test_parseOctal_minimumLength() {
        byte[] buffer = new byte[] { '0', ' ' }; // Minimum valid input
        long result = TarUtils.parseOctal(buffer, 0, buffer.length);
        assertEquals(0L, result);
    }

    @Test
    public void test_parseOctal_leadingNUL() {
        byte[] buffer = new byte[] { 0, '1', '2', '3', ' ' }; // Leading NUL should return 0
        long result = TarUtils.parseOctal(buffer, 0, buffer.length);
        assertEquals(0L, result);
    }

    @Test
    public void test_parseOctal_invalidCharacter() {
        byte[] buffer = new byte[] { '1', '2', 'A', ' ' }; // Invalid character 'A'
        try {
            TarUtils.parseOctal(buffer, 0, buffer.length);
            fail("Expected IllegalArgumentException for invalid character.");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
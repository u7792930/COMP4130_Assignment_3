package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_parseBoolean_NormalFlow_1 {

    @Test
    public void test_parseBoolean_validTrueValue() {
        byte[] buffer = new byte[] {0, 0, 0, 0, 1}; // The boolean value to be parsed is at the end
        int offset = 4;
        assertTrue(TarUtils.parseBoolean(buffer, offset));
    }

    @Test
    public void test_parseBoolean_validFalseValue() {
        byte[] buffer = new byte[] {0, 0, 0, 0, 0}; // The boolean value to be parsed is at the end
        int offset = 4;
        assertFalse(TarUtils.parseBoolean(buffer, offset));
    }

    @Test
    public void test_parseBoolean_trueValueWithLeadingSpaces() {
        byte[] buffer = new byte[] {' ', ' ', ' ', 1}; // The boolean value to be parsed is preceded by spaces
        int offset = 3;
        assertTrue(TarUtils.parseBoolean(buffer, offset));
    }
}
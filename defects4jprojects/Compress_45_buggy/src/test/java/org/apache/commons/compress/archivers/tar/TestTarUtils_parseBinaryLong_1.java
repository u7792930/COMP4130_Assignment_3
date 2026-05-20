package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_parseBinaryLong_1 {

    @Test
    public void test_parseBinaryLong_lengthExceedsLimit() {
        byte[] buffer = new byte[10]; // Length is 10
        int offset = 0;
        int length = 10;
        boolean negative = false;

        try {
            TarUtils.parseBinaryLong(buffer, offset, length, negative);
            fail("Expected IllegalArgumentException for length exceeding limit");
        } catch (IllegalArgumentException e) {
            assertEquals("At offset 0, 10 byte binary number exceeds maximum signed long value", e.getMessage());
        }
    }

    @Test
    public void test_parseBinaryLong_negativeValue() {
        byte[] buffer = new byte[] {0, 1, 2}; // Example binary representation
        int offset = 0;
        int length = 3;
        boolean negative = true;

        long result = TarUtils.parseBinaryLong(buffer, offset, length, negative);
        assertEquals(-258, result); // (1*256 + 2) is 258, negative makes it -258
    }

    @Test
    public void test_parseBinaryLong_positiveValue() {
        byte[] buffer = new byte[] {0, 1, 2}; // Example binary representation
        int offset = 0;
        int length = 3;
        boolean negative = false;

        long result = TarUtils.parseBinaryLong(buffer, offset, length, negative);
        assertEquals(258, result); // (1*256 + 2) is 258
    }
}
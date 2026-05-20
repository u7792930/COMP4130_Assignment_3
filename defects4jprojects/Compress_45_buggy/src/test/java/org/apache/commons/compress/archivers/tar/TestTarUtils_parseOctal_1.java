package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_parseOctal_1 {

    @Test
    public void test_parseOctal_invalidLength() {
        byte[] buffer = new byte[3];
        int offset = 0;
        int length = 1; // Invalid length less than 2

        try {
            TarUtils.parseOctal(buffer, offset, length);
            Assert.fail("Expected IllegalArgumentException for invalid length");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_parseOctal_leadingNull() {
        byte[] buffer = new byte[] { 0, '1', '2', '3', ' ' };
        int offset = 0;
        int length = 5;

        long result = TarUtils.parseOctal(buffer, offset, length);
        Assert.assertEquals(0L, result); // Should return 0 for leading NUL
    }

    @Test
    public void test_parseOctal_invalidCharacter() {
        byte[] buffer = new byte[] { '8', ' ' }; // Invalid octal character '8'
        int offset = 0;
        int length = 2;

        try {
            TarUtils.parseOctal(buffer, offset, length);
            Assert.fail("Expected IllegalArgumentException for invalid character");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
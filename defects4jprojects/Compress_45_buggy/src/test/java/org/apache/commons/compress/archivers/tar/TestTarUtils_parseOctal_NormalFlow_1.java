package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

public class TestTarUtils_parseOctal_NormalFlow_1 {

    @Test
    public void test_parseOctal_validInput() {
        byte[] buffer = {'1', '2', '3', ' ', 0}; // Octal '123' followed by a space and NUL
        long result = TarUtils.parseOctal(buffer, 0, buffer.length);
        assertEquals(83L, result); // 1*64 + 2*8 + 3*1 = 83
    }

    @Test
    public void test_parseOctal_leadingSpaces() {
        byte[] buffer = {' ', ' ', '4', '5', ' ', 0}; // Octal '45' with leading spaces
        long result = TarUtils.parseOctal(buffer, 0, buffer.length);
        assertEquals(37L, result); // 4*8 + 5*1 = 37
    }

    @Test
    public void test_parseOctal_trailingSpaces() {
        byte[] buffer = {'6', '7', ' ', ' ', 0}; // Octal '67' with trailing spaces
        long result = TarUtils.parseOctal(buffer, 0, buffer.length);
        assertEquals(55L, result); // 6*8 + 7*1 = 55
    }
}
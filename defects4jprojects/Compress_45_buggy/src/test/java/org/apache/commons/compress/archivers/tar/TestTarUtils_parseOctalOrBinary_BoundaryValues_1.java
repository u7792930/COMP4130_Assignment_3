package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_parseOctalOrBinary_BoundaryValues_1 {

    @Test
    public void test_parseOctalOrBinary_minimumLength() {
        byte[] buffer = new byte[] { 0x00 }; // Minimum length for octal
        long result = TarUtils.parseOctalOrBinary(buffer, 0, 1);
        assertEquals(0L, result); // Expect octal interpretation of 0
    }

    @Test
    public void test_parseOctalOrBinary_maximumOctalValue() {
        byte[] buffer = new byte[] { 0x07, 0x07, 0x07, 0x07, 0x07, 0x07, 0x07, 0x07 }; // Octal 77777777
        long result = TarUtils.parseOctalOrBinary(buffer, 0, 8);
        assertEquals(2097151L, result); // Expect octal 77777777 to be 2097151 in decimal
    }

    @Test
    public void test_parseOctalOrBinary_negativeBinary() {
        byte[] buffer = new byte[] { (byte) 0xff, 0x01 }; // First byte indicates a negative binary number
        long result = TarUtils.parseOctalOrBinary(buffer, 0, 2);
        assertEquals(-1L, result); // Expect -1 for binary interpretation of 0b00000001 with negative sign
    }

    @Test
    public void test_parseOctalOrBinary_binaryInterpretation() {
        byte[] buffer = new byte[] { (byte) 0x80, 0x01 }; // First byte indicates binary interpretation
        long result = TarUtils.parseOctalOrBinary(buffer, 0, 2);
        assertEquals(1L, result); // Expect binary interpretation of 0b00000001
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_parseOctalOrBinary_invalidOctal() {
        byte[] buffer = new byte[] { 0x08 }; // Invalid octal digit
        TarUtils.parseOctalOrBinary(buffer, 0, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_parseOctalOrBinary_exceedingBinary() {
        byte[] buffer = new byte[] { (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff }; // Exceeds signed long
        TarUtils.parseOctalOrBinary(buffer, 0, 9);
    }
}
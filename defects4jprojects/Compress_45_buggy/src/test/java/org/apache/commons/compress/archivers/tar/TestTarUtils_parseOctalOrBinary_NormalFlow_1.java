package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestTarUtils_parseOctalOrBinary_NormalFlow_1 {

    @Test
    public void test_parseOctalOrBinary_validOctal() {
        byte[] buffer = { '7', '5', '0', ' ' }; // Representing octal 750
        int offset = 0;
        int length = 4;
        long expected = 488; // Decimal equivalent of octal 750
        long result = TarUtils.parseOctalOrBinary(buffer, offset, length);
        assertEquals(expected, result);
    }

    @Test
    public void test_parseOctalOrBinary_validBinary() {
        byte[] buffer = { (byte) 0x80, 0x01, 0x00 }; // First byte indicates binary
        int offset = 0;
        int length = 3;
        long expected = 256; // Binary 100000000
        long result = TarUtils.parseOctalOrBinary(buffer, offset, length);
        assertEquals(expected, result);
    }

    @Test
    public void test_parseOctalOrBinary_validNegativeBinary() {
        byte[] buffer = { (byte) 0xff, 0x01, 0x00 }; // First byte indicates negative binary
        int offset = 0;
        int length = 3;
        long expected = -256; // Negative binary 100000000
        long result = TarUtils.parseOctalOrBinary(buffer, offset, length);
        assertEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_parseOctalOrBinary_invalidOctal() {
        byte[] buffer = { '8', '0', ' ' }; // Invalid octal number
        int offset = 0;
        int length = 3;
        TarUtils.parseOctalOrBinary(buffer, offset, length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_parseOctalOrBinary_invalidBinary() {
        byte[] buffer = { (byte) 0x80, 0x02, 0x00 }; // Invalid binary number
        int offset = 0;
        int length = 3;
        TarUtils.parseOctalOrBinary(buffer, offset, length);
    }

    @Test
    public void test_parseOctalOrBinary_validBinaryLong() {
        byte[] buffer = { (byte) 0x80, 0x00, 0x01, 0x00 }; // First byte indicates binary
        int offset = 0;
        int length = 4;
        long expected = 65536; // Binary 10000000000000000
        long result = TarUtils.parseOctalOrBinary(buffer, offset, length);
        assertEquals(expected, result);
    }
}
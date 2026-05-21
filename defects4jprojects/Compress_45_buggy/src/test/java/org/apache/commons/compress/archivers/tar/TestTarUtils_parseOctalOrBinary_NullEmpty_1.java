package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_parseOctalOrBinary_NullEmpty_1 {

    @Test(expected = IllegalArgumentException.class)
    public void test_parseOctalOrBinary_nullBuffer() {
        TarUtils.parseOctalOrBinary(null, 0, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_parseOctalOrBinary_emptyBuffer() {
        TarUtils.parseOctalOrBinary(new byte[0], 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_parseOctalOrBinary_negativeOffset() {
        TarUtils.parseOctalOrBinary(new byte[]{0}, -1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_parseOctalOrBinary_invalidLength() {
        TarUtils.parseOctalOrBinary(new byte[]{0}, 0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_parseOctalOrBinary_invalidOctal() {
        TarUtils.parseOctalOrBinary(new byte[]{(byte) 0x00, (byte) 0x08}, 0, 2);
    }

    @Test
    public void test_parseOctalOrBinary_validOctal() {
        long result = TarUtils.parseOctalOrBinary(new byte[]{(byte) 0x00, (byte) 0x07, (byte) 0x00}, 0, 3);
        assertEquals(7, result);
    }

    @Test
    public void test_parseOctalOrBinary_validBinary() {
        long result = TarUtils.parseOctalOrBinary(new byte[]{(byte) 0x80, (byte) 0x01, (byte) 0x00}, 0, 3);
        assertEquals(1, result);
    }

    @Test
    public void test_parseOctalOrBinary_largeBinary() {
        long result = TarUtils.parseOctalOrBinary(new byte[]{(byte) 0x80, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, 0, 5);
        assertEquals(4294967295L, result);
    }
}
package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatOctalBytes_NullEmpty_1 {

    @Test(expected = IllegalArgumentException.class)
    public void test_formatOctalBytes_nullBuffer() {
        long value = 10L;
        byte[] buf = null;
        int offset = 0;
        int length = 10;

        TarUtils.formatOctalBytes(value, buf, offset, length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_formatOctalBytes_emptyBuffer() {
        long value = 10L;
        byte[] buf = new byte[0];
        int offset = 0;
        int length = 0;

        TarUtils.formatOctalBytes(value, buf, offset, length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_formatOctalBytes_offsetOutOfBounds() {
        long value = 10L;
        byte[] buf = new byte[5];
        int offset = 5; // out of bounds
        int length = 5;

        TarUtils.formatOctalBytes(value, buf, offset, length);
    }

    @Test
    public void test_formatOctalBytes_validInput() {
        long value = 10L;
        byte[] buf = new byte[12]; // Enough space for octal representation, space, and null
        int offset = 0;
        int length = 12;

        int result = TarUtils.formatOctalBytes(value, buf, offset, length);

        assertEquals(12, result);
        assertArrayEquals(new byte[] { '1', '2', ' ', 0 }, new byte[] { buf[0], buf[1], buf[10], buf[11] });
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_formatOctalBytes_insufficientBuffer() {
        long value = 1000L; // Requires more space for octal representation
        byte[] buf = new byte[10]; // Not enough space
        int offset = 0;
        int length = 10;

        TarUtils.formatOctalBytes(value, buf, offset, length);
    }
}
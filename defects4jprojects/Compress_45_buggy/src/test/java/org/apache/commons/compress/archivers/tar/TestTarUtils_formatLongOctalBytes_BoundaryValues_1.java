package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestTarUtils_formatLongOctalBytes_BoundaryValues_1 {

    @Test
    public void test_formatLongOctalBytes_zeroValue() {
        long value = 0;
        byte[] buf = new byte[12];
        int offset = 0;
        int length = 11;
        int expectedOffset = 11;

        int resultOffset = TarUtils.formatLongOctalBytes(value, buf, offset, length);
        byte[] expectedBuf = "00000000000 ".getBytes(); // 11 chars for octal + space

        assertArrayEquals(expectedBuf, buf);
        assertEquals(expectedOffset, resultOffset);
    }

    @Test
    public void test_formatLongOctalBytes_negativeValue() {
        long value = -1;
        byte[] buf = new byte[12];
        int offset = 0;
        int length = 11;
        int expectedOffset = 11;

        int resultOffset = TarUtils.formatLongOctalBytes(value, buf, offset, length);
        byte[] expectedBuf = "17777777777 ".getBytes(); // octal for -1 with space

        assertArrayEquals(expectedBuf, buf);
        assertEquals(expectedOffset, resultOffset);
    }

    @Test
    public void test_formatLongOctalBytes_maxLongValue() {
        long value = Long.MAX_VALUE;
        byte[] buf = new byte[12];
        int offset = 0;
        int length = 11;
        int expectedOffset = 11;

        int resultOffset = TarUtils.formatLongOctalBytes(value, buf, offset, length);
        byte[] expectedBuf = "77777777777 ".getBytes(); // octal for Long.MAX_VALUE with space

        assertArrayEquals(expectedBuf, buf);
        assertEquals(expectedOffset, resultOffset);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_formatLongOctalBytes_bufferTooSmall() {
        long value = 123;
        byte[] buf = new byte[10]; // too small
        int offset = 0;
        int length = 11; // requested length exceeds buffer size

        TarUtils.formatLongOctalBytes(value, buf, offset, length);
    }
}
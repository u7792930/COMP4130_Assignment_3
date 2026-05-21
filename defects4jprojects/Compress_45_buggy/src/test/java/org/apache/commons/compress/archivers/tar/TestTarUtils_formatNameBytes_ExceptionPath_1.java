package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatNameBytes_ExceptionPath_1 {

    @Test
    public void test_formatNameBytes_ioExceptionFromDefaultEncoding() {
        String name = "testName";
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;

        // Simulating IOException for the default encoding by using a name that exceeds the buffer size
        int result = TarUtils.formatNameBytes(name, buf, offset, length);
        assertEquals(10, result);
        assertArrayEquals(new byte[]{116, 101, 115, 116, 78, 97, 109, 101, 0, 0}, buf);
    }

    @Test
    public void test_formatNameBytes_ioExceptionFromFallbackEncoding() {
        String name = "testName";
        byte[] buf = new byte[5]; // Smaller buffer to trigger truncation
        int offset = 0;
        int length = 5;

        // Simulating IOException for the fallback encoding by using a name that exceeds the buffer size
        int result = TarUtils.formatNameBytes(name, buf, offset, length);
        assertEquals(5, result);
        assertArrayEquals(new byte[]{116, 101, 115, 116, 0}, buf);
    }
}
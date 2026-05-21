package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

public class TestTarUtils_formatNameBytes_NormalFlow_1 {

    @Test
    public void test_formatNameBytes_validInput() {
        String name = "testName";
        byte[] buf = new byte[20];
        int offset = 0;
        int length = name.length();

        int result = TarUtils.formatNameBytes(name, buf, offset, length);

        assertEquals("The result offset should be the sum of the offset and length", offset + length, result);
        assertEquals("Buffer should contain the name", 't', buf[0]);
        assertEquals("Buffer should contain the name", 'e', buf[1]);
        assertEquals("Buffer should contain the name", 's', buf[2]);
        assertEquals("Buffer should contain the name", 't', buf[3]);
        assertEquals("Buffer should contain the name", 'N', buf[4]);
        assertEquals("Buffer should contain the name", 'a', buf[5]);
        assertEquals("Buffer should contain the name", 'm', buf[6]);
        assertEquals("Buffer should contain the name", 'e', buf[7]);
        assertEquals("Buffer should not contain any extra data", 0, buf[8]);
    }

    @Test
    public void test_formatNameBytes_truncatedBuffer() {
        String name = "longNameExample";
        byte[] buf = new byte[10]; // buffer too small
        int offset = 0;
        int length = buf.length; // copy the whole buffer length

        int result = TarUtils.formatNameBytes(name, buf, offset, length);

        assertEquals("The result offset should be the sum of the offset and length", offset + length, result);
        assertEquals("Buffer should contain the name", 'l', buf[0]);
        assertEquals("Buffer should contain the name", 'o', buf[1]);
        assertEquals("Buffer should contain the name", 'n', buf[2]);
        assertEquals("Buffer should contain the name", 'g', buf[3]);
        assertEquals("Buffer should contain the name", 'N', buf[4]);
        assertEquals("Buffer should contain the name", 'a', buf[5]);
        assertEquals("Buffer should contain the name", 'm', buf[6]);
        assertEquals("Buffer should contain the name", 'e', buf[7]);
        assertEquals("Buffer should contain the name", 'E', buf[8]);
        assertEquals("Buffer should contain the name", 'x', buf[9]);
    }

    @Test
    public void test_formatNameBytes_bufferLongerThanName() {
        String name = "short";
        byte[] buf = new byte[10]; // buffer longer than name
        int offset = 0;
        int length = name.length();

        int result = TarUtils.formatNameBytes(name, buf, offset, length);

        assertEquals("The result offset should be the sum of the offset and length", offset + length, result);
        assertEquals("Buffer should contain the name", 's', buf[0]);
        assertEquals("Buffer should contain the name", 'h', buf[1]);
        assertEquals("Buffer should contain the name", 'o', buf[2]);
        assertEquals("Buffer should contain the name", 'r', buf[3]);
        assertEquals("Buffer should contain the name", 't', buf[4]);
        assertEquals("Buffer should be filled with trailing NULs", 0, buf[5]);
        assertEquals("Buffer should be filled with trailing NULs", 0, buf[6]);
        assertEquals("Buffer should be filled with trailing NULs", 0, buf[7]);
        assertEquals("Buffer should be filled with trailing NULs", 0, buf[8]);
        assertEquals("Buffer should be filled with trailing NULs", 0, buf[9]);
    }
}
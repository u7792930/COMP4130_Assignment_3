package org.apache.commons.compress.archivers.tar;

import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import static org.junit.Assert.*;

public class TestTarUtils_formatNameBytes_2 {

    @Test
    public void test_formatNameBytes_nameTooLong() {
        String name = "ThisNameIsDefinitelyTooLongForTheBuffer";
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        ZipEncoding encoding = ZipEncoding.getZipEncoding("UTF-8");

        try {
            int result = TarUtils.formatNameBytes(name, buf, offset, length, encoding);
            Assert.assertEquals(offset + length, result);
            // Check if the buffer has been filled correctly (truncated)
            String expected = "ThisNameI"; // Truncated based on buffer size
            Assert.assertArrayEquals(expected.getBytes(), java.util.Arrays.copyOf(buf, expected.length()));
            // Check the rest of the buffer to ensure it's padded with NULs
            for (int i = expected.length(); i < buf.length; i++) {
                Assert.assertEquals(0, buf[i]);
            }
        } catch (IOException e) {
            Assert.fail("IOException should not be thrown");
        }
    }

    @Test
    public void test_formatNameBytes_bufferLongerThanName() {
        String name = "ShortName";
        byte[] buf = new byte[20];
        int offset = 0;
        int length = 20;
        ZipEncoding encoding = ZipEncoding.getZipEncoding("UTF-8");

        try {
            int result = TarUtils.formatNameBytes(name, buf, offset, length, encoding);
            Assert.assertEquals(offset + length, result);
            // Check if the buffer has been filled correctly
            String expected = "ShortName";
            Assert.assertArrayEquals(expected.getBytes(), java.util.Arrays.copyOf(buf, expected.length()));
            // Check the rest of the buffer to ensure it's padded with NULs
            for (int i = expected.length(); i < buf.length; i++) {
                Assert.assertEquals(0, buf[i]);
            }
        } catch (IOException e) {
            Assert.fail("IOException should not be thrown");
        }
    }

    @Test
    public void test_formatNameBytes_emptyName() {
        String name = "";
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        ZipEncoding encoding = ZipEncoding.getZipEncoding("UTF-8");

        try {
            int result = TarUtils.formatNameBytes(name, buf, offset, length, encoding);
            Assert.assertEquals(offset + length, result);
            // Check the buffer to ensure it's filled with NULs
            for (int i = 0; i < buf.length; i++) {
                Assert.assertEquals(0, buf[i]);
            }
        } catch (IOException e) {
            Assert.fail("IOException should not be thrown");
        }
    }
}
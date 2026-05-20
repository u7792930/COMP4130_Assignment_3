package org.apache.commons.compress.archivers.tar;

import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import static org.junit.Assert.*;

public class TestTarUtils_parseName_2 {

    @Test
    public void test_parseName_emptyBuffer() {
        byte[] buffer = new byte[0];
        int offset = 0;
        int length = 0;
        ZipEncoding encoding = ZipEncoding.getZipEncoding("UTF-8");

        try {
            String result = TarUtils.parseName(buffer, offset, length, encoding);
            Assert.assertEquals("", result);
        } catch (IOException e) {
            Assert.fail("IOException should not have been thrown.");
        }
    }

    @Test
    public void test_parseName_bufferWithNUL() {
        byte[] buffer = new byte[] { 'a', 'b', 'c', 0, 'd', 'e' };
        int offset = 0;
        int length = buffer.length;
        ZipEncoding encoding = ZipEncoding.getZipEncoding("UTF-8");

        try {
            String result = TarUtils.parseName(buffer, offset, length, encoding);
            Assert.assertEquals("abc", result);
        } catch (IOException e) {
            Assert.fail("IOException should not have been thrown.");
        }
    }

    @Test
    public void test_parseName_lengthExceedsBuffer() {
        byte[] buffer = new byte[] { 'f', 'g', 'h', 0 };
        int offset = 0;
        int length = 10; // Exceeds actual buffer length
        ZipEncoding encoding = ZipEncoding.getZipEncoding("UTF-8");

        try {
            String result = TarUtils.parseName(buffer, offset, length, encoding);
            Assert.assertEquals("fgh", result);
        } catch (IOException e) {
            Assert.fail("IOException should not have been thrown.");
        }
    }
}
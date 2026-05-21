package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;

public class TestTarUtils_parseName_ExceptionPath_2 {

    @Test
    public void test_parseName_validBuffer() throws IOException {
        byte[] buffer = "testfile".getBytes(); // Valid buffer
        int offset = 0;
        int length = buffer.length; // Use full length
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        String result = TarUtils.parseName(buffer, offset, length, encoding);
        Assert.assertEquals("testfile", result); // Check if the result matches the expected name
    }

    @Test
    public void test_parseName_bufferWithNullTerminator() throws IOException {
        byte[] buffer = "testfile\0extra".getBytes(); // Buffer with null terminator
        int offset = 0;
        int length = buffer.length; // Use full length
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        String result = TarUtils.parseName(buffer, offset, length, encoding);
        Assert.assertEquals("testfile", result); // Check if the result matches the expected name before null
    }

    @Test
    public void test_parseName_lengthTooLarge() {
        byte[] buffer = new byte[5]; // Create a buffer of length 5
        int offset = 0;
        int length = 10; // Length exceeds buffer size
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        try {
            TarUtils.parseName(buffer, offset, length, encoding);
            Assert.fail("Expected IOException for length exceeding buffer size");
        } catch (IOException e) {
            // Exception is expected
        }
    }

    @Test
    public void test_parseName_offsetOutOfBounds() {
        byte[] buffer = new byte[5]; // Create a buffer of length 5
        int offset = 10; // Simulate offset out of bounds
        int length = 5;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        try {
            TarUtils.parseName(buffer, offset, length, encoding);
            Assert.fail("Expected IOException for offset out of bounds");
        } catch (IOException e) {
            // Exception is expected
        }
    }

    @Test
    public void test_parseName_nullBuffer() {
        byte[] buffer = null; // Simulate null buffer
        int offset = 0;
        int length = 10;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        try {
            TarUtils.parseName(buffer, offset, length, encoding);
            Assert.fail("Expected IOException for null buffer");
        } catch (IOException e) {
            // Exception is expected
        }
    }
}
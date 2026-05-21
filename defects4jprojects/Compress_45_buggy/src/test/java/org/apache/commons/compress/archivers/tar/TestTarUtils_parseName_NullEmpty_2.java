package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import org.junit.Assert;
import java.io.IOException;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;

public class TestTarUtils_parseName_NullEmpty_2 {

    @Test
    public void test_parseName_nullBuffer() {
        try {
            TarUtils.parseName(null, 0, 10, ZipEncodingHelper.getZipEncoding("UTF-8"));
            Assert.fail("Expected IOException for null buffer");
        } catch (IOException e) {
            // Expected exception
        }
    }

    @Test
    public void test_parseName_emptyBuffer() {
        byte[] buffer = new byte[0];
        try {
            String result = TarUtils.parseName(buffer, 0, 0, ZipEncodingHelper.getZipEncoding("UTF-8"));
            Assert.assertEquals("", result);
        } catch (IOException e) {
            Assert.fail("IOException should not have been thrown for empty buffer");
        }
    }

    @Test
    public void test_parseName_bufferWithNullByte() {
        byte[] buffer = new byte[]{65, 66, 67, 0}; // "ABC" followed by null
        try {
            String result = TarUtils.parseName(buffer, 0, 4, ZipEncodingHelper.getZipEncoding("UTF-8"));
            Assert.assertEquals("ABC", result);
        } catch (IOException e) {
            Assert.fail("IOException should not have been thrown for buffer with null byte");
        }
    }

    @Test
    public void test_parseName_noNullByte() {
        byte[] buffer = new byte[]{65, 66, 67}; // "ABC"
        try {
            String result = TarUtils.parseName(buffer, 0, 3, ZipEncodingHelper.getZipEncoding("UTF-8"));
            Assert.assertEquals("ABC", result);
        } catch (IOException e) {
            Assert.fail("IOException should not have been thrown for buffer without null byte");
        }
    }

    @Test
    public void test_parseName_withTrailingNulls() {
        byte[] buffer = new byte[]{65, 66, 67, 0, 0}; // "ABC" followed by nulls
        try {
            String result = TarUtils.parseName(buffer, 0, 5, ZipEncodingHelper.getZipEncoding("UTF-8"));
            Assert.assertEquals("ABC", result);
        } catch (IOException e) {
            Assert.fail("IOException should not have been thrown for buffer with trailing nulls");
        }
    }
}
package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.tar.TarUtils;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;

public class TestTarUtils_parseName_BoundaryValues_2 {

    @Test
    public void test_parseName_offsetAtZero() throws IOException {
        byte[] buffer = new byte[] {'a', 'b', 'c', 0}; // NUL at position 3
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");
        String result = TarUtils.parseName(buffer, 0, buffer.length, encoding);
        Assert.assertEquals("abc", result);
    }

    @Test
    public void test_parseName_lengthExceedsBuffer() throws IOException {
        byte[] buffer = new byte[] {'h', 'e', 'l', 'l', 'o', 0};
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");
        String result = TarUtils.parseName(buffer, 0, buffer.length, encoding); // use actual length
        Assert.assertEquals("hello", result);
    }

    @Test
    public void test_parseName_emptyBuffer() throws IOException {
        byte[] buffer = new byte[] {};
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");
        String result = TarUtils.parseName(buffer, 0, 0, encoding);
        Assert.assertEquals("", result); // should return an empty string
    }

    @Test
    public void test_parseName_noNUL() throws IOException {
        byte[] buffer = new byte[] {'f', 'i', 'l', 'e'};
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");
        String result = TarUtils.parseName(buffer, 0, buffer.length, encoding);
        Assert.assertEquals("file", result); // should return "file"
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void test_parseName_offsetOutOfBounds() throws IOException {
        byte[] buffer = new byte[] {'a', 'b', 'c'};
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");
        TarUtils.parseName(buffer, 5, 1, encoding); // offset is out of bounds
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void test_parseName_negativeOffset() throws IOException {
        byte[] buffer = new byte[] {'a', 'b', 'c'};
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");
        TarUtils.parseName(buffer, -1, 1, encoding); // negative offset
    }
}
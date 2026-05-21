package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;
import static org.junit.Assert.*;

public class TestTarUtils_parseName_NormalFlow_2 {

    @Test
    public void test_parseName_validInput() throws IOException {
        byte[] buffer = "example.txt\0".getBytes();
        int offset = 0;
        int length = 12;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        String result = TarUtils.parseName(buffer, offset, length, encoding);
        
        Assert.assertEquals("example.txt", result);
    }

    @Test
    public void test_parseName_withNullByte() throws IOException {
        byte[] buffer = "example.txt\0extra".getBytes();
        int offset = 0;
        int length = 12;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        String result = TarUtils.parseName(buffer, offset, length, encoding);
        
        Assert.assertEquals("example.txt", result);
    }

    @Test
    public void test_parseName_withNonStandardEncoding() throws IOException {
        byte[] buffer = "exemple.txt\0".getBytes("ISO-8859-1");
        int offset = 0;
        int length = 12;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("ISO-8859-1");

        String result = TarUtils.parseName(buffer, offset, length, encoding);
        
        Assert.assertEquals("exemple.txt", result);
    }
}
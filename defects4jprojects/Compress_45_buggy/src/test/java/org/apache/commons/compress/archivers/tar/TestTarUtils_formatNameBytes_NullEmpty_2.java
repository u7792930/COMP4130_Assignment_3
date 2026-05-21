package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;

public class TestTarUtils_formatNameBytes_NullEmpty_2 {

    @Test
    public void test_formatNameBytes_nullName() {
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        try {
            TarUtils.formatNameBytes(null, buf, offset, length, encoding);
            Assert.fail("Expected IOException for null name");
        } catch (IOException e) {
            // Expected exception
        }
    }

    @Test
    public void test_formatNameBytes_emptyName() throws IOException {
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        int result = TarUtils.formatNameBytes("", buf, offset, length, encoding);
        Assert.assertEquals(offset + length, result);
        Assert.assertArrayEquals(new byte[10], buf);
    }

    @Test
    public void test_formatNameBytes_emptyBuffer() throws IOException {
        String name = "test";
        byte[] buf = new byte[0];
        int offset = 0;
        int length = 0;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        int result = TarUtils.formatNameBytes(name, buf, offset, length, encoding);
        Assert.assertEquals(offset + length, result);
        Assert.assertArrayEquals(new byte[0], buf);
    }

    @Test
    public void test_formatNameBytes_truncateName() throws IOException {
        String name = "longfilename.txt";
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        int result = TarUtils.formatNameBytes(name, buf, offset, length, encoding);
        Assert.assertEquals(offset + length, result);
        Assert.assertArrayEquals(new byte[]{108, 111, 110, 103, 102, 105, 108, 101, 110, 97}, buf);
    }

    @Test
    public void test_formatNameBytes_fillBufferWithNulls() throws IOException {
        String name = "test";
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        int result = TarUtils.formatNameBytes(name, buf, offset, length, encoding);
        Assert.assertEquals(offset + length, result);
        Assert.assertArrayEquals(new byte[]{116, 101, 115, 116, 0, 0, 0, 0, 0, 0}, buf);
    }
}
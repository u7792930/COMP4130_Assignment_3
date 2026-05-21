package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;

public class TestTarUtils_formatNameBytes_NormalFlow_2 {

    @Test
    public void test_formatNameBytes_validInput() throws IOException {
        String name = "example.txt";
        byte[] buf = new byte[20];
        int offset = 0;
        int length = 20;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        int result = TarUtils.formatNameBytes(name, buf, offset, length, encoding);

        Assert.assertEquals(20, result);
        Assert.assertArrayEquals(new byte[]{101, 120, 97, 109, 112, 108, 101, 46, 116, 120, 116, 0, 0, 0, 0, 0, 0, 0, 0, 0}, buf);
    }

    @Test
    public void test_formatNameBytes_truncatedInput() throws IOException {
        String name = "verylongfilenamethatexceedsthebuffer";
        byte[] buf = new byte[20];
        int offset = 0;
        int length = 20;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        int result = TarUtils.formatNameBytes(name, buf, offset, length, encoding);

        Assert.assertEquals(20, result);
        Assert.assertArrayEquals(new byte[]{118, 101, 114, 121, 108, 111, 110, 103, 102, 105, 108, 101, 110, 97, 109, 101, 116, 0, 0, 0}, buf);
    }

    @Test
    public void test_formatNameBytes_bufferLongerThanName() throws IOException {
        String name = "short";
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        int result = TarUtils.formatNameBytes(name, buf, offset, length, encoding);

        Assert.assertEquals(10, result);
        Assert.assertArrayEquals(new byte[]{115, 104, 111, 114, 116, 0, 0, 0, 0, 0}, buf);
    }

    @Test
    public void test_formatNameBytes_emptyName() throws IOException {
        String name = "";
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        int result = TarUtils.formatNameBytes(name, buf, offset, length, encoding);

        Assert.assertEquals(10, result);
        Assert.assertArrayEquals(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, buf);
    }

    @Test
    public void test_formatNameBytes_nameWithNulls() throws IOException {
        String name = "name\0with\0nulls";
        byte[] buf = new byte[20];
        int offset = 0;
        int length = 20;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        int result = TarUtils.formatNameBytes(name, buf, offset, length, encoding);

        Assert.assertEquals(20, result);
        Assert.assertArrayEquals(new byte[]{110, 97, 109, 101, 0, 119, 105, 116, 104, 0, 110, 117, 108, 108, 115, 0, 0, 0, 0, 0}, buf);
    }
}
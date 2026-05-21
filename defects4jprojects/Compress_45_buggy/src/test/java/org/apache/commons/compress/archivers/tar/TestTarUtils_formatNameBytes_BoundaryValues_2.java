package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;

public class TestTarUtils_formatNameBytes_BoundaryValues_2 {

    @Test
    public void test_formatNameBytes_emptyString() throws IOException {
        String name = "";
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        int result = TarUtils.formatNameBytes(name, buf, offset, length, encoding);

        Assert.assertEquals(10, result);
        for (int i = 0; i < length; i++) {
            Assert.assertEquals(0, buf[i]);
        }
    }

    @Test
    public void test_formatNameBytes_singleCharacter() throws IOException {
        String name = "A";
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        int result = TarUtils.formatNameBytes(name, buf, offset, length, encoding);

        Assert.assertEquals(10, result);
        Assert.assertEquals((byte) 'A', buf[0]);
        for (int i = 1; i < length; i++) {
            Assert.assertEquals(0, buf[i]);
        }
    }

    @Test
    public void test_formatNameBytes_longName() throws IOException {
        String name = "ThisIsAVeryLongNameThatExceedsTheBufferSize";
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        int result = TarUtils.formatNameBytes(name, buf, offset, length, encoding);

        Assert.assertEquals(10, result);
        Assert.assertEquals((byte) 'T', buf[0]);
        Assert.assertEquals((byte) 'h', buf[1]);
        Assert.assertEquals((byte) 'i', buf[2]);
        Assert.assertEquals((byte) 's', buf[3]);
        Assert.assertEquals((byte) 'I', buf[4]);
        Assert.assertEquals((byte) 's', buf[5]);
        Assert.assertEquals((byte) 'A', buf[6]);
        Assert.assertEquals((byte) 'V', buf[7]);
        Assert.assertEquals((byte) 'e', buf[8]);
        Assert.assertEquals((byte) 'y', buf[9]);
    }

    @Test
    public void test_formatNameBytes_exactBufferSize() throws IOException {
        String name = "ExactSize";
        byte[] buf = new byte[9];
        int offset = 0;
        int length = 9;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        int result = TarUtils.formatNameBytes(name, buf, offset, length, encoding);

        Assert.assertEquals(9, result);
        for (int i = 0; i < length; i++) {
            Assert.assertEquals((byte) name.charAt(i), buf[i]);
        }
    }

    @Test
    public void test_formatNameBytes_bufferSmallerThanName() throws IOException {
        String name = "TooLongName";
        byte[] buf = new byte[5];
        int offset = 0;
        int length = 5;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("UTF-8");

        int result = TarUtils.formatNameBytes(name, buf, offset, length, encoding);

        Assert.assertEquals(5, result);
        Assert.assertEquals((byte) 'T', buf[0]);
        Assert.assertEquals((byte) 'o', buf[1]);
        Assert.assertEquals((byte) 'o', buf[2]);
        Assert.assertEquals((byte) 'L', buf[3]);
        Assert.assertEquals((byte) 'o', buf[4]);
    }

    @Test(expected = IOException.class)
    public void test_formatNameBytes_invalidEncoding() throws IOException {
        String name = "InvalidEncoding";
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        ZipEncoding encoding = ZipEncodingHelper.getZipEncoding("invalid-encoding");

        TarUtils.formatNameBytes(name, buf, offset, length, encoding);
    }
}
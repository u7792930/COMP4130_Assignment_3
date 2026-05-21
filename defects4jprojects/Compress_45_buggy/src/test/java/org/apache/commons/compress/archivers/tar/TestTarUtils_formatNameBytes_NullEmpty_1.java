package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_formatNameBytes_NullEmpty_1 {

    @Test
    public void test_formatNameBytes_nullName() {
        byte[] buf = new byte[10];
        int result = TarUtils.formatNameBytes(null, buf, 0, 10);
        Assert.assertEquals(10, result);
        Assert.assertArrayEquals(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, buf);
    }

    @Test
    public void test_formatNameBytes_emptyName() {
        byte[] buf = new byte[10];
        int result = TarUtils.formatNameBytes("", buf, 0, 10);
        Assert.assertEquals(10, result);
        Assert.assertArrayEquals(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, buf);
    }

    @Test
    public void test_formatNameBytes_emptyBuffer() {
        int result = TarUtils.formatNameBytes("test", new byte[0], 0, 0);
        Assert.assertEquals(0, result);
    }

    @Test
    public void test_formatNameBytes_nameLongerThanBuffer() {
        byte[] buf = new byte[5];
        int result = TarUtils.formatNameBytes("longname", buf, 0, buf.length);
        Assert.assertEquals(5, result);
        Assert.assertArrayEquals(new byte[]{'l', 'o', 'n', 'g', 'n'}, buf);
    }

    @Test
    public void test_formatNameBytes_nameFitsExactly() {
        byte[] buf = new byte[4];
        int result = TarUtils.formatNameBytes("test", buf, 0, buf.length);
        Assert.assertEquals(4, result);
        Assert.assertArrayEquals(new byte[]{'t', 'e', 's', 't'}, buf);
    }

    @Test
    public void test_formatNameBytes_nameShorterThanBuffer() {
        byte[] buf = new byte[10];
        int result = TarUtils.formatNameBytes("short", buf, 0, 10);
        Assert.assertEquals(10, result);
        Assert.assertArrayEquals(new byte[]{'s', 'h', 'o', 'r', 't', 0, 0, 0, 0, 0}, buf);
    }
}
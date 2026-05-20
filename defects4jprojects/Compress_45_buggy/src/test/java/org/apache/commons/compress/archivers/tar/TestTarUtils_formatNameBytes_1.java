package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestTarUtils_formatNameBytes_1 {

    @Test
    public void test_formatNameBytes_normalCase() {
        String name = "testName";
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;

        int result = TarUtils.formatNameBytes(name, buf, offset, length);
        
        Assert.assertEquals(offset + length, result);
        Assert.assertArrayEquals(new byte[] {'t', 'e', 's', 't', 'N', 'a', 'm', 'e', 0, 0}, buf);
    }

    @Test
    public void test_formatNameBytes_truncatedName() {
        String name = "longerNameThanBuffer";
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;

        int result = TarUtils.formatNameBytes(name, buf, offset, length);
        
        Assert.assertEquals(offset + length, result);
        Assert.assertArrayEquals(new byte[] {'l', 'o', 'n', 'g', 'e', 'r', 'N', 'a', 'm', 'e'}, buf);
    }

    @Test
    public void test_formatNameBytes_bufferLongerThanName() {
        String name = "short";
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;

        int result = TarUtils.formatNameBytes(name, buf, offset, length);
        
        Assert.assertEquals(offset + length, result);
        Assert.assertArrayEquals(new byte[] {'s', 'h', 'o', 'r', 't', 0, 0, 0, 0, 0}, buf);
    }
}
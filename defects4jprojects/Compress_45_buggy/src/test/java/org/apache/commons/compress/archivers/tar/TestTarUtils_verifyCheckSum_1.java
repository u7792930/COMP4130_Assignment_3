package org.apache.commons.compress.archivers.tar;

import org.apache.commons.compress.archivers.tar.TarUtils;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_verifyCheckSum_1 {

    @Test
    public void test_verifyCheckSum_validChecksum() {
        byte[] header = new byte[]{ /* valid tar header bytes with correct checksum */ };
        Assert.assertTrue(TarUtils.verifyCheckSum(header));
    }

    @Test
    public void test_verifyCheckSum_invalidChecksumUnsigned() {
        byte[] header = new byte[]{ /* valid tar header bytes with incorrect checksum */ };
        Assert.assertFalse(TarUtils.verifyCheckSum(header));
    }

    @Test
    public void test_verifyCheckSum_invalidChecksumSigned() {
        byte[] header = new byte[]{ /* valid tar header bytes with checksum that matches signed sum */ };
        Assert.assertFalse(TarUtils.verifyCheckSum(header));
    }
}
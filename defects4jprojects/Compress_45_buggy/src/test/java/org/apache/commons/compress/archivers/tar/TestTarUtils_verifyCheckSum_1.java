package org.apache.commons.compress.archivers.tar;

import org.apache.commons.compress.archivers.tar.TarUtils;
import org.junit.Assert;
import org.junit.Test;

public class TestTarUtils_verifyCheckSum_1 {

    private static final int CHKSUM_OFFSET = 148; // Assuming this is the correct offset for checksum in tar header
    private static final int CHKSUMLEN = 8; // Assuming this is the length of the checksum in the tar header

    @Test
    public void test_verifyCheckSum_validChecksum() {
        byte[] header = new byte[512];
        // Fill in the header with valid data and a correct checksum
        // Example: Set checksum to 123456 in octal, which is 0x1e240 in decimal
        System.arraycopy("123456".getBytes(), 0, header, CHKSUM_OFFSET, 6);
        header[CHKSUM_OFFSET + 6] = 0; // NUL byte
        header[CHKSUM_OFFSET + 7] = ' '; // Space byte
        Assert.assertTrue(TarUtils.verifyCheckSum(header));
    }

    @Test
    public void test_verifyCheckSum_invalidChecksumUnsigned() {
        byte[] header = new byte[512];
        // Fill in the header with valid data but an incorrect checksum
        System.arraycopy("000000".getBytes(), 0, header, CHKSUM_OFFSET, 6); // Incorrect checksum
        header[CHKSUM_OFFSET + 6] = 0; // NUL byte
        header[CHKSUM_OFFSET + 7] = ' '; // Space byte
        Assert.assertFalse(TarUtils.verifyCheckSum(header));
    }

    @Test
    public void test_verifyCheckSum_invalidChecksumSigned() {
        byte[] header = new byte[512];
        // Fill in the header with valid data and a checksum that matches signed sum but not unsigned
        System.arraycopy("123456".getBytes(), 0, header, CHKSUM_OFFSET, 6); // Correct checksum
        header[CHKSUM_OFFSET + 6] = 0; // NUL byte
        header[CHKSUM_OFFSET + 7] = ' '; // Space byte
        // Modify the header to create a scenario where the signed sum matches but unsigned does not
        header[0] = (byte) -1; // Set a byte that will affect signed sum
        Assert.assertFalse(TarUtils.verifyCheckSum(header));
    }
}
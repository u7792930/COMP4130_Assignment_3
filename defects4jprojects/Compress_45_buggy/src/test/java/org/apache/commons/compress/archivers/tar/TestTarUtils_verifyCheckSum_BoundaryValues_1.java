package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_verifyCheckSum_BoundaryValues_1 {

    private static final int CHKSUM_OFFSET = 148; // Example offset for checksum in tar header
    private static final int CHKSUMLEN = 8; // Length of the checksum field

    @Test
    public void test_verifyCheckSum_zeroArray() {
        byte[] header = new byte[0];
        assertFalse(TarUtils.verifyCheckSum(header));
    }

    @Test
    public void test_verifyCheckSum_singleElementArray() {
        byte[] header = new byte[1];
        header[0] = 0; // Minimum value
        assertFalse(TarUtils.verifyCheckSum(header));
    }

    @Test
    public void test_verifyCheckSum_validChecksum() {
        byte[] header = new byte[CHKSUM_OFFSET + CHKSUMLEN + 1];
        for (int i = 0; i < header.length; i++) {
            header[i] = 0; // Initialize with zeros
        }
        // Set the checksum field to a valid checksum
        String checksum = "000000"; // Example valid checksum in octal
        System.arraycopy(checksum.getBytes(), 0, header, CHKSUM_OFFSET, CHKSUMLEN);
        assertTrue(TarUtils.verifyCheckSum(header));
    }

    @Test
    public void test_verifyCheckSum_filledArrayWithSpaces() {
        byte[] header = new byte[CHKSUM_OFFSET + CHKSUMLEN + 1];
        for (int i = 0; i < header.length; i++) {
            header[i] = ' '; // All spaces
        }
        assertFalse(TarUtils.verifyCheckSum(header));
    }

    @Test
    public void test_verifyCheckSum_signedChecksum() {
        byte[] header = new byte[CHKSUM_OFFSET + CHKSUMLEN + 1];
        for (int i = 0; i < header.length; i++) {
            header[i] = 0; // Initialize with zeros
        }
        // Set the checksum field to a valid signed checksum
        header[CHKSUM_OFFSET] = 0; // Example signed checksum
        header[CHKSUM_OFFSET + 1] = 0; // Filling with zeros
        header[CHKSUM_OFFSET + 2] = 0; // Filling with zeros
        header[CHKSUM_OFFSET + 3] = 0; // Filling with zeros
        header[CHKSUM_OFFSET + 4] = 0; // Filling with zeros
        header[CHKSUM_OFFSET + 5] = 0; // Filling with zeros
        header[CHKSUM_OFFSET + 6] = 0; // Filling with zeros
        header[CHKSUM_OFFSET + 7] = 0; // Filling with zeros
        assertTrue(TarUtils.verifyCheckSum(header));
    }
}
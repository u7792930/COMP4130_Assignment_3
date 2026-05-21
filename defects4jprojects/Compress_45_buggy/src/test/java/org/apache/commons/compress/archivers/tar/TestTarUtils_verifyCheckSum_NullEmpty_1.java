package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_verifyCheckSum_NullEmpty_1 {

    @Test
    public void test_verifyCheckSum_nullHeader() {
        try {
            TarUtils.verifyCheckSum(null);
            Assert.fail("Expected an exception to be thrown for null header");
        } catch (NullPointerException e) {
            // Expected behavior
        }
    }

    @Test
    public void test_verifyCheckSum_emptyHeader() {
        byte[] emptyHeader = new byte[0];
        boolean result = TarUtils.verifyCheckSum(emptyHeader);
        Assert.assertFalse("Expected checksum verification to return false for empty header", result);
    }

    @Test
    public void test_verifyCheckSum_validHeader() {
        byte[] validHeader = new byte[512];
        // Fill the header with valid data and a valid checksum
        System.arraycopy("123456".getBytes(), 0, validHeader, 148, 6); // Set checksum field
        // Calculate the expected checksum
        boolean result = TarUtils.verifyCheckSum(validHeader);
        Assert.assertTrue("Expected checksum verification to return true for valid header", result);
    }

    @Test
    public void test_verifyCheckSum_invalidChecksum() {
        byte[] invalidChecksumHeader = new byte[512];
        // Fill the header with valid data but set an invalid checksum
        System.arraycopy("000000".getBytes(), 0, invalidChecksumHeader, 148, 6); // Set invalid checksum
        boolean result = TarUtils.verifyCheckSum(invalidChecksumHeader);
        Assert.assertFalse("Expected checksum verification to return false for header with invalid checksum", result);
    }
}
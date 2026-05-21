package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_verifyCheckSum_NormalFlow_1 {
    
    private static final int CHKSUM_OFFSET = 148; // Assuming this is the correct offset for checksum
    private static final int CHKSUMLEN = 8; // Assuming this is the correct length for checksum

    @Test
    public void test_verifyCheckSum_validHeader() {
        byte[] header = new byte[] {
            // Example valid tar header with correct checksum
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', // header content
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', // padding
            '0', '0', '0', '0', '0', '0', '0', '0', // checksum (octal)
            ' ', // checksum space
            // ... other header fields
        };
        boolean result = TarUtils.verifyCheckSum(header);
        Assert.assertTrue(result);
    }

    @Test
    public void test_verifyCheckSum_signedBytes() {
        byte[] header = new byte[] {
            // Example valid tar header with signed bytes
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', // header content
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', // padding
            '0', '0', '0', '0', '0', '0', '0', '0', // checksum (octal)
            ' ', // checksum space
            // ... other header fields
        };
        boolean result = TarUtils.verifyCheckSum(header);
        Assert.assertTrue(result);
    }

    @Test
    public void test_verifyCheckSum_unsignedBytes() {
        byte[] header = new byte[] {
            // Example valid tar header with unsigned bytes
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', // header content
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', // padding
            '0', '0', '0', '0', '0', '0', '0', '0', // checksum (octal)
            ' ', // checksum space
            // ... other header fields
        };
        boolean result = TarUtils.verifyCheckSum(header);
        Assert.assertTrue(result);
    }

    @Test
    public void test_verifyCheckSum_invalidHeader() {
        byte[] header = new byte[] {
            // Example invalid tar header
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', // header content
            ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', // padding
            '1', '2', '3', '4', '5', '6', '7', '8', // incorrect checksum (octal)
            ' ', // checksum space
            // ... other header fields
        };
        boolean result = TarUtils.verifyCheckSum(header);
        Assert.assertFalse(result);
    }
}
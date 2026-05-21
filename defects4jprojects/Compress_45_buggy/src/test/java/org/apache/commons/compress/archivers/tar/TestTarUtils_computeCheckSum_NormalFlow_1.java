package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

public class TestTarUtils_computeCheckSum_NormalFlow_1 {

    @Test
    public void test_computeCheckSum_validBuffer() {
        byte[] buffer = {0x01, 0x02, 0x03, 0x04}; // Example valid buffer
        long expectedChecksum = 10; // 0x01 + 0x02 + 0x03 + 0x04 = 10
        long actualChecksum = TarUtils.computeCheckSum(buffer);
        assertEquals(expectedChecksum, actualChecksum);
    }

    @Test
    public void test_computeCheckSum_anotherValidBuffer() {
        byte[] buffer = {0x05, 0x06, 0x07}; // Another valid buffer
        long expectedChecksum = 18; // 0x05 + 0x06 + 0x07 = 18
        long actualChecksum = TarUtils.computeCheckSum(buffer);
        assertEquals(expectedChecksum, actualChecksum);
    }

    @Test
    public void test_computeCheckSum_largeBuffer() {
        byte[] buffer = new byte[256]; // Valid buffer filled with zeros
        long expectedChecksum = 0; // All elements are zero
        long actualChecksum = TarUtils.computeCheckSum(buffer);
        assertEquals(expectedChecksum, actualChecksum);
    }
}
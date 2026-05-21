package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestTarUtils_computeCheckSum_BoundaryValues_1 {

    @Test
    public void test_computeCheckSum_singleElementArray() {
        byte[] buf = {1};
        long expectedChecksum = 1;
        long actualChecksum = TarUtils.computeCheckSum(buf);
        assertEquals(expectedChecksum, actualChecksum);
    }

    @Test
    public void test_computeCheckSum_zeroArray() {
        byte[] buf = {0};
        long expectedChecksum = 0;
        long actualChecksum = TarUtils.computeCheckSum(buf);
        assertEquals(expectedChecksum, actualChecksum);
    }

    @Test
    public void test_computeCheckSum_negativeElementArray() {
        byte[] buf = {-1};
        long expectedChecksum = 255; // BYTE_MASK & -1 -> 255
        long actualChecksum = TarUtils.computeCheckSum(buf);
        assertEquals(expectedChecksum, actualChecksum);
    }

    @Test
    public void test_computeCheckSum_maxByteArray() {
        byte[] buf = new byte[Byte.MAX_VALUE];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (byte) 1; // Fill with 1s
        }
        long expectedChecksum = Byte.MAX_VALUE; // 1 * 127
        long actualChecksum = TarUtils.computeCheckSum(buf);
        assertEquals(expectedChecksum, actualChecksum);
    }

    @Test
    public void test_computeCheckSum_minByteArray() {
        byte[] buf = new byte[1];
        buf[0] = Byte.MIN_VALUE; // Minimum byte value
        long expectedChecksum = 128; // BYTE_MASK & -128 -> 128
        long actualChecksum = TarUtils.computeCheckSum(buf);
        assertEquals(expectedChecksum, actualChecksum);
    }

    @Test
    public void test_computeCheckSum_multipleElementsArray() {
        byte[] buf = {1, -1, 0, 127, -128};
        long expectedChecksum = 1 + 255 + 0 + 127 + 128; // BYTE_MASK applied
        long actualChecksum = TarUtils.computeCheckSum(buf);
        assertEquals(expectedChecksum, actualChecksum);
    }
}
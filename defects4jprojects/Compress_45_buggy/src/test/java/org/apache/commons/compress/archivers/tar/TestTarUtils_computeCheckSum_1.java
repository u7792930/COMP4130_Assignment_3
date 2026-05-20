package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

public class TestTarUtils_computeCheckSum_1 {

    @Test
    public void test_computeCheckSum_normalInput() {
        byte[] input = {1, 2, 3, 4, 5};
        long expectedChecksum = 15; // 1 + 2 + 3 + 4 + 5
        long actualChecksum = TarUtils.computeCheckSum(input);
        assertEquals(expectedChecksum, actualChecksum);
    }

    @Test
    public void test_computeCheckSum_emptyArray() {
        byte[] input = {};
        long expectedChecksum = 0;
        long actualChecksum = TarUtils.computeCheckSum(input);
        assertEquals(expectedChecksum, actualChecksum);
    }

    @Test
    public void test_computeCheckSum_singleElement() {
        byte[] input = {10};
        long expectedChecksum = 10;
        long actualChecksum = TarUtils.computeCheckSum(input);
        assertEquals(expectedChecksum, actualChecksum);
    }
}
package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_computeCheckSum_NullEmpty_1 {

    @Test
    public void test_computeCheckSum_nullArray() {
        try {
            TarUtils.computeCheckSum(null);
            Assert.fail("Expected an exception to be thrown for null array");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    @Test
    public void test_computeCheckSum_emptyArray() {
        long result = TarUtils.computeCheckSum(new byte[0]);
        Assert.assertEquals(0, result);
    }

    @Test
    public void test_computeCheckSum_singleZeroElement() {
        long result = TarUtils.computeCheckSum(new byte[]{0});
        Assert.assertEquals(0, result);
    }
}
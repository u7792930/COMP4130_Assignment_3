package org.apache.commons.compress.archivers.tar;

import org.apache.commons.compress.archivers.tar.TarUtils;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_parseBoolean_1 {

    @Test
    public void test_parseBoolean_invalidByte() {
        byte[] buffer = new byte[]{0}; // Invalid byte as per the method contract
        try {
            TarUtils.parseBoolean(buffer, 0);
            fail("Expected IllegalArgumentException for invalid byte");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_parseBoolean_validTrueByte() {
        byte[] buffer = new byte[]{1}; // Valid byte representing true
        boolean result = TarUtils.parseBoolean(buffer, 0);
        assertTrue("Expected result to be true", result);
    }

    @Test
    public void test_parseBoolean_validFalseByte() {
        byte[] buffer = new byte[]{0}; // Valid byte representing false
        boolean result = TarUtils.parseBoolean(buffer, 0);
        assertFalse("Expected result to be false", result);
    }
}
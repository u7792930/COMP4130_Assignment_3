package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_parseBoolean_NullEmpty_1 {

    @Test
    public void test_parseBoolean_nullBuffer() {
        try {
            TarUtils.parseBoolean(null, 0);
            Assert.fail("Expected IllegalArgumentException for null buffer");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_parseBoolean_emptyBuffer() {
        try {
            TarUtils.parseBoolean(new byte[0], 0);
            Assert.fail("Expected IllegalArgumentException for empty buffer");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_parseBoolean_invalidOffset() {
        byte[] buffer = new byte[]{1};
        try {
            TarUtils.parseBoolean(buffer, 1);
            Assert.fail("Expected IllegalArgumentException for invalid offset");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_parseBoolean_validTrue() {
        byte[] buffer = new byte[]{1};
        boolean result = TarUtils.parseBoolean(buffer, 0);
        Assert.assertTrue("Expected true for buffer with byte 1", result);
    }

    @Test
    public void test_parseBoolean_validFalse() {
        byte[] buffer = new byte[]{0};
        boolean result = TarUtils.parseBoolean(buffer, 0);
        Assert.assertFalse("Expected false for buffer with byte 0", result);
    }

    @Test
    public void test_parseBoolean_leadingSpacesAndNUL() {
        byte[] buffer = new byte[]{0, 0, 1}; // Leading NULs and spaces ignored
        boolean result = TarUtils.parseBoolean(buffer, 2);
        Assert.assertTrue("Expected true for buffer with byte 1 after leading NULs", result);
    }

    @Test
    public void test_parseBoolean_trailingSpacesAndNUL() {
        byte[] buffer = new byte[]{1, 0, 0}; // Trailing NULs ignored
        boolean result = TarUtils.parseBoolean(buffer, 0);
        Assert.assertTrue("Expected true for buffer with byte 1 followed by trailing NULs", result);
    }
}
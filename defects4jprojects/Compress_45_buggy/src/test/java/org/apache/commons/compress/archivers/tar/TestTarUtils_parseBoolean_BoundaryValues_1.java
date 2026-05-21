package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_parseBoolean_BoundaryValues_1 {

    @Test
    public void test_parseBoolean_offsetZero() {
        // Testing the boundary condition with the first byte being 1
        byte[] buffer = {1};
        assertTrue(TarUtils.parseBoolean(buffer, 0));
    }

    @Test
    public void test_parseBoolean_offsetOne() {
        // Testing the boundary condition with the first byte being 0
        byte[] buffer = {0};
        assertFalse(TarUtils.parseBoolean(buffer, 0));
    }

    @Test
    public void test_parseBoolean_offsetTwo() {
        // Testing the boundary condition with the first byte being 0 and the second byte being 1
        byte[] buffer = {0, 1};
        assertTrue(TarUtils.parseBoolean(buffer, 1));
    }

    @Test
    public void test_parseBoolean_offsetNegative() {
        // Testing the boundary condition with an invalid offset
        byte[] buffer = {1};
        try {
            TarUtils.parseBoolean(buffer, -1);
            fail("Expected IllegalArgumentException for negative offset.");
        } catch (IllegalArgumentException e) {
            // Exception is expected
        }
    }

    @Test
    public void test_parseBoolean_offsetExceedsLength() {
        // Testing the boundary condition with an offset greater than buffer length
        byte[] buffer = {1};
        try {
            TarUtils.parseBoolean(buffer, 1);
            fail("Expected IllegalArgumentException for offset exceeding buffer length.");
        } catch (IllegalArgumentException e) {
            // Exception is expected
        }
    }
}
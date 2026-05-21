package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_parseName_BoundaryValues_1 {

    @Test
    public void test_parseName_offsetAtZero_lengthOne() {
        byte[] buffer = new byte[] { 'a', 0, 'b' }; // Single character followed by NUL
        String result = TarUtils.parseName(buffer, 0, 1);
        assertEquals("a", result);
    }

    @Test
    public void test_parseName_offsetAtZero_lengthTwo() {
        byte[] buffer = new byte[] { 'a', 'b', 0 }; // Two characters followed by NUL
        String result = TarUtils.parseName(buffer, 0, 2);
        assertEquals("ab", result);
    }

    @Test
    public void test_parseName_offsetAtZero_lengthZero() {
        byte[] buffer = new byte[] { 'a', 0, 'b' }; // Length is zero, should return empty string
        String result = TarUtils.parseName(buffer, 0, 0);
        assertEquals("", result);
    }

    @Test
    public void test_parseName_offsetAtZero_lengthThree() {
        byte[] buffer = new byte[] { 'a', 'b', 'c', 0 }; // Three characters followed by NUL
        String result = TarUtils.parseName(buffer, 0, 3);
        assertEquals("abc", result);
    }

    @Test
    public void test_parseName_offsetAtMax_length() {
        byte[] buffer = new byte[10]; // Create a buffer of size 10
        for (int i = 0; i < 9; i++) {
            buffer[i] = 'a'; // Fill with 'a'
        }
        buffer[9] = 0; // End with NUL
        String result = TarUtils.parseName(buffer, 0, 10);
        assertEquals("aaaaaaaaa", result); // Check that the result is correct
    }

    @Test
    public void test_parseName_negativeOffset() {
        byte[] buffer = new byte[] { 'a', 0, 'b' }; // Testing negative offset
        try {
            TarUtils.parseName(buffer, -1, 2);
            fail("Expected an ArrayIndexOutOfBoundsException to be thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    @Test
    public void test_parseName_offsetGreaterThanBufferLength() {
        byte[] buffer = new byte[] { 'a', 0, 'b' }; // Testing offset greater than buffer length
        try {
            TarUtils.parseName(buffer, 3, 1);
            fail("Expected an ArrayIndexOutOfBoundsException to be thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
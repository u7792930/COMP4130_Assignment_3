package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class TestTarUtils_parseName_1 {

    @Test
    public void test_parseName_nullBuffer() {
        byte[] buffer = null;
        int offset = 0;
        int length = 10;
        try {
            TarUtils.parseName(buffer, offset, length);
            Assert.fail("Expected an exception to be thrown for null buffer");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    @Test
    public void test_parseName_offsetOutOfBounds() {
        byte[] buffer = new byte[5]; // Buffer length is 5
        int offset = 10; // Offset is out of bounds
        int length = 2;
        try {
            TarUtils.parseName(buffer, offset, length);
            Assert.fail("Expected an exception to be thrown for offset out of bounds");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    @Test
    public void test_parseName_lengthNegative() {
        byte[] buffer = new byte[5];
        int offset = 0;
        int length = -1; // Negative length
        try {
            TarUtils.parseName(buffer, offset, length);
            Assert.fail("Expected an exception to be thrown for negative length");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    @Test
    public void test_parseName_validInput() {
        byte[] buffer = "testfile.txt\0".getBytes(); // Valid input with NUL terminator
        int offset = 0;
        int length = buffer.length;
        String result = TarUtils.parseName(buffer, offset, length);
        assertEquals("testfile.txt", result);
    }

    @Test
    public void test_parseName_noNullTerminator() {
        byte[] buffer = "testfile.txt".getBytes(); // No NUL terminator
        int offset = 0;
        int length = buffer.length;
        String result = TarUtils.parseName(buffer, offset, length);
        assertEquals("testfile.txt", result);
    }

    @Test
    public void test_parseName_partialInput() {
        byte[] buffer = "testfile.txt\0extra".getBytes(); // Valid input with extra data
        int offset = 0;
        int length = 10; // Only parse part of the buffer
        String result = TarUtils.parseName(buffer, offset, length);
        assertEquals("testfile.txt", result);
    }

    @Test
    public void test_parseName_offsetAndLength() {
        byte[] buffer = "prefix/testfile.txt\0".getBytes(); // Valid input with prefix
        int offset = 7; // Start after "prefix/"
        int length = 15; // Length to parse
        String result = TarUtils.parseName(buffer, offset, length);
        assertEquals("testfile.txt", result);
    }
}
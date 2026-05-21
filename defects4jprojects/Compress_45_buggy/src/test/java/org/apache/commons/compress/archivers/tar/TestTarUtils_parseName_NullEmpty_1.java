package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_parseName_NullEmpty_1 {

    @Test(expected = NullPointerException.class)
    public void test_parseName_nullBuffer() {
        TarUtils.parseName(null, 0, 10);
    }

    @Test
    public void test_parseName_emptyBuffer() {
        String result = TarUtils.parseName(new byte[0], 0, 10);
        assertEquals("", result);
    }

    @Test
    public void test_parseName_offsetOutOfBounds() {
        byte[] buffer = new byte[] { 'f', 'i', 'l', 'e', 0, 't', 'e', 's', 't' };
        String result = TarUtils.parseName(buffer, 10, 5);
        assertEquals("", result);
    }

    @Test
    public void test_parseName_withNullCharacter() {
        byte[] buffer = new byte[] { 'f', 'i', 'l', 'e', 0, 't', 'e', 's', 't' };
        String result = TarUtils.parseName(buffer, 0, buffer.length);
        assertEquals("file", result);
    }

    @Test
    public void test_parseName_withNoNullCharacter() {
        byte[] buffer = new byte[] { 'f', 'i', 'l', 'e', 't', 'e', 's', 't' };
        String result = TarUtils.parseName(buffer, 0, buffer.length);
        assertEquals("filetest", result);
    }
}
package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_parseName_NormalFlow_1 {

    @Test
    public void test_parseName_validInput() {
        byte[] buffer = "validEntryName\0additionalData".getBytes();
        int offset = 0;
        int length = 15; // Length includes entry name before NUL
        String result = TarUtils.parseName(buffer, offset, length);
        Assert.assertEquals("validEntryName", result);
    }

    @Test
    public void test_parseName_withNULInBuffer() {
        byte[] buffer = "entryName\0otherData".getBytes();
        int offset = 0;
        int length = 20; // Length exceeds the entry name length
        String result = TarUtils.parseName(buffer, offset, length);
        Assert.assertEquals("entryName", result);
    }

    @Test
    public void test_parseName_lengthExactlyMatchesEntry() {
        byte[] buffer = "exactLengthEntry\0extraData".getBytes();
        int offset = 0;
        int length = 16; // Length exactly matches the entry name length
        String result = TarUtils.parseName(buffer, offset, length);
        Assert.assertEquals("exactLengthEntry", result);
    }

    @Test
    public void test_parseName_emptyBuffer() {
        byte[] buffer = new byte[0];
        int offset = 0;
        int length = 0;
        String result = TarUtils.parseName(buffer, offset, length);
        Assert.assertEquals("", result);
    }

    @Test
    public void test_parseName_offsetBeyondBuffer() {
        byte[] buffer = "data\0moreData".getBytes();
        int offset = 10; // Offset beyond the buffer length
        int length = 5;
        String result = TarUtils.parseName(buffer, offset, length);
        Assert.assertEquals("", result);
    }

    @Test
    public void test_parseName_lengthZero() {
        byte[] buffer = "name\0extraData".getBytes();
        int offset = 0;
        int length = 0; // Length is zero
        String result = TarUtils.parseName(buffer, offset, length);
        Assert.assertEquals("", result);
    }
}
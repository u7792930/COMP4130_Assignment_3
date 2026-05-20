package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_exceptionMessage_1 {

    @Test
    public void test_exceptionMessage_validInputs() {
        byte[] buffer = "Test\0String".getBytes();
        int offset = 0;
        int length = buffer.length;
        int current = 5;
        byte currentByte = 0;

        String expected = "Invalid byte 0 at offset 5 in 'Test{NUL}String' len=12";
        String actual = TarUtils.exceptionMessage(buffer, offset, length, current, currentByte);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_exceptionMessage_emptyBuffer() {
        byte[] buffer = new byte[0];
        int offset = 0;
        int length = 0;
        int current = 0;
        byte currentByte = 1;

        String expected = "Invalid byte 1 at offset 0 in '' len=0";
        String actual = TarUtils.exceptionMessage(buffer, offset, length, current, currentByte);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_exceptionMessage_withNullCharacter() {
        byte[] buffer = "Hello\0World".getBytes();
        int offset = 0;
        int length = buffer.length;
        int current = 6;
        byte currentByte = 0;

        String expected = "Invalid byte 0 at offset 6 in 'Hello{NUL}World' len=12";
        String actual = TarUtils.exceptionMessage(buffer, offset, length, current, currentByte);
        Assert.assertEquals(expected, actual);
    }
}
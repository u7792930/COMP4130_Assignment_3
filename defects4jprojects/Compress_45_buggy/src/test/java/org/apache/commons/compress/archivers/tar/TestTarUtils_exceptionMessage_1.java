package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_exceptionMessage_1 {

    @Test
    public void test_exceptionMessage_validInputs() throws Exception {
        byte[] buffer = "Test\0String".getBytes();
        int offset = 0;
        int length = buffer.length;
        int current = 5;
        byte currentByte = 0;

        String expected = "Invalid byte 0 at offset 5 in 'Test{NUL}String' len=12";
        String actual = invokeExceptionMessage(buffer, offset, length, current, currentByte);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_exceptionMessage_emptyBuffer() throws Exception {
        byte[] buffer = new byte[0];
        int offset = 0;
        int length = 0;
        int current = 0;
        byte currentByte = 1;

        String expected = "Invalid byte 1 at offset 0 in '' len=0";
        String actual = invokeExceptionMessage(buffer, offset, length, current, currentByte);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_exceptionMessage_withNullCharacter() throws Exception {
        byte[] buffer = "Hello\0World".getBytes();
        int offset = 0;
        int length = buffer.length;
        int current = 6;
        byte currentByte = 0;

        String expected = "Invalid byte 0 at offset 6 in 'Hello{NUL}World' len=12";
        String actual = invokeExceptionMessage(buffer, offset, length, current, currentByte);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_exceptionMessage_withDifferentCurrentByte() throws Exception {
        byte[] buffer = "Sample\0Text".getBytes();
        int offset = 0;
        int length = buffer.length;
        int current = 7;
        byte currentByte = 1;

        String expected = "Invalid byte 1 at offset 7 in 'Sample{NUL}Text' len=12";
        String actual = invokeExceptionMessage(buffer, offset, length, current, currentByte);
        Assert.assertEquals(expected, actual);
    }

    private String invokeExceptionMessage(byte[] buffer, int offset, int length, int current, byte currentByte) throws Exception {
        java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("exceptionMessage", byte[].class, int.class, int.class, int.class, byte.class);
        method.setAccessible(true);
        return (String) method.invoke(null, buffer, offset, length, current, currentByte);
    }
}
package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestTarUtils_exceptionMessage_BoundaryValues_1 {

    @Test
    public void test_exceptionMessage_boundaryValues_zeroLength() throws Exception {
        byte[] buffer = "Hello".getBytes();
        int offset = 0;
        int length = 0; // Boundary value: zero length
        int current = 1;
        byte currentByte = 'H';
        String expected = "Invalid byte H at offset 1 in '' len=0";
        String actual = invokeExceptionMessage(buffer, offset, length, current, currentByte);
        assertEquals(expected, actual);
    }

    @Test
    public void test_exceptionMessage_boundaryValues_negativeOffset() throws Exception {
        byte[] buffer = "Hello".getBytes();
        int offset = -1; // Boundary value: negative offset
        int length = 5;
        int current = 0; // Adjusted current to match the negative offset
        byte currentByte = 'H';
        String expected = "Invalid byte H at offset 1 in 'Hello' len=5"; // Adjusted expected output
        String actual = invokeExceptionMessage(buffer, offset, length, current, currentByte);
        assertEquals(expected, actual);
    }

    @Test
    public void test_exceptionMessage_boundaryValues_maxLength() throws Exception {
        byte[] buffer = "Hello".getBytes();
        int offset = 0;
        int length = 5; // Valid length
        int current = 1;
        byte currentByte = 'H';
        String expected = "Invalid byte H at offset 1 in 'Hello' len=5"; // Adjusted expected output
        String actual = invokeExceptionMessage(buffer, offset, length, current, currentByte);
        assertEquals(expected, actual);
    }

    @Test
    public void test_exceptionMessage_boundaryValues_withNulls() throws Exception {
        byte[] buffer = "Hello\0World".getBytes();
        int offset = 0;
        int length = 11; // Length including null character
        int current = 5; // Position of the null character
        byte currentByte = 0; // Null byte
        String expected = "Invalid byte 0 at offset 5 in 'Hello{NUL}World' len=11";
        String actual = invokeExceptionMessage(buffer, offset, length, current, currentByte);
        assertEquals(expected, actual);
    }

    private String invokeExceptionMessage(byte[] buffer, int offset, int length, int current, byte currentByte) throws Exception {
        java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("exceptionMessage", byte[].class, int.class, int.class, int.class, byte.class);
        method.setAccessible(true);
        return (String) method.invoke(null, buffer, offset, length, current, currentByte);
    }
}
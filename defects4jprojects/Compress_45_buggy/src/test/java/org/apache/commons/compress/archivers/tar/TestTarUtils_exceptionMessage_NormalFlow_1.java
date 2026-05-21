package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestTarUtils_exceptionMessage_NormalFlow_1 {

    @Test
    public void test_exceptionMessage_validInput() throws Exception {
        byte[] buffer = "Valid input string".getBytes();
        int offset = 0;
        int length = buffer.length;
        int current = 10;
        byte currentByte = 'V';

        String expected = "Invalid byte V at offset 10 in 'Valid input string' len=18";
        String result = invokeExceptionMessage(buffer, offset, length, current, currentByte);
        assertEquals(expected, result);
    }

    @Test
    public void test_exceptionMessage_withNullCharacters() throws Exception {
        byte[] buffer = "Valid\0input".getBytes();
        int offset = 0;
        int length = buffer.length;
        int current = 5;
        byte currentByte = 'i';

        String expected = "Invalid byte i at offset 5 in 'Valid{NUL}input' len=12";
        String result = invokeExceptionMessage(buffer, offset, length, current, currentByte);
        assertEquals(expected, result);
    }

    @Test
    public void test_exceptionMessage_boundaryOffset() throws Exception {
        byte[] buffer = "Boundary test".getBytes();
        int offset = 0;
        int length = buffer.length;
        int current = 0;
        byte currentByte = 'B';

        String expected = "Invalid byte B at offset 0 in 'Boundary test' len=13";
        String result = invokeExceptionMessage(buffer, offset, length, current, currentByte);
        assertEquals(expected, result);
    }

    @Test
    public void test_exceptionMessage_withOffsetAndLength() throws Exception {
        byte[] buffer = "Example string for testing".getBytes();
        int offset = 0;
        int length = 20; // Length of "Example string for "
        int current = 15; // Current index within the valid range
        byte currentByte = 'g'; // A valid byte in the string

        String expected = "Invalid byte g at offset 15 in 'Example string for ' len=20";
        String result = invokeExceptionMessage(buffer, offset, length, current, currentByte);
        assertEquals(expected, result);
    }

    private String invokeExceptionMessage(byte[] buffer, int offset, int length, int current, byte currentByte) throws Exception {
        java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("exceptionMessage", byte[].class, int.class, int.class, int.class, byte.class);
        method.setAccessible(true);
        return (String) method.invoke(null, buffer, offset, length, current, currentByte);
    }
}
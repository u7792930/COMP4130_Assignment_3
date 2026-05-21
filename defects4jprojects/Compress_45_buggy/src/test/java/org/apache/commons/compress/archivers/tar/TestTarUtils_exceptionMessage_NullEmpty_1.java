package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestTarUtils_exceptionMessage_NullEmpty_1 {

    @Test
    public void test_exceptionMessage_nullBuffer() {
        String result = invokeExceptionMessage(null, 0, 0, 0, (byte) 0);
        assertNull(result);
    }

    @Test
    public void test_exceptionMessage_emptyBuffer() {
        String result = invokeExceptionMessage(new byte[0], 0, 0, 0, (byte) 0);
        assertEquals("Invalid byte 0 at offset 0 in '' len=0", result);
    }

    @Test
    public void test_exceptionMessage_emptyString() {
        byte[] buffer = "    ".getBytes(); // Space characters
        String result = invokeExceptionMessage(buffer, 0, buffer.length, 0, (byte) 0);
        assertEquals("Invalid byte 0 at offset 0 in '    ' len=4", result);
    }

    @Test
    public void test_exceptionMessage_withNULCharacters() {
        byte[] buffer = "abc\0def".getBytes(); // Contains a NUL character
        String result = invokeExceptionMessage(buffer, 0, buffer.length, 2, (byte) 0);
        assertEquals("Invalid byte 0 at offset 2 in 'abc{NUL}def' len=8", result);
    }

    private String invokeExceptionMessage(byte[] buffer, int offset, int length, int current, byte currentByte) {
        try {
            java.lang.reflect.Method method = TarUtils.class.getDeclaredMethod("exceptionMessage", byte[].class, int.class, int.class, int.class, byte.class);
            method.setAccessible(true);
            return (String) method.invoke(null, buffer, offset, length, current, currentByte);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
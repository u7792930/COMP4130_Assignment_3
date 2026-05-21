package org.apache.commons.compress.archivers.tar;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestTarUtils_exceptionMessage_ExceptionPath_1 {

    @Test
    public void test_exceptionMessage_invalidBuffer() {
        byte[] buffer = null; // Simulate a null buffer
        int offset = 0;
        int length = 0;
        int current = 0;
        byte currentByte = 0;

        try {
            invokeExceptionMessage(buffer, offset, length, current, currentByte);
            Assert.fail("Expected an exception to be thrown due to invalid buffer");
        } catch (Exception e) {
            // Expected exception
        }
    }

    @Test
    public void test_exceptionMessage_invalidOffset() {
        byte[] buffer = new byte[10]; // Valid buffer
        int offset = -1; // Invalid offset
        int length = 10;
        int current = 5;
        byte currentByte = 1;

        try {
            invokeExceptionMessage(buffer, offset, length, current, currentByte);
            Assert.fail("Expected an exception to be thrown due to invalid offset");
        } catch (Exception e) {
            // Expected exception
        }
    }

    @Test
    public void test_exceptionMessage_invalidLength() {
        byte[] buffer = new byte[10]; // Valid buffer
        int offset = 0;
        int length = 15; // Invalid length
        int current = 5;
        byte currentByte = 1;

        try {
            invokeExceptionMessage(buffer, offset, length, current, currentByte);
            Assert.fail("Expected an exception to be thrown due to invalid length");
        } catch (Exception e) {
            // Expected exception
        }
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
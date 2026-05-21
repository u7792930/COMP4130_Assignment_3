package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;
import java.io.ObjectOutputStream;
import static org.junit.Assert.*;

public class TestMultiValueMap_writeObject_ExceptionPath_1 {

    private class TestMultiValueMap extends MultiValueMap {
        // Expose the private method for testing.
        private void testWriteObject(ObjectOutputStream out) throws IOException {
            // Use reflection to access the private method
            try {
                java.lang.reflect.Method method = MultiValueMap.class.getDeclaredMethod("writeObject", ObjectOutputStream.class);
                method.setAccessible(true);
                method.invoke(this, out);
            } catch (Exception e) {
                throw new IOException("Reflection exception", e);
            }
        }
    }

    @Test
    public void test_writeObject_ioException() {
        // Arrange
        TestMultiValueMap map = new TestMultiValueMap();
        ObjectOutputStream out = null;

        try {
            // This will create a stream that throws an IOException when writing.
            out = new ObjectOutputStream(new java.io.OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    throw new IOException("Forced IOException");
                }
            });

            // Act
            map.testWriteObject(out);
            Assert.fail("Expected IOException was not thrown.");
        } catch (IOException e) {
            // Expected exception
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

public class TestMultiValueMap_readObject_ExceptionPath_1 {

    private static class TestMultiValueMap extends MultiValueMap<Object, Object> {
        private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            map = (Map<Object, Object>) in.readObject();
        }
    }

    @Test
    public void test_readObject_IOExceptionThrown() {
        try {
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(new byte[0])) {
                @Override
                public void defaultReadObject() throws IOException {
                    throw new IOException("Simulated IOException");
                }
                
                @Override
                public Object readUnshared() throws IOException {
                    return null; // This is needed to satisfy the method signature
                }
            };
            new TestMultiValueMap().readObject(in);
            Assert.fail("Expected IOException was not thrown");
        } catch (IOException e) {
            Assert.assertEquals("Simulated IOException", e.getMessage());
        } catch (ClassNotFoundException e) {
            Assert.fail("Unexpected ClassNotFoundException thrown");
        }
    }

    @Test
    public void test_readObject_ClassNotFoundExceptionThrown() {
        try {
            ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(new byte[0])) {
                @Override
                public Object readUnshared() throws IOException, ClassNotFoundException {
                    throw new ClassNotFoundException("Simulated ClassNotFoundException");
                }
                
                @Override
                public void defaultReadObject() throws IOException {
                    // No exception thrown here
                }
            };
            new TestMultiValueMap().readObject(in);
            Assert.fail("Expected ClassNotFoundException was not thrown");
        } catch (IOException e) {
            Assert.fail("Unexpected IOException thrown");
        } catch (ClassNotFoundException e) {
            Assert.assertEquals("Simulated ClassNotFoundException", e.getMessage());
        }
    }
}
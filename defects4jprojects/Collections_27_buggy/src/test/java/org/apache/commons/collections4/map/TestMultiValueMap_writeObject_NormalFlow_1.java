package org.apache.commons.collections4.map;

import org.junit.Test;
import org.junit.Assert;

import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

public class TestMultiValueMap_writeObject_NormalFlow_1 {

    @Test
    public void test_writeObject_validOutputStream() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        // Setup a ByteArrayOutputStream to capture the output
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(baos)) {
            // Call the private method using reflection
            java.lang.reflect.Method method = MultiValueMap.class.getDeclaredMethod("writeObject", ObjectOutputStream.class);
            method.setAccessible(true);
            method.invoke(map, out);
            // If it reaches this point, it means no exception was thrown
            Assert.assertNotNull(baos.toByteArray());
        } catch (IOException e) {
            Assert.fail("IOException was thrown: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected exception was thrown: " + e.getMessage());
        }
    }

    @Test
    public void test_writeObject_withLargeMap() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();
        for (int i = 0; i < 1000; i++) {
            map.put("key" + i, "value" + i);
        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(baos)) {
            java.lang.reflect.Method method = MultiValueMap.class.getDeclaredMethod("writeObject", ObjectOutputStream.class);
            method.setAccessible(true);
            method.invoke(map, out);
            Assert.assertNotNull(baos.toByteArray());
        } catch (IOException e) {
            Assert.fail("IOException was thrown: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected exception was thrown: " + e.getMessage());
        }
    }

    @Test
    public void test_writeObject_emptyMap() {
        MultiValueMap<Object, Object> map = new MultiValueMap<>();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(baos)) {
            java.lang.reflect.Method method = MultiValueMap.class.getDeclaredMethod("writeObject", ObjectOutputStream.class);
            method.setAccessible(true);
            method.invoke(map, out);
            Assert.assertNotNull(baos.toByteArray());
        } catch (IOException e) {
            Assert.fail("IOException was thrown: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Unexpected exception was thrown: " + e.getMessage());
        }
    }
}
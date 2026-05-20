package org.apache.commons.collections4.map;

import org.apache.commons.collections4.map.ReflectionFactory;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestReflectionFactory_create_1 {

    @Test
    public void test_create_exceptionThrown_whenClassCannotBeInstantiated() {
        try {
            // Create an instance of ReflectionFactory
            ReflectionFactory factory = new ReflectionFactory();
            // Attempt to create an instance of a class that cannot be instantiated (e.g., an abstract class)
            factory.create(); // This should throw an exception
            Assert.fail("Expected FunctorException was not thrown");
        } catch (FunctorException e) {
            Assert.assertTrue(e.getMessage().contains("Cannot instantiate class"));
        } catch (Exception e) {
            Assert.fail("Unexpected exception was thrown: " + e.getMessage());
        }
    }

    @Test
    public void test_create_validClass_instantiationSuccess() {
        // Assuming we have a valid class for instantiation
        Class<SomeValidClass> clazz = SomeValidClass.class;

        ReflectionFactory factory = new ReflectionFactory();
        SomeValidClass instance = factory.create(clazz);

        Assert.assertNotNull(instance);
    }

    @Test
    public void test_create_exceptionThrown_onInstantiationFailure() {
        try {
            ReflectionFactory factory = new ReflectionFactory();
            factory.create(SomeInvalidClass.class); // Class that cannot be instantiated
            Assert.fail("Expected FunctorException was not thrown");
        } catch (FunctorException e) {
            Assert.assertTrue(e.getMessage().contains("Cannot instantiate class"));
        } catch (Exception e) {
            Assert.fail("Unexpected exception was thrown: " + e.getMessage());
        }
    }
}
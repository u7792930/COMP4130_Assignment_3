package org.apache.commons.collections4.map;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestReflectionFactory_create_NormalFlow_1 {

    @Test
    public void test_create_instanceOfClazz() {
        // Given
        ReflectionFactory<MyClass> factory = new ReflectionFactory<>(MyClass.class);
        
        // When
        MyClass instance = factory.create();
        
        // Then
        Assert.assertNotNull(instance);
    }

    @Test
    public void test_create_multipleInstances() {
        // Given
        ReflectionFactory<MyClass> factory = new ReflectionFactory<>(MyClass.class);
        
        // When
        MyClass instance1 = factory.create();
        MyClass instance2 = factory.create();
        
        // Then
        Assert.assertNotNull(instance1);
        Assert.assertNotNull(instance2);
        Assert.assertNotSame(instance1, instance2); // Assuming MyClass has a no-arg constructor
    }

    @Test
    public void test_create_differentClasses() {
        // Given
        ReflectionFactory<MyOtherClass> factory = new ReflectionFactory<>(MyOtherClass.class);
        
        // When
        MyOtherClass instance = factory.create();
        
        // Then
        Assert.assertNotNull(instance);
    }
}

class ReflectionFactory<T> {
    private final Class<T> clazz;

    public ReflectionFactory(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T create() {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

class MyClass {
    public MyClass() {}
}

class MyOtherClass {
    public MyOtherClass() {}
}
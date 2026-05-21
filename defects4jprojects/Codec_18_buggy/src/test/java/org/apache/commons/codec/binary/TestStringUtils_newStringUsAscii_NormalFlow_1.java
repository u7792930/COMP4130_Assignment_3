package org.apache.commons.codec.binary;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStringUtils_newStringUsAscii_NormalFlow_1 {

    @Test
    public void test_newStringUsAscii_validInput() {
        byte[] input = new byte[]{72, 101, 108, 108, 111}; // ASCII for "Hello"
        String expected = "Hello";
        String actual = StringUtils.newStringUsAscii(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_newStringUsAscii_anotherValidInput() {
        byte[] input = new byte[]{87, 111, 114, 108, 100}; // ASCII for "World"
        String expected = "World";
        String actual = StringUtils.newStringUsAscii(input);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_newStringUsAscii_differentValidInput() {
        byte[] input = new byte[]{65, 114, 101, 32, 121, 111, 117}; // ASCII for "Are you"
        String expected = "Are you";
        String actual = StringUtils.newStringUsAscii(input);
        Assert.assertEquals(expected, actual);
    }
}
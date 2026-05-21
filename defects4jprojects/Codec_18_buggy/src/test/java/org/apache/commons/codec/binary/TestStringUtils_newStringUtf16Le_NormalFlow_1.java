package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

public class TestStringUtils_newStringUtf16Le_NormalFlow_1 {

    @Test
    public void test_newStringUtf16Le_validInput() {
        byte[] input = new byte[] { 'H', 0, 'e', 0, 'l', 0, 'l', 0, 'o', 0 };
        String expected = "Hello";
        String actual = StringUtils.newStringUtf16Le(input);
        assertEquals(expected, actual);
    }

    @Test
    public void test_newStringUtf16Le_anotherValidInput() {
        byte[] input = new byte[] { 'W', 0, 'o', 0, 'r', 0, 'l', 0, 'd', 0 };
        String expected = "World";
        String actual = StringUtils.newStringUtf16Le(input);
        assertEquals(expected, actual);
    }

    @Test
    public void test_newStringUtf16Le_specialCharacters() {
        byte[] input = new byte[] { '!', 0, '@', 0, '#', 0, '$', 0 };
        String expected = "!@#$";
        String actual = StringUtils.newStringUtf16Le(input);
        assertEquals(expected, actual);
    }
}
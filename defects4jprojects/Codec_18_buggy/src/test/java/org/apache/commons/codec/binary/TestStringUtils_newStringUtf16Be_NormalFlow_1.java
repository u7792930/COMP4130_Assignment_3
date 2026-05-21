package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

public class TestStringUtils_newStringUtf16Be_NormalFlow_1 {

    @Test
    public void test_newStringUtf16Be_validInput() {
        byte[] input = {0x00, 0x48, 0x00, 0x65, 0x00, 0x6C, 0x00, 0x6C, 0x00, 0x6F}; // represents "Hello"
        String expected = "Hello";
        String result = StringUtils.newStringUtf16Be(input);
        assertEquals(expected, result);
    }

    @Test
    public void test_newStringUtf16Be_anotherValidInput() {
        byte[] input = {0x00, 0x57, 0x00, 0x6F, 0x00, 0x72, 0x00, 0x6C, 0x00, 0x64}; // represents "World"
        String expected = "World";
        String result = StringUtils.newStringUtf16Be(input);
        assertEquals(expected, result);
    }

    @Test
    public void test_newStringUtf16Be_withDifferentCharacters() {
        byte[] input = {0x00, 0x4E, 0x00, 0x6F, 0x00, 0x72, 0x00, 0x64}; // represents "Nord"
        String expected = "Nord";
        String result = StringUtils.newStringUtf16Be(input);
        assertEquals(expected, result);
    }
}
package org.apache.commons.compress.archivers.tar;

import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;
import java.nio.ByteBuffer;
import org.apache.commons.compress.archivers.zip.ZipEncoding;

public class TestTarUtils_formatNameBytes_ExceptionPath_2 {

    @Test
    public void test_formatNameBytes_invalidEncoding() {
        String name = "testName";
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        ZipEncoding encoding = new ZipEncoding() {
            @Override
            public ByteBuffer encode(String name) throws IOException {
                throw new IOException("Invalid encoding"); // Simulate encoding failure
            }

            @Override
            public String decode(byte[] bytes) {
                return null; // Not needed for this test
            }

            @Override
            public boolean canEncode(String name) {
                return false; // Simulate that this encoding cannot encode the name
            }
        };

        try {
            TarUtils.formatNameBytes(name, buf, offset, length, encoding);
            Assert.fail("Expected IOException to be thrown");
        } catch (IOException e) {
            // Expected exception
        }
    }

    @Test
    public void test_formatNameBytes_nameTooLong() {
        String name = "ThisNameIsDefinitelyLongerThanTheBufferSize";
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        ZipEncoding encoding = new ZipEncoding() {
            @Override
            public ByteBuffer encode(String name) {
                return ByteBuffer.wrap(name.getBytes()); // Simulate encoding
            }

            @Override
            public String decode(byte[] bytes) {
                return null; // Not needed for this test
            }

            @Override
            public boolean canEncode(String name) {
                return true; // Simulate that this encoding can encode the name
            }
        };

        try {
            int resultOffset = TarUtils.formatNameBytes(name, buf, offset, length, encoding);
            Assert.assertEquals(offset + length, resultOffset);
            Assert.assertArrayEquals(new byte[]{84, 104, 105, 115, 78, 97, 109, 101, 0, 0}, buf);
        } catch (IOException e) {
            Assert.fail("IOException should not have been thrown");
        }
    }

    @Test
    public void test_formatNameBytes_nullName() {
        String name = null;
        byte[] buf = new byte[10];
        int offset = 0;
        int length = 10;
        ZipEncoding encoding = new ZipEncoding() {
            @Override
            public ByteBuffer encode(String name) {
                return ByteBuffer.wrap(new byte[0]); // Simulate encoding for null
            }

            @Override
            public String decode(byte[] bytes) {
                return null; // Not needed for this test
            }

            @Override
            public boolean canEncode(String name) {
                return false; // Simulate that this encoding cannot encode the name
            }
        };

        try {
            TarUtils.formatNameBytes(name, buf, offset, length, encoding);
            Assert.fail("Expected IOException to be thrown due to null name");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
package com.bknieriem;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class MainTest {
    @Test
    void main() throws IOException {
        testTrue();
        testFalse();
    }

    void testTrue() throws IOException {
        Main.main(new String[] {"test1.txt"});
    }

    void testFalse() throws IOException {
        Main.main(new String[] {"test2.txt"});
    }
}
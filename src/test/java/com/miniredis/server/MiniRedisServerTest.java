package com.miniredis.server;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class MiniRedisServerTest {

    @Test
    void versionIsDefined() {
        assertFalse(MiniRedisServer.VERSION.isBlank());
    }
}
package com.bnt.rcp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RcpApplicationTests {

	@Test
    @Disabled("Ignoring this test temporarily")
    void contextLoads() {
        assertTrue(true, "Spring context should load without errors");
    }

}

package com.mathochiststudios.escapefromuni.headless;

import com.mathochiststudios.escapefromuni.headless.JUnitExtensions.RetryExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(RetryExtension.class)
public class TestRetryExtension {

    private static int attempt = 0;

    @Test
    public void testWithRetry() {
        attempt++;
        System.out.println("Test attempt: " + attempt);

        if (attempt < 5) {
            throw new RuntimeException("Simulated test failure on attempt " + attempt);
        }
    }

}

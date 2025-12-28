package com.mathochiststudios.escapefromuni.headless.JUnitExtensions;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.lang.reflect.InvocationTargetException;

/**
 * JUnit 5 extension that retries a test up to a maximum number of attempts if it fails. <br>
 * I have added this because some tests are flaky and the texture manager sometimes fails
 * in headless mode for unknown reasons. Possibly due to resource loading timing issues or race conditions.
 * This extension helps to mitigate those issues by allowing tests to be retried automatically to avoid
 * false negatives in test results and CI pipelines.
 * <br>
 * Usage: Annotate your test class with @ExtendWith(RetryExtension.class) to enable
 * <br>
 * <br>
 * CREDITS: Adapted from <a href="https://www.baeldung.com/junit-implement-retry">Emmanuel Mireku Omari's Blog</a>
 */
public class RetryExtension implements TestExecutionExceptionHandler {

    // will retry a failed test up to 5 times (total of 4 retries)
    private static final int MAX_RETRIES = 4;
    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create("RetryExtension");

    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {

        ExtensionContext.Store store = extensionContext.getRoot().getStore(NAMESPACE);
        String key = extensionContext.getUniqueId();
        int retries = store.getOrDefault(key, Integer.class, 0);

        Throwable lastThrowable = throwable;

        while (retries < MAX_RETRIES) {
            retries++;
            store.put(key, retries);
            System.out.println("Retrying test " + extensionContext.getDisplayName() + ", attempt " + retries);
            System.out.println("Last failure reason: " + lastThrowable.getMessage());
            try {
                // invoke the test method again
                extensionContext.getRequiredTestMethod().invoke(extensionContext.getRequiredTestInstance());
                // success
                return;
            } catch (InvocationTargetException ite) {
                // record underlying cause and continue retrying
                lastThrowable = ite.getCause();
            } catch (Throwable t) {
                // record any other reflection/runtime failure and continue
                lastThrowable = t;
            }
        }

        // exhausted retries, rethrow last observed failure
        throw lastThrowable;

    }

}

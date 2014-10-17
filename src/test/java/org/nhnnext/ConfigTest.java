package org.nhnnext;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Tests for {@link org.nhnnext.Config}.
 */
public class ConfigTest {
    @Test
    public void testConfig() {
        String host = Config.UNIQUSH_HOST;
        Assert.assertNotNull(host);
        Assert.assertNotEquals(host, "");
    }

    @Test(expected=ExceptionInInitializerError.class)
    public void testConfigEnvException() throws Exception {
        Map<String, String> environ = getModifiableEnviron();
        if (environ.containsKey("PUSH_SERVER_WORKER_ENV"))
            environ.replace("PUSH_SERVER_WORKER_ENV", "ErrorEnv");
        Class.forName("org.nhnnext.Config");
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> getModifiableEnviron() throws NoSuchFieldException, IllegalAccessException {
        Map<String, String> unmodifiable = System.getenv();
        Class<?> cu = unmodifiable.getClass();
        Field m = cu.getDeclaredField("m");
        m.setAccessible(true);
        return (Map<String, String>)m.get(unmodifiable);
    }
}

package com.itexico.unittestingsample.junit;

import org.junit.Rule;
import org.junit.Test;

import java.util.logging.Logger;

/**
 * Created by iTexico Developer on 8/8/2016.
 */
public class LogRuleTest {

    @Rule
    public LogRule logger = new LogRule();

    @Test
    public void checkOutMyLogger() {
        final Logger log = logger.getLogger();
        log.warning("Your test is showing!");
    }
}

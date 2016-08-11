package com.itexico.unittestingsample.junit;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.logging.Logger;

/**
 * Created by iTexico Developer on 8/8/2016.
 */
public class LogRule implements TestRule {
    private static final String TAG = LogRule.class.getSimpleName();
    private Statement base;
    private Description description;

    @Override
    public Statement apply(Statement base, Description description) {
        this.base = base;
        this.description = description;
        return new MyStatement(base);
    }

    private Logger logger;

    public Logger getLogger() {
        return this.logger;
    }

    public class MyStatement extends Statement {
        private final Statement base;

        public MyStatement(Statement base) {
            this.base = base;
        }

        @Override
        public void evaluate() throws Throwable {
            logger = Logger.getLogger(description.getTestClass().getName() + '.' + description.getDisplayName());
            System.out.print("Before ");
            base.evaluate();
            System.out.print("After ");
        }
    }
}

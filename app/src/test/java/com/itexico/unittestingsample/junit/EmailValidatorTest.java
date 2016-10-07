package com.itexico.unittestingsample.junit;

import android.test.suitebuilder.annotation.SmallTest;

import com.itexico.unittestingsample.EmailValidator;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by iTexico Developer on 8/8/2016.
 */
@SmallTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmailValidatorTest {
    @Test
    public void correctEmailSimple() {
        assertTrue(EmailValidator.isValidEmail("name@email.com"));
    }

    @Test
    public void correctEmailSubDomain() {
        assertTrue(EmailValidator.isValidEmail("name@email.co.uk"));
    }

    @Test
    public void invalidEmail() {
        assertFalse(EmailValidator.isValidEmail("name@email"));
    }

    @Test
    public void invalidEmailDoubleDot() {
        assertFalse(EmailValidator.isValidEmail("name@email..com"));
    }

    @Test
    public void invalidEmailNoUsername() {
        assertFalse(EmailValidator.isValidEmail("@email.com"));
    }

    @Test
    public void emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(""));
    }

    @Test
    public void emailValidator_NullEmail_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(null));
    }
}

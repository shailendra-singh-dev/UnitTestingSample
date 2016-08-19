package com.itexico.unittestingsample.junit;

import com.itexico.unittestingsample.PasswordValidator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by iTexico Developer on 8/8/2016.
 */
@RunWith(Parameterized.class)
public class PasswordValidatorTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Parameterized.Parameter
    public String mPassword;

    @Test(expected = IllegalArgumentException.class)
    public void passwordValidator_MiniumLength() {
        boolean isPassed =PasswordValidator.isValidPassword("test");
        if(!isPassed){
            throw new IllegalArgumentException();
        }
    }

    public PasswordValidatorTest(){
    }

    @Parameterized.Parameters
    public static List<Object> data() {
        Object[] data = new Object[]{"testPassword", "test@1234"};
        return Arrays.asList(data);
    }

    @Test
    public void throwsIllegalArgumentExceptionIfLengthIsLess() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Minimum Length expected is 10.");
        boolean isPassed =PasswordValidator.isValidPassword("tempPassword@1234");
        if(!isPassed){
            throw new IllegalArgumentException("Minimum Length expected is 10.");
        }
    }

    @Test
    public void testUsingTempFolder() throws IOException {
        File createdFolder = folder.newFolder("newfolder");
        File createdFile = folder.newFile("myfilefile.txt");
        assertTrue(createdFile.exists());
    }

}

package projectzero.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmailUtilsTest {

    @Test
    public void notifyUser() {
        EmailUtils.notifyUser("les.volodumur@gmail.com", "Hello", "Test");
    }
}
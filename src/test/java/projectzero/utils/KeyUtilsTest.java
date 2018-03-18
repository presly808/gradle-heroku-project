package projectzero.utils;

import org.junit.Test;

import java.security.Key;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class KeyUtilsTest {

    @Test
    public void getUniqueKey() {
        Set<String> set = new HashSet<>();
//        System.out.println(KeyUtils.getUniqueKey());
        for (int i = 0; i < 1000; i++)
            set.add(KeyUtils.getUniqueKey());
        assertEquals(set.size(), 1000);
    }
}
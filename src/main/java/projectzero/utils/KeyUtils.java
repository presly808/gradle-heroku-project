package projectzero.utils;

import java.math.BigInteger;
import java.security.SecureRandom;


public class KeyUtils {

    private static SecureRandom random = new SecureRandom();

    /**
     * @return randomly generated big integer key
     */
    // todo generate usin UUID it will be just a string
    public static String getUniqueKey() {
        return new BigInteger(256, random).toString(32);
    }
}

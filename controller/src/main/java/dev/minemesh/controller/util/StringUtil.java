package dev.minemesh.controller.util;

import java.math.BigInteger;
import java.nio.ByteBuffer;

public class StringUtil {

    public static String generateIdString(long time, short counter) {
        var buffer = ByteBuffer.allocate(10)
                .putLong(time)
                .putShort(counter);
        var bytes = buffer.array();

        var bigInteger = new BigInteger(bytes);

        // hax string
        // radix=32 -> more characters
        return bigInteger.toString(32);
    }

}

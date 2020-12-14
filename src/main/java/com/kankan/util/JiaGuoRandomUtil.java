package com.kankan.util;

import org.apache.commons.lang.math.RandomUtils;

public class JiaGuoRandomUtil {

    public static String generateCode(Integer length) {

        StringBuffer stringBuffer = new StringBuffer();

        while (length > 0) {
            stringBuffer.append(RandomUtils.nextInt(10));
            length--;
        }
        return stringBuffer.toString();
    }
}

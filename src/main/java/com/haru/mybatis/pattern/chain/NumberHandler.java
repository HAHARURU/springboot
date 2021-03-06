package com.haru.mybatis.pattern.chain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author HARU
 * @since 2018/10/20
 */
public class NumberHandler extends StringHandler {
    @Override
    public void handleString(String text) {
        String regEx = "[^0-9]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(text);
        System.out.println(matcher.replaceAll("").trim());
        this.transmit(text);
    }
}

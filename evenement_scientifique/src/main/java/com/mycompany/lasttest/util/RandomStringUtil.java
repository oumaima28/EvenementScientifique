/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lasttest.util;

import java.util.Random;

/**
 *
 * @author HP
 */
public class RandomStringUtil {

    private static final char[] symbols;

    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch) {
            tmp.append(ch);
        }
        for (char ch = 'a'; ch <= 'z'; ++ch) {
            tmp.append(ch);
        }
        symbols = tmp.toString().toCharArray();
    }

    private static final Random RANDOM = new Random();

    private static final char[] BUF= new char[10];

    public static String generate() {
        for (int idx = 0; idx < BUF.length; ++idx) {
            BUF[idx] = symbols[RANDOM.nextInt(symbols.length)];
        }
        return new String(BUF);
    }
}

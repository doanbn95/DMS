package com.omi.sakura.common.utils;

import org.apache.commons.text.WordUtils;

public class CapitalizeText {
    public static String convertText(String text) {
        return WordUtils.capitalize(text.toLowerCase());
    }

    public static String formatText(String text) {
        text = text.trim();
        while (text.indexOf("  ") != -1) {
            text = text.replace("  ", " ");
        }
        return text;
    }
}

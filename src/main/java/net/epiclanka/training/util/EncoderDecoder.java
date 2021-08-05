package net.epiclanka.training.util;

import java.util.Base64;

public class EncoderDecoder {
    public static String base64Encoding(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public static String base64Decoding(String encodedText) {
        String decodeString = "";
        byte[] decodeBytes = Base64.getDecoder().decode(encodedText);
        decodeString = new String(decodeBytes);
        return decodeString;
    }
}

package com.nicou.imagemanager.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class ShaHashUtil {

    public static String hashSha(byte[] content, String hashType) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(hashType);
        byte[] encodedHash = digest.digest(content);
        return bytesToHex(encodedHash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

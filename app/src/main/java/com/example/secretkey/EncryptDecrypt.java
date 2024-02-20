package com.example.secretkey;

import android.util.Base64;

import java.lang.reflect.Array;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptDecrypt {

    private static final String ALGORITHM = "Blowfish";
    private static final String MODE = "Blowfish/CBC/PKCS5Padding";

    public static String encrypt(String value, String mykey) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(mykey.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(MODE);

            byte[] ivBytes = new byte[cipher.getBlockSize()];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(ivBytes);

            cipher.init(cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(ivBytes));
            byte[] encryptedValues = cipher.doFinal(value.getBytes());

            byte[] combined = new byte[ivBytes.length + encryptedValues.length];
            System.arraycopy(ivBytes, 0, combined, 0, ivBytes.length);
            System.arraycopy(encryptedValues, 0, combined, ivBytes.length, encryptedValues.length);

            return Base64.encodeToString(combined, Base64.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String value, String myKey) throws BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException{
        byte[] combined = Base64.decode(value, Base64.DEFAULT);
        byte[] ivBytes = Arrays.copyOfRange(combined,0,8);
        byte[] encryptedValues = Arrays.copyOfRange(combined,8,combined.length);

        SecretKeySpec secretKeySpec = new SecretKeySpec(myKey.getBytes(),ALGORITHM);
        Cipher cipher = Cipher.getInstance(MODE);
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,new IvParameterSpec(ivBytes));
        return new String(cipher.doFinal(encryptedValues));



    }
}

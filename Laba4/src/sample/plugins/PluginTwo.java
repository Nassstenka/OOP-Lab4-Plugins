package sample.plugins;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class PluginTwo {

    private static SecretKeySpec secretKey;

    private static void setKey(String myKey) throws Exception
    {
        MessageDigest sha;
        byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
        sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        secretKey = new SecretKeySpec(key, "Blowfish");
    }

    public static byte[] encrypt(byte[] plainText, String key) throws Exception
    {
        setKey(key);
        Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Base64.getEncoder().encode(cipher.doFinal(plainText));
    }

    public static byte[] decrypt(byte[] cipherText, String key) throws Exception
    {
        setKey(key);
        Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(Base64.getDecoder().decode(cipherText));
    }
}

package kerberos.des;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author steffen
 */
public class TripleDes {

        public static byte[] createDesKey(String pw){
        MessageDigest digest = null;
        try {
            digest = java.security.MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        digest.update(pw.getBytes());
        byte[] hash = digest.digest();
        byte[] key = new byte[8];
        System.arraycopy(hash, 0, key, 0, 8);
        return key;
    }

    public static byte[] createTripleDeyKey(){
        try {
            KeyGenerator desEdeGen = KeyGenerator.getInstance("DESede"); // Triple DES
            SecretKey desEdeKey = desEdeGen.generateKey();               // Generate a key
            SecretKeyFactory desEdeFactory = SecretKeyFactory.getInstance("DESede");
            DESedeKeySpec desEdeSpec = (DESedeKeySpec) desEdeFactory.getKeySpec(desEdeKey, javax.crypto.spec.DESedeKeySpec.class);
            byte[] rawDesEdeKey = desEdeSpec.getKey();
            return rawDesEdeKey;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    public static byte[] createTripleDesKey(String pw){
        if(pw.length() != 24) throw new RuntimeException("Schlüssel " + pw + " hat die falsche länge.");
        return pw.getBytes();
    }

    public static byte[] desToTripleDes(byte[] deskey){
        byte[] tdeskey = new byte[24];
        System.arraycopy(deskey, 0, tdeskey, 0, 8);
        System.arraycopy(deskey, 0, tdeskey, 8, 8);
        System.arraycopy(deskey, 0, tdeskey, 16, 8);
        return tdeskey;
    }

    private SecretKey key;

    public TripleDes(byte[] key){
        this.key = new SecretKeySpec(key, "DESede");
    }

    public byte[] encrypt(byte[] plaintext){
        try {
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plaintext);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public byte[] decrypt(byte[] ciphertext){
        try {
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(ciphertext);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


}

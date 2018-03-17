package com.currencycloud.fakebook.utils;

import org.springframework.core.io.ClassPathResource;
import sun.security.x509.*;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;

/**
 * Created by lekanomotayo on 15/03/2018.
 */
public class KeyUtils {

    /***
     * Keystore should be readonly and the user running this application
     * should be the one with read access (chmod 400)
     */


    public static final String KEYSTORE;
    public static final String KEYSTORE_PWD;
    public static final String ALIAS;
    public static final String KEYSTORE_TYPE = "JKS";

    static{
        KEYSTORE = System.getProperty("keystore", "keystore.jks");
        KEYSTORE_PWD = System.getProperty("keystorepwd", "Password12");
        ALIAS = System.getProperty("alias", "main");
    }

    // Java Keystore
    public static KeyStore loadKeyStore(final String keystoreFile, final String password, final String keyStoreType) throws Exception {
        if (null == keystoreFile) {
            throw new IllegalArgumentException("Keystore url cannot be null");
        }
        final KeyStore keystore = KeyStore.getInstance(keyStoreType);
        File file = new ClassPathResource(keystoreFile).getFile();
        FileInputStream fis = new FileInputStream(file);

        try {
            keystore.load(fis, password.toCharArray());
            //LOG.debug("Loaded key store");
        } finally {
            if (null != fis) {
                fis.close();
            }
        }
        return keystore;
    }


    // Get Keypair in Java Keystoe
    public static KeyPair getKeyPair(final KeyStore keystore, final String alias, final String password) throws Exception{

        final Key key = (PrivateKey) keystore.getKey(alias, password.toCharArray());
        final Certificate cert = keystore.getCertificate(alias);
        final PublicKey publicKey = cert.getPublicKey();

        return new KeyPair(publicKey, (PrivateKey) key);
    }

    private static KeyPair getKeyPair(String file, String pwd, String alias) throws Exception{
        KeyStore keyStore = loadKeyStore(file, pwd, KeyStore.getDefaultType());
        return getKeyPair(keyStore, alias, pwd);
    }

    // Encryt data
    public static String encrypt(String cipherText) throws Exception{
        KeyPair keyPair = getKeyPair(KEYSTORE, KEYSTORE_PWD, ALIAS);
        PublicKey publicKey = keyPair.getPublic();
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE,  publicKey);
        byte[] encodedBytes = cipher.doFinal(cipherText.getBytes());
        return Base64.getEncoder().encodeToString(encodedBytes);
    }

    //Decrypt data
    public static String decrypt(String base64CipherText){
        ;
        try {
            KeyPair keyPair = getKeyPair(KEYSTORE, KEYSTORE_PWD, ALIAS);
            PrivateKey privateKeyEntry = keyPair.getPrivate();
            Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c.init(Cipher.DECRYPT_MODE,  privateKeyEntry);
            byte[] decodedBytes = c.doFinal(Base64.getDecoder().decode(base64CipherText));
            return new String(decodedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

}

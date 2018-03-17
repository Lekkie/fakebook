package com.currencycloud.fakebook.utils;


import com.currencycloud.fakebook.utils.KeyUtils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by lekanomotayo on 16/03/2018.
 */
public class KeyUtilsTest {

    @Test
    public void test_encrypt_decrypt()throws Exception{

        String encryptedText = "NbvelfqZFh44ZayXLTfxCkbcPBhkDfAWNETNDyvjm5U6jG0e4Te0OCex8ZKRPTRt/WMpclC5d8zOIT4gp7CDzIJto2jUvLXEjQN7IjeoiVE8ewa4CtVfN/GAR3i5DPtYVCNyuLs8aP62QV0ZbtjzHOkquEYFWpq+AdJcIUE32ryE0DojeLzH0DQJ138U1leIod8mreMz3PeZg1Mxi1nE59wZi5JWvImYCez8ZDgFTtPXc+9KdVBgM5ttBHJgT2gvL8cLo+Bw6BovJ2eDa8mZJR7pRF93tWVRPiFQtf1qKruM7ZwayMWo7pTKRtzf44rcG+LJawBYL+kBjmjDIyfQ0w==";
        String cipherText = "DE439C178F925C03";
        String encrypted = KeyUtils.encrypt(cipherText);
        String cipher = KeyUtils.decrypt(encrypted);

        assertNotNull("cipherText cannot be empty", cipherText);
        assertNotNull("cipherText cannot be empty", cipher);
        assertEquals("Decrypted text did not match expected value", cipher, cipherText);

    }


}

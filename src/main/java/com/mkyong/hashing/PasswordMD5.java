package com.mkyong.hashing;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordMD5 {

    public static String MD5( String text ) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance( "MD5" );
        byte[] hashInBytes = md.digest( text.getBytes( StandardCharsets.UTF_8 ) );

        StringBuilder sb = new StringBuilder();
        for ( byte b : hashInBytes )
        {
            sb.append( String.format( "%02x", b ) );
        }
        return sb.toString();
    }
}

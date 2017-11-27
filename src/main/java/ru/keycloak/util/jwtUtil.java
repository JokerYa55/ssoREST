/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.keycloak.util;

/**
 *
 * @author vasil
 */
public class jwtUtil {

    public static String decodeToken(String token) {
        String result = "";       
        String[] parts = token.split("[.]");
        try {
            int index = 0;
            for (String part : parts) {
                if (index >= 2) {
                    break;
                }

                index++;
                byte[] partAsBytes = part.getBytes("UTF-8");
                String decodedPart = new String(java.util.Base64.getUrlDecoder().decode(partAsBytes), "UTF-8");

                result += decodedPart;
            }
        } catch (Exception e) {
            throw new RuntimeException("Couldnt decode jwt", e);
        }
        return result;
    }
}

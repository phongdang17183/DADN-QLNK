package com.example.IotProject.util;

import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtils {
    public static Map<String, String> decodeIdToken(String idToken) {
        DecodedJWT jwt = JWT.decode(idToken);
        String username = jwt.getClaim("username").asString();
        String sub = jwt.getClaim("sub").asString();
        String name = jwt.getClaim("name").asString();
        return Map.of("username", username, "name", name, "sub", sub);
    }

    public static Map<String, String> decodeWebToken(String idToken) {
        DecodedJWT jwt = JWT.decode(idToken);
        String username = jwt.getClaim("username").asString();
        String sub = jwt.getClaim("sub").asString();
        return Map.of("username", username, "sub", sub);
    }
}

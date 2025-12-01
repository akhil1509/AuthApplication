package com.auth.Auth.App.helper;

import java.util.UUID;

public class UUIDHelper {
    public static UUID generateUUID(String uuid){
        return UUID.fromString(uuid);
    }
}

package com.wondrous.oauth_jwt.dto;


import java.util.Map;

public class KaKaoResponse implements OAuth2Response {
    private final Map<String, Object> attribute;

    public KaKaoResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
        System.out.println("Kakao attribute: " + attribute);
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attribute.get("properties");
        return properties.get("nickname").toString();
    }
}

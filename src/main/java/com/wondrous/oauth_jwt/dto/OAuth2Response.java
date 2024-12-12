package com.wondrous.oauth_jwt.dto;


public interface OAuth2Response {

    String getProvider();
    String getProviderId();
    String getEmail();
    String getName();
}

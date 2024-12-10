package com.wondrous.oauth_jwt.service;


import com.wondrous.oauth_jwt.dto.GoogleResponse;
import com.wondrous.oauth_jwt.dto.KaKaoResponse;
import com.wondrous.oauth_jwt.dto.NaverResponse;
import com.wondrous.oauth_jwt.dto.OAuth2Response;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuth2User = " + oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;

        if(registrationId == "naver") {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId == "kakao") {
            oAuth2Response = new KaKaoResponse(oAuth2User.getAttributes());
        }
        else if (registrationId == "google") {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else {
            System.out.println("유효하지 않은 로그인 방식입니다.");
            throw new OAuth2AuthenticationException("유효하지 않은 로그인 방식");
        }
        // 이후 작성 예정

        return oAuth2User;
    }
}


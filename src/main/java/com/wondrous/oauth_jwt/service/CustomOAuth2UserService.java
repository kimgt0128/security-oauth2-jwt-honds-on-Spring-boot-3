package com.wondrous.oauth_jwt.service;


import com.wondrous.oauth_jwt.dto.*;
import com.wondrous.oauth_jwt.entity.OAuth2Member;
import com.wondrous.oauth_jwt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuth2User = " + oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;

        System.out.println("registrationID: " + registrationId);

        if(registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("kakao")) {
            oAuth2Response = new KaKaoResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else {
            System.out.println("유효하지 않은 로그인 방식입니다.");
            throw new OAuth2AuthenticationException("유효하지 않은 로그인 방식");
        }
        // 유저 존재 여부 확인 후 가입 처리 로직

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        OAuth2Member existData = memberRepository.findByMemberName(username);
        String role = null;

        if (existData == null) {
            OAuth2Member newOauth2Member = new OAuth2Member();
            newOauth2Member.setMemberName(username);
            newOauth2Member.setEmail(oAuth2Response.getEmail());
            newOauth2Member.setRole("ROLE_USER");

            memberRepository.save(newOauth2Member);
        }
        else {

            role = existData.getRole();
            existData.setEmail(oAuth2Response.getEmail());
            memberRepository.save(existData);
        }

        /**
         * 기본 반환 형태: return oAuth2User;
         * OAuth2User형태로 반환해야 하지만 OAuth2User을 상속받은 CustomOAuth2User으로 반환
         */
        return new CustomOAuth2User(oAuth2Response, role);
    }
}


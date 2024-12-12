package com.wondrous.oauth_jwt.repository;

import com.wondrous.oauth_jwt.entity.OAuth2Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<OAuth2Member, Long> {
    OAuth2Member findByMemberName(String memberName);


}

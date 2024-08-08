package org.choongang.mypage.services;

import lombok.RequiredArgsConstructor;
import org.choongang.member.services.MemberSaveService;
import org.choongang.mypage.controllers.RequestProfile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberSaveService saveService;

    public void update(RequestProfile form) {

    }
}
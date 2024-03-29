package com.acorn.finals.controller;

import com.acorn.finals.model.dto.ChannelDto;
import com.acorn.finals.model.dto.MemberDto;
import com.acorn.finals.model.entity.MemberEntity;
import com.acorn.finals.service.MemberService;
import com.acorn.finals.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final TokenService tokenService;
    // channel member mapper

    /**
     * 내 정보를 받아옵니다
     *
     * @return
     */
    @GetMapping("/@me")
    public MemberDto queryMyInfo(Authentication auth) {
        int memberId = Integer.parseInt(auth.getName());
        return memberService.findMemberById(memberId);
    }

    /**
     * 내가 참여중인 모든 채널의 정보를 받아옵니다
     *
     * @return list of channel
     */
    @GetMapping("/@me/channel")
    public List<ChannelDto> listAllChannel(Authentication auth) {
        // TODO: Authentication to MemberDto
        Integer memberId = Integer.parseInt(auth.getName());
        return memberService.listAllChannelsByMemberId(memberId);
    }


    /**
     * 내 정보를 수정합니다
     *
     * @param dto
     * @return
     */
    @PatchMapping("/updateStatus")
    public ResponseEntity<Void> updateMyInfo(@RequestBody MemberDto dto) {
        memberService.updateStatus(dto);
        return ResponseEntity.ok(null);
    }

    /**
     * 닉네임을 변경합니다
     *
     * @param dto
     * @return
     */
    @PutMapping("/changeNick")
    public ResponseEntity<MemberDto> changeNick(@RequestBody MemberDto dto) {
        MemberDto responseDto = memberService.changeNickandTag(dto);
        return ResponseEntity.ok().body(responseDto);
    }

    /**
     * 회원가입
     *
     * @param entity require nickname , hashtag
     * @return boolean
     */
    @PostMapping("/signup")
    public boolean join(@RequestBody MemberEntity entity) {
        return memberService.signup(entity);
    }

    /**
     * 로그아웃
     *
     * @return if RefreshToken delete and changeStatus return true nor false
     */
    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(Authentication auth) {
        //Cookie 값 지우기~
        ResponseCookie refreshTokenCookie =
                ResponseCookie.from("RefreshToken", "")
                        .maxAge(0)
                        .httpOnly(true)
                        .path("/")
                        .build();
        var memberId = Integer.parseInt(auth.getName());
        boolean logoutResult = tokenService.deleteRefreshTokenByMemberId(memberId);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(logoutResult);
    }
}


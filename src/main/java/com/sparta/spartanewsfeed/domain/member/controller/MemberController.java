package com.sparta.spartanewsfeed.domain.member.controller;

import com.sparta.spartanewsfeed.domain.member.Member;
import com.sparta.spartanewsfeed.domain.member.dto.*;
import com.sparta.spartanewsfeed.domain.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {
	private final MemberService memberService;

	public MemberController(final MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseMember getLogInMemberInfo(@RequestAttribute(name = "member") Member member) {
		return ResponseMember.make(member);
	}

	@GetMapping("/{memberId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseMember findById(@PathVariable(name = "memberId") Long memberId) {
		return memberService.findById(memberId);
	}
	
	@GetMapping("/search-condition")
	@ResponseStatus(HttpStatus.OK)
	public List<ResponseMember> findAllByNameOrNickname(@RequestParam(name = "nameOrNickname", defaultValue = "") String nameOrNickname) {
		return memberService.findAllByNameOrNickname(nameOrNickname);
	}

	@PostMapping("/verify-identity")
	@ResponseStatus(HttpStatus.OK)
	public VerifyIdentityResult verifyIdentity(@RequestBody @Valid RequestVerifyIdentity request,
											   @RequestAttribute(name = "member") Member member) {
		return memberService.verifyIdentity(request, member);
	}

	@PatchMapping("/info")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateInfo(@RequestAttribute(name = "member") Member member,
						   @RequestBody @Valid UpdateInfo request) {
		memberService.updateInfo(member, request);
	}

	@PatchMapping("/password")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePassword(@RequestAttribute(name = "member") Member member,
							   @RequestBody @Valid UpdatePassword request) {
		memberService.updatePassword(member, request);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@RequestAttribute(name = "member") Member member) {
		memberService.delete(member);
	}
}

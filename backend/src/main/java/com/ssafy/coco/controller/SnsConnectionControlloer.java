package com.ssafy.coco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.ssafy.coco.relationvo.Board;
import com.ssafy.coco.service.JwtService;
import com.ssafy.coco.service.MemberService;
import com.ssafy.coco.service.SnsApiService;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.Tokens;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/jwt")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api(tags = { "SNS Controller" }, description = "SSAFY HRM resource API (Test)")
public class SnsConnectionControlloer {
	
	@Autowired
	SnsApiService snsApiService;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	MemberService memberService;
	
	
	
	@ApiOperation(value = "sns 로그인을 id 받아오기", response = List.class)
	@RequestMapping(value = "/snsLogin", method = RequestMethod.POST)
    public ResponseEntity<Tokens> snsLogin(@RequestBody String id) throws Exception {
		Member m = new Member();
		m.setId(id);
		System.out.println("m.getid"+m.getId());
		
		List<Member> list = memberService.findMember(m);
		int size = list.size();
		if(size == 0)//존재하지않으면
		{
			return new ResponseEntity<Tokens>(HttpStatus.NO_CONTENT);
		}
		else//존재 하면
		{
			m = list.get(0);
			Tokens tokens = jwtService.login(m.getId(), m.getPassword());
			return new ResponseEntity<Tokens>(tokens,HttpStatus.OK);
		}
    }
	
	@ApiOperation(value = "jwt 생성", response = String.class)
	@RequestMapping(value = "/login2")
    public String login(@RequestParam("code") String code) {
		System.out.println(code);
        String access_Token = snsApiService.getAccessToken(code);
//		JsonNode access_Token = snsApiService.getKakaoAccessToken(code);
        System.out.println("controller access_token : " + access_Token);
        
        return code;
    }
}

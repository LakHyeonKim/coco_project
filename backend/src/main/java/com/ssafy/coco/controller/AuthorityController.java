package com.ssafy.coco.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.coco.dao.MemberDao;
import com.ssafy.coco.relationvo.Board;
import com.ssafy.coco.relationvo.SignUpMember;
import com.ssafy.coco.service.JwtService;
import com.ssafy.coco.service.MailService;
import com.ssafy.coco.service.MemberService;
import com.ssafy.coco.service.TransactionService;
import com.ssafy.coco.vo.Alarm;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.Tokens;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/jwt")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api(tags = { "Authority Controller" }, description = "로그인및 토큰 관리 컨트롤러")
public class AuthorityController {

	@Autowired
	private JwtService jwtService;
	@Autowired
	MemberDao memberDao;
	@Autowired
	MemberService memberService;
	@Autowired
	MailService mailService;
	@Autowired
	private TransactionService transactionService;

	@ApiOperation(value = "프로필 사진과 함께 가입 하기", response = List.class)
	@PostMapping("/signUp")
	public ResponseEntity<Integer> signUp(SignUpMember signUpMember, HttpServletRequest request) throws Exception {
		System.out.println(signUpMember);
		int answer = (int) transactionService.signUp(signUpMember);
		if (answer<=0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answer, HttpStatus.OK);
	}
	
	@ApiOperation(value = "id로 sns로그인", response = List.class)
	@RequestMapping(value = "/snsLogin", method = RequestMethod.POST)
	public ResponseEntity<Tokens> snsLogin(@RequestBody String id) throws Exception {
		Member m = new Member();
		m.setId(id);
		System.out.println("m.getid" + m.getId());

		List<Member> list = memberService.findMember(m);
		int size = list.size();

		if (size == 0)// 존재하지않으면
		{
			return new ResponseEntity<Tokens>(HttpStatus.NO_CONTENT);
		} else// 존재 하면
		{
			m = list.get(0);
			Tokens tokens = jwtService.login(m.getId(), m.getPassword());
			return new ResponseEntity<Tokens>(tokens, HttpStatus.OK);
		}
	}

	@ApiOperation(value = "id로 임시 비밀번호 발급", response = List.class)
	@RequestMapping(value = "/getTemporaryPassword", method = { RequestMethod.POST })
	public ResponseEntity getTemporaryPassword(@RequestBody Member member) throws Exception {
		// 사용자의 id를 받아서
		List<Member> list = memberService.findMember(member);
		if (list.size() > 0) {
			Member m = list.get(0);
			String newpwd = mailService.findPwd(m.getId(), m.getNickname(), "@2");
			m.setPassword(newpwd);
			memberService.updateMember(m);
			return new ResponseEntity(HttpStatus.OK);
		} else// 존재 x
		{
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "이메일 인증", response = List.class)
	@RequestMapping(value = "/certificationByEmail/{key}", method = { RequestMethod.GET })
	public ResponseEntity certificationByEmail(@PathVariable String key) throws Exception {
		// 사용자의 id를 받아서
		int idmember = jwtService.getIdmemberByToken(key);
		if (idmember > 0) {
			Member m = new Member();
			m.setIdmember(idmember);
			m = memberService.findMember(m).get(0);
			m.setIsManager(2);
			memberService.updateMember(m);
			return new ResponseEntity(HttpStatus.OK);
		} else// 존재 x
		{
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "이메일 인증 메일 전송", response = List.class)
	@RequestMapping(value = "/sendEmailKey", method = { RequestMethod.POST })
	public ResponseEntity sendEmailKey(@RequestBody Member member) throws Exception {
		// 사용자의 id를 받아서
		List<Member> list = memberService.findMember(member);
		if (list.size() > 0) {
			Member m = list.get(0);
			String key = jwtService.makeJwt("" + m.getIdmember(), "!@323213214214324", 1);
			mailService.sendMail(m.getId(), "[SEE-SAW] 인증 메일입니다.", "<a href=http://192.168.100.95:8888/jwt/certificationByEmail/"+key+">인증하기</a>");
			return new ResponseEntity(HttpStatus.OK);
		} else// 존재 x
		{
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "카카오 api를 통한 코드를 이용하여 로그인", response = List.class)
	@RequestMapping(value = "/kakaologin", produces = "application/json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ResponseEntity<String> kakaoLogin(@RequestParam("code") String code, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		// System.out.println(code);
		JsonNode token = jwtService.getAccessToken(code);
		System.out.println("토토큰" + token);
		JsonNode profile = jwtService.getKakaoUserInfo(token.path("access_token").toString());
		System.out.println("DD" + profile);

		Member vo = jwtService.changeData(profile);
		Member searchMember = new Member();
		searchMember.setId(vo.getId());
		System.out.println(searchMember);
		ObjectMapper objectMapper = new ObjectMapper();
		List<Member> list = memberService.findMember(searchMember);

		boolean isExist = list.size() != 0 ? true : false;
		if (isExist)// 계정 존재시에
		{
			Member result = list.get(0);
			Tokens tokens2 = jwtService.login(result.getId(), result.getPassword());
			System.out.println("tokens2:" + tokens2.getAccessToken());
			System.out.println("kkao:" + token.path("access_token").toString());
			String memberJson = "{\"Member\":" + objectMapper.writeValueAsString(result) + ",";
			memberJson += "\"accessToken\":" + '"' + tokens2.getAccessToken() + '"';
			memberJson += ",\"refreshToken\":\"" + tokens2.getRefreshToken() + "\"}";
			System.out.println("멤버제이슨" + memberJson);

			return new ResponseEntity<String>(memberJson, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(objectMapper.writeValueAsString(vo), HttpStatus.OK);
		}
	}

	@ApiOperation(value = "로그인시에 유효한경우 액세스,리프레쉬 토큰을 발급해준다.", response = List.class)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity login(@RequestBody JSONObject input) throws Exception {
		Map<String, Object> map = jwtService.getMapFromJsonObject(input);
		String id = (String) map.get("id");
		String password = (String) map.get("password");
		System.out.println(id);
		System.out.println(password);
		Tokens tokens = jwtService.login(id, password);
		if (tokens != null) {
			return new ResponseEntity(tokens, HttpStatus.OK);
		} else {
			System.out.println("failfail");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "간단한 토큰 유효성 검사", response = List.class)
	@RequestMapping(value = "/auth/{tt}", method = RequestMethod.GET)
	public boolean authToken(@PathVariable String tt) throws Exception {
		String jwt = tt;
		System.out.println("우왕와아앙");
		if (jwt == null) {
			System.out.println("nulㄴㄴl");
			return false;
		} else {
			return jwtService.checkJwt(jwt);
		}
	}

	@ApiOperation(value = "리프레쉬 토큰을 통한 액세스 토큰 발급", response = List.class)
	@RequestMapping(value = "/getAccessTokenByRefreshToken/", method = RequestMethod.POST)
	public ResponseEntity<String> getAccessTokenByRefreshToken(@RequestBody String refToken) throws Exception {
		System.out.println("ref!!" + refToken);
		int idmember = memberDao.findIdByRefreshToken(refToken);
		System.out.println(idmember + "ref!!!");
		if (idmember == 0) {
			return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		} else {
			String accessToken = jwtService.makeJwt("" + idmember, 1);
			return new ResponseEntity<String>(accessToken, HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "중복확인 ", response = List.class)
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public ResponseEntity<Boolean> check(@RequestBody JSONObject json) throws Exception {
		
		String id = (String)json.get("id");
		System.out.println("? " + json.get("id"));
		boolean answer = memberService.check(id);
		System.out.println(id);
		System.out.println(answer);
		return new ResponseEntity<Boolean>(answer, HttpStatus.OK);
	}
	
	@ApiOperation(value = "닉네임 중복 체크", response = List.class)
	@RequestMapping(value = "/checkNickName", method = RequestMethod.POST)
	public ResponseEntity<List<Alarm>> checkNickName(@RequestBody Member member) throws Exception {
		int size = memberService.findMember(member).size();
		if (size ==0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Alarm>>(HttpStatus.OK);
	}
}
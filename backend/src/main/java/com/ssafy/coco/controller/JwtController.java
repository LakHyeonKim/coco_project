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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.coco.dao.MemberDao;
import com.ssafy.coco.relationvo.Board;
import com.ssafy.coco.service.JwtService;
import com.ssafy.coco.vo.Alarm;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.Tokens;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/jwt")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api(tags = { "JWT Controller" }, description = "SSAFY HRM resource API (Test)")
public class JwtController {

	@Autowired
	private JwtService jwtService;
	@Autowired
	MemberDao memberDao;

	@ApiOperation(value = "jwt 생성", response = List.class)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity login(@RequestBody JSONObject input) throws Exception {
		Map<String, Object> map =  getMapFromJsonObject(input);
		String id = (String) map.get("id");
		String password = (String) map.get("password");
		Tokens tokens = jwtService.login(id,password);
		if (tokens != null) {
			return new ResponseEntity(tokens, HttpStatus.OK);
		} else {
			System.out.println("failfail");
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "jwt 생성", response = List.class)
	@RequestMapping(value = "/create2", method = RequestMethod.POST)
	public String createJwt2(@RequestBody JSONObject input) throws Exception {
		System.out.println("들어옴 createJwt2");
		Map<String, Object> map = getMapFromJsonObject(input);
		String id = (String) map.get("id");
		String password = (String) map.get("password");
		Member m = new Member();
		m.setId(id);
		m.setPassword(password);
		List<Member> list = memberDao.findMember(m);
		if (list.size() > 0) {
			m = list.get(0);
			m.setGrade("아이언");
			return jwtService.makeJwt("" + m.getIdmember(), 1);
		} else {
			return "fail";
		}

	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMapFromJsonObject(JSONObject jsonObj) {
		Map<String, Object> map = null;
		try {
			map = new ObjectMapper().readValue(jsonObj.toJSONString(), Map.class);

		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	@ApiOperation(value = "jwt 확인", response = List.class)
	@RequestMapping(value = "/auth/{tt}", method = RequestMethod.GET)
	public boolean authToken(@PathVariable String tt) throws Exception {
		String jwt = tt;
		System.out.println(jwt);
		if (jwt == null) {
			System.out.println("null");
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
}
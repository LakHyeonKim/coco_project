package com.ssafy.coco.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.coco.dao.MemberDao;
import com.ssafy.coco.service.JwtService;
import com.ssafy.coco.vo.Alarm;
import com.ssafy.coco.vo.Member;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api(tags = { "SSAFY HRM" }, description = "SSAFY HRM resource API (Test)")
public class JwtController {

	@Autowired
	private JwtService jwtService;
	@Autowired MemberDao memberDao;

	@ApiOperation(value = "jwt 생성", response = List.class)
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createJwt(@RequestBody String id) throws Exception {
		System.out.println("들어옴 createJwt");
		String password = "1234";
		return jwtService.makeJwt(id,password);
	}
	
	@ApiOperation(value = "jwt 생성", response = List.class)
	@RequestMapping(value = "/create2", method = RequestMethod.POST)
	public String createJwt(@RequestBody JSONObject input) throws Exception {
		System.out.println("들어옴 createJwt");
		Map<String, Object> map =  getMapFromJsonObject(input);
		String id = (String) map.get("id");
		String password = (String) map.get("password");
		Member m = new Member();
		m.setId(id);
		m.setPassword(password);
		List<Member> list= memberDao.findMember(m);
		if(list.size()>0) 
		{
			m = list.get(0);
			m.setGrade("아이언");
			return jwtService.makeJwt(""+m.getIdmember());
		}
		else
		{
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
    public static Map<String, Object> getMapFromJsonObject( JSONObject jsonObj ) {
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
}
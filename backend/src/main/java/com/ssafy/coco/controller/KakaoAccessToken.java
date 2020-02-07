package com.ssafy.coco.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.coco.service.JwtService;
import com.ssafy.coco.service.MailService2;
import com.ssafy.coco.service.MemberService;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.Tokens;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api(tags = { "tttt Controller" }, description = "SSAFY HRM resource API (Test)")
public class KakaoAccessToken {
	@Autowired
	MemberService memberService;
	@Autowired
	MailService2 mailService;
	@Autowired
	JwtService jwtService;
	@ApiOperation(value = "메일 테스트", response = List.class)
	@RequestMapping(value = "/mailTest", method = {
			RequestMethod.POST })
	public ResponseEntity mailTest(@RequestBody Member member) throws Exception {
		//사용자의 id를 받아서 
		System.out.println("감초"+member);
		List<Member> list = memberService.findMember(member);
		if(list.size()>0)
		{
			Member m = list.get(0);
			String newpwd = mailService.findPwd(m.getId(), m.getNickname(), "@2");
			m.setPassword(newpwd);
			memberService.updateMember(m);
			return new ResponseEntity(HttpStatus.OK);
		}
		else//존재 x
		{
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@ApiOperation(value = "sns 로그인을 통한 토큰 받아오기3", response = List.class)
	@RequestMapping(value = "/kakaologin2", produces = "application/json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ResponseEntity<String> kakaoLogin(@RequestParam("code") String code, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		// System.out.println(code);
		JsonNode token = KakaoLogin.getAccessToken(code);
		System.out.println("토토큰" + token);
		JsonNode profile = KakaoLogin.getKakaoUserInfo(token.path("access_token").toString());
		System.out.println("DD" + profile);

		Member vo = KakaoLogin.changeData(profile);
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
			
			System.out.println("tokens2:"+tokens2.getAccessToken());
			System.out.println("kkao:"+token.path("access_token").toString());
			String memberJson = "{\"Member\":" + objectMapper.writeValueAsString(result) + ",";
			memberJson += "\"accessToken\":" +'"'+ tokens2.getAccessToken()+'"';
			memberJson += ",\"refreshToken\":\"" + tokens2.getRefreshToken() + "\"}";
			System.out.println("멤버제이슨" + memberJson);
			
			return new ResponseEntity<String>(memberJson, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(objectMapper.writeValueAsString(vo), HttpStatus.OK);
		}
	}
	@ApiOperation(value = "sns 로그인을 통한 토큰 받아오기2", response = List.class)
	@RequestMapping(value = "/putToken/{code}", method = RequestMethod.GET)
	public JsonNode getKakaoAccessToken(@PathVariable String code) {

		final String RequestUrl = "https://kauth.kakao.com/oauth/token"; // Host
		final List<NameValuePair> postParams = new ArrayList<NameValuePair>();

		postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
		postParams.add(new BasicNameValuePair("client_id", "41ced2884f7e3fbdee26ed46e0de8d47")); // REST API KEY
		postParams.add(new BasicNameValuePair("redirect_uri", "http://localhost:8888")); // 리다이렉트 URI
		postParams.add(new BasicNameValuePair("code", code)); // 로그인 과정중 얻은 code 값

		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(RequestUrl);

		JsonNode returnNode = null;

		try {
			post.setEntity(new UrlEncodedFormEntity(postParams));

			final HttpResponse response = client.execute(post);
			final int responseCode = response.getStatusLine().getStatusCode();

			System.out.println("\nSending 'POST' request to URL : " + RequestUrl);
			System.out.println("Post parameters : " + postParams);
			System.out.println("Response Code : " + responseCode);

			// JSON 형태 반환값 처리
			ObjectMapper mapper = new ObjectMapper();

			returnNode = mapper.readTree(response.getEntity().getContent());

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}

		return returnNode;
	}
}

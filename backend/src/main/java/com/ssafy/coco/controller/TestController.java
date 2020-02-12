package com.ssafy.coco.controller;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.coco.dao.MemberDao;
import com.ssafy.coco.relationvo.Board;
import com.ssafy.coco.service.JwtService;
import com.ssafy.coco.service.MailService;
import com.ssafy.coco.service.MemberService;
import com.ssafy.coco.vo.Alarm;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.Tokens;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/test")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api(tags = { "test Controller" }, description = "테스트 컨트롤러")
public class TestController {

	@Autowired
	private JwtService jwtService;
	@Autowired
	MemberDao memberDao;
	@Autowired
	MemberService memberService;
	@Autowired
	MailService mailService;

	@ApiOperation(value = "카카오 api를 통한 코드를 이용하여 로그인", response = List.class)
	@RequestMapping(value = "/googlelogin2", produces = "application/json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ResponseEntity<String> kakaoLogin2(@RequestParam("code") String code, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		// TODO Auto-generated method stub
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("code", code);
		parameters.add("client_id", "762237131426-6aasgpj3j773f8p9dsrv6voiccn6o2uq.apps.googleusercontent.com");
		parameters.add("client_secret", "eJSRiU8DNoCiN9Lgeg7PwAoi");
		parameters.add("redirect_uri", "http://localhost:8888/test/googlelogin2");
		parameters.add("grant_type", "authorization_code");

		HttpEntity<MultiValueMap<String, String>> rest_request = new HttpEntity<>(parameters, headers);

		URI uri = URI.create("https://www.googleapis.com/oauth2/v4/token");

		ResponseEntity<String> rest_reponse;
		rest_reponse = restTemplate.postForEntity(uri, rest_request, String.class);
		String bodys = rest_reponse.getBody();
		System.out.println(bodys);

		return rest_reponse;
	}

	@ApiOperation(value = "카카오 api를 통한 코드를 이용하여 로그인", response = List.class)
	@RequestMapping(value = "/googlelogin", produces = "application/json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonNode kakaoLogin(@RequestParam("code") String code, HttpSession session) throws Exception {
		//final String RequestUrl = "https://www.googleapis.com/oauth2/v4/token";
		final String RequestUrl = "https://accounts.google.com/o/oauth2/token";
		
		final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
		postParams.add(new BasicNameValuePair("client_id",
				"762237131426-6aasgpj3j773f8p9dsrv6voiccn6o2uq.apps.googleusercontent.com")); // REST API KEY
		postParams.add(new BasicNameValuePair("client_secret", "eJSRiU8DNoCiN9Lgeg7PwAoi")); // REST API KEY
		postParams.add(new BasicNameValuePair("redirect_uri", "http://localhost:8888/test/googlelogin")); // 리다이렉트 URI
		postParams.add(new BasicNameValuePair("code", code)); // 로그인 과정중 얻은 code 값
		// http://192.168.100.94:8080
		// http://192.168.100.95:8888/test/kakaologin2
		final HttpClient client = HttpClientBuilder.create().build();
		System.out.println("Ss");
		final HttpPost post = new HttpPost(RequestUrl);
		System.out.println(post);
		JsonNode returnNode = null;

		try {
			post.setEntity(new UrlEncodedFormEntity(postParams));
			final HttpResponse response = client.execute(post);
			final int responseCode = response.getStatusLine().getStatusCode();
			System.out.println(response.getStatusLine().getReasonPhrase());

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
			// clear resources
		}

		System.out.println("ddd");
		// JsonNode token = jwtService.getAccessToken(code);
		String access_token = returnNode.path("access_token").toString();
		String refresh_token = returnNode.path("refresh_token").toString();

		System.out.println("access_token:" + access_token);
		System.out.println("refresh_token:" + refresh_token);
		getKakaoUserInfo(access_token);
		return returnNode;
	}

	@ApiOperation(value = "카카오 api를 통한 코드를 이용하여 로그인", response = List.class)
	@RequestMapping(value = "/getInform", produces = "application/json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public ResponseEntity<String> getInform(String code) throws Exception {
		// TODO Auto-generated method stub
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("code", code);
		parameters.add("client_id", "762237131426-6aasgpj3j773f8p9dsrv6voiccn6o2uq.apps.googleusercontent.com");
		parameters.add("client_secret", "eJSRiU8DNoCiN9Lgeg7PwAoi");
		parameters.add("redirect_uri", "http://localhost:8888/test/googlelogin2");
		parameters.add("grant_type", "authorization_code");

		HttpEntity<MultiValueMap<String, String>> rest_request = new HttpEntity<>(parameters, headers);

		URI uri = URI.create("https://accounts.google.com/o/oauth2/token");

		ResponseEntity<String> rest_reponse;
		rest_reponse = restTemplate.postForEntity(uri, rest_request, String.class);
		String bodys = rest_reponse.getBody();
		System.out.println(bodys);

		return rest_reponse;
	}

	@ApiOperation(value = "카카오 api를 통한 코드를 이용하여 로그인", response = List.class)
	@RequestMapping(value = "/getKakaoUserInfo", produces = "application/json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public JsonNode getKakaoUserInfo(String token) {
		
		System.out.println("ssadas");
		final String RequestUrl = "https://people.googleapis.com/auth/user.emails.read";
		
		final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("access_token", token));
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(RequestUrl);
		// add header
		//post.addHeader("Authorization", "Bearer " + token);

		JsonNode returnNode = null;

		try {
			final HttpResponse response = client.execute(post);
			final int responseCode = response.getStatusLine().getStatusCode();

			System.out.println("\nSending 'POST' request to URL : " + RequestUrl);
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
			// clear resources
		}
		return returnNode;

	}
}
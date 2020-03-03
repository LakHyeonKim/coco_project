package com.ssafy.coco.controller;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
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
import com.google.api.client.auth.oauth.OAuthGetAccessToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleBrowserClientRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.JsonObject;
import com.ssafy.coco.dao.MemberDao;
import com.ssafy.coco.relationvo.Board;
import com.ssafy.coco.service.JwtService;
import com.ssafy.coco.service.MailService;
import com.ssafy.coco.service.MemberService;
import com.ssafy.coco.service.PostService;
import com.ssafy.coco.vo.Alarm;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.Post;
import com.ssafy.coco.vo.Tokens;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@RestController
@RequestMapping("/test")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api(tags = { "test Controller" }, description = "SSAFY HRM resource API (Test)")
public class TestController {

	@Autowired
	private JwtService jwtService;
	@Autowired
	MemberDao memberDao;
	@Autowired
	MemberService memberService;
	@Autowired
	MailService mailService;
	@Autowired
	PostService postService;
	
	@ApiOperation(value = "파일 다운로드(선택)", response = List.class)
	@RequestMapping(value = "/download/{idPost}", method = RequestMethod.GET)
 	public ResponseEntity<Resource> download(@PathVariable long idPost) throws IOException {
		Post post = new Post();
		post.setIdpost(idPost);
		List list = postService.findPost(post);
		if (list.size() == 0)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		post = (Post) list.get(0);
		
		if(post.getFilePath()==null) 
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		System.out.println("원래 패스"+post.getFilePath());
		String tempPath = post.getFilePath().substring(post.getFilePath().indexOf(":8888/")+6);
		System.out.println("path"+tempPath);
		
		Path path = Paths.get("src/main/webapp/"+tempPath);
		String contentType = Files.probeContentType(path);
		System.out.println(contentType);
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE,contentType);
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + path.getFileName().toString());
		Resource resource = new InputStreamResource(Files.newInputStream(path));
		
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "파일 다운로드2", response = List.class)
	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

		File file = new File("src/main/webapp/userfile/", "0_git.png");

		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

		// User-Agent : 어떤 운영체제로 어떤 브라우저를 서버( 홈페이지 )에 접근하는지 확인함
		String header = request.getHeader("User-Agent");
		String fileName;

		if ((header.contains("MSIE")) || (header.contains("Trident")) || (header.contains("Edge"))) {
			// 인터넷 익스플로러 10이하 버전, 11버전, 엣지에서 인코딩
			fileName = URLEncoder.encode("ssd", "UTF-8");
		} else {
			// 나머지 브라우저에서 인코딩
			fileName = new String("ssd".getBytes("UTF-8"), "iso-8859-1");
		}
		// 형식을 모르는 파일첨부용 contentType
		response.setContentType("blob");
		// 다운로드와 다운로드될 파일이름
		response.setHeader("Content-Disposition", "attachment; filename=" + "0_vue.png");
		// 파일복사
		FileCopyUtils.copy(in, response.getOutputStream());
		in.close();
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}

	@ApiOperation(value = "파일 다운로드 (브라우저별 수정 적용)", response = List.class)
	@RequestMapping(value = "/fileDown/{bno}", method = RequestMethod.GET)
	private void fileDown(@PathVariable int bno, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		// FileVO fileVO = mBoardService.fileDetailService(bno);
		// 파일 업로드된 경로
		try {
			//192.185.23.95: WAR 파일 경로 찾기
			String fileUrl = "src/main/webapp/userfile/";
//          fileUrl += "/";
			String savePath = fileUrl;
			String fileName = "0_git.png";

			// 실제 내보낼 파일명
			String oriFileName = "test name";
			InputStream in = null;
			OutputStream os = null;
			File file = null;
			boolean skip = false;
			String client = "";

			// 파일을 읽어 스트림에 담기
			try {
				file = new File(savePath, fileName);
				System.out.println(file.getAbsoluteFile());
				in = new FileInputStream(file);
			} catch (FileNotFoundException fe) {
				skip = true;
			}

			client = request.getHeader("User-Agent");

			// 파일 다운로드 헤더 지정
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Description", "JSP Generated Data");

			if (!skip) {
				// IE
				if (client.indexOf("MSIE") != -1) {
					response.setHeader("Content-Disposition", "attachment; filename=\""
							+ java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
					// IE 11 이상.
				} else if (client.indexOf("Trident") != -1) {
					response.setHeader("Content-Disposition", "attachment; filename=\""
							+ java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ") + "\"");
				} else {
					// 한글 파일명 처리
					response.setHeader("Content-Disposition",
							"attachment; filename=\"" + new String(oriFileName.getBytes("UTF-8"), "ISO8859_1") + "\"");
					response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
				}
				response.setHeader("Content-Length", "" + file.length());
				os = response.getOutputStream();
				byte b[] = new byte[(int) file.length()];
				int leng = 0;
				while ((leng = in.read(b)) > 0) {
					os.write(b, 0, leng);
				}
			} else {
				response.setContentType("text/html;charset=UTF-8");
				System.out.println("<script language='javascript'>alert('파일을 찾을 수 없습니다');history.back();</script>");
			}
			in.close();
			os.close();
		} catch (Exception e) {
			System.out.println("ERROR : " + e.getMessage());
		}

	}

	@ApiOperation(value = "Google Custom Search api 사용", response = List.class)
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity search() throws Exception {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		JSONParser p = new JSONParser();
		String uri = "https://www.googleapis.com/customsearch/v1/siterestrict?key=AIzaSyDwijj_hIBLqxw5__S3dkghvPZbt-_djvk&cx=011639170629408361658:ycrrovtrshs&q=자바&start=10";
		ResponseEntity<String> rest_reponse;
		rest_reponse = restTemplate.getForEntity(uri, String.class);

		String bodys = rest_reponse.getBody();
		System.out.println(bodys);
		JSONObject data = (JSONObject) p.parse(bodys);
		/*
		 * JSONArray items = (JSONArray) data.get("items"); JsonObject item; for(int i =
		 * 0 ; i < items.size(); i++) { Map<String,String> map = (Map<String, String>)
		 * items.get(i);
		 * 
		 * String title = map.get("title"); String link = map.get("link"); String
		 * snippet = ""+map.get("snippet"); Map<String,String> pagemap =
		 * map.get("pagemap"); JsonObject pagemap = (JsonObject) p.parse(tt); String
		 * cse_thumnail= "" +pagemap.get("cse_thumbnail"); String src= ""
		 * +pagemap.get("src");
		 * 
		 * System.out.println(title); System.out.println(link);
		 * System.out.println(snippet); System.out.println(pagemap);
		 * System.out.println(cse_thumnail); System.out.println(src); }
		 */

		System.out.println("Ss");

		return new ResponseEntity(bodys, HttpStatus.OK);

	}

	@ApiOperation(value = "카카오 api를 통한 코드를 이용하여 로그인", response = List.class)
	@RequestMapping(value = "/tttt{code}", produces = "application/json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void setUp(@PathVariable String code) throws IOException {
		HttpTransport httpTransport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();

		// Go to the Google API Console, open your application's
		// credentials page, and copy the client ID and client secret.
		// Then paste them into the following code.
		String clientId = "531269159065-2p985a1qoudvhpdpc441bqvjnvqa17kq.apps.googleusercontent.com";
		String clientSecret = "j_PpDCc4q-HFfuehoM6eeMLX";

		// Or your redirect URL for web based applications.
		String redirectUrl = "http://localhost:8888/test/tttt";
		String scope = "https://www.googleapis.com/auth/contacts.readonly";

		// Step 1: Authorize -->
		String authorizationUrl = new GoogleBrowserClientRequestUrl(clientId, "http://localhost:8888/test/tttt",
				Arrays.asList(scope)).build();
		// Point or redirect your user to the authorizationUrl.
		System.out.println("Go to the following link in your browser:");
		System.out.println(authorizationUrl);

		// Read the authorization code from the standard input stream.
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("What is the authorization code?");
		// String code = in.readLine();
		// End of Step 1 <--

		// Step 2: Exchange -->
		GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(httpTransport, jsonFactory,
				clientId, clientSecret, code, "http://localhost:8888/test/tttt").execute();
		// End of Step 2 <--

		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(jsonFactory).setClientSecrets(clientId, clientSecret).build()
				.setFromTokenResponse(tokenResponse);
		System.out.println("Dddd");
	}

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
		parameters.add("client_id", "531269159065-2p985a1qoudvhpdpc441bqvjnvqa17kq.apps.googleusercontent.com");
		parameters.add("client_secret", "j_PpDCc4q-HFfuehoM6eeMLX");
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
}
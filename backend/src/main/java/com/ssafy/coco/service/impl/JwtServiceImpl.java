package com.ssafy.coco.service.impl;

import io.jsonwebtoken.*;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.coco.dao.MemberDao;
import com.ssafy.coco.service.JwtService;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.Tokens;

@Service
public class JwtServiceImpl implements JwtService{

	private String secretKey = "ssafyisbest";

    private Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

    @Autowired
    MemberDao memberDao;
    @Override
    public String makeJwt(HttpServletRequest res) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + 1000 * 60 * 1);
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Map<String, Object> headerMap = new HashMap<String, Object>();

        headerMap.put("typ","JWT");
        headerMap.put("alg","HS256");

        Map<String, Object> map= new HashMap<String, Object>();

        String name = "김진호";
        String email = "kongkong@naver.com";

        map.put("name", name);
        map.put("email", email);

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
                .setClaims(map)
                .setExpiration(expireTime)
                .signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }
    
    @Override
    public String makeJwt(String idmember,int time) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + 1000 * 60 * 60 );
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Map<String, Object> headerMap = new HashMap<String, Object>();

        headerMap.put("typ","JWT");
        headerMap.put("alg","HS256");

        Map<String, Object> map= new HashMap<String, Object>();

        map.put("idmember", idmember);

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
                .setClaims(map)
                .setExpiration(expireTime)
                .signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }
    
    @Override
    public String makeJwt(String id, String pwd) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + 1000 * 60 * 1);
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Map<String, Object> headerMap = new HashMap<String, Object>();

        headerMap.put("typ","JWT");
        headerMap.put("alg","HS256");

        Map<String, Object> map= new HashMap<String, Object>();

        map.put("id", id);
        map.put("password", pwd);

        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
                .setClaims(map)
                .setExpiration(expireTime)
                .signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }
    
    @Override
    public boolean checkJwt(String jwt) throws Exception {
        try {
        	System.out.println("start");
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                    .parseClaimsJws(jwt).getBody(); // 정상 수행된다면 해당 토큰은 정상토큰
            System.out.println("Dd"+claims);
            logger.info("expireTime :" + claims.getExpiration());
            logger.info("name :" + claims.get("name"));
            logger.info("Email :" + claims.get("email"));
            return true;
        } catch (ExpiredJwtException exception) {
            logger.info("토큰 만료");
            return false;
        } catch (JwtException exception) {
            logger.info("토큰 변조");
            return false;
        }
    }

	@Override
	public HttpStatus checkJwt2(String jwt) throws Exception {
		try {
        	System.out.println("start");
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                    .parseClaimsJws(jwt).getBody(); // 정상 수행된다면 해당 토큰은 정상토큰
            System.out.println("Dd"+claims);
            logger.info("expireTime :" + claims.getExpiration());
            logger.info("idmember :" + claims.get("idmember"));
            return HttpStatus.ACCEPTED;
        } catch (ExpiredJwtException exception) {
            logger.info("토큰 만료");
            return HttpStatus.UNAUTHORIZED;
        } catch (JwtException exception) {
            logger.info("토큰 변조");
            return HttpStatus.UNAUTHORIZED;
        }
	}

	@Override
	public boolean getAccessTokenByRefreshToken(String tt) throws Exception {
		// TODO Auto-generated method stub
		return false;
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
	
	@Override
	public Tokens login(JSONObject input) throws Exception {
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
			
			String refreshToken = makeJwt(""+System.currentTimeMillis(),24*14);//나중에 뭘로 할지 찾기
			m.setRefreshToken(refreshToken);
			memberDao.updateRefreshToken(m);
			Tokens tokens = new Tokens(makeJwt(""+m.getIdmember(),1),refreshToken);
			return tokens;
		}
		else
		{
			return null;
		}
	}
	

	

}

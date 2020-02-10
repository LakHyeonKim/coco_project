package com.ssafy.coco.service.impl;

import java.util.Random;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Service;

import com.ssafy.coco.service.MailService;
@Service
public class MailServiceImpl implements MailService{

	@Override
	public String createKey() throws Exception {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();
		
		for (int i = 0 ; i < 10 ; i++)
		{
			int index = rnd.nextInt(3);
			
			switch(index)
			{
			case 0 :
				key.append((char)(int)(rnd.nextInt(26)+97));
				break;
			case 1 :
				key.append((char)(int)(rnd.nextInt(26)+65));
			case 2 :
				key.append(rnd.nextInt(10));
				break;
			}
		}
		return key.toString();
	}

	@Override
	public void sendMail(String email, String subject, String msg) throws Exception {
		String charset = "utf-8";
		String hostSMTP = "smtp.naver.com";
		String hostSMTPid = "a13975";
		String hostSMTPpwd = "4856aa!!";
		
		String fromEmail = "a13975@naver.com";
		String fromName = "이모찌";
		
		HtmlEmail mail = new HtmlEmail();
		mail.setDebug(true);
		mail.setCharset(charset);
		mail.setSSLOnConnect(true);
		mail.setHostName(hostSMTP);
		mail.setSmtpPort(993);
		mail.setAuthentication(hostSMTPid, hostSMTPpwd);
		mail.setStartTLSEnabled(true);
		mail.addTo(email);
		mail.setFrom(fromEmail,fromName,charset);
		mail.setSubject(subject);
		mail.setHtmlMsg(msg);
		mail.send();
	}
	public String findPwd(String mem_email, String mem_id, String inputCode)
	{
		try {
			String subject = "[SEE-SAW] 임시 비밀번호 발급 안내";
			String msg = "";
			String keyCode = createKey();
			msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
			msg += "<h3 style='color:blue;'><strong>"+mem_id;
			msg += "님</strong>의 임시 비밀번호입니다. 로그인 후 비밀번호를 변경하세요</h3>";
			msg += "<p> 임시 비밀번호 : <strong>"+ keyCode +"</strong></p></div>";
			
			sendMail(mem_email, subject, msg);
			return keyCode;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}

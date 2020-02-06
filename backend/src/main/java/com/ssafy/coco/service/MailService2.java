package com.ssafy.coco.service;

public interface MailService2 {
	public String createKey() throws Exception;
	public void sendMail(String email, String subject, String msg) throws Exception;
	public void findPwd(String mem_email, String mem_id, String inputCode);
	
}

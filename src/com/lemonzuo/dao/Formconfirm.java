package com.lemonzuo.dao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lemonzuo.model.Form;

public class Formconfirm {

	public String confirm(Form from) {
		String message = null;
		if("".equals(from.userName)||"".equals(from.userPass)||"".equals(from.name)
				||"".equals(from.userMobile)||"".equals(from.userAdress)
				||"".equals(from.userEmail)) {
			message = "�û��������롢�������绰���롢��ַ���ʼ�Ϊ������";
		} else if(!matchPass(from.userPass)) {
			message = "�����ʽΪ��6-20λ��ĸ���ַ���";
		} else if(!matchAge(from.userAge)) {
			message = "�����д��ڷ�����";
		} else if(from.userMobile.length() != 11 || !matchNumble(from.userMobile) ) {
			message = "�ֻ��Ÿ�ʽ����";
		} else if(!matchEmail(from.userEmail)) {
			message = "�����ʽ����";
		} else {
			message = "ע��ɹ�";
		}
		return message;
	}
	
	//������֤
	public boolean matchPass(String pass) {
		boolean flag = true;
		String pattern = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\\\\\[\\\\\\\\].<>/?~��@#��%����&*��������+|{}��������������'��������]){6,20}$";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(pass);
		flag = m.matches();
		return flag;
	}
	
	//�ֻ�������֤
	public boolean matchNumble(String number) {
		boolean flag = true;
		String pattern = "0?(13|14|15|17|18|19)[0-9]{9}";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(number);
		flag = m.matches();
		return flag;
	}
	
	//������֤
	public boolean matchAge(String age) {
		boolean flag = true;
		String pattern = "[1-9]\\d*";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(age);
		flag = m.matches();
		return flag;
	}
	
	//������֤
	public boolean matchEmail(String age) {
		boolean flag = true;
		String pattern = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(age);
		flag = m.matches();
		return flag;
	}
}

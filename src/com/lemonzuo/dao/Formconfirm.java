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
			message = "用户名、密码、姓名、电话号码、地址、邮件为必填项";
		} else if(!matchPass(from.userPass)) {
			message = "密码格式为：6-20位字母数字符号";
		} else if(!matchAge(from.userAge)) {
			message = "年龄中存在非数字";
		} else if(from.userMobile.length() != 11 || !matchNumble(from.userMobile) ) {
			message = "手机号格式错误";
		} else if(!matchEmail(from.userEmail)) {
			message = "邮箱格式错误";
		} else {
			message = "注册成功";
		}
		return message;
	}
	
	//密码验证
	public boolean matchPass(String pass) {
		boolean flag = true;
		String pattern = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\\\\\[\\\\\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“'。，、？]){6,20}$";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(pass);
		flag = m.matches();
		return flag;
	}
	
	//手机号码验证
	public boolean matchNumble(String number) {
		boolean flag = true;
		String pattern = "0?(13|14|15|17|18|19)[0-9]{9}";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(number);
		flag = m.matches();
		return flag;
	}
	
	//年龄验证
	public boolean matchAge(String age) {
		boolean flag = true;
		String pattern = "[1-9]\\d*";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(age);
		flag = m.matches();
		return flag;
	}
	
	//邮箱验证
	public boolean matchEmail(String age) {
		boolean flag = true;
		String pattern = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(age);
		flag = m.matches();
		return flag;
	}
}

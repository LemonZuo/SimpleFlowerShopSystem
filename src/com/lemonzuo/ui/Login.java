package com.lemonzuo.ui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.lemonzuo.dao.UserDao;

@SuppressWarnings({ "serial" })
public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textUserName;
	private JPasswordField textPass;
	public static String USER_NAME;
	private String userPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					Login frame = new Login();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
				
		setTitle("\u767B\u5F55");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//窗体可见
		setVisible(true);
		//禁用缩放
		setResizable(false);
		//窗体居中
		this.setLocationRelativeTo(null);
		
		//标题
		JLabel labTitle = new JLabel("\u767B  \u5F55");
		labTitle.setFont(new Font("微软雅黑", Font.PLAIN, 30));
		labTitle.setBounds(173, 10, 87, 47);
		contentPane.add(labTitle);
		
		//用户名标签
		JLabel labUserName = new JLabel("\u7528\u6237\u540D\uFF1A");
		labUserName.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		labUserName.setBounds(66, 82, 64, 28);
		contentPane.add(labUserName);
		
		//密码标签
		JLabel labUserPass = new JLabel("\u5BC6  \u7801\uFF1A");
		labUserPass.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		labUserPass.setBounds(66, 137, 64, 28);
		contentPane.add(labUserPass);
		
		//用户名填写框
		textUserName = new JTextField();
		textUserName.setBounds(171, 87, 165, 21);
		//内边距
		textUserName.setMargin(new Insets(0,5,0,0));
		contentPane.add(textUserName);
		textUserName.setColumns(10);
		
		//密码填写框
		textPass = new JPasswordField();
		textPass.setBounds(173, 142, 163, 21);
		//内边距
		textPass.setMargin(new Insets(0,5,0,0));
		contentPane.add(textPass);
		
		//用户登录
		JButton btnUserLogin = new JButton("\u7528\u6237\u767B\u5F55");
		btnUserLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				USER_NAME = textUserName.getText();
				userPass = new String(textPass.getPassword());
				UserDao userDao = new UserDao();
				//1、判断是否填写用户名及密码
				if("".equals(USER_NAME) || "".equals(userPass) ) {
					JOptionPane.showMessageDialog(Login.this, "请填写用户名及密码", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else if (userDao.userExist(USER_NAME)){ //2、判断用户是否存在
					//用户账号及密码的验证
					if(userDao.userLogin(USER_NAME, userPass)) {
						JOptionPane.showMessageDialog(Login.this, "登录成功", "提示", JOptionPane.INFORMATION_MESSAGE);
						Login.this.dispose();;
						new UserIndex();
					} else {
						JOptionPane.showMessageDialog(Login.this, "登录失败：账号密码不匹配", "警告", JOptionPane.WARNING_MESSAGE);
					}
					
				} else {
					JOptionPane.showMessageDialog(Login.this, "用户名不存在", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnUserLogin.setBounds(37, 209, 106, 36);
		contentPane.add(btnUserLogin);
		
		//用户注册
		JButton btnUserRegist = new JButton("\u7528\u6237\u6CE8\u518C");
		btnUserRegist.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent arg0) {
				//跳转注册页面
				Login.this.dispose();;
				new UserRegist();
			}
		});
		btnUserRegist.setBounds(173, 209, 93, 36);
		contentPane.add(btnUserRegist);
		
		//管理员登录
		JButton btnAdminLogin = new JButton("\u7BA1\u7406\u5458\u767B\u5F55");
		btnAdminLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				USER_NAME = textUserName.getText();
				userPass = new String(textPass.getPassword());
				UserDao userDao = new UserDao();
				//1、判断是否填写用户名及密码
				if("".equals(USER_NAME) || "".equals(userPass) ) {
					JOptionPane.showMessageDialog(Login.this, "请填写用户名及密码", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else if (userDao.adminExist(USER_NAME)){ //2、判断用户是否存在
					//用户账号及密码的验证
					if(userDao.adminLogin(USER_NAME, userPass)) {
						JOptionPane.showMessageDialog(Login.this, "登录成功", "提示", JOptionPane.INFORMATION_MESSAGE);
						//跳转管理员主界面
						new AdminMain();
						//关闭当前窗口
						Login.this.dispose();
					} else {
						JOptionPane.showMessageDialog(Login.this, "登录失败：账号密码不匹配", "警告", JOptionPane.WARNING_MESSAGE);
					}
					
				} else {
					JOptionPane.showMessageDialog(Login.this, "用户名不存在", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnAdminLogin.setBounds(300, 209, 101, 36);
		contentPane.add(btnAdminLogin);
	}
	
	
	
}

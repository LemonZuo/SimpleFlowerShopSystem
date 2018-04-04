package com.lemonzuo.ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.lemonzuo.dao.Formconfirm;
import com.lemonzuo.dao.UserDao;
import com.lemonzuo.model.Form;

@SuppressWarnings("serial")
public class UserRegist extends JFrame {

	private JPanel contentPane;
	private JTextField textUserName;
	private JTextField textName;
	private JPasswordField textUserPass;
	private JRadioButton rbMan;
	private JTextField textAge;
	private JTextField textMobile;
	private JTextField textAdress;
	private JTextField textEmail;

	/**
	 * Create the frame.
	 */
	public UserRegist() {		
		setTitle("\u7528\u6237\u6CE8\u518C");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//窗体可见
		setVisible(true);
		//禁用缩放
		setResizable(false);
		//窗体居中
		setLocationRelativeTo(null);
		
		//标题
		JLabel labTitle = new JLabel("\u7528 \u6237 \u6CE8 \u518C");
		labTitle.setFont(new Font("微软雅黑 Light", Font.PLAIN, 30));
		labTitle.setBounds(144, 10, 156, 53);
		contentPane.add(labTitle);
		
		//用户名标签
		JLabel labUserName = new JLabel("\u7528\u6237\u540D\uFF1A");
		labUserName.setFont(new Font("宋体", Font.PLAIN, 16));
		labUserName.setBounds(50, 77, 64, 26);
		contentPane.add(labUserName);
		
		//密码标签
		JLabel labUserPass = new JLabel("\u5BC6  \u7801\uFF1A");
		labUserPass.setFont(new Font("宋体", Font.PLAIN, 16));
		labUserPass.setBounds(50, 113, 64, 26);
		contentPane.add(labUserPass);
		
		//姓名标签
		JLabel labName = new JLabel("\u59D3  \u540D\uFF1A");
		labName.setFont(new Font("宋体", Font.PLAIN, 16));
		labName.setBounds(50, 149, 64, 26);
		contentPane.add(labName);
		
		//性别标签
		JLabel labSex = new JLabel("\u6027  \u522B\uFF1A");
		labSex.setFont(new Font("宋体", Font.PLAIN, 16));
		labSex.setBounds(50, 181, 64, 26);
		contentPane.add(labSex);
		
		//年龄标签
		JLabel labAge = new JLabel("\u5E74  \u9F84\uFF1A");
		labAge.setFont(new Font("宋体", Font.PLAIN, 16));
		labAge.setBounds(50, 214, 64, 26);
		contentPane.add(labAge);
		
		//手机标签
		JLabel labMobile = new JLabel("\u624B  \u673A\uFF1A");
		labMobile.setFont(new Font("宋体", Font.PLAIN, 16));
		labMobile.setBounds(50, 247, 64, 26);
		contentPane.add(labMobile);
		
		//地址标签
		JLabel labAdress = new JLabel("\u5730  \u5740\uFF1A");
		labAdress.setFont(new Font("宋体", Font.PLAIN, 16));
		labAdress.setBounds(50, 275, 64, 26);
		contentPane.add(labAdress);
		
		//邮箱标签
		JLabel labEmail = new JLabel("\u90AE  \u7BB1\uFF1A");
		labEmail.setFont(new Font("宋体", Font.PLAIN, 16));
		labEmail.setBounds(50, 303, 64, 26);
		contentPane.add(labEmail);
		
		//用户名输入框
		textUserName = new JTextField();
		textUserName.setBounds(144, 81, 243, 21);
		contentPane.add(textUserName);
		textUserName.setColumns(10);
		
		//姓名输入框
		textName = new JTextField();
		textName.setColumns(10);
		textName.setBounds(144, 153, 243, 21);
		contentPane.add(textName);
		
		//密码框
		textUserPass = new JPasswordField();
		textUserPass.setBounds(144, 117, 243, 21);
		contentPane.add(textUserPass);
		
		//性别男
		rbMan = new JRadioButton("\u7537");
		rbMan.setBounds(144, 184, 48, 23);
		contentPane.add(rbMan);
		
		//性别女
		JRadioButton rbWoman = new JRadioButton("\u5973");
		rbWoman.setBounds(205, 184, 48, 23);
		contentPane.add(rbWoman);
		
		//性别按钮组
		ButtonGroup bgSex = new ButtonGroup();
		bgSex.add(rbWoman);
		bgSex.add(rbMan);
		
		//年龄框
		textAge = new JTextField();
		textAge.setColumns(10);
		textAge.setBounds(144, 218, 243, 21);
		contentPane.add(textAge);
		
		//手机号码框
		textMobile = new JTextField();
		textMobile.setColumns(10);
		textMobile.setBounds(144, 251, 243, 21);
		contentPane.add(textMobile);
		
		//地址框
		textAdress = new JTextField();
		textAdress.setColumns(10);
		textAdress.setBounds(144, 280, 243, 21);
		contentPane.add(textAdress);
		
		//邮箱框
		textEmail = new JTextField();
		textEmail.setColumns(10);
		textEmail.setBounds(144, 307, 243, 21);
		contentPane.add(textEmail);
		
		JButton btnRegist = new JButton("\u9A6C\u4E0A\u6CE8\u518C");
		btnRegist.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				//获取表单信息
				String userName = textUserName.getText();
				String userPass = new String(textUserPass.getPassword());
				String name = textName.getText();
				int sex = rbMan.isSelected()?1:2;
				String userAge = textAge.getText();
				String userMobile = textMobile.getText();
				String userAdress = textAdress.getText();
				String userEmail = textEmail.getText();
				//1、验证用户名是否存在
				UserDao userdao = new UserDao();
				if(userdao.userExist(userName)) {
					JOptionPane.showMessageDialog(UserRegist.this, "用户名已存在", "提示", JOptionPane.WARNING_MESSAGE);
				} else {
					//2、表单信息验证
					//封装成类
					Form form = new Form(userName,userPass,name,sex,userAge,userMobile,userAdress,
							userEmail);
					Formconfirm formconfirm = new Formconfirm();
					String message = formconfirm.confirm(form);
					if(message == "注册成功") {
						if(userdao.addUser(form)) {
							JOptionPane.showMessageDialog(UserRegist.this, message, "注册成功", JOptionPane.INFORMATION_MESSAGE);
							UserRegist.this.dispose();
							//跳转登录界面
							new Login().main(null);
						} else {
							JOptionPane.showMessageDialog(UserRegist.this, "数据提交失败", "注册失败", JOptionPane.WARNING_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(UserRegist.this, message, "提示", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		btnRegist.setBounds(174, 351, 93, 36);
		contentPane.add(btnRegist);
		
		JButton btnLogin = new JButton("\u7528\u6237\u767B\u5F55");
		btnLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent e) {
				//跳转登录界面
				UserRegist.this.dispose();;
				new Login();
			}
		});
		btnLogin.setBounds(50, 351, 93, 36);
		contentPane.add(btnLogin);
		
		JButton btnReset = new JButton("\u91CD\u7F6E");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear();
			}
		});
		btnReset.setBounds(294, 351, 93, 36);
		contentPane.add(btnReset);
	}
	
	/**
	 * 重置所有信息
	 */
	public void clear() {
		textUserName.setText("");
		textUserPass.setText("");
		textName.setText("");
		rbMan.setSelected(true);
		textAge.setText("");
		textMobile.setText("");
		textAdress.setText("");
		textEmail.setText("");
	}
}

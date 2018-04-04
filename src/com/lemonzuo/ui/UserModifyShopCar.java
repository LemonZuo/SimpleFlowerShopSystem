package com.lemonzuo.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.lemonzuo.dao.UserDao;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class UserModifyShopCar extends JFrame {

	private JPanel contentPane;
	private JTextField textID;
	private JTextField textFlowerName;
	private JTextField textFlowerNum;
	private String ID;
	private String flowerName;
	private String flowerNum;
	public static Vector<String> DATA;
	public static String USER_NAME = UserShopCar.USER_NAME;

	/**
	 * Create the frame.
	 */
	public UserModifyShopCar() {
		//监听本窗口关闭
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				//跳转购物车
				new UserShopCar();
			}
		});
		

		
		//窗体
		//窗体标题
		setTitle("\u4FEE\u6539");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 400);
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
		JLabel labTitle = new JLabel("\u4FE1\u606F\u4FEE\u6539");
		labTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labTitle.setFont(new Font("宋体", Font.PLAIN, 20));
		labTitle.setBounds(10, 29, 414, 39);
		contentPane.add(labTitle);
		
		//ID标签
		JLabel labID = new JLabel("\u5E8F\u53F7\uFF1A");
		labID.setHorizontalAlignment(SwingConstants.CENTER);
		labID.setFont(new Font("宋体", Font.PLAIN, 14));
		labID.setBounds(57, 91, 61, 39);
		contentPane.add(labID);
		
		//花名标签
		JLabel labFlowerName = new JLabel("\u9C9C\u82B1\uFF1A");
		labFlowerName.setHorizontalAlignment(SwingConstants.CENTER);
		labFlowerName.setFont(new Font("宋体", Font.PLAIN, 14));
		labFlowerName.setBounds(57, 140, 61, 39);
		contentPane.add(labFlowerName);
		
		//数目标签
		JLabel labFlowerNum = new JLabel("\u6570\u91CF\uFF1A");
		labFlowerNum.setHorizontalAlignment(SwingConstants.CENTER);
		labFlowerNum.setFont(new Font("宋体", Font.PLAIN, 14));
		labFlowerNum.setBounds(57, 189, 61, 39);
		contentPane.add(labFlowerNum);
		
		//ID显示框
		textID = new JTextField();
		textID.setBounds(130, 96, 205, 30);
		contentPane.add(textID);
		textID.setColumns(10);
		//设置内边距
		textID.setMargin(new Insets(0,5,0,0));
		//禁用编辑
		textID.setEditable(false);
		textID.setBackground(Color.white);
		
		//花名显示框
		textFlowerName = new JTextField();
		textFlowerName.setColumns(10);
		textFlowerName.setBounds(130, 149, 205, 30);
		textFlowerName.setMargin(new Insets(0,5,0,0));
		textFlowerName.setEditable(false);
		textFlowerName.setBackground(Color.WHITE);
		contentPane.add(textFlowerName);
		
		//数量填写框
		textFlowerNum = new JTextField();
		textFlowerNum.setColumns(10);
		textFlowerNum.setBounds(130, 198, 205, 30);
		textFlowerNum.setMargin(new Insets(0,5,0,0));
		contentPane.add(textFlowerNum);
		
		//初始化数据
		setData();
		
		//重置按钮
		JButton btnReset = new JButton("\u91CD\u7F6E");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFlowerNum.setText(flowerNum);
			}
		});
		btnReset.setBounds(264, 256, 99, 39);
		contentPane.add(btnReset);
		
		//提交按钮
		JButton btnSubmit = new JButton("\u786E\u8BA4");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//实现修改
				modify();
			}
		});
		btnSubmit.setBounds(88, 256, 99, 39);
		contentPane.add(btnSubmit);
		
	}
	
	//修改方法
	public void modify() {
		//获取所需数据
		String newFlowerNum = textFlowerNum.getText();
		UserDao userDao = new UserDao();
		String time = userDao.getTime();
		//修改提示
		//userOption 接收是否进行修改
		int userOption = JOptionPane.showConfirmDialog(UserModifyShopCar.this, "是否修改", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (userOption == 0) {
			//判断输入的是否为数字
			if(userDao.matchNum(newFlowerNum)) {
				//与数据库进行操作
				if( Integer.parseInt(newFlowerNum) < Integer.parseInt(userDao.getFlowerNum(flowerName))) { //预修改数量小于库存量
					//执行更新操作
					if( userDao.userModifyShopCarFlowerNum(USER_NAME, flowerName, newFlowerNum,time) ) {
						JOptionPane.showMessageDialog(UserModifyShopCar.this, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
						//关闭当前窗体
						UserModifyShopCar.this.dispose();
					} else {
						//数据库异常
						JOptionPane.showMessageDialog(UserModifyShopCar.this, "修改失败", "提示", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					//库存不足提示
					JOptionPane.showMessageDialog(UserModifyShopCar.this, "库存不足无法修改", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				//数字输入框出现非法字符
				JOptionPane.showMessageDialog(UserModifyShopCar.this, "请输入合法数字", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	public void setData() {
		//获取预修改的数据
		DATA = UserShopCar.DATA;
		//用户未选择鲜花
		if (DATA.isEmpty()) {
			
		} else {
			ID = DATA.get(0);
			flowerName = DATA.get(1);
			flowerNum = DATA.get(2);
			
			//将信息输出到窗体
			textID.setText(ID);
			textFlowerName.setText(flowerName);
			textFlowerNum.setText(flowerNum);
		}
		
	}
}

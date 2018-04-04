package com.lemonzuo.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.lemonzuo.dao.UserDao;

@SuppressWarnings("serial")
public class SetFlowerToShopCar extends JFrame {

	private JPanel contentPane;
	private JTextField textID;
	private JTextField textName;
	private JTextField textNum;
	public static String FLOWER_NAME;
	public static String FLOWER_ID;
	public static String FLOWER_NUM;
	public static String USER_NAME = UserIndex.USER_NAME;

	/**
	 * Create the frame.
	 */
	public SetFlowerToShopCar() {

		//窗体
		setTitle("\u6DFB\u52A0\u8D2D\u7269\u8F66");
		//默认关闭方式
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 403, 320);
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
		
		//标题标签
		JLabel labTitle = new JLabel("\u6DFB\u52A0\u5230\u8D2D\u7269\u8F66");
		labTitle.setForeground(new Color(255, 51, 153));
		labTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labTitle.setFont(new Font("宋体", Font.PLAIN, 18));
		labTitle.setBounds(156, 10, 108, 39);
		contentPane.add(labTitle);
		
		//ID标签
		JLabel lbFlowerId = new JLabel("\u9C9C\u82B1ID:");
		lbFlowerId.setHorizontalAlignment(SwingConstants.CENTER);
		lbFlowerId.setFont(new Font("宋体", Font.PLAIN, 16));
		lbFlowerId.setBounds(66, 53, 65, 26);
		contentPane.add(lbFlowerId);
		
		//花名标签
		JLabel labFlowerName = new JLabel("\u9C9C\u82B1\u540D\u5B57");
		labFlowerName.setHorizontalAlignment(SwingConstants.CENTER);
		labFlowerName.setFont(new Font("宋体", Font.PLAIN, 16));
		labFlowerName.setBounds(66, 89, 65, 26);
		contentPane.add(labFlowerName);
		
		//数量标签
		JLabel labNum = new JLabel("\u6DFB\u52A0\u6570\u91CF");
		labNum.setHorizontalAlignment(SwingConstants.CENTER);
		labNum.setFont(new Font("宋体", Font.PLAIN, 16));
		labNum.setBounds(66, 137, 65, 26);
		contentPane.add(labNum);
		
		//提交按钮
		JButton btnSubmit = new JButton("\u786E\u8BA4\u6DFB\u52A0");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//获取输入数目
				String num = textNum.getText();
				UserDao userDao = new UserDao();
				//获取花名
				FLOWER_NAME = UserIndex.FLOWER_NAME;
				//获取花的数目
				FLOWER_NUM = UserIndex.FLOWER_NUM;
				String time = userDao.getTime();
				//获取时间
				if("".equals(num) || num == null) {
					JOptionPane.showMessageDialog(SetFlowerToShopCar.this, "请填写数量", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else if(userDao.matchNum(num)) {
					//预添加数量与数据库库存进行比对
					if(Integer.parseInt(FLOWER_NUM) >= Integer.parseInt(num)) {
						//更新数据
						boolean flag = userDao.setToShopCar(USER_NAME, FLOWER_NAME, num, time);
						if( flag ) {
							JOptionPane.showMessageDialog(SetFlowerToShopCar.this, "添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
							SetFlowerToShopCar.this.dispose();
						} else {
							JOptionPane.showMessageDialog(SetFlowerToShopCar.this, "操作失败", "提示", JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						//添加数目大于库存量
						JOptionPane.showMessageDialog(SetFlowerToShopCar.this, "库存不足无法添加", "提示", JOptionPane.INFORMATION_MESSAGE);
					}
					
				} else {
					//其他字符
					JOptionPane.showMessageDialog(SetFlowerToShopCar.this, "数量不能为非数字", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		btnSubmit.setBounds(67, 192, 93, 39);
		contentPane.add(btnSubmit);
		
		//重置按钮
		JButton btnReSet = new JButton("\u91CD\u7F6E\u6570\u91CF");
		btnReSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textNum.setText("");
			}
		});
		btnReSet.setBounds(216, 192, 93, 39);
		contentPane.add(btnReSet);
		
		//设置初始数据
		FLOWER_NAME = UserIndex.FLOWER_NAME;
		FLOWER_ID = UserIndex.FLOWER_ID;
		
		//ID显示框
		textID = new JTextField();
		textID.setBounds(156, 57, 153, 21);
		textID.setMargin(new Insets(0,5,0,0));
		if(FLOWER_ID != null) {
			textID.setText(FLOWER_ID);
		} else {
			textID.setText("");
		}
		textID.setBackground(Color.WHITE);
		textID.setEditable(false);
		contentPane.add(textID);
		textID.setColumns(10);
		
		//花名显示框
		textName = new JTextField();
		textName.setColumns(10);
		textName.setBounds(156, 93, 153, 21);
		contentPane.add(textName);
		//设置内边距
		textName.setMargin(new Insets(0,5,0,0));
		if(FLOWER_NAME != null) {
			textName.setText(FLOWER_NAME);
		} else {
			textName.setText("");
		}
		textName.setBackground(Color.white);
		//禁止编辑
		textName.setEditable(false);
		
		textNum = new JTextField();
		textNum.setColumns(10);
		//添加内边距
		textNum.setMargin(new Insets(0,5,0,0));
		textNum.setText("");
		textNum.setBounds(156, 141, 153, 21);
		contentPane.add(textNum);
	}
}

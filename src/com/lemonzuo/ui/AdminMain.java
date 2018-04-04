package com.lemonzuo.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings({ "serial", "unused" })
//TODO AdminMain
public class AdminMain extends JFrame {

	private JPanel contentPane;
	public static String USER_NAME = Login.USER_NAME;


	/**
	 * Create the frame.
	 */
	public AdminMain() {
		

		//窗体
		
		setTitle("漫步经心后台管理页面");
		//窗口关闭方式
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 300);
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
		JLabel labTitle = new JLabel("漫步经心店家管理");
		labTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labTitle.setForeground(new Color(102, 204, 255));
		labTitle.setFont(new Font("宋体", Font.PLAIN, 18));
		labTitle.setBounds(10, 22, 264, 31);
		contentPane.add(labTitle);
		
		//订单管理
		JButton btnOrderControl = new JButton("订单管理");
		btnOrderControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO 订单管理
				new AdminOrderControl();
			}
		});
		btnOrderControl.setBounds(89, 73, 109, 41);
		contentPane.add(btnOrderControl);
		
		//鲜花管理
		JButton btnGoodsControl = new JButton("鲜花管理");
		btnGoodsControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//页面跳转鲜花管理页面
				new AdminFlowerControl();
				//关闭当前窗口
//				AdminMain.this.dispose();
			}
		});
		btnGoodsControl.setBounds(89, 124, 109, 41);
		contentPane.add(btnGoodsControl);
		
		//退出系统
		JButton btnExit = new JButton("退出系统");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AdminMain.this.dispose();
			}
		});
		btnExit.setBounds(89, 186, 109, 41);
		contentPane.add(btnExit);
	}
}

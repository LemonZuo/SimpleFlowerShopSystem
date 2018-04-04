package com.lemonzuo.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.lemonzuo.dao.UserDao;

@SuppressWarnings({ "serial", "unused" })
public class AdminAddFlower extends JFrame {

	private JPanel contentPane;
	private JTextField textFlowerName;
	private JTextField textFlowerPrice;
	private JTextField textFlowerCount;

	/**
	 * Create the frame.
	 */
	public AdminAddFlower() {
		//窗体关闭监听
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				//返回上一页 鲜花管理页面
				new AdminFlowerControl();
			}
		});
			
		//窗体
		setTitle("\u6DFB\u52A0\u9C9C\u82B1");
		//关闭方式
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 353);
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
		JLabel labTitle = new JLabel("\u6DFB\u52A0\u9C9C\u82B1");
		labTitle.setForeground(new Color(102, 153, 255));
		labTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labTitle.setFont(new Font("宋体", Font.PLAIN, 18));
		labTitle.setBounds(10, 10, 414, 30);
		contentPane.add(labTitle);
		
		//添加花名标签
		JLabel labFlowerName = new JLabel("\u9C9C\u82B1:");
		labFlowerName.setFont(new Font("宋体", Font.PLAIN, 16));
		labFlowerName.setBounds(59, 65, 64, 30);
		contentPane.add(labFlowerName);
		
		//价格标签
		JLabel labPrice = new JLabel("\u4EF7\u683C:");
		labPrice.setFont(new Font("宋体", Font.PLAIN, 16));
		labPrice.setBounds(59, 123, 64, 30);
		contentPane.add(labPrice);
		
		//库存标签
		JLabel labCount = new JLabel("\u5E93\u5B58:");
		labCount.setFont(new Font("宋体", Font.PLAIN, 16));
		labCount.setBounds(59, 176, 64, 30);
		contentPane.add(labCount);
		
		//花名输入框
		textFlowerName = new JTextField();
		textFlowerName.setBounds(121, 62, 244, 30);
		textFlowerName.setMargin(new Insets(0,5,0,0));
		contentPane.add(textFlowerName);
		textFlowerName.setColumns(10);
		
		//价格输入框
		textFlowerPrice = new JTextField();
		textFlowerPrice.setColumns(10);
		textFlowerPrice.setBounds(121, 123, 244, 30);
		textFlowerPrice.setMargin(new Insets(0,5,0,0));
		contentPane.add(textFlowerPrice);
		
		//库存输入框
		textFlowerCount = new JTextField();
		textFlowerCount.setColumns(10);
		textFlowerCount.setBounds(121, 176, 244, 30);
		textFlowerCount.setMargin(new Insets(0,5,0,0));
		contentPane.add(textFlowerCount);
		
		//添加按钮
		JButton btnAdd = new JButton("\u6DFB\u52A0");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//执行添加
				add();
			}
		});
		btnAdd.setBounds(59, 244, 93, 30);
		contentPane.add(btnAdd);
		
		//取消按钮
		JButton btnCancel = new JButton("\u53D6\u6D88");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//关闭本窗口
				AdminAddFlower.this.dispose();
			}
		});
		btnCancel.setBounds(272, 244, 93, 30);
		contentPane.add(btnCancel);
		
		//重置按钮
		JButton btnReSET = new JButton("\u91CD\u7F6E");
		btnReSET.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//重置数据
				textFlowerName.setText("");
				textFlowerPrice.setText("");
				textFlowerCount.setText("");
			}
		});
		btnReSET.setBounds(162, 244, 93, 30);
		contentPane.add(btnReSET);
	}
	
	//新增鲜花
	public void add() {
		//获取数据
		String flowerName = textFlowerName.getText();
		String flowerPrice = textFlowerPrice.getText();
		String flowerCount = textFlowerCount.getText();
		UserDao userDao = new UserDao();
		if( flowerName.length() == 0 || flowerPrice.length() == 0 || flowerCount.length() == 0 ) { //信息填写状态
			JOptionPane.showMessageDialog(AdminAddFlower.this, "请填写完整数据", "提示", JOptionPane.INFORMATION_MESSAGE);
		} else if ( !userDao.matchNum(flowerPrice) ) { //价格不合法
			JOptionPane.showMessageDialog(AdminAddFlower.this, "价格非法", "提示", JOptionPane.INFORMATION_MESSAGE);
		} else if ( !userDao.matchNum(flowerCount) ) { //库存不合法
			JOptionPane.showMessageDialog(AdminAddFlower.this, "库存非法", "提示", JOptionPane.INFORMATION_MESSAGE);
		} else if ( JOptionPane.showConfirmDialog(AdminAddFlower.this, "确认添加","提示",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) != 0 ) {
			//确认添加
		} else if ( userDao.FlowerExist(flowerName) ) { //已存在该鲜花
			JOptionPane.showMessageDialog(AdminAddFlower.this, "该鲜花已存在", "提示", JOptionPane.INFORMATION_MESSAGE);
		} else if (userDao.AdminAddFlower(flowerName, flowerCount, flowerPrice)) {	//执行添加
			JOptionPane.showMessageDialog(AdminAddFlower.this, "添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
			//关闭本窗口
			AdminAddFlower.this.dispose();
		} else {
			//数据库异常
			JOptionPane.showMessageDialog(AdminAddFlower.this, "操作失败", "提示", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
}

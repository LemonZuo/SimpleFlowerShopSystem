package com.lemonzuo.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
//TODO
public class AdminFlowerModify extends JFrame {

	private JPanel contentPane;
	private JTextField textID;
	private JTextField textFlowerName;
	private JTextField textPrice;
	private JTextField textCount;
	private JTextField textSale;
	private JTextField textNewPrice;
	private JTextField textNewCount;
	public static Vector<String> DATA;

	/**
	 * Create the frame.
	 */
	public AdminFlowerModify() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				new AdminFlowerControl();
			}
		});
		

		
		setTitle("\u6570\u636E\u4FEE\u6539\u9875\u9762");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 467);
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
		
		JLabel labTitle = new JLabel("\u9C9C\u82B1\u4FE1\u606F\u4FEE\u6539");
		labTitle.setForeground(new Color(102, 204, 255));
		labTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labTitle.setFont(new Font("宋体", Font.PLAIN, 18));
		labTitle.setBounds(10, 10, 414, 27);
		contentPane.add(labTitle);
		
		JLabel labID = new JLabel("\u5E8F\u53F7:");
		labID.setFont(new Font("宋体", Font.PLAIN, 16));
		labID.setBounds(60, 47, 56, 27);
		contentPane.add(labID);
		
		textID = new JTextField();
		textID.setBounds(123, 47, 250, 27);
		textID.setEditable(false);
		textID.setBackground(Color.white);
		textID.setMargin(new Insets(0,5,0,0));
		contentPane.add(textID);
		textID.setColumns(10);
		
		JLabel labFlowerName = new JLabel("\u9C9C\u82B1:");
		labFlowerName.setFont(new Font("宋体", Font.PLAIN, 16));
		labFlowerName.setBounds(60, 99, 56, 27);
		contentPane.add(labFlowerName);
		
		textFlowerName = new JTextField();
		textFlowerName.setColumns(10);
		textFlowerName.setBounds(123, 99, 250, 27);
		textFlowerName.setEditable(false);
		textFlowerName.setBackground(Color.white);
		textFlowerName.setMargin(new Insets(0,5,0,0));
		contentPane.add(textFlowerName);
		
		JLabel labPrice = new JLabel("\u4EF7\u683C:");
		labPrice.setFont(new Font("宋体", Font.PLAIN, 16));
		labPrice.setBounds(60, 159, 56, 27);
		contentPane.add(labPrice);
		
		textPrice = new JTextField();
		textPrice.setColumns(10);
		textPrice.setBounds(123, 159, 98, 27);
		textPrice.setEditable(false);
		textPrice.setBackground(Color.white);
		textPrice.setMargin(new Insets(0,5,0,0));
		contentPane.add(textPrice);
		
		JLabel labCount = new JLabel("\u5E93\u5B58:");
		labCount.setFont(new Font("宋体", Font.PLAIN, 16));
		labCount.setBounds(60, 228, 56, 27);
		contentPane.add(labCount);
		
		textCount = new JTextField();
		textCount.setColumns(10);
		textCount.setBounds(123, 228, 98, 27);
		textCount.setEditable(false);
		textCount.setBackground(Color.white);
		textCount.setMargin(new Insets(0,5,0,0));
		contentPane.add(textCount);
		
		JLabel label_4 = new JLabel("\u9500\u91CF:");
		label_4.setFont(new Font("宋体", Font.PLAIN, 16));
		label_4.setBounds(60, 298, 56, 27);
		contentPane.add(label_4);
		
		textSale = new JTextField();
		textSale.setColumns(10);
		textSale.setBounds(123, 298, 250, 27);
		textSale.setEditable(false);
		textSale.setBackground(Color.white);
		textSale.setMargin(new Insets(0,5,0,0));
		contentPane.add(textSale);
		
		JButton btnCancel = new JButton("\u4FEE\u6539");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//修改
				modify();
			}
		});
		btnCancel.setBounds(60, 364, 111, 40);
		contentPane.add(btnCancel);
		
		JButton btnReSet = new JButton("\u91CD\u7F6E");
		btnReSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textNewPrice.setText("");
				textNewCount.setText("");
			}
		});
		btnReSet.setBounds(262, 364, 111, 40);
		contentPane.add(btnReSet);
		
		textNewPrice = new JTextField();
		textNewPrice.setColumns(10);
		textNewPrice.setBounds(275, 160, 98, 27);
		textNewPrice.setMargin(new Insets(0,5,0,0));
		contentPane.add(textNewPrice);
		
		textNewCount = new JTextField();
		textNewCount.setColumns(10);
		textNewCount.setBounds(275, 228, 98, 27);
		textNewCount.setMargin(new Insets(0,5,0,0));
		contentPane.add(textNewCount);
		
		initData();
	}
	/**
	 * 初始化数据
	 */
	public void initData() {
		DATA = AdminFlowerControl.DATA;
		if (DATA.isEmpty()) {
			JOptionPane.showMessageDialog(AdminFlowerModify.this, "获取数据失败", "提示", JOptionPane.ERROR_MESSAGE);
		} else {
			textID.setText(DATA.get(0));
			textFlowerName.setText(DATA.get(1));
			textPrice.setText(DATA.get(2));
			textCount.setText(DATA.get(3));
			textSale.setText(DATA.get(4));
		}

	}
	
	public void modify() {
		UserDao userDao = new UserDao();
		String price = textNewPrice.getText();
		String count = textNewCount.getText();
		if(!userDao.matchNum(price)) {
			JOptionPane.showMessageDialog(AdminFlowerModify.this, "价格数字非法", "警告", JOptionPane.INFORMATION_MESSAGE);
		} else if( !userDao.matchNum(count) ) {
			JOptionPane.showMessageDialog(AdminFlowerModify.this, "库存数字非法", "警告", JOptionPane.INFORMATION_MESSAGE);
		} else {
			int option = JOptionPane.showConfirmDialog(AdminFlowerModify.this, "是否修改", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(option == 0) {
				//执行修改操作
				count = String.valueOf(Integer.parseInt(count)+Integer.parseInt(textCount.getText()));
				boolean flag;
				flag = userDao.AdminModifyFlowerDetil(textFlowerName.getText(), count, price);
				if(flag) {
					JOptionPane.showMessageDialog(AdminFlowerModify.this, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
					AdminFlowerModify.this.dispose();
				} else {
					JOptionPane.showMessageDialog(AdminFlowerModify.this, "修改操作失败", "提示", JOptionPane.WARNING_MESSAGE);
					AdminFlowerModify.this.dispose();
				}
			}
			
		}
	}
}

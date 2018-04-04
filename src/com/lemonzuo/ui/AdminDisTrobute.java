package com.lemonzuo.ui;

import java.awt.Font;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.lemonzuo.dao.UserDao;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class AdminDisTrobute extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;
	public Vector<String> DATA = AdminOrderControl.DATA;


	/**
	 * Create the frame.
	 */
	public AdminDisTrobute() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				new AdminOrderControl();
			}
		});
		setTitle("\u914D\u8D27");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 462);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//窗体可见
		setVisible(true);
		//窗体居中
		setLocationRelativeTo(null);
		//禁用缩放
		this.setResizable(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u914D\u8D27\u9875\u9762");
		label.setFont(new Font("宋体", Font.PLAIN, 18));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(10, 10, 414, 24);
		contentPane.add(label);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 46, 414, 303);
		contentPane.add(scrollPane);
		
		table = new JTable();
		tableModel = new DefaultTableModel();
		table.setModel(tableModel);
		scrollPane.setViewportView(table);
		// 设置table内容居中
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		// 两种方式
		//tcr.setHorizontalAlignment(JLabel.CENTER);
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, tcr);
		
		JButton button = new JButton("\u786E\u8BA4\u914D\u8D27");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO 配货方法
				disTrobute();
			}
		});
		button.setBounds(171, 365, 101, 38);
		contentPane.add(button);
		
		showData();
	}
	
	//显示数据
	public void showData() {
		UserDao userDao = new UserDao();
		Vector<String> title = new Vector<String>();
		title.add("序号");
		title.add("鲜花");
		title.add("数量");
		String orderID = DATA.get(1);
		Vector<Vector<String>> list = userDao.OrderDetial(orderID);
		if(list.isEmpty()) {
			tableModel.setDataVector(null, title);
		} else {
			tableModel.setDataVector(list, title);
		}

	}
	
	//配货
	public void disTrobute() {
		int rowCount = table.getRowCount();
		String orderID = DATA.get(1);
		UserDao userDao = new UserDao();
		int j = 0;
		for(int i = 0; i <rowCount ; i++) {
			String flowerName = String.valueOf(table.getValueAt(j, 1));
			String flowerNum = String.valueOf(table.getValueAt(j, 2));
			//判断库存
			if(Integer.parseInt(userDao.getFlowerNum(flowerName)) < Integer.parseInt(flowerNum) ) {
				JOptionPane.showMessageDialog(AdminDisTrobute.this,flowerName+"库存不足" , "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				//配货操作
				if(userDao.disTrobuteFlower(flowerName, flowerNum)) {
					JOptionPane.showMessageDialog(AdminDisTrobute.this,orderID+":"+flowerName+"配货成功" , "成功", JOptionPane.INFORMATION_MESSAGE);
					userDao.disTrobuteFlowerFlag(flowerName, orderID);
				} else {
					JOptionPane.showMessageDialog(AdminDisTrobute.this,orderID+":"+flowerName+"操作失败" , "失败", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			j++;
		}
		if( userDao.orderExam(orderID)) {
			//某些商品配货失败
			AdminDisTrobute.this.dispose();
			
		} else {
			//修改订单状态为配货
			userDao.modifyOrderCondition(orderID);
			//关闭本页面
			AdminDisTrobute.this.dispose();
		}
	
	}
	

}

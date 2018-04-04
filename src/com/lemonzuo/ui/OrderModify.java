package com.lemonzuo.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.lemonzuo.dao.UserDao;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class OrderModify extends JFrame {

	private JPanel contentPane;
	public static Vector<String> DATA = UserOrderList.DATA;
	private JTextField textOrderID;
	private JTable table;
	private DefaultTableModel tableModel;
	public static String USER_NAME = UserOrderList.USER_NAME;


	/**
	 * Create the frame.
	 */
	public OrderModify() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				new UserOrderList();
			}
		});
		setTitle("\u8BA2\u5355\u8BE6\u60C5\u9875");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 470);
		//窗体可见
		setVisible(true);
		//禁用缩放
		setResizable(false);
		//窗体居中
		this.setLocationRelativeTo(null);
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//标题标签
		JLabel labTitle = new JLabel("\u8BA2\u5355\u8BE6\u60C5");
		labTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labTitle.setFont(new Font("宋体", Font.PLAIN, 20));
		labTitle.setBounds(10, 10, 414, 29);
		contentPane.add(labTitle);
		
		//ID标签
		JLabel labOrderID = new JLabel("\u8BA2\u5355\u53F7:");
		labOrderID.setFont(new Font("宋体", Font.PLAIN, 16));
		labOrderID.setBounds(10, 49, 62, 29);
		contentPane.add(labOrderID);
		
		//ID显示框
		textOrderID = new JTextField();
		textOrderID.setBounds(82, 49, 342, 29);
		textOrderID.setEditable(false);
		textOrderID.setBackground(Color.white);
		textOrderID.setMargin(new Insets(0,5,0,0));
		contentPane.add(textOrderID);
		textOrderID.setColumns(10);
		
		//修改按钮
		JButton btnModifyNum = new JButton("\u4FEE\u6539\u6570\u91CF");
		btnModifyNum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO 修改
				FlowerNumModify();
			}
		});
		btnModifyNum.setBounds(10, 383, 131, 38);
		contentPane.add(btnModifyNum);
		
		//删除按钮
		JButton btnDel = new JButton("\u5220\u9664");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO 删除
				FlowerDel();
			}
		});
		btnDel.setBounds(293, 383, 131, 38);
		contentPane.add(btnDel);
		
		//滚动条区域
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 88, 414, 281);
		contentPane.add(scrollPane);
		
		//tab区域
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
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);
		
		//修改按钮
		JMenuItem muNum = new JMenuItem("\u4FEE\u6539\u6570\u91CF");
		muNum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO 修改
				FlowerNumModify();
			}
		});
		popupMenu.add(muNum);
		
		//删除按钮
		JMenuItem muDel = new JMenuItem("\u5220\u9664");
		muDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO 删除
				FlowerDel();
			}
		});
		popupMenu.add(muDel);
		//显示数据
		SetData();
	}
	
	//显示数据
	public void SetData() {
		UserDao userDao = new UserDao();
		//获取订单编号
		String orderID = DATA.get(0);
		textOrderID.setText(orderID);
		//设置表头
		Vector<String> title = new Vector<String>();
		title.add("序号");
		title.add("鲜花");
		title.add("数量");
		//数据
		Vector<Vector<String>> list = userDao.OrderDetial(orderID);
		//添加数据
		if ( list.isEmpty()) {
			tableModel.setDataVector(null, title);
		} else {
			tableModel.setDataVector(list, title);
		}
		
		
	}
	
	//读取数据
	public Vector<String> getData() {
		Vector<String> data = new Vector<String>();
		//得到所选行
		int row = table.getSelectedRow();
		//得到选取总行数
		int rowCount = table.getSelectedRowCount();
		if( row == -1) {	//用户未选择行
			JOptionPane.showMessageDialog(OrderModify.this, "请选择操作项", "提示", JOptionPane.INFORMATION_MESSAGE);
		} else if( rowCount > 1 ) {	//行数大于一
			JOptionPane.showMessageDialog(OrderModify.this, "无法同时操作多个项目", "提示", JOptionPane.INFORMATION_MESSAGE);
		} else {
			//添加数据
			data.add(String.valueOf(table.getValueAt(row, 0)));
			data.add(String.valueOf(table.getValueAt(row, 1)));
			data.add(String.valueOf(table.getValueAt(row, 2)));
		}
		return data;
	} 
	
	//TODO 删除函数
	public void FlowerDel() {
		Vector<String> data = getData();
		if(data.isEmpty()) {
			
		} else {
			String orderID = DATA.get(0);
			String flowerName = data.get(1);
			UserDao userDao = new UserDao();
			if(userDao.UserDelOrderFlower(orderID, flowerName)) {
				JOptionPane.showMessageDialog(OrderModify.this, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				//刷新数据
				//判断订单中鲜花是否全部删除
				if( userDao.orderExistFlower(orderID)) {
					//依旧存在鲜花
				} else {
					//没有鲜花 删除本订单
					if (userDao.userDeleteOrder(USER_NAME, orderID)) {
						JOptionPane.showMessageDialog(OrderModify.this, "删除订单成功", "提示", JOptionPane.INFORMATION_MESSAGE);
						//关闭本窗口
						OrderModify.this.dispose();
					} else {
						JOptionPane.showMessageDialog(OrderModify.this, "删除订单失败", "提示", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				SetData();
			} else {
				JOptionPane.showMessageDialog(OrderModify.this, "删除失败", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	public void FlowerNumModify() {
		int option = JOptionPane.showConfirmDialog(OrderModify.this, "是否修改数量", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if( option == 0 ) {
			Vector<String> data = getData();
			if(data.isEmpty()) {
				
			} else {
				//获取数据
				UserDao userDao = new UserDao();
				String orderID = DATA.get(0);
				String flowerName = data.get(1);
				//获取该花的库存
				String flowerNum = userDao.getFlowerNum(flowerName);
				//预修改的数量
				String newNum = JOptionPane.showInputDialog("请输入修改数量");
				//预修改数量和库存判断
				if(Integer.parseInt(newNum)> Integer.parseInt(flowerNum)) {
					JOptionPane.showMessageDialog(OrderModify.this, "库存不足无法修改", "警告", JOptionPane.ERROR_MESSAGE);
				} else {
					if(userDao.UserModifyOrderFlowerNum(orderID, flowerName, newNum)) {
						JOptionPane.showMessageDialog(OrderModify.this, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
						SetData();
					} else {
						JOptionPane.showMessageDialog(OrderModify.this, "操作失败", "提示", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		} else {
			
		}
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}

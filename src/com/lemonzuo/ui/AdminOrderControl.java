package com.lemonzuo.ui;

import java.awt.Component;
import java.awt.Font;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.lemonzuo.dao.UserDao;

@SuppressWarnings("serial")
public class AdminOrderControl extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel tableModel;
	private JTable table;
	public static Vector<String> DATA;

	/**
	 * Create the frame.
	 */
	public AdminOrderControl() {
		//JFRame窗体
		setTitle("\u8BA2\u5355\u7BA1\u7406");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 460);
		//窗体可见
		setVisible(true);
		//窗体居中
		setLocationRelativeTo(null);
		//禁用缩放
		this.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//标题
		JLabel labTitle = new JLabel("\u8BA2\u5355\u7BA1\u7406\u754C\u9762");
		labTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labTitle.setFont(new Font("宋体", Font.PLAIN, 18));
		labTitle.setBounds(10, 10, 424, 28);
		contentPane.add(labTitle);
		
		//滚动条区域
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 49, 424, 304);
		contentPane.add(scrollPane);
		
		//表格
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
		
		//TODO 配货按钮
		JButton btnDistribute = new JButton("\u914D\u8D27");
		btnDistribute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disTrobute();
			}
		});
		btnDistribute.setBounds(10, 371, 99, 37);
		contentPane.add(btnDistribute);
		
		//派送按钮
		JButton btnDelive = new JButton("\u6D3E\u9001");
		btnDelive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deLieve();
				orderList();
			}
		});
		btnDelive.setBounds(335, 371, 99, 37);
		contentPane.add(btnDelive);
		
		//关闭按钮
		JButton btnClose = new JButton("\u5173\u95ED\u672C\u9875\u9762");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminOrderControl.this.dispose();
			}
		});
		btnClose.setBounds(172, 371, 99, 37);
		contentPane.add(btnClose);
		
		//显示所有订单
		orderList();
		
	}
	
	//显示订单列表
	public void orderList() {
		//获取订单列表
		UserDao userDao = new UserDao();
		Vector<String> title = new Vector<String>();
		title.add("序号");
		title.add("订单号");
		title.add("订单状态");
		title.add("用户");
		Vector<Vector<String>> list = userDao.allOrderList();
		//添加数据
		if( list.isEmpty() ) {
			tableModel.setDataVector(null, title);
		} else {
			tableModel.setDataVector(list, title);
		}
		
		//table右键菜单
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);
		
		JMenuItem muDistribute = new JMenuItem("\u914D\u8D27");
		muDistribute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO 配货
				disTrobute();
			}
		});
		popupMenu.add(muDistribute);
		
		JMenuItem muDelive = new JMenuItem("\u6D3E\u9001");
		muDelive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO 派送
				deLieve();
				orderList();
			}
		});
		popupMenu.add(muDelive);
	}
	
	//获取管理员选中行的数据
	public Vector<String> getData() {
		Vector<String> data= new Vector<String>();
		int row = table.getSelectedRow();
		int len = table.getColumnCount();
		int rowCount = table.getSelectedRowCount();
		int j = 0;
		if( row == -1) {
			//为选择行
			JOptionPane.showMessageDialog(AdminOrderControl.this, "请选择订单", "提示", JOptionPane.INFORMATION_MESSAGE);
		} else if ( rowCount > 1) {
			//多行
			JOptionPane.showMessageDialog(AdminOrderControl.this, "无法同时操作多个订单", "提示", JOptionPane.INFORMATION_MESSAGE);
		} else {
			for(int i = 0; i< len;i++) {
				String info = String.valueOf(table.getValueAt(row, j));
				data.add(info);
				j++;
			}
		}
		return data;
	}
	
	//配货
	public void disTrobute() {
		//获取预操作订单数据
		DATA = getData();
		if(DATA.isEmpty()) {
			
		} else {
			String condition = DATA.get(2);
			if("提交".equals(condition)) {
				//跳转配货页面
				new AdminDisTrobute();
				//关闭当前页面
				AdminOrderControl.this.dispose();
			} else {
				//订单为其他状态
				JOptionPane.showMessageDialog(AdminOrderControl.this, "该订单当前状态下无法配货","提示",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	//派送
	public void deLieve() {
		DATA = getData();
		if(DATA.isEmpty()) {
			
		} else {
			String condition = DATA.get(2);
			String orderID = DATA.get(1);
			if("配货".equals(condition)) {
				UserDao userDao = new UserDao();
				if(!userDao.orderExam(orderID)) {
					userDao.OrderCondition(orderID);
					JOptionPane.showMessageDialog(AdminOrderControl.this, "订单派送成功","提示",JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(AdminOrderControl.this, "该订单当前状态下无法配货","提示",JOptionPane.INFORMATION_MESSAGE);
			}
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

package com.lemonzuo.ui;

import java.awt.Font;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.lemonzuo.dao.UserDao;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class UserOrderList extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;
	public static String USER_NAME = UserShopCar.USER_NAME;
	public static Vector<String> DATA = new Vector<String>();


	/**
	 * Create the frame.
	 */
	public UserOrderList() {
		//窗体
		setTitle("\u6211\u7684\u8BA2\u5355");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 466);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//标题
		JLabel labTitle = new JLabel("\u5168\u90E8\u8BA2\u5355");
		labTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labTitle.setFont(new Font("宋体", Font.PLAIN, 20));
		labTitle.setBounds(10, 10, 424, 36);
		contentPane.add(labTitle);
		

		//查看按钮
		JButton btnOrderDetial = new JButton("\u67E5\u770B");
		btnOrderDetial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//查看订单按钮
				OrderDetial();
			}
		});
		btnOrderDetial.setBounds(10, 375, 93, 36);
		contentPane.add(btnOrderDetial);
		
		//退单
		JButton btnBackOrder = new JButton("\u9000\u5355");
		btnBackOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO 调用BackOrdder()
				BackOrdder();
			}
		});
		btnBackOrder.setBounds(341, 375, 93, 36);
		contentPane.add(btnBackOrder);
		
		//修改订单
		JButton btnOrderModify = new JButton("\u4FEE\u6539");
		btnOrderModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO 修改
				OrderModify();
			}
		});
		btnOrderModify.setBounds(123, 375, 93, 36);
		contentPane.add(btnOrderModify);
		
		//删除历史订单
		JButton btnOrderDel = new JButton("\u5220\u9664");
		btnOrderDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO 删除操作
				OrderDel();
			}
		});
		btnOrderDel.setBounds(226, 375, 93, 36);
		contentPane.add(btnOrderDel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 424, 291);
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
		table.setSelectionMode(0);
		//窗体可见
		setVisible(true);
		//禁用缩放
		setResizable(false);
		//窗体居中
		setLocationRelativeTo(null);
		
		ShowOrderList();
	}
	
	//显示订单列表
	public void ShowOrderList() {
		//表头
		Vector<String> title = new Vector<String>();
		title.add("序号");
		title.add("订单号");
		title.add("订单状态");
		title.add("下单时间");
		//接收数据
		UserDao userDao = new UserDao();
		Vector<Vector<String>> list = userDao.UserOrderList(USER_NAME);
		//表格添加数据
		if( list.isEmpty()) {
			tableModel.setDataVector(null, title);
		} else {
			tableModel.setDataVector(list, title);
		}
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);
		
		JMenuItem muOrderDetial = new JMenuItem("\u67E5\u770B");
		muOrderDetial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//查看
				OrderDetial();
			}
		});
		popupMenu.add(muOrderDetial);
		
		JMenuItem muOrderModify = new JMenuItem("\u4FEE\u6539");
		muOrderModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO 修改
				OrderModify();
			}
		});
		popupMenu.add(muOrderModify);
		
		JMenuItem muOrderDel = new JMenuItem("\u5220\u9664");
		muOrderDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO 删除操作
				OrderDel();
			}
		});
		popupMenu.add(muOrderDel);
		
		JMenuItem muBackOrder = new JMenuItem("\u9000\u5355");
		muBackOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//退单
				BackOrdder();
			}
		});
		popupMenu.add(muBackOrder);
	}
	
	//获取选中行数据
	public Vector<String> orderData() {
		Vector<String> orderData = new Vector<String>();
		int row = table.getSelectedRow();
		int rowCount = table.getSelectedRowCount();
		if (row == -1 ) {
			JOptionPane.showMessageDialog(UserOrderList.this, "请选择订单", "提示", JOptionPane.INFORMATION_MESSAGE);
		}else {
				if (rowCount > 1) {
				JOptionPane.showMessageDialog(UserOrderList.this, "无法同时操作多个订单", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				orderData.add(String.valueOf(table.getValueAt(row, 1)));
				orderData.add(String.valueOf(table.getValueAt(row, 2)));
			}
		}
		return orderData;
	}
	
	//查看详细订单
	public void OrderDetial() {
		DATA = orderData();
		//用户未选择
		if(DATA.isEmpty()) {
			
		} else {
			//跳转详情页
			new OrderDetial();
			//关闭本窗口
			UserOrderList.this.dispose();

		}
	}
	
	//退单
	public void BackOrdder() {
		//获取用户选取订单数据
		DATA = orderData();
		if(DATA.isEmpty()) { //用户未选择订单
			
		} else {
			//获取状态和订单号
			String condition = DATA.get(1);
			String orderID = DATA.get(0);
			//提示
			int option = JOptionPane.showConfirmDialog(UserOrderList.this, "确认取消订单?","提示",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(option != 0) { //不进行操作
				
			} else if ( !condition.equals("提交") ) { //订单状态不为提交
				JOptionPane.showMessageDialog(UserOrderList.this, "当前订单状态为"+condition+"无法进行此项操作", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				UserDao userDao = new UserDao();
				if( userDao.userDeleteOrder(USER_NAME, orderID)) { //取消操作
					JOptionPane.showMessageDialog(UserOrderList.this, "取消订单成功", "提示", JOptionPane.INFORMATION_MESSAGE);
					//刷新数据
					ShowOrderList();
				} else {
					//数据库写入失败
					JOptionPane.showMessageDialog(UserOrderList.this, "操作失败请重试", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}

	}
	
	//删除操作
	public void OrderDel() {
		//获取用户订单
		DATA = orderData();
		if(DATA.isEmpty()) {	//	用户未选择
			
		} else {
			//得到数据
			String orderID = DATA.get(0);
			String condition = DATA.get(1);
			//是否操作
			int option = JOptionPane.showConfirmDialog(UserOrderList.this, "确认删除订单?","提示",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(option != 0 ) { //取消操作
				
			} else if( !condition.equals("签收") ) { //订单状态不合法
				JOptionPane.showMessageDialog(UserOrderList.this, "当前订单状态为"+condition+"无法进行此项操作", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				UserDao userDao = new UserDao();
				if( userDao.userDeleteOrder(USER_NAME, orderID)) {
					//进行删除操作
					JOptionPane.showMessageDialog(UserOrderList.this, "删除订单成功", "提示", JOptionPane.INFORMATION_MESSAGE);
					//刷新数据
					ShowOrderList();
				} else { //数据库操作失败
					JOptionPane.showMessageDialog(UserOrderList.this, "操作失败请重试", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		
	}
	
	//修改
	public void OrderModify() {
		//获取数据
		DATA = orderData();
		if( DATA.isEmpty()) {//用户未选择订单
			
		} else {
			String orderID = DATA.get(0);
			String condition = DATA.get(1);
			if("提交".equals(condition)) { //修改鲜花
				new OrderModify();
				UserOrderList.this.dispose();
			} else if ("派送".equals(condition)) { //修改订单状态为签收
				int option = JOptionPane.showConfirmDialog(UserOrderList.this, "签收订单?","提示",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				if(option == 0) {
					//确认收货
					UserDao userDao = new UserDao();
					if(userDao.UserConfirmGetOrder(USER_NAME, orderID)) {
						//收货成功
						JOptionPane.showMessageDialog(UserOrderList.this, "已收货", "提示", JOptionPane.INFORMATION_MESSAGE);
						 ShowOrderList();
					} else {
						//收货失败
						JOptionPane.showMessageDialog(UserOrderList.this, "操作失败", "提示", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					
				}
			} else {
				JOptionPane.showMessageDialog(UserOrderList.this, "当前订单状态为"+condition+"无法进行此项操作", "提示", JOptionPane.INFORMATION_MESSAGE);
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

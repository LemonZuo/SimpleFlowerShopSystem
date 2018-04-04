package com.lemonzuo.ui;

import java.awt.Color;
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
public class UserShopCar extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;
	public static String USER_NAME = SetFlowerToShopCar.USER_NAME;
	public static Vector<String> DATA;



	/**
	 * Create the frame.
	 */
	public UserShopCar() {

		
		//窗体标题
		setTitle("\u6211\u7684\u8D2D\u7269\u8F66");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//窗体可见
		setVisible(true);
		//禁用缩放
		setResizable(false);
		//窗体居中
		this.setLocationRelativeTo(null);
		
		//标题
		JLabel labTitle = new JLabel("\u4E2A\u4EBA\u4E2D\u5FC3");
		labTitle.setForeground(new Color(255, 0, 0));
		labTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labTitle.setFont(new Font("宋体", Font.PLAIN, 18));
		labTitle.setBounds(10, 20, 414, 41);
		contentPane.add(labTitle);

		//滚动条区域
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 414, 291);
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
		
		//修改按钮
		JButton btnModify = new JButton("\u4FEE\u6539");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//修改收藏数量
				modify();
			}
		});
		btnModify.setBounds(10, 361, 100, 42);
		contentPane.add(btnModify);
		
		//删除按钮
		JButton btnDel = new JButton("\u5220\u9664");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//删除该鲜花
				delete();
			}
		});
		btnDel.setBounds(171, 361, 100, 42);
		contentPane.add(btnDel);
		
		//提交按钮
		JButton btnSubmit = new JButton("\u63D0\u4EA4\u8BA2\u5355");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO 提交订单
				submitOrder ();
			}
		});
		btnSubmit.setBounds(324, 361, 100, 42);
		contentPane.add(btnSubmit);
		
		//显示购物车数据
		showShopCar();
	}
	
	//购物车数据
	public Vector<String> rowdata() {
		//定义Vector对象
		Vector<String> data = new Vector<String>();
		String info = "";
		//获取所选行数
		int row = table.getSelectedRow();
		//判断是否选中
		if ( row == -1) {
			JOptionPane.showMessageDialog(UserShopCar.this, "未选择鲜花,无法执行修改操作", "警告", JOptionPane.WARNING_MESSAGE);
		} else {
			//获取列数
			int len = table.getColumnCount();
			for(int i = 0;i < len;i++) {
				//将每一列数据添加进去
				info = String.valueOf(table.getValueAt(row, i));
				data.add(info);
			}
		}
		return data;
			
	}
	
	
	//修改
	public void modify() {
		//获取数据
		DATA = rowdata();
		//用户未选择鲜花不进行页面跳转
		if (DATA.isEmpty()) {
			
		} else {
			//跳转修改页面
			new UserModifyShopCar();
			//关闭当前窗口
			this.dispose();
		}
		
	}
	
	//删除
	public void delete() {
		//获取数据
				DATA = rowdata();
				//用户未选择鲜花不进行页面跳转
				if (DATA.isEmpty()) {
					
				} else {
					//用户确认删除
					int userOption = JOptionPane.showConfirmDialog(UserShopCar.this, "是否确认删除", "删除确认？", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(userOption == 0 ) {
						//执行删除操作
						String flowerName = DATA.get(1);
						UserDao userDao = new UserDao();
						if(userDao.userDeleteShopCarFlower(USER_NAME, flowerName)) {
							JOptionPane.showMessageDialog(UserShopCar.this, "删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
							showShopCar();
						} else {
							JOptionPane.showMessageDialog(UserShopCar.this, "操作失败", "提示", JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						//不进行删除操作
					}
				}
	}
	
	//显示购物车数据
	public void showShopCar() {
		UserDao userDao = new UserDao();
		//定义二维Vector对象
		Vector<Vector<String>> list = userDao.getShopCar(USER_NAME);
		//定义表头
		Vector<String> title = new Vector<String>();
		title.add("序号");
		title.add("鲜花");
		title.add("数量");
		title.add("添加时间");
		title.add("最后修改时间");
		//根据用户显示数据
		if(list.isEmpty()) {
			tableModel.setDataVector(null, title);
			JOptionPane.showMessageDialog(UserShopCar.this, "未查询到相关数据", "购物车为空", JOptionPane.INFORMATION_MESSAGE);
			UserShopCar.this.dispose();
		} else {
			tableModel.setDataVector(list, title);
		}
		
		//右键菜单
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);
		
		//修改选项
		JMenuItem goodsModify = new JMenuItem("修改");
		goodsModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//修改操作
				modify();	
			}
		});
		popupMenu.add(goodsModify);
		
		//删除选型
		JMenuItem goodsDel = new JMenuItem("删除");
		goodsDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//删除鲜花 调用delete()
				delete();
			}
		});
		popupMenu.add(goodsDel);
	}
	
	public void submitOrder () {
		UserDao userDao = new UserDao();
		//行数统计
		int len = table.getRowCount();
		if( len == 0) {
			JOptionPane.showMessageDialog(UserShopCar.this, "购物车为空,无法创建订单","提示",JOptionPane.ERROR_MESSAGE);
		} else {
			//创建订单号
			String orderID = userDao.CreateOrderID();
			//创建订单
			if ( userDao.CreateOrder( USER_NAME, orderID ) ) {
				//逐步写入订单信息
				for(int i = 0; i < len;i++) {
					int j = 0;
					//写入订单
					boolean flag = userDao.CreateOrderList(orderID, String.valueOf(table.getValueAt(j, 1)) , String.valueOf(table.getValueAt(j, 2)));
					if (flag) {
						//写入成功提示
						JOptionPane.showMessageDialog(UserShopCar.this, table.getValueAt(j, 1)+" 成功加入订单", "提示", JOptionPane.INFORMATION_MESSAGE);
						userDao.userDeleteShopCarFlower(USER_NAME, String.valueOf(table.getValueAt(j, 1)));
						j++;
						//刷新购物车
						showShopCar();
					} else {
						//写入订单失败
						JOptionPane.showMessageDialog(UserShopCar.this, table.getValueAt(j, 1)+" 加入订单失败", "提示", JOptionPane.INFORMATION_MESSAGE);
						j++;
					}
				}
			} else {
				//订单创建失败
				JOptionPane.showMessageDialog(UserShopCar.this, "订单创建失败", "警告", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	//监听右键菜单
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

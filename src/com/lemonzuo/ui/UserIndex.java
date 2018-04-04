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

@SuppressWarnings("serial")
public class UserIndex extends JFrame {

	private JPanel contentPane;
	private JTextField textFlowerName;
	private JTable table;
	private DefaultTableModel tableModel;
	public static String USER_NAME = Login.USER_NAME;
	public static String FLOWER_NAME;
	public static String FLOWER_ID;
	public static String FLOWER_NUM;
	
	/**
	 * Create the frame.
	 */
	public UserIndex() {
		

		
		//窗体标题
		setTitle("\u6F2B\u6B65\u7ECF\u5FC3");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 552);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//窗体可见
		setVisible(true);
		//禁用缩放
		setResizable(false);
		//窗体居中
		setLocationRelativeTo(null);
		
		//面板标题
		JLabel labTitle = new JLabel("\u6F2B  \u6B65  \u7ECF  \u5FC3");
		labTitle.setForeground(new Color(102, 204, 255));
		labTitle.setFont(new Font("微软雅黑 Light", Font.PLAIN, 20));
		labTitle.setBounds(156, 29, 122, 27);
		contentPane.add(labTitle);
		
		//花名标签
		JLabel labFlowerName = new JLabel("\u82B1\u540D\uFF1A");
		labFlowerName.setFont(new Font("宋体", Font.PLAIN, 16));
		labFlowerName.setBounds(21, 64, 48, 40);
		contentPane.add(labFlowerName);
		
		//花名输入框
		textFlowerName = new JTextField();
		textFlowerName.setBounds(79, 68, 214, 36);
		//设置内边距
		textFlowerName.setMargin(new Insets(0,5,0,0));
		contentPane.add(textFlowerName);
		textFlowerName.setColumns(10);
		
		//查询按钮
		JButton btnSubmit = new JButton("\u67E5\u8BE2");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showFlower();
			}
		});
		btnSubmit.setBounds(316, 67, 93, 37);
		contentPane.add(btnSubmit);
		
		//滚动条区域
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 119, 390, 291);
		contentPane.add(scrollPane);
		
		//表格区域
		table = new JTable();
		tableModel = new DefaultTableModel();
		// 设置table内容居中
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		// 两种方式
		//tcr.setHorizontalAlignment(JLabel.CENTER);
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, tcr);
		
		//设置表格模式
		table.setModel(tableModel);
		//表格放置区域
		scrollPane.setViewportView(table);
		
		JButton btnMyShopCar = new JButton("\u8D2D\u7269\u8F66");
		btnMyShopCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UserShopCar();
			}
		});
		btnMyShopCar.setBounds(21, 438, 78, 40);
		contentPane.add(btnMyShopCar);
		
		JButton btnMyOrder = new JButton("\u8BA2\u5355");
		btnMyOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//TODO 查看所有订单
				new UserOrderList();
			}
		});
		btnMyOrder.setBounds(331, 438, 78, 40);
		contentPane.add(btnMyOrder);
		
		JLabel lbWelcome = new JLabel("\u6F2B\u6B65\u7ECF\u5FC3\u6B22\u8FCE\u60A8");
		if(USER_NAME != null) {
			lbWelcome.setText("漫步经心欢迎您"+USER_NAME);
		}
		lbWelcome.setFont(new Font("宋体", Font.PLAIN, 14));
		lbWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lbWelcome.setBounds(128, 438, 165, 40);
		contentPane.add(lbWelcome);
		
		//显示商品信息
		showFlower();
	}
	
	/**
	 * 显示商品方法
	 */
	public void showFlower() {
		UserDao userDao = new UserDao();
		Vector<Vector<String>> list = userDao.getFlowerList(textFlowerName.getText());
		//定义表头数据
		Vector<String> title = new Vector<String>();
		title.add("编号");
		title.add("商品名");
		title.add("价格");
		title.add("库存量");
		title.add("累计销量");
		
		//向表格中添加数据
		if(list.isEmpty()) {
			tableModel.setDataVector(null, title);
		} else {
			tableModel.setDataVector(list, title);
		}
		
		//table右键菜单
		JPopupMenu popupMenu = new JPopupMenu();
		//添加到表格所在区域
		addPopup(table, popupMenu);
		//定义右键菜单选项
		JMenuItem shopCar = new JMenuItem("加入购物车");
		//将菜单项添加到右键菜单
		popupMenu.add(shopCar);
		
		//监听右键菜单动作
		shopCar.addActionListener(new ActionListener() {
			@SuppressWarnings({ })
			public void actionPerformed(ActionEvent e) {
				//获取当前选中花名
				FLOWER_NAME = getFlowerName();
				//判断是否选择一行
				if ("".equals(FLOWER_NAME) || FLOWER_NAME == null) {
					JOptionPane.showMessageDialog(UserIndex.this, "请单击左键选择鲜花", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					UserDao userDao = new UserDao();
					if(userDao.flowerExistShopCar(USER_NAME, FLOWER_NAME)) {
						JOptionPane.showMessageDialog(UserIndex.this, "添加失败,购物车中已存在该商品", "提示", JOptionPane.WARNING_MESSAGE);
					} else {
						FLOWER_ID = userDao.getFlowerId(FLOWER_NAME);
						FLOWER_NUM = userDao.getFlowerNum(FLOWER_NAME);
						new SetFlowerToShopCar();
					}
				}
			}
			
			/**
			 * 返回用户选中花名
			 * @return 鲜花名字
			 */
			public String getFlowerName() {
				String FlowerName = "";
				//获取鼠标所选行数
				int row = table.getSelectedRow();
				int rowCount = table.getSelectedRowCount();
				if(row == -1) {
					
				} else if (rowCount > 1){
					JOptionPane.showMessageDialog(UserIndex.this, "无法同时选择多种鲜花", "提示", JOptionPane.ERROR_MESSAGE);
				}else {
					//获取花名
					FlowerName = String.valueOf(table.getValueAt(row, 1));
				}
				
				return FlowerName;
			}
		});
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

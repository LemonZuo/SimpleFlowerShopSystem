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

//TODO AdminOrderControl
@SuppressWarnings("serial")
public class AdminFlowerControl extends JFrame {

	private JPanel contentPane;
	private JTextField textFlowerName;
	private JTable table;
	private DefaultTableModel tableModel;
	public static Vector<String> DATA;


	/**
	 * Create the frame.
	 */
	public AdminFlowerControl() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				//返回上一页
//				new AdminMain();
			}
		});

		//窗体
		//窗体
		setTitle("\u9C9C\u82B1\u7BA1\u7406");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 514);
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
		JLabel labTitle = new JLabel("\u6F2B\u6B65\u7ECF\u5FC3\u9C9C\u82B1\u7BA1\u7406");
		labTitle.setForeground(new Color(255, 51, 102));
		labTitle.setFont(new Font("宋体", Font.PLAIN, 18));
		labTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labTitle.setBounds(10, 10, 414, 30);
		contentPane.add(labTitle);
		
		//花名标签
		JLabel labName = new JLabel("\u82B1\u540D\uFF1A");
		labName.setHorizontalAlignment(SwingConstants.CENTER);
		labName.setFont(new Font("宋体", Font.PLAIN, 16));
		labName.setBounds(10, 50, 54, 30);
		contentPane.add(labName);
		
		//查询按钮
		JButton btnSerach = new JButton("\u67E5\u8BE2");
		btnSerach.addActionListener(new ActionListener() {
			//查询功能
			public void actionPerformed(ActionEvent arg0) {
				//显示查询结果
				showFlower();
			}
		});
		btnSerach.setBounds(331, 50, 93, 30);
		contentPane.add(btnSerach);
		
		//花名输入框
		textFlowerName = new JTextField();
		textFlowerName.setBounds(61, 52, 260, 30);
		textFlowerName.setMargin(new Insets(0,5,0,0));
		contentPane.add(textFlowerName);
		textFlowerName.setColumns(10);
		
		//滚动条区域
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 90, 414, 311);
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
		
		//新增鲜花按钮
		JButton btnAdd = new JButton("\u6DFB\u52A0");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//跳转新增鲜花界面
				new AdminAddFlower();
				//关闭当前窗口
				AdminFlowerControl.this.dispose();
			}
		});
		btnAdd.setBounds(10, 411, 104, 46);
		contentPane.add(btnAdd);
		
		//下架鲜花
		JButton btnDelete = new JButton("\u4E0B\u67B6");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//执行下架操作
				delete();
			}
		});
		btnDelete.setBounds(320, 411, 104, 46);
		contentPane.add(btnDelete);
		
		//修改按钮
		JButton btnMoify = new JButton("\u4FEE\u6539");
		btnMoify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//修改操作
				modify();
			}
		});
		btnMoify.setBounds(173, 411, 104, 46);
		contentPane.add(btnMoify);
		
		//初始显示数据
		showFlower();
	}
	
	/**
	 * 显示鲜花数据
	 */
	public void showFlower() {
		//获取花名
		String flowerName = textFlowerName.getText();
		UserDao userDao = new UserDao();
		//定义标题
		Vector<String> title = new Vector<String>();
		//添加表头
		title.add("序号");
		title.add("鲜花");
		title.add("价格");
		title.add("库存量");
		title.add("累计销量");
		//接收数据
		Vector<Vector<String>> list = userDao.getFlowerList(flowerName);
		//往表格中添加数据
		if( list.isEmpty()) {
			tableModel.setDataVector(null, title);
		} else {
			tableModel.setDataVector(list, title);
		}
		
		//右键菜单
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);
		
		//修改
		JMenuItem muModify = new JMenuItem("\u4FEE\u6539");
		muModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//执行修改
				modify();
			}
		});
		popupMenu.add(muModify);
		
		//删除
		JMenuItem muDelete = new JMenuItem("\u4E0B\u67B6");
		muDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//执行删除
				delete();
			}
		});
		popupMenu.add(muDelete);
	}
	
	/**
	 * 获取选中行的数据
	 * @return 返回选中行的数据
	 */
	public Vector<String> rowData() {
		Vector<String> data = new Vector<String>();
		String info = "";
		//获取列数
		int len = table.getColumnCount();
		//获取所选行
		int row = table.getSelectedRow();
		//获取一共选取行数
		int rowCount = table.getSelectedColumnCount();
		//判断是否选择
		if (row == -1) { //未选择
			JOptionPane.showMessageDialog(AdminFlowerControl.this, "请单击左键选择一种鲜花", "提示", JOptionPane.INFORMATION_MESSAGE);
		} else if (rowCount > 1) { //选择多行
			JOptionPane.showMessageDialog(AdminFlowerControl.this, "鲜花数大于一无法操作", "警告", JOptionPane.ERROR_MESSAGE);
		} else { //选取单行
			for(int i = 0;i < len;i++) {
				//获取数据
				info = String.valueOf(table.getValueAt(row, i ));
				if (info == null) {
					info = "";
				}
				//添加数据
				data.add(info);
			}
		}
		return data;
	}
	
	
	//修改
	public void modify() {
		//获取数据
		DATA = rowData();
		if( DATA.isEmpty() ) {
			
		} else {
			//跳转修改页面
			new AdminFlowerModify();
			//关闭本窗口
			AdminFlowerControl.this.dispose();
		}

	}
	
	//下架
	public void delete() {
		//获取数据
		DATA = rowData();
		if( DATA.isEmpty() ) {
			
		} else {
			//获取花名
			String flowerName = DATA.get(1);
			//下架提醒
			int option = JOptionPane.showConfirmDialog(AdminFlowerControl.this, "是否下架该鲜花", "确认提醒", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if( option == 0 ) { //用户确认删除
				UserDao userDao = new UserDao();
				if( userDao.AdminDeleteFlower(flowerName)) {
					//下架成功
					JOptionPane.showMessageDialog(AdminFlowerControl.this, "下架成功", "操作结果", JOptionPane.INFORMATION_MESSAGE);
					//刷新数据
					showFlower();
				} else {
					//下架失败
					JOptionPane.showMessageDialog(AdminFlowerControl.this, "下架失败,请稍后重试", "操作结果", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				//取消下架
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

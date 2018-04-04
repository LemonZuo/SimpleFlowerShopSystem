package com.lemonzuo.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
public class OrderDetial extends JFrame {

	private JPanel contentPane;
	private JTextField textOrderID;
	private JTable table;
	private DefaultTableModel tableModel;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public OrderDetial() {
		//�������ڹر�
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				new UserOrderList();
			}
		});
		//����
		setTitle("\u8BA2\u5355\u8BE6\u60C5\u9875");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 432);
		//����ɼ�
		setVisible(true);
		//��������
		setResizable(false);
		//�������
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//����
		JLabel labTitle = new JLabel("\u8BA2\u5355\u8BE6\u60C5");
		labTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labTitle.setFont(new Font("����", Font.PLAIN, 20));
		labTitle.setBounds(10, 10, 414, 29);
		contentPane.add(labTitle);
		
		//ID��ǩ
		JLabel labOrderID = new JLabel("\u8BA2\u5355\u53F7:");
		labOrderID.setFont(new Font("����", Font.PLAIN, 16));
		labOrderID.setBounds(10, 49, 62, 29);
		contentPane.add(labOrderID);
		
		//ID��ʾ��
		textOrderID = new JTextField();
		textOrderID.setBounds(82, 49, 342, 29);
		textOrderID.setEditable(false);
		textOrderID.setBackground(Color.white);
		textOrderID.setMargin(new Insets(0,5,0,0));
		contentPane.add(textOrderID);
		textOrderID.setColumns(10);
		
		//����������
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 88, 414, 281);
		contentPane.add(scrollPane);
		
		//�������
		table = new JTable();
		tableModel = new DefaultTableModel();
		table.setModel(tableModel);
		scrollPane.setViewportView(table);
		// ����table���ݾ���
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		// ���ַ�ʽ
		//tcr.setHorizontalAlignment(JLabel.CENTER);
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, tcr);
		
		//��ʼ����
		SetData();
	}
	
	public void SetData() {
		UserDao userDao = new UserDao();
		//��ȡ�������
		String orderID = UserOrderList.DATA.get(0);
		textOrderID.setText(orderID);
		//���ñ�ͷ
		Vector<String> title = new Vector<String>();
		title.add("���");
		title.add("�ʻ�");
		title.add("����");
		//���ݻ�ȡ
		Vector<Vector<String>> list = userDao.OrderDetial(orderID);
		//�������
		if ( list.isEmpty()) {
			tableModel.setDataVector(null, title);
		} else {
			tableModel.setDataVector(list, title);
		}
		
		
	}
}

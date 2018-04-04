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
		//����ɼ�
		setVisible(true);
		//��������
		setResizable(false);
		//�������
		this.setLocationRelativeTo(null);
		setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//�����ǩ
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
		
		//�޸İ�ť
		JButton btnModifyNum = new JButton("\u4FEE\u6539\u6570\u91CF");
		btnModifyNum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO �޸�
				FlowerNumModify();
			}
		});
		btnModifyNum.setBounds(10, 383, 131, 38);
		contentPane.add(btnModifyNum);
		
		//ɾ����ť
		JButton btnDel = new JButton("\u5220\u9664");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO ɾ��
				FlowerDel();
			}
		});
		btnDel.setBounds(293, 383, 131, 38);
		contentPane.add(btnDel);
		
		//����������
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 88, 414, 281);
		contentPane.add(scrollPane);
		
		//tab����
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
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);
		
		//�޸İ�ť
		JMenuItem muNum = new JMenuItem("\u4FEE\u6539\u6570\u91CF");
		muNum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO �޸�
				FlowerNumModify();
			}
		});
		popupMenu.add(muNum);
		
		//ɾ����ť
		JMenuItem muDel = new JMenuItem("\u5220\u9664");
		muDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO ɾ��
				FlowerDel();
			}
		});
		popupMenu.add(muDel);
		//��ʾ����
		SetData();
	}
	
	//��ʾ����
	public void SetData() {
		UserDao userDao = new UserDao();
		//��ȡ�������
		String orderID = DATA.get(0);
		textOrderID.setText(orderID);
		//���ñ�ͷ
		Vector<String> title = new Vector<String>();
		title.add("���");
		title.add("�ʻ�");
		title.add("����");
		//����
		Vector<Vector<String>> list = userDao.OrderDetial(orderID);
		//�������
		if ( list.isEmpty()) {
			tableModel.setDataVector(null, title);
		} else {
			tableModel.setDataVector(list, title);
		}
		
		
	}
	
	//��ȡ����
	public Vector<String> getData() {
		Vector<String> data = new Vector<String>();
		//�õ���ѡ��
		int row = table.getSelectedRow();
		//�õ�ѡȡ������
		int rowCount = table.getSelectedRowCount();
		if( row == -1) {	//�û�δѡ����
			JOptionPane.showMessageDialog(OrderModify.this, "��ѡ�������", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		} else if( rowCount > 1 ) {	//��������һ
			JOptionPane.showMessageDialog(OrderModify.this, "�޷�ͬʱ���������Ŀ", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		} else {
			//�������
			data.add(String.valueOf(table.getValueAt(row, 0)));
			data.add(String.valueOf(table.getValueAt(row, 1)));
			data.add(String.valueOf(table.getValueAt(row, 2)));
		}
		return data;
	} 
	
	//TODO ɾ������
	public void FlowerDel() {
		Vector<String> data = getData();
		if(data.isEmpty()) {
			
		} else {
			String orderID = DATA.get(0);
			String flowerName = data.get(1);
			UserDao userDao = new UserDao();
			if(userDao.UserDelOrderFlower(orderID, flowerName)) {
				JOptionPane.showMessageDialog(OrderModify.this, "ɾ���ɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				//ˢ������
				//�ж϶������ʻ��Ƿ�ȫ��ɾ��
				if( userDao.orderExistFlower(orderID)) {
					//���ɴ����ʻ�
				} else {
					//û���ʻ� ɾ��������
					if (userDao.userDeleteOrder(USER_NAME, orderID)) {
						JOptionPane.showMessageDialog(OrderModify.this, "ɾ�������ɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						//�رձ�����
						OrderModify.this.dispose();
					} else {
						JOptionPane.showMessageDialog(OrderModify.this, "ɾ������ʧ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					}
				}
				SetData();
			} else {
				JOptionPane.showMessageDialog(OrderModify.this, "ɾ��ʧ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	public void FlowerNumModify() {
		int option = JOptionPane.showConfirmDialog(OrderModify.this, "�Ƿ��޸�����", "��ʾ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if( option == 0 ) {
			Vector<String> data = getData();
			if(data.isEmpty()) {
				
			} else {
				//��ȡ����
				UserDao userDao = new UserDao();
				String orderID = DATA.get(0);
				String flowerName = data.get(1);
				//��ȡ�û��Ŀ��
				String flowerNum = userDao.getFlowerNum(flowerName);
				//Ԥ�޸ĵ�����
				String newNum = JOptionPane.showInputDialog("�������޸�����");
				//Ԥ�޸������Ϳ���ж�
				if(Integer.parseInt(newNum)> Integer.parseInt(flowerNum)) {
					JOptionPane.showMessageDialog(OrderModify.this, "��治���޷��޸�", "����", JOptionPane.ERROR_MESSAGE);
				} else {
					if(userDao.UserModifyOrderFlowerNum(orderID, flowerName, newNum)) {
						JOptionPane.showMessageDialog(OrderModify.this, "�޸ĳɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						SetData();
					} else {
						JOptionPane.showMessageDialog(OrderModify.this, "����ʧ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
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

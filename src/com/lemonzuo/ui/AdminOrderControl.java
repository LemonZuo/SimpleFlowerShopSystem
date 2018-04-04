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
		//JFRame����
		setTitle("\u8BA2\u5355\u7BA1\u7406");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 460);
		//����ɼ�
		setVisible(true);
		//�������
		setLocationRelativeTo(null);
		//��������
		this.setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//����
		JLabel labTitle = new JLabel("\u8BA2\u5355\u7BA1\u7406\u754C\u9762");
		labTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labTitle.setFont(new Font("����", Font.PLAIN, 18));
		labTitle.setBounds(10, 10, 424, 28);
		contentPane.add(labTitle);
		
		//����������
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 49, 424, 304);
		contentPane.add(scrollPane);
		
		//���
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
		
		//TODO �����ť
		JButton btnDistribute = new JButton("\u914D\u8D27");
		btnDistribute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				disTrobute();
			}
		});
		btnDistribute.setBounds(10, 371, 99, 37);
		contentPane.add(btnDistribute);
		
		//���Ͱ�ť
		JButton btnDelive = new JButton("\u6D3E\u9001");
		btnDelive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deLieve();
				orderList();
			}
		});
		btnDelive.setBounds(335, 371, 99, 37);
		contentPane.add(btnDelive);
		
		//�رհ�ť
		JButton btnClose = new JButton("\u5173\u95ED\u672C\u9875\u9762");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminOrderControl.this.dispose();
			}
		});
		btnClose.setBounds(172, 371, 99, 37);
		contentPane.add(btnClose);
		
		//��ʾ���ж���
		orderList();
		
	}
	
	//��ʾ�����б�
	public void orderList() {
		//��ȡ�����б�
		UserDao userDao = new UserDao();
		Vector<String> title = new Vector<String>();
		title.add("���");
		title.add("������");
		title.add("����״̬");
		title.add("�û�");
		Vector<Vector<String>> list = userDao.allOrderList();
		//�������
		if( list.isEmpty() ) {
			tableModel.setDataVector(null, title);
		} else {
			tableModel.setDataVector(list, title);
		}
		
		//table�Ҽ��˵�
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);
		
		JMenuItem muDistribute = new JMenuItem("\u914D\u8D27");
		muDistribute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO ���
				disTrobute();
			}
		});
		popupMenu.add(muDistribute);
		
		JMenuItem muDelive = new JMenuItem("\u6D3E\u9001");
		muDelive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO ����
				deLieve();
				orderList();
			}
		});
		popupMenu.add(muDelive);
	}
	
	//��ȡ����Աѡ���е�����
	public Vector<String> getData() {
		Vector<String> data= new Vector<String>();
		int row = table.getSelectedRow();
		int len = table.getColumnCount();
		int rowCount = table.getSelectedRowCount();
		int j = 0;
		if( row == -1) {
			//Ϊѡ����
			JOptionPane.showMessageDialog(AdminOrderControl.this, "��ѡ�񶩵�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		} else if ( rowCount > 1) {
			//����
			JOptionPane.showMessageDialog(AdminOrderControl.this, "�޷�ͬʱ�����������", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		} else {
			for(int i = 0; i< len;i++) {
				String info = String.valueOf(table.getValueAt(row, j));
				data.add(info);
				j++;
			}
		}
		return data;
	}
	
	//���
	public void disTrobute() {
		//��ȡԤ������������
		DATA = getData();
		if(DATA.isEmpty()) {
			
		} else {
			String condition = DATA.get(2);
			if("�ύ".equals(condition)) {
				//��ת���ҳ��
				new AdminDisTrobute();
				//�رյ�ǰҳ��
				AdminOrderControl.this.dispose();
			} else {
				//����Ϊ����״̬
				JOptionPane.showMessageDialog(AdminOrderControl.this, "�ö�����ǰ״̬���޷����","��ʾ",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	//����
	public void deLieve() {
		DATA = getData();
		if(DATA.isEmpty()) {
			
		} else {
			String condition = DATA.get(2);
			String orderID = DATA.get(1);
			if("���".equals(condition)) {
				UserDao userDao = new UserDao();
				if(!userDao.orderExam(orderID)) {
					userDao.OrderCondition(orderID);
					JOptionPane.showMessageDialog(AdminOrderControl.this, "�������ͳɹ�","��ʾ",JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(AdminOrderControl.this, "�ö�����ǰ״̬���޷����","��ʾ",JOptionPane.INFORMATION_MESSAGE);
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

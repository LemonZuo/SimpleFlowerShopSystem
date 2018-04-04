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
		//����
		setTitle("\u6211\u7684\u8BA2\u5355");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 466);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//����
		JLabel labTitle = new JLabel("\u5168\u90E8\u8BA2\u5355");
		labTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labTitle.setFont(new Font("����", Font.PLAIN, 20));
		labTitle.setBounds(10, 10, 424, 36);
		contentPane.add(labTitle);
		

		//�鿴��ť
		JButton btnOrderDetial = new JButton("\u67E5\u770B");
		btnOrderDetial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//�鿴������ť
				OrderDetial();
			}
		});
		btnOrderDetial.setBounds(10, 375, 93, 36);
		contentPane.add(btnOrderDetial);
		
		//�˵�
		JButton btnBackOrder = new JButton("\u9000\u5355");
		btnBackOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO ����BackOrdder()
				BackOrdder();
			}
		});
		btnBackOrder.setBounds(341, 375, 93, 36);
		contentPane.add(btnBackOrder);
		
		//�޸Ķ���
		JButton btnOrderModify = new JButton("\u4FEE\u6539");
		btnOrderModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO �޸�
				OrderModify();
			}
		});
		btnOrderModify.setBounds(123, 375, 93, 36);
		contentPane.add(btnOrderModify);
		
		//ɾ����ʷ����
		JButton btnOrderDel = new JButton("\u5220\u9664");
		btnOrderDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO ɾ������
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
		// ����table���ݾ���
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		// ���ַ�ʽ
		//tcr.setHorizontalAlignment(JLabel.CENTER);
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, tcr);
		table.setSelectionMode(0);
		//����ɼ�
		setVisible(true);
		//��������
		setResizable(false);
		//�������
		setLocationRelativeTo(null);
		
		ShowOrderList();
	}
	
	//��ʾ�����б�
	public void ShowOrderList() {
		//��ͷ
		Vector<String> title = new Vector<String>();
		title.add("���");
		title.add("������");
		title.add("����״̬");
		title.add("�µ�ʱ��");
		//��������
		UserDao userDao = new UserDao();
		Vector<Vector<String>> list = userDao.UserOrderList(USER_NAME);
		//����������
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
				//�鿴
				OrderDetial();
			}
		});
		popupMenu.add(muOrderDetial);
		
		JMenuItem muOrderModify = new JMenuItem("\u4FEE\u6539");
		muOrderModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO �޸�
				OrderModify();
			}
		});
		popupMenu.add(muOrderModify);
		
		JMenuItem muOrderDel = new JMenuItem("\u5220\u9664");
		muOrderDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO ɾ������
				OrderDel();
			}
		});
		popupMenu.add(muOrderDel);
		
		JMenuItem muBackOrder = new JMenuItem("\u9000\u5355");
		muBackOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//�˵�
				BackOrdder();
			}
		});
		popupMenu.add(muBackOrder);
	}
	
	//��ȡѡ��������
	public Vector<String> orderData() {
		Vector<String> orderData = new Vector<String>();
		int row = table.getSelectedRow();
		int rowCount = table.getSelectedRowCount();
		if (row == -1 ) {
			JOptionPane.showMessageDialog(UserOrderList.this, "��ѡ�񶩵�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		}else {
				if (rowCount > 1) {
				JOptionPane.showMessageDialog(UserOrderList.this, "�޷�ͬʱ�����������", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			} else {
				orderData.add(String.valueOf(table.getValueAt(row, 1)));
				orderData.add(String.valueOf(table.getValueAt(row, 2)));
			}
		}
		return orderData;
	}
	
	//�鿴��ϸ����
	public void OrderDetial() {
		DATA = orderData();
		//�û�δѡ��
		if(DATA.isEmpty()) {
			
		} else {
			//��ת����ҳ
			new OrderDetial();
			//�رձ�����
			UserOrderList.this.dispose();

		}
	}
	
	//�˵�
	public void BackOrdder() {
		//��ȡ�û�ѡȡ��������
		DATA = orderData();
		if(DATA.isEmpty()) { //�û�δѡ�񶩵�
			
		} else {
			//��ȡ״̬�Ͷ�����
			String condition = DATA.get(1);
			String orderID = DATA.get(0);
			//��ʾ
			int option = JOptionPane.showConfirmDialog(UserOrderList.this, "ȷ��ȡ������?","��ʾ",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(option != 0) { //�����в���
				
			} else if ( !condition.equals("�ύ") ) { //����״̬��Ϊ�ύ
				JOptionPane.showMessageDialog(UserOrderList.this, "��ǰ����״̬Ϊ"+condition+"�޷����д������", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			} else {
				UserDao userDao = new UserDao();
				if( userDao.userDeleteOrder(USER_NAME, orderID)) { //ȡ������
					JOptionPane.showMessageDialog(UserOrderList.this, "ȡ�������ɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					//ˢ������
					ShowOrderList();
				} else {
					//���ݿ�д��ʧ��
					JOptionPane.showMessageDialog(UserOrderList.this, "����ʧ��������", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}

	}
	
	//ɾ������
	public void OrderDel() {
		//��ȡ�û�����
		DATA = orderData();
		if(DATA.isEmpty()) {	//	�û�δѡ��
			
		} else {
			//�õ�����
			String orderID = DATA.get(0);
			String condition = DATA.get(1);
			//�Ƿ����
			int option = JOptionPane.showConfirmDialog(UserOrderList.this, "ȷ��ɾ������?","��ʾ",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(option != 0 ) { //ȡ������
				
			} else if( !condition.equals("ǩ��") ) { //����״̬���Ϸ�
				JOptionPane.showMessageDialog(UserOrderList.this, "��ǰ����״̬Ϊ"+condition+"�޷����д������", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			} else {
				UserDao userDao = new UserDao();
				if( userDao.userDeleteOrder(USER_NAME, orderID)) {
					//����ɾ������
					JOptionPane.showMessageDialog(UserOrderList.this, "ɾ�������ɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					//ˢ������
					ShowOrderList();
				} else { //���ݿ����ʧ��
					JOptionPane.showMessageDialog(UserOrderList.this, "����ʧ��������", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		
	}
	
	//�޸�
	public void OrderModify() {
		//��ȡ����
		DATA = orderData();
		if( DATA.isEmpty()) {//�û�δѡ�񶩵�
			
		} else {
			String orderID = DATA.get(0);
			String condition = DATA.get(1);
			if("�ύ".equals(condition)) { //�޸��ʻ�
				new OrderModify();
				UserOrderList.this.dispose();
			} else if ("����".equals(condition)) { //�޸Ķ���״̬Ϊǩ��
				int option = JOptionPane.showConfirmDialog(UserOrderList.this, "ǩ�ն���?","��ʾ",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				if(option == 0) {
					//ȷ���ջ�
					UserDao userDao = new UserDao();
					if(userDao.UserConfirmGetOrder(USER_NAME, orderID)) {
						//�ջ��ɹ�
						JOptionPane.showMessageDialog(UserOrderList.this, "���ջ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						 ShowOrderList();
					} else {
						//�ջ�ʧ��
						JOptionPane.showMessageDialog(UserOrderList.this, "����ʧ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					
				}
			} else {
				JOptionPane.showMessageDialog(UserOrderList.this, "��ǰ����״̬Ϊ"+condition+"�޷����д������", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
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

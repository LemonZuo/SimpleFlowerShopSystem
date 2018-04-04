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

		
		//�������
		setTitle("\u6211\u7684\u8D2D\u7269\u8F66");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//����ɼ�
		setVisible(true);
		//��������
		setResizable(false);
		//�������
		this.setLocationRelativeTo(null);
		
		//����
		JLabel labTitle = new JLabel("\u4E2A\u4EBA\u4E2D\u5FC3");
		labTitle.setForeground(new Color(255, 0, 0));
		labTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labTitle.setFont(new Font("����", Font.PLAIN, 18));
		labTitle.setBounds(10, 20, 414, 41);
		contentPane.add(labTitle);

		//����������
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 414, 291);
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
		
		//�޸İ�ť
		JButton btnModify = new JButton("\u4FEE\u6539");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//�޸��ղ�����
				modify();
			}
		});
		btnModify.setBounds(10, 361, 100, 42);
		contentPane.add(btnModify);
		
		//ɾ����ť
		JButton btnDel = new JButton("\u5220\u9664");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ɾ�����ʻ�
				delete();
			}
		});
		btnDel.setBounds(171, 361, 100, 42);
		contentPane.add(btnDel);
		
		//�ύ��ť
		JButton btnSubmit = new JButton("\u63D0\u4EA4\u8BA2\u5355");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO �ύ����
				submitOrder ();
			}
		});
		btnSubmit.setBounds(324, 361, 100, 42);
		contentPane.add(btnSubmit);
		
		//��ʾ���ﳵ����
		showShopCar();
	}
	
	//���ﳵ����
	public Vector<String> rowdata() {
		//����Vector����
		Vector<String> data = new Vector<String>();
		String info = "";
		//��ȡ��ѡ����
		int row = table.getSelectedRow();
		//�ж��Ƿ�ѡ��
		if ( row == -1) {
			JOptionPane.showMessageDialog(UserShopCar.this, "δѡ���ʻ�,�޷�ִ���޸Ĳ���", "����", JOptionPane.WARNING_MESSAGE);
		} else {
			//��ȡ����
			int len = table.getColumnCount();
			for(int i = 0;i < len;i++) {
				//��ÿһ��������ӽ�ȥ
				info = String.valueOf(table.getValueAt(row, i));
				data.add(info);
			}
		}
		return data;
			
	}
	
	
	//�޸�
	public void modify() {
		//��ȡ����
		DATA = rowdata();
		//�û�δѡ���ʻ�������ҳ����ת
		if (DATA.isEmpty()) {
			
		} else {
			//��ת�޸�ҳ��
			new UserModifyShopCar();
			//�رյ�ǰ����
			this.dispose();
		}
		
	}
	
	//ɾ��
	public void delete() {
		//��ȡ����
				DATA = rowdata();
				//�û�δѡ���ʻ�������ҳ����ת
				if (DATA.isEmpty()) {
					
				} else {
					//�û�ȷ��ɾ��
					int userOption = JOptionPane.showConfirmDialog(UserShopCar.this, "�Ƿ�ȷ��ɾ��", "ɾ��ȷ�ϣ�", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(userOption == 0 ) {
						//ִ��ɾ������
						String flowerName = DATA.get(1);
						UserDao userDao = new UserDao();
						if(userDao.userDeleteShopCarFlower(USER_NAME, flowerName)) {
							JOptionPane.showMessageDialog(UserShopCar.this, "ɾ���ɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
							showShopCar();
						} else {
							JOptionPane.showMessageDialog(UserShopCar.this, "����ʧ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						}
					} else {
						//������ɾ������
					}
				}
	}
	
	//��ʾ���ﳵ����
	public void showShopCar() {
		UserDao userDao = new UserDao();
		//�����άVector����
		Vector<Vector<String>> list = userDao.getShopCar(USER_NAME);
		//�����ͷ
		Vector<String> title = new Vector<String>();
		title.add("���");
		title.add("�ʻ�");
		title.add("����");
		title.add("���ʱ��");
		title.add("����޸�ʱ��");
		//�����û���ʾ����
		if(list.isEmpty()) {
			tableModel.setDataVector(null, title);
			JOptionPane.showMessageDialog(UserShopCar.this, "δ��ѯ���������", "���ﳵΪ��", JOptionPane.INFORMATION_MESSAGE);
			UserShopCar.this.dispose();
		} else {
			tableModel.setDataVector(list, title);
		}
		
		//�Ҽ��˵�
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);
		
		//�޸�ѡ��
		JMenuItem goodsModify = new JMenuItem("�޸�");
		goodsModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//�޸Ĳ���
				modify();	
			}
		});
		popupMenu.add(goodsModify);
		
		//ɾ��ѡ��
		JMenuItem goodsDel = new JMenuItem("ɾ��");
		goodsDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ɾ���ʻ� ����delete()
				delete();
			}
		});
		popupMenu.add(goodsDel);
	}
	
	public void submitOrder () {
		UserDao userDao = new UserDao();
		//����ͳ��
		int len = table.getRowCount();
		if( len == 0) {
			JOptionPane.showMessageDialog(UserShopCar.this, "���ﳵΪ��,�޷���������","��ʾ",JOptionPane.ERROR_MESSAGE);
		} else {
			//����������
			String orderID = userDao.CreateOrderID();
			//��������
			if ( userDao.CreateOrder( USER_NAME, orderID ) ) {
				//��д�붩����Ϣ
				for(int i = 0; i < len;i++) {
					int j = 0;
					//д�붩��
					boolean flag = userDao.CreateOrderList(orderID, String.valueOf(table.getValueAt(j, 1)) , String.valueOf(table.getValueAt(j, 2)));
					if (flag) {
						//д��ɹ���ʾ
						JOptionPane.showMessageDialog(UserShopCar.this, table.getValueAt(j, 1)+" �ɹ����붩��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						userDao.userDeleteShopCarFlower(USER_NAME, String.valueOf(table.getValueAt(j, 1)));
						j++;
						//ˢ�¹��ﳵ
						showShopCar();
					} else {
						//д�붩��ʧ��
						JOptionPane.showMessageDialog(UserShopCar.this, table.getValueAt(j, 1)+" ���붩��ʧ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
						j++;
					}
				}
			} else {
				//��������ʧ��
				JOptionPane.showMessageDialog(UserShopCar.this, "��������ʧ��", "����", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	//�����Ҽ��˵�
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

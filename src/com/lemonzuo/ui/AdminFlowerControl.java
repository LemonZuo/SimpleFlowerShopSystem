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
				//������һҳ
//				new AdminMain();
			}
		});

		//����
		//����
		setTitle("\u9C9C\u82B1\u7BA1\u7406");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 514);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//����ɼ�
		setVisible(true);
		//��������
		setResizable(false);
		//�������
		setLocationRelativeTo(null);
		
		//����
		JLabel labTitle = new JLabel("\u6F2B\u6B65\u7ECF\u5FC3\u9C9C\u82B1\u7BA1\u7406");
		labTitle.setForeground(new Color(255, 51, 102));
		labTitle.setFont(new Font("����", Font.PLAIN, 18));
		labTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labTitle.setBounds(10, 10, 414, 30);
		contentPane.add(labTitle);
		
		//������ǩ
		JLabel labName = new JLabel("\u82B1\u540D\uFF1A");
		labName.setHorizontalAlignment(SwingConstants.CENTER);
		labName.setFont(new Font("����", Font.PLAIN, 16));
		labName.setBounds(10, 50, 54, 30);
		contentPane.add(labName);
		
		//��ѯ��ť
		JButton btnSerach = new JButton("\u67E5\u8BE2");
		btnSerach.addActionListener(new ActionListener() {
			//��ѯ����
			public void actionPerformed(ActionEvent arg0) {
				//��ʾ��ѯ���
				showFlower();
			}
		});
		btnSerach.setBounds(331, 50, 93, 30);
		contentPane.add(btnSerach);
		
		//���������
		textFlowerName = new JTextField();
		textFlowerName.setBounds(61, 52, 260, 30);
		textFlowerName.setMargin(new Insets(0,5,0,0));
		contentPane.add(textFlowerName);
		textFlowerName.setColumns(10);
		
		//����������
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 90, 414, 311);
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
		
		//�����ʻ���ť
		JButton btnAdd = new JButton("\u6DFB\u52A0");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//��ת�����ʻ�����
				new AdminAddFlower();
				//�رյ�ǰ����
				AdminFlowerControl.this.dispose();
			}
		});
		btnAdd.setBounds(10, 411, 104, 46);
		contentPane.add(btnAdd);
		
		//�¼��ʻ�
		JButton btnDelete = new JButton("\u4E0B\u67B6");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ִ���¼ܲ���
				delete();
			}
		});
		btnDelete.setBounds(320, 411, 104, 46);
		contentPane.add(btnDelete);
		
		//�޸İ�ť
		JButton btnMoify = new JButton("\u4FEE\u6539");
		btnMoify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//�޸Ĳ���
				modify();
			}
		});
		btnMoify.setBounds(173, 411, 104, 46);
		contentPane.add(btnMoify);
		
		//��ʼ��ʾ����
		showFlower();
	}
	
	/**
	 * ��ʾ�ʻ�����
	 */
	public void showFlower() {
		//��ȡ����
		String flowerName = textFlowerName.getText();
		UserDao userDao = new UserDao();
		//�������
		Vector<String> title = new Vector<String>();
		//��ӱ�ͷ
		title.add("���");
		title.add("�ʻ�");
		title.add("�۸�");
		title.add("�����");
		title.add("�ۼ�����");
		//��������
		Vector<Vector<String>> list = userDao.getFlowerList(flowerName);
		//��������������
		if( list.isEmpty()) {
			tableModel.setDataVector(null, title);
		} else {
			tableModel.setDataVector(list, title);
		}
		
		//�Ҽ��˵�
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);
		
		//�޸�
		JMenuItem muModify = new JMenuItem("\u4FEE\u6539");
		muModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//ִ���޸�
				modify();
			}
		});
		popupMenu.add(muModify);
		
		//ɾ��
		JMenuItem muDelete = new JMenuItem("\u4E0B\u67B6");
		muDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ִ��ɾ��
				delete();
			}
		});
		popupMenu.add(muDelete);
	}
	
	/**
	 * ��ȡѡ���е�����
	 * @return ����ѡ���е�����
	 */
	public Vector<String> rowData() {
		Vector<String> data = new Vector<String>();
		String info = "";
		//��ȡ����
		int len = table.getColumnCount();
		//��ȡ��ѡ��
		int row = table.getSelectedRow();
		//��ȡһ��ѡȡ����
		int rowCount = table.getSelectedColumnCount();
		//�ж��Ƿ�ѡ��
		if (row == -1) { //δѡ��
			JOptionPane.showMessageDialog(AdminFlowerControl.this, "�뵥�����ѡ��һ���ʻ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		} else if (rowCount > 1) { //ѡ�����
			JOptionPane.showMessageDialog(AdminFlowerControl.this, "�ʻ�������һ�޷�����", "����", JOptionPane.ERROR_MESSAGE);
		} else { //ѡȡ����
			for(int i = 0;i < len;i++) {
				//��ȡ����
				info = String.valueOf(table.getValueAt(row, i ));
				if (info == null) {
					info = "";
				}
				//�������
				data.add(info);
			}
		}
		return data;
	}
	
	
	//�޸�
	public void modify() {
		//��ȡ����
		DATA = rowData();
		if( DATA.isEmpty() ) {
			
		} else {
			//��ת�޸�ҳ��
			new AdminFlowerModify();
			//�رձ�����
			AdminFlowerControl.this.dispose();
		}

	}
	
	//�¼�
	public void delete() {
		//��ȡ����
		DATA = rowData();
		if( DATA.isEmpty() ) {
			
		} else {
			//��ȡ����
			String flowerName = DATA.get(1);
			//�¼�����
			int option = JOptionPane.showConfirmDialog(AdminFlowerControl.this, "�Ƿ��¼ܸ��ʻ�", "ȷ������", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if( option == 0 ) { //�û�ȷ��ɾ��
				UserDao userDao = new UserDao();
				if( userDao.AdminDeleteFlower(flowerName)) {
					//�¼ܳɹ�
					JOptionPane.showMessageDialog(AdminFlowerControl.this, "�¼ܳɹ�", "�������", JOptionPane.INFORMATION_MESSAGE);
					//ˢ������
					showFlower();
				} else {
					//�¼�ʧ��
					JOptionPane.showMessageDialog(AdminFlowerControl.this, "�¼�ʧ��,���Ժ�����", "�������", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				//ȡ���¼�
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

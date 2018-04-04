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
		

		
		//�������
		setTitle("\u6F2B\u6B65\u7ECF\u5FC3");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 552);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//����ɼ�
		setVisible(true);
		//��������
		setResizable(false);
		//�������
		setLocationRelativeTo(null);
		
		//������
		JLabel labTitle = new JLabel("\u6F2B  \u6B65  \u7ECF  \u5FC3");
		labTitle.setForeground(new Color(102, 204, 255));
		labTitle.setFont(new Font("΢���ź� Light", Font.PLAIN, 20));
		labTitle.setBounds(156, 29, 122, 27);
		contentPane.add(labTitle);
		
		//������ǩ
		JLabel labFlowerName = new JLabel("\u82B1\u540D\uFF1A");
		labFlowerName.setFont(new Font("����", Font.PLAIN, 16));
		labFlowerName.setBounds(21, 64, 48, 40);
		contentPane.add(labFlowerName);
		
		//���������
		textFlowerName = new JTextField();
		textFlowerName.setBounds(79, 68, 214, 36);
		//�����ڱ߾�
		textFlowerName.setMargin(new Insets(0,5,0,0));
		contentPane.add(textFlowerName);
		textFlowerName.setColumns(10);
		
		//��ѯ��ť
		JButton btnSubmit = new JButton("\u67E5\u8BE2");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				showFlower();
			}
		});
		btnSubmit.setBounds(316, 67, 93, 37);
		contentPane.add(btnSubmit);
		
		//����������
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 119, 390, 291);
		contentPane.add(scrollPane);
		
		//�������
		table = new JTable();
		tableModel = new DefaultTableModel();
		// ����table���ݾ���
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		// ���ַ�ʽ
		//tcr.setHorizontalAlignment(JLabel.CENTER);
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(Object.class, tcr);
		
		//���ñ��ģʽ
		table.setModel(tableModel);
		//����������
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
				//TODO �鿴���ж���
				new UserOrderList();
			}
		});
		btnMyOrder.setBounds(331, 438, 78, 40);
		contentPane.add(btnMyOrder);
		
		JLabel lbWelcome = new JLabel("\u6F2B\u6B65\u7ECF\u5FC3\u6B22\u8FCE\u60A8");
		if(USER_NAME != null) {
			lbWelcome.setText("�������Ļ�ӭ��"+USER_NAME);
		}
		lbWelcome.setFont(new Font("����", Font.PLAIN, 14));
		lbWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lbWelcome.setBounds(128, 438, 165, 40);
		contentPane.add(lbWelcome);
		
		//��ʾ��Ʒ��Ϣ
		showFlower();
	}
	
	/**
	 * ��ʾ��Ʒ����
	 */
	public void showFlower() {
		UserDao userDao = new UserDao();
		Vector<Vector<String>> list = userDao.getFlowerList(textFlowerName.getText());
		//�����ͷ����
		Vector<String> title = new Vector<String>();
		title.add("���");
		title.add("��Ʒ��");
		title.add("�۸�");
		title.add("�����");
		title.add("�ۼ�����");
		
		//�������������
		if(list.isEmpty()) {
			tableModel.setDataVector(null, title);
		} else {
			tableModel.setDataVector(list, title);
		}
		
		//table�Ҽ��˵�
		JPopupMenu popupMenu = new JPopupMenu();
		//��ӵ������������
		addPopup(table, popupMenu);
		//�����Ҽ��˵�ѡ��
		JMenuItem shopCar = new JMenuItem("���빺�ﳵ");
		//���˵�����ӵ��Ҽ��˵�
		popupMenu.add(shopCar);
		
		//�����Ҽ��˵�����
		shopCar.addActionListener(new ActionListener() {
			@SuppressWarnings({ })
			public void actionPerformed(ActionEvent e) {
				//��ȡ��ǰѡ�л���
				FLOWER_NAME = getFlowerName();
				//�ж��Ƿ�ѡ��һ��
				if ("".equals(FLOWER_NAME) || FLOWER_NAME == null) {
					JOptionPane.showMessageDialog(UserIndex.this, "�뵥�����ѡ���ʻ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
				} else {
					UserDao userDao = new UserDao();
					if(userDao.flowerExistShopCar(USER_NAME, FLOWER_NAME)) {
						JOptionPane.showMessageDialog(UserIndex.this, "���ʧ��,���ﳵ���Ѵ��ڸ���Ʒ", "��ʾ", JOptionPane.WARNING_MESSAGE);
					} else {
						FLOWER_ID = userDao.getFlowerId(FLOWER_NAME);
						FLOWER_NUM = userDao.getFlowerNum(FLOWER_NAME);
						new SetFlowerToShopCar();
					}
				}
			}
			
			/**
			 * �����û�ѡ�л���
			 * @return �ʻ�����
			 */
			public String getFlowerName() {
				String FlowerName = "";
				//��ȡ�����ѡ����
				int row = table.getSelectedRow();
				int rowCount = table.getSelectedRowCount();
				if(row == -1) {
					
				} else if (rowCount > 1){
					JOptionPane.showMessageDialog(UserIndex.this, "�޷�ͬʱѡ������ʻ�", "��ʾ", JOptionPane.ERROR_MESSAGE);
				}else {
					//��ȡ����
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

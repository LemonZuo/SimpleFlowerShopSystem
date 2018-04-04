package com.lemonzuo.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.lemonzuo.dao.UserDao;

@SuppressWarnings({ "serial", "unused" })
public class AdminAddFlower extends JFrame {

	private JPanel contentPane;
	private JTextField textFlowerName;
	private JTextField textFlowerPrice;
	private JTextField textFlowerCount;

	/**
	 * Create the frame.
	 */
	public AdminAddFlower() {
		//����رռ���
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				//������һҳ �ʻ�����ҳ��
				new AdminFlowerControl();
			}
		});
			
		//����
		setTitle("\u6DFB\u52A0\u9C9C\u82B1");
		//�رշ�ʽ
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 353);
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
		JLabel labTitle = new JLabel("\u6DFB\u52A0\u9C9C\u82B1");
		labTitle.setForeground(new Color(102, 153, 255));
		labTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labTitle.setFont(new Font("����", Font.PLAIN, 18));
		labTitle.setBounds(10, 10, 414, 30);
		contentPane.add(labTitle);
		
		//��ӻ�����ǩ
		JLabel labFlowerName = new JLabel("\u9C9C\u82B1:");
		labFlowerName.setFont(new Font("����", Font.PLAIN, 16));
		labFlowerName.setBounds(59, 65, 64, 30);
		contentPane.add(labFlowerName);
		
		//�۸��ǩ
		JLabel labPrice = new JLabel("\u4EF7\u683C:");
		labPrice.setFont(new Font("����", Font.PLAIN, 16));
		labPrice.setBounds(59, 123, 64, 30);
		contentPane.add(labPrice);
		
		//����ǩ
		JLabel labCount = new JLabel("\u5E93\u5B58:");
		labCount.setFont(new Font("����", Font.PLAIN, 16));
		labCount.setBounds(59, 176, 64, 30);
		contentPane.add(labCount);
		
		//���������
		textFlowerName = new JTextField();
		textFlowerName.setBounds(121, 62, 244, 30);
		textFlowerName.setMargin(new Insets(0,5,0,0));
		contentPane.add(textFlowerName);
		textFlowerName.setColumns(10);
		
		//�۸������
		textFlowerPrice = new JTextField();
		textFlowerPrice.setColumns(10);
		textFlowerPrice.setBounds(121, 123, 244, 30);
		textFlowerPrice.setMargin(new Insets(0,5,0,0));
		contentPane.add(textFlowerPrice);
		
		//��������
		textFlowerCount = new JTextField();
		textFlowerCount.setColumns(10);
		textFlowerCount.setBounds(121, 176, 244, 30);
		textFlowerCount.setMargin(new Insets(0,5,0,0));
		contentPane.add(textFlowerCount);
		
		//��Ӱ�ť
		JButton btnAdd = new JButton("\u6DFB\u52A0");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ִ�����
				add();
			}
		});
		btnAdd.setBounds(59, 244, 93, 30);
		contentPane.add(btnAdd);
		
		//ȡ����ť
		JButton btnCancel = new JButton("\u53D6\u6D88");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//�رձ�����
				AdminAddFlower.this.dispose();
			}
		});
		btnCancel.setBounds(272, 244, 93, 30);
		contentPane.add(btnCancel);
		
		//���ð�ť
		JButton btnReSET = new JButton("\u91CD\u7F6E");
		btnReSET.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//��������
				textFlowerName.setText("");
				textFlowerPrice.setText("");
				textFlowerCount.setText("");
			}
		});
		btnReSET.setBounds(162, 244, 93, 30);
		contentPane.add(btnReSET);
	}
	
	//�����ʻ�
	public void add() {
		//��ȡ����
		String flowerName = textFlowerName.getText();
		String flowerPrice = textFlowerPrice.getText();
		String flowerCount = textFlowerCount.getText();
		UserDao userDao = new UserDao();
		if( flowerName.length() == 0 || flowerPrice.length() == 0 || flowerCount.length() == 0 ) { //��Ϣ��д״̬
			JOptionPane.showMessageDialog(AdminAddFlower.this, "����д��������", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		} else if ( !userDao.matchNum(flowerPrice) ) { //�۸񲻺Ϸ�
			JOptionPane.showMessageDialog(AdminAddFlower.this, "�۸�Ƿ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		} else if ( !userDao.matchNum(flowerCount) ) { //��治�Ϸ�
			JOptionPane.showMessageDialog(AdminAddFlower.this, "���Ƿ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		} else if ( JOptionPane.showConfirmDialog(AdminAddFlower.this, "ȷ�����","��ʾ",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) != 0 ) {
			//ȷ�����
		} else if ( userDao.FlowerExist(flowerName) ) { //�Ѵ��ڸ��ʻ�
			JOptionPane.showMessageDialog(AdminAddFlower.this, "���ʻ��Ѵ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		} else if (userDao.AdminAddFlower(flowerName, flowerCount, flowerPrice)) {	//ִ�����
			JOptionPane.showMessageDialog(AdminAddFlower.this, "��ӳɹ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
			//�رձ�����
			AdminAddFlower.this.dispose();
		} else {
			//���ݿ��쳣
			JOptionPane.showMessageDialog(AdminAddFlower.this, "����ʧ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
}

package ui;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import rmi.RemoteHelper;
import ui.MainFrame.codeKeyListener;
import ui.MainFrame.codeMouseListener;

public class NewFile extends JDialog {

	public JButton confirm;
	public JButton wrong;
	private MainFrame mainFrame;
	private String newFileName = "";

	public JTextField newFileNameField;
	private static final String PROMPT = "New file name: ";

	// 判断是否登录
	private static boolean newFiled = false;

	public NewFile(JFrame frame, String title, Boolean modal, MainFrame mainFrame) {

		/**
		 * 基本设置
		 **/
		super(frame, "New File", true);
		this.mainFrame = mainFrame;
		this.setSize(250, 150);
		this.setResizable(false);
		this.setLocation(frame.getLocation().x + (frame.getWidth() - this.getWidth()) / 2,
				frame.getLocation().y + (frame.getHeight() - this.getHeight()) / 2);
		this.getContentPane().setLayout(null);

		/**
		 * 添加组件
		 **/

		// 添加prompt
		JLabel prompt = new JLabel(PROMPT);
		prompt.setFont(new Font("Arial", Font.PLAIN, 15));
		prompt.setBounds(60, 20, 140, 25);
		this.getContentPane().add(prompt);

		// 添加newFileName
		newFileNameField = new JTextField();
		newFileNameField.setBounds(40, 60, 140, 30);
		this.getContentPane().add(newFileNameField);

		// 添加确定按钮
		Icon confirmIcon = new ImageIcon("ConfirmIcon.png");
		confirm = new JButton(confirmIcon);
		confirm.setBorder(null);
		confirm.setContentAreaFilled(false);
		confirm.setFocusPainted(false);
		confirm.setBounds(190, 60, 30, 30);
		this.getContentPane().add(confirm);
		confirm.addMouseListener(new ConfirmListener(this));

		// 添加提示错误按钮
		Icon wrongIcon = new ImageIcon("WrongIcon.png");
		wrong = new JButton(wrongIcon);
		wrong.setBorder(null);
		wrong.setContentAreaFilled(false);
		wrong.setFocusPainted(false);
		wrong.setBounds(190, 20, 30, 30);
		this.getContentPane().add(wrong);
		wrong.setVisible(false);
		
		// 可见性记得放在最后，否则要repaint
		this.setVisible(true);
	}

	// 添加确定按钮的键盘监听
	class ConfirmListener extends MouseAdapter {

		private NewFile newFile;

		public ConfirmListener(NewFile newFile) {
			this.newFile = newFile;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// 变蓝
			newFile.confirm.setIcon(new ImageIcon("ConfirmIconEntered.png"));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// 变蓝
			newFile.confirm.setIcon(new ImageIcon("ConfirmIcon.png"));
		}

		// TODO 需要判断是否与已有文件重名（不做了）
		@Override
		public void mouseClicked(MouseEvent e) {
			// 获得新文件名
			boolean rightInput = true;
			if(!newFileName.equals("")){
				rightInput = false;
			}
			else{
			for (int i = 0; i < newFileNameField.getText().length(); i++) {
				char each = newFileNameField.getText().charAt(i);
				if (!((each >= '0' && each <= '9') || (each >= 'a' && each <= 'z') || (each >= 'A' && each <= 'Z'))) {
					rightInput = false;
				}
			}
			}
			
			// 若已经输入新文件名，则关闭对话框，并把save选项设为可用
			if (rightInput) {
				newFileName = newFileNameField.getText();
				this.newFile.dispose();
				NewFile.newFiled = true;
				// 将result框清空
				JTextArea tempOutput = mainFrame.getOutputSection();
				tempOutput.setText("");
				mainFrame.setOutputSection(tempOutput);
				// 将现在的文件设为新文件
				mainFrame.setCurrentFileName(newFileName);

				if (Account.isSignedIn()) {
					// 把Save设为enable
					JMenuItem tempSaveMenuItem = newFile.mainFrame.getInstance().getSaveMenuItem();
					tempSaveMenuItem.setEnabled(true);
					newFile.mainFrame.getInstance().setSaveMenuItem(tempSaveMenuItem);
					
					// 更新open里的文件
					List<String> fileList = null;
					try {
						fileList = RemoteHelper.getInstance().getIOService()
								.readFileList(mainFrame.getAccountDialog().getUserName());
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					mainFrame.addAllFile(fileList);

				}
			}
			
			else{
				wrong.setVisible(true);
	
			}

		}
	}

	public static boolean isNewFiled() {
		return newFiled;
	}

	public static void setNewFiled(boolean newFiled) {
		NewFile.newFiled = newFiled;
	}

	public String getNewFileName() {
		return this.newFileName;
	}

}

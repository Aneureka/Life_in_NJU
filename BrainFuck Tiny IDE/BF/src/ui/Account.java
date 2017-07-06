package ui;

import java.awt.Color;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import rmi.RemoteHelper;

public class Account extends JDialog {

	// 登录用户名及密码
	private String userName;
	private String password;

	// 注册用户名，填写密码
	private String newUserName;
	private String newPassword;

	// 判断是否登录
	private static boolean signedIn = false;

	// 初始化必要的组件
	private JTextField userNameField;
	private JPasswordField passwordField;
	private JButton signIn;
	private JButton signUp;
	private JButton newAccount;
	private JButton back;
	private JButton wrong;
	private MainFrame mainFrame;

	public Account(JFrame frame, String title, Boolean modal, MainFrame mainFrame) {

		/**
		 * 基本设置
		 **/
		super(frame, title, modal);
		this.mainFrame = mainFrame;
		this.setSize(400, 170);
		this.setResizable(false);
		this.setLocation(frame.getLocation().x + (frame.getWidth() - this.getWidth()) / 2,
				frame.getLocation().y + (frame.getHeight() - this.getHeight()) / 2);
		this.getContentPane().setLayout(null);

		/**
		 * 添加组件
		 **/

		// 添加提示语
		JLabel prompt = new JLabel("Please enter your username and password.");
		prompt.setFont(new Font("Arial", Font.BOLD, 17));
		prompt.setBounds(15, 15, 350, 25);
		this.getContentPane().add(prompt);

		// 添加输入账号的提示语
		JLabel userNamePrompt = new JLabel("Username: ");
		userNamePrompt.setFont(new Font("Arial", Font.PLAIN, 15));
		userNamePrompt.setBounds(15, 60, 100, 25);
		this.getContentPane().add(userNamePrompt);

		// 添加输入密码的提示语
		JLabel passwordPrompt = new JLabel("Password: ");
		passwordPrompt.setFont(new Font("Arial", Font.PLAIN, 15));
		passwordPrompt.setBounds(15, 90, 100, 25);
		this.getContentPane().add(passwordPrompt);

		// 添加输入账号或密码错误的提示语
		JLabel wrongPrompt = new JLabel("Wrong userName or password.");
		wrongPrompt.setFont(new Font("Arial", Font.PLAIN, 13));
		wrongPrompt.setForeground(Color.red);
		wrongPrompt.setBounds(90, 120, 350, 25);
		wrongPrompt.setVisible(false);
		this.getContentPane().add(wrongPrompt);

		// 添加输入账号的文本域
		userNameField = new JTextField();
		userNameField.setBounds(105, 60, 140, 25);
		this.getContentPane().add(userNameField);

		// 添加输入密码的文本域
		passwordField = new JPasswordField();
		passwordField.setBounds(105, 90, 140, 25);
		this.getContentPane().add(passwordField);

		// 添加NewAccount按钮
		newAccount = new JButton("New account");
		newAccount.setForeground(Color.BLUE);
		newAccount.setBorder(null);
		newAccount.setContentAreaFilled(false);
		newAccount.setFocusPainted(false);
		newAccount.setBounds(250, 60, 100, 25);
		newAccount.setVisible(true);
		newAccount.addMouseListener(new NewAccountListener(this));
		this.getContentPane().add(newAccount);

		// 添加Sign in按钮
		signIn = new JButton("Sign in");
		signIn.setForeground(Color.black);
		signIn.setBorder(null);
		signIn.setContentAreaFilled(false);
		signIn.setFocusPainted(false);
		signIn.setBounds(250, 90, 60, 25);
		signIn.setVisible(true);
		signIn.addMouseListener(new SignInListener(this));
		this.getContentPane().add(signIn);

		// 添加Sign up按钮，原先处于隐藏状态
		signUp = new JButton("Sign up");
		signUp.setForeground(Color.BLACK);
		signUp.setBorder(null);
		signUp.setContentAreaFilled(false);
		signUp.setFocusPainted(false);
		signUp.setBounds(250, 90, 65, 25);
		signUp.setVisible(false);
		signUp.addMouseListener(new SignUpListener(this));
		this.getContentPane().add(signUp);

		// 添加Back按钮，原先处于隐藏状态
		back = new JButton("Back");
		back.setForeground(Color.BLUE);
		back.setBorder(null);
		back.setContentAreaFilled(false);
		back.setFocusPainted(false);
		back.setBounds(250, 60, 65, 25);
		back.setVisible(false);
		back.addMouseListener(new BackListener(this));
		this.getContentPane().add(back);

		// 添加提示错误按钮
		Icon wrongIcon = new ImageIcon("WrongIcon.png");
		wrong = new JButton(wrongIcon);
		wrong.setBorder(null);
		wrong.setContentAreaFilled(false);
		wrong.setFocusPainted(false);
		wrong.setBounds(310, 85, 30, 30);
		this.getContentPane().add(wrong);
		wrong.setVisible(false);

		// 可见性再放到最后
		this.setVisible(true);
	}

	/**
	 * 添加各个按钮的鼠标监听
	 **/

	// 登陆按钮
	class SignInListener extends MouseAdapter {

		private Account account;

		public SignInListener(Account account) {
			this.account = account;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// 变白
			account.signIn.setForeground(Color.GRAY);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// 变白
			account.signIn.setForeground(Color.BLACK);
		}

		@Override
		public void mouseClicked(MouseEvent e) {

			// 获得该用户的用户名与密码并保存（在txt文件里），实现与服务器的连接，
			// 账号对比，若错误，出现提示语，若正确，成功，路径找到该用户的文件夹。
			account.userName = account.userNameField.getText();
			account.password = String.valueOf(account.passwordField.getPassword());

			boolean rightInput = true;
			if (account.userName.equals("") || account.password.equals("")) {
				rightInput = false;
			}

			else {
				for (int i = 0; i < account.userName.length(); i++) {
					char each = account.userName.charAt(i);
					if (!((each >= '0' && each <= '9') || (each >= 'a' && each <= 'z')
							|| (each >= 'A' && each <= 'Z'))) {
						rightInput = false;
					}
				}

				for (int i = 0; i < account.password.length(); i++) {
					char each = account.password.charAt(i);
					if (!((each >= '0' && each <= '9') || (each >= 'a' && each <= 'z')
							|| (each >= 'A' && each <= 'Z'))) {
						rightInput = false;
					}
				}

			}

			if (rightInput) {
				wrong.setVisible(false);
				try {
					boolean judgeSignIn = RemoteHelper.getInstance().getUserService().login(account.userName,
							account.password);

					if (judgeSignIn) {
						Account.signedIn = judgeSignIn;
						// 关闭account窗口
						account.dispose();
						// 将Account按钮改成以用户名命名的按钮
						JButton tempAccount = account.mainFrame.getInstance().getAccount();
						tempAccount.setText(userName);
						account.mainFrame.getInstance().setAccount(tempAccount);

						// 显示open里的文件
						List<String> fileList = null;
						fileList = RemoteHelper.getInstance().getIOService().readFileList(getUserName());
						mainFrame.addAllFile(fileList);
						System.out.println(mainFrame.textArea.getText());
						// 把Save设为enable
						if (!mainFrame.textArea.getText().equals("New or Open, and code here...") || !mainFrame.inputSection.getText().equals("Your input here...")) {
							JMenuItem tempSaveMenuItem = account.mainFrame.getInstance().getSaveMenuItem();
							tempSaveMenuItem.setEnabled(true);
							account.mainFrame.getInstance().setSaveMenuItem(tempSaveMenuItem);
						}

					} else {
						wrong.setVisible(true);
					}
				} catch (RemoteException e1) {

					e1.printStackTrace();
				}

			}

			else {
				wrong.setVisible(true);
			}
		}
	}

	// 注册按钮
	class SignUpListener extends MouseAdapter {

		private Account account;

		public SignUpListener(Account account) {
			this.account = account;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// 变白
			account.signUp.setForeground(Color.GRAY);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// 变白
			account.signUp.setForeground(Color.BLACK);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// 限制用户名框和密码框能键入的字符类型 1-9 a-z A-Z 等
			// 创建新用户的文件夹。

			// 获得新用户的用户名及密码
			account.newUserName = account.userNameField.getText();
			account.newPassword = String.valueOf(account.passwordField.getPassword());
			// System.out.println(account.newPassword);

			// 在面板上提示输入不合法
			boolean rightInput = true;
			if (account.newUserName.equals("") || account.newPassword.equals("")) {
				rightInput = false;
			}

			else {
				for (int i = 0; i < account.newUserName.length(); i++) {
					char each = account.newUserName.charAt(i);
					if (!((each >= '0' && each <= '9') || (each >= 'a' && each <= 'z')
							|| (each >= 'A' && each <= 'Z'))) {
						rightInput = false;
					}
				}

				for (int i = 0; i < account.newPassword.length(); i++) {
					char each = account.newPassword.charAt(i);
					if (!((each >= '0' && each <= '9') || (each >= 'a' && each <= 'z')
							|| (each >= 'A' && each <= 'Z'))) {
						rightInput = false;
					}
				}

			}

			if (rightInput) {
				wrong.setVisible(false);
				try {
					boolean judgeSignUp = RemoteHelper.getInstance().getUserService().signUp(account.newUserName,
							account.newPassword);
					if (!judgeSignUp) {
						wrong.setVisible(true);

					} else {
						// 面板上提示注册成功
						wrong.setVisible(false);
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}

			else {
				wrong.setVisible(true);
			}
		}
	}

	// 创建新用户跳转按钮
	class NewAccountListener extends MouseAdapter {

		private Account account;

		public NewAccountListener(Account account) {
			this.account = account;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// 变白
			account.newAccount.setForeground(Color.GRAY);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// 变白
			account.newAccount.setForeground(Color.BLUE);
		}

		@Override
		public void mouseClicked(MouseEvent e) {

			// 隐藏原先两个按钮， 显示后来两个按钮
			account.newAccount.setVisible(false);
			account.signIn.setVisible(false);
			account.signUp.setVisible(true);
			account.back.setVisible(true);
			// 隐藏错误提示
			wrong.setVisible(false);
			// 清空textField
			account.userNameField.setText("");
			account.passwordField.setText("");

		}

	}

	// 取消创建新用户跳转按钮
	class BackListener extends MouseAdapter {

		private Account account;

		public BackListener(Account account) {
			this.account = account;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// 变白
			account.back.setForeground(Color.GRAY);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// 变白
			account.back.setForeground(Color.BLUE);
		}

		@Override
		public void mouseClicked(MouseEvent e) {

			// 隐藏后来两个按钮， 显示原先两个按钮
			account.newAccount.setVisible(true);
			account.signIn.setVisible(true);
			account.signUp.setVisible(false);
			account.back.setVisible(false);
			// 隐藏错误提示
			wrong.setVisible(false);
			// 清空textField
			account.userNameField.setText("");
			account.passwordField.setText("");
		}

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewUserName() {
		return newUserName;
	}

	public void setNewUserName(String newUserName) {
		this.newUserName = newUserName;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public static boolean isSignedIn() {
		return signedIn;
	}

	public void setSignedIn(boolean signedIn) {
		Account.signedIn = signedIn;
	}

	public Account getInstance() {
		return this;
	}
}

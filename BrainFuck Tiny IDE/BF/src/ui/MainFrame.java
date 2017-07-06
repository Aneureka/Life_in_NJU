package ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Popup;
import javax.swing.PopupFactory;

import rmi.RemoteHelper;

public class MainFrame extends JFrame {

	private JFrame frame;
	private NewFile newFile;
	private Account accountDialog;

	public JTextArea textArea;
	public JTextArea inputSection;
	public JTextArea outputSection;

	private RemoteHelper remoteHelper;
	private JMenuItem saveMenuItem;
	private JMenu openMenu;
	private JMenu versionMenu;
	private JButton wrong;
	private JButton account;
	private String currentFileName;
	private PopupFactory factory;
	private Popup popup;
	private JLabel accountPrompt;
	private JLabel undoPrompt;
	private boolean prompting;
	
	// undo redo
	private JMenu operateMenu;
	private JMenuItem undoMenuItem;
	private JMenuItem redoMenuItem;
	private ArrayList<String> codeList = new ArrayList<>();
	private int listIndex = 0;
	private int keyCode = -1;
	private String selectedText;
	private boolean firstUndo = true;

	public MainFrame() {
		// 创建窗体
		frame = new JFrame("BF Client");
		// 连接remoteHelper
		remoteHelper = RemoteHelper.getInstance();
		// Font font = new Font("黑体", Font.PLAIN, 10);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.lightGray);
		menuBar.setBorderPainted(false);
		menuBar.setBounds(0, 0, 10, 10);

		// 添加File菜单
		JMenu fileMenu = new JMenu("File");
		// fileMenu.setFont(font);
		menuBar.add(fileMenu);
		JMenuItem newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		openMenu = new JMenu("Open"); // 添加Open(二级菜单)
		fileMenu.add(openMenu);
		JMenuItem fileList = new JMenuItem("fileList");
		openMenu.add(fileList);
		saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setEnabled(false); // 未登录情况下save不可用
		fileMenu.add(saveMenuItem);
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		frame.setJMenuBar(menuBar);

		// 添加Run菜单
		JMenu runMenu = new JMenu("Run");
		// runMenu.setFont(font);
		menuBar.add(runMenu);
		JMenuItem executeMenuItem = new JMenuItem("Execute");
		runMenu.add(executeMenuItem);

		// 添加Version菜单
		versionMenu = new JMenu("Version");
		// versionMenu.setFont(font);
		menuBar.add(versionMenu);

		// 添加Operate菜单，添加undo redo菜单栏
		operateMenu = new JMenu("Operate");
		menuBar.add(operateMenu);
		undoMenuItem = new JMenuItem("Undo");
		operateMenu.add(undoMenuItem);
		redoMenuItem = new JMenuItem("Redo");
		operateMenu.add(redoMenuItem);

		// 添加第一次undo时的气泡操作提示
		undoPrompt = new JLabel("  Please click again to do FIRST undo.");
		undoPrompt.setBackground(Color.lightGray);
		undoPrompt.setOpaque(true);
		undoPrompt.setForeground(Color.WHITE);
		undoPrompt.setVisible(false);
		menuBar.add(undoPrompt);
		//prompting = false;

		// 添加错误按钮，在用户之前
		Icon wrongIcon = new ImageIcon("WrongIcon.png");
		wrong = new JButton(wrongIcon);
		wrong.setBorder(null);
		wrong.setContentAreaFilled(false);
		wrong.setFocusPainted(false);
		wrong.setVisible(false);
		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(wrong);

		// 添加用户按钮
		account = new JButton("Account");
		account.setBackground(Color.lightGray);
		account.setBorderPainted(false);
		account.setContentAreaFilled(false);
		account.setFocusPainted(false);
		menuBar.add(account);

		// 添加用户登录时的气泡操作提示
		factory = PopupFactory.getSharedInstance();
		accountPrompt = new JLabel("Please click twice to log out.");
		accountPrompt.setBackground(Color.WHITE);
		accountPrompt.setOpaque(true);
		prompting = false;

		// 文本编辑代码区
		textArea = new JTextArea();
		// textArea.setWrapStyleWord(true);
		JScrollPane panelTextArea = new JScrollPane(textArea);
		panelTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setBackground(Color.WHITE);
		textArea.setText("New or Open, and code here...");
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		frame.add(panelTextArea);

		// 数据输入区
		inputSection = new JTextArea();
		// inputSection.setWrapStyleWord(true);
		JScrollPane panelInputSection = new JScrollPane(inputSection);
		panelInputSection.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		inputSection.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.lightGray, 1), "Parameter"));
		inputSection.setBackground(Color.WHITE);
		inputSection.setMargin(new Insets(10, 10, 10, 10));
		inputSection.setText("Your input here...");
		inputSection.setEditable(false);
		inputSection.setLineWrap(true);
		frame.add(panelInputSection);

		// 数据输出区
		outputSection = new JTextArea();
		// outputSection.setWrapStyleWord(true);
		JScrollPane panelOutputSection = new JScrollPane(outputSection);
		panelOutputSection.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		outputSection.setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.lightGray, 1), "Result"));
		outputSection.setBackground(Color.WHITE);
		outputSection.setMargin(new Insets(10, 10, 10, 10));
		outputSection.setEditable(false);
		outputSection.setLineWrap(true);
		frame.add(panelOutputSection);

		// 设置布局
		GridBagLayout layout = new GridBagLayout();
		frame.setLayout(layout);
		// 设置Constraints
		GridBagConstraints s = new GridBagConstraints();
		s.fill = GridBagConstraints.BOTH;

		s.gridwidth = 0;
		s.weightx = 1;
		s.weighty = 0;
		layout.setConstraints(menuBar, s);

		s.gridwidth = 0;
		s.gridheight = 1;
		s.weightx = 1;
		s.weighty = 1;
		layout.setConstraints(panelTextArea, s);

		s.gridwidth = 1;
		s.weightx = 0.5;
		s.weighty = 0.3;
		layout.setConstraints(panelInputSection, s);

		s.gridwidth = 0;
		s.weightx = 0.5;
		s.weighty = 0.3;
		layout.setConstraints(panelOutputSection, s);

		// 添加各个菜单和按钮的监听
		newMenuItem.addActionListener(new MenuItemActionListener());
		openMenu.addActionListener(new MenuItemActionListener());
		saveMenuItem.addActionListener(new MenuItemActionListener());
		exitMenuItem.addActionListener(new MenuItemActionListener());
		executeMenuItem.addActionListener(new MenuItemActionListener());
		undoMenuItem.addActionListener(new MenuItemActionListener());
		redoMenuItem.addActionListener(new MenuItemActionListener());
		account.addMouseListener(new AccountListener(account));
		textArea.addKeyListener(new codeKeyListener());
		textArea.addMouseListener(new codeMouseListener());

		// frame的一些基本设置
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(610, 520);
		frame.setLocation(400, 200);
		frame.setVisible(true);
	}

	// 让Version菜单显示文件版本的方法
	public void addAllVersion(List<String> versionNameList) {

		// 先清空所有的文件
		this.versionMenu.removeAll();

		for (String version : versionNameList) {
			JMenuItem versionItem = new JMenuItem(version);
			versionMenu.add(versionItem);
			versionItem.addActionListener(new MenuItemActionListener());
		}
	}

	// 让open二级菜单显示文件的方法， 让newFile时调用，newAccount时调用
	public void addAllFile(List<String> fileNameList) {

		// 先清空所有的文件
		this.openMenu.removeAll();

		for (String file : fileNameList) {
			JMenuItem fileItem = new JMenuItem(file);
			openMenu.add(fileItem);
			fileItem.addActionListener(new MenuItemActionListener());
		}

	}

	public class MenuItemActionListener implements ActionListener {
		/**
		 * 子菜单响应事件
		 */
		@Override
		public void actionPerformed(ActionEvent e) {

			// 获得菜单和按钮命令
			String cmd = e.getActionCommand();

			if (cmd.equals("New")) {
				// 弹出新窗口，用于输入新文件名
				newFile = new NewFile(frame, "New File", true, getInstance());
				//
				if (!newFile.getNewFileName().equals("")) {
					textArea.setEditable(true);
					textArea.setText("");
					inputSection.setEditable(true);
					inputSection.setText("");
				}
			}

			else if (cmd.equals("Save")) {

				String code = textArea.getText();
				String param = inputSection.getText();
				String file = code + "\n" + param;

				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				String time = df.format(new Date());

				// 判断是否登录，如果没登陆，就没反应，并给出提示
				if (Account.isSignedIn()) {
					try {
						// 写文件
						boolean successfulWrite = remoteHelper.getIOService().writeFile(file,
								accountDialog.getUserName(), newFile.getNewFileName(), time);
						// 判断文件是否写入成功
						if (successfulWrite) {
							// 成功，则在界面更新版本
							List<String> allVersion;
							allVersion = remoteHelper.getIOService().readVersionList(accountDialog.getUserName(),
									newFile.getNewFileName());
							addAllVersion(allVersion);
							wrong.setVisible(false);
						}

						else {
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

			else if (cmd.equals("Exit"))

			{
				frame.dispose();
			}

			else if (cmd.equals("Execute"))

			{
				// 获得代码和输入
				String code = textArea.getText();
				String param = inputSection.getText();
				// 预设结果
				String result = "Wrong";
				try {
					result = remoteHelper.getExecuteService().execute(code, param);

				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				outputSection.setText(result);

			}

			else if (cmd.equals("Undo")) {
				
				// 如果是第一次undo，给出提示
				if(firstUndo){
					undoPrompt.setVisible(true);
				}
				
				else{
					undoPrompt.setVisible(false);
				}
				firstUndo = false;
				
				if (listIndex - 1 >= 0) {
					listIndex--;
					textArea.setText(codeList.get(listIndex));
					wrong.setVisible(false);
				} else {
					wrong.setVisible(true);
				}
			}

			else if (cmd.equals("Redo")) {
				if (listIndex + 1 < codeList.size()) {
					listIndex++;
					textArea.setText(codeList.get(listIndex));
					wrong.setVisible(false);
				} else {
					wrong.setVisible(true);
				}
			}

			// 对每个文件和版本的操作
			else {
				try {
					// 判断是不是点到open里的文件
					boolean cmdFile = false;

					// 获得文件和版本列表，再与cmd做比较
					List<String> versionList = new ArrayList<>();
					List<String> fileList = new ArrayList<>();
					versionList = remoteHelper.getIOService().readVersionList(accountDialog.getUserName(),
							currentFileName);
					fileList = remoteHelper.getIOService().readFileList(accountDialog.getUserName());

					for (String eachFile : fileList) {
						if (cmd.equals(eachFile)) {
							// 将现在的文件设为cmd
							setCurrentFileName(cmd);
							versionList = remoteHelper.getIOService().readVersionList(accountDialog.getUserName(),
									currentFileName);
							List<String> file = new ArrayList<>();
							file = remoteHelper.getIOService().readFile(accountDialog.getUserName(), cmd);
							// 显示对应文件最新的代码和输入
							textArea.setText(file.get(0));
							inputSection.setText(file.get(1));
							addAllVersion(versionList);
							// cmdFile 设为true
							cmdFile = true;
							// 将textArea设为可写
							textArea.setEditable(true);
							inputSection.setEditable(true);
							// 将save设为enable
							saveMenuItem.setEnabled(true);	
							// undoredo添加监听
							textArea.addKeyListener(new codeKeyListener());
							textArea.addMouseListener(new codeMouseListener());
						}
					}

					if (!cmdFile) {
						for (String version : versionList) {
							if (cmd.equals(version)) {
								List<String> versionFile = new ArrayList<>();
								versionFile = remoteHelper.getIOService().readFileVersion(accountDialog.getUserName(),
										currentFileName, cmd);
								// 显示对应版本文件的代码和输入
								textArea.setText(versionFile.get(0));
								inputSection.setText(versionFile.get(1));
							}
						}
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}

		}

	}

	class AccountListener extends MouseAdapter {

		private JButton account;

		public AccountListener(JButton account) {
			this.account = account;

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			account.setForeground(Color.WHITE);

			// 实现将鼠标移到窗口的时候出现气泡提示
			if (Account.isSignedIn()) {
				popup = factory.getPopup(account, accountPrompt, frame.getLocation().x + frame.getWidth() - 175,
						frame.getLocation().y + 70);
				popup.show();
				prompting = true;
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			account.setForeground(Color.BLACK);

			// 鼠标移出窗口时关闭气泡提示
			if (prompting) {
				popup.hide();
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (account.getText().equals("Account")) {
				accountDialog = new Account(frame, "Account", true, getInstance());
			} else {

				if (e.getClickCount() == 2) {
					account.setText("Account");
					accountDialog.setSignedIn(false);
					saveMenuItem.setEnabled(false);
				}
			}

		}

	}

	// 创建textArea的键盘、鼠标监听类（undo redo）,所有操作均在text Editable为true才进行
	class codeKeyListener implements KeyListener {

		// 保存在keyRealeased之前的文本，可能会用到
		String textForKeyReleased;

		@Override
		public void keyTyped(KeyEvent e) {

			if (textArea.isEditable()) {

				if (keyCode == -1) {
					codeList.add(listIndex, textArea.getText());
					// 删除掉这次修改所占位置后面的内容（如果有的话）
					if (listIndex < codeList.size() - 1) {
						for (int i = listIndex; i < codeList.size(); i++) {
							codeList.remove(i);
						}
					}
					listIndex++;
				}

				else if (selectedText != null) {
					if ((listIndex != 0) && !textArea.getText().equals(codeList.get(codeList.size() - 1))) {
						codeList.add(listIndex, textArea.getText());
						// 删除掉这次修改所占位置后面的内容（如果有的话）
						if (listIndex < codeList.size() - 1) {
							for (int i = listIndex; i < codeList.size(); i++) {
								codeList.remove(i);
							}
						}
						listIndex++;
					}
				}

				textForKeyReleased = textArea.getText();

			}
		}

		@Override
		public void keyPressed(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent e) {

			if (textArea.isEditable()) {
				if (selectedText != null) {
					if ((listIndex != 0) && !textArea.getText().equals(codeList.get(codeList.size() - 1))) {
						codeList.add(listIndex, textArea.getText());
						// 删除掉这次修改所占位置后面的内容（如果有的话）
						if (listIndex < codeList.size() - 1) {
							for (int i = listIndex; i < codeList.size(); i++) {
								codeList.remove(i);
							}
						}
						listIndex++;
					}
				}

				else if ((keyCode == KeyEvent.VK_BACK_SPACE && e.getKeyCode() != KeyEvent.VK_BACK_SPACE)
						|| (keyCode != KeyEvent.VK_BACK_SPACE && e.getKeyCode() == KeyEvent.VK_BACK_SPACE)) {
					if (!textArea.getText().equals(codeList.get(codeList.size() - 1))) {
						codeList.add(listIndex, textForKeyReleased);
						// 删除掉这次修改所占位置后面的内容（如果有的话）
						if (listIndex < codeList.size() - 1) {
							for (int i = listIndex; i < codeList.size(); i++) {
								codeList.remove(i);
							}
						}
						listIndex++;
					}
				}

				textForKeyReleased = textArea.getText();
				selectedText = null;
				keyCode = e.getKeyCode();
				// System.out.println(e.getKeyCode());
				textForKeyReleased = textArea.getText();
			}
		}
	}

	class codeMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (textArea.isEditable()) {
				// 若鼠标点击时文本已经改动，则需要add新的文本
				if ((listIndex != 0) && !textArea.getText().equals(codeList.get(codeList.size() - 1))) {
					codeList.add(listIndex, textArea.getText());
					// 删除掉这次修改所占位置后面的内容（如果有的话）
					if (listIndex < codeList.size() - 1) {
						for (int i = listIndex; i < codeList.size(); i++) {
							codeList.remove(i);
						}
					}
					listIndex++;
					// System.out.println(codeList);
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (textArea.isEditable()) {
				// 更新选中的文本
				selectedText = textArea.getSelectedText();
				// add选中之前的文本
				

			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public JMenuItem getSaveMenuItem() {
		return saveMenuItem;
	}

	public void setSaveMenuItem(JMenuItem saveMenuItem) {
		this.saveMenuItem = saveMenuItem;
	}

	public JButton getAccount() {
		return account;
	}

	public void setAccount(JButton account) {
		this.account = account;
	}

	public NewFile getNewFile() {
		return newFile;
	}

	public JTextArea getOutputSection() {
		return outputSection;
	}

	public void setOutputSection(JTextArea outputSection) {
		this.outputSection = outputSection;
	}

	public void setNewFile(NewFile newFile) {
		this.newFile = newFile;
	}

	public Account getAccountDialog() {
		return accountDialog;
	}

	public void setAccountDialog(Account accountDialog) {
		this.accountDialog = accountDialog;
	}

	public String getCurrentFileName() {
		return currentFileName;
	}

	public void setCurrentFileName(String currentFileName) {
		this.currentFileName = currentFileName;
	}

	public RemoteHelper getRemoteHelper() {
		return remoteHelper;
	}

	public MainFrame getInstance() {
		return this;
	}
}

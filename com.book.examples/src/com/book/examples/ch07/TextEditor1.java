package com.book.examples.ch07;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.ResourceManager;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridLayout;

public class TextEditor1 {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			TextEditor1 window = new TextEditor1();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(1002, 673);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		Label lblNewLabel = new Label(scrolledComposite, SWT.BORDER);
		lblNewLabel.setImage(SWTResourceManager.getImage("D:\\git\\eclipse-plugins\\com.book.examples\\icons\\gl.jpg"));
		lblNewLabel.pack();
		scrolledComposite.setContent(lblNewLabel);
		scrolledComposite.setMinSize(lblNewLabel.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Browser browser = new Browser(shell, SWT.NONE);
		browser.setUrl("https://www.baidu.com/");
		
		StyledText styledText = new StyledText(shell, SWT.BORDER);
		styledText.setText("\u5728SWT\u7684table\u4E2D\u6216\u5728JFace\u7684TableViewer\u4E2D\u6DFB\u52A0checkbox\r\n\uFF081\uFF09\u5982\u679C\u662F\u5728\u7B2C\u4E00\u5217\uFF0C\u5219\u53EF\u4EE5\u4F7F\u7528SWT.CHECK\u3000\u6837\u5F0F\u6765\u5B9E\u73B0\uFF0C\u4F8B\u5982\uFF1A\r\n\r\n       Table table = new Table(parent, SWT.CHECK)\uFF1B\r\n\r\n\uFF082\uFF09\u5982\u679C\u4E0D\u662F\u7B2C\u4E00\u5217\uFF0C\u5219\u53EF\u4EE5\u4F7F\u7528TableEditor\uFF0C\u521B\u5EFA\u4E00\u4E2AControl\u60AC\u6D6E\u5728CellEditor\u4E4B\u4E0A\uFF0C\u4F8B\u5982\uFF1A\r\n\r\n       TableEditor editor = new TableEditor(table);\r\n       Button button = new Button(table, SWT.CHECK);\r\n       button.pack();\r\n       editor.minimumWidth = button.getSize().x;\r\n       editor.horizontalAlignment = SWT.LEFT;\r\n       editor.setEditor(button, items[i], 2);\r\n\r\nDemo\u8FDE\u63A5\uFF1A\r\n\r\nhttp://www.java2s.com/Tutorial/Java/0280__SWT/TableCellEditorComboTextandButton.htm");

	}
}

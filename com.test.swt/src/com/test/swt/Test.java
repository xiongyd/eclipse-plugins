package com.test.swt;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Test extends AbstractExample {

	private Label infoLabel;
	private Text usernameText;
	private Text passwordText;
	private Combo roleCombo;
	
	private Button rememberPWBtn=null;
	private Button autoLoginBtn=null;
	
	@Override
	public void todo(Shell shell) {
		Group group = new Group(shell, SWT.NONE);
		group.setText("用户登录");
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 30;
		layout.marginHeight = 10;
		group.setLayout(layout);
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		{
			Composite cp = new Composite(group, SWT.NONE);
			GridLayout layoutcp = new GridLayout();
			layoutcp.numColumns = 2;
			layoutcp.marginHeight = 1;
			cp.setLayout(layoutcp);
			cp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,true,2,2));
			
			infoLabel = new Label(cp, SWT.NONE);
			infoLabel.setText("请输入用户名 密码");
			infoLabel.setLayoutData(new GridData(GridData.FILL_BOTH));
			infoLabel.setAlignment(SWT.RIGHT);
		}
		
		{
            Label usernameLabel = new Label(group,SWT.NONE);
            usernameLabel.setText("username:");
            
            usernameText = new Text(group,SWT.BORDER);
            usernameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        }
        {
            Label passwordLabel = new Label(group,SWT.NONE);
            passwordLabel.setText("password:");
            
            passwordText = new Text(group,SWT.BORDER | SWT.PASSWORD);
            passwordText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        }
        {
            Label roleLabel = new Label(group,SWT.NONE);
            roleLabel.setText("role:");
            
            roleCombo = new Combo(group,SWT.DROP_DOWN);
            roleCombo.setItems(new String[]{"Admin","custom"});
            roleCombo.select(1);
        }
        
            new Label(group,SWT.NONE);
            rememberPWBtn = new Button(group,SWT.CHECK);
            rememberPWBtn.setText("记住密码");

        
            new Label(group,SWT.NONE);
            autoLoginBtn = new Button(group,SWT.CHECK);
            autoLoginBtn.setText("自动登录");
        
        
        {
            new Label(group,SWT.NONE);
            
            Button loginBtn = new Button(group,SWT.PUSH);
            loginBtn.setText("login");
            
            loginBtn.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent evt){
                    infoLabel.setText("login successfully.");
                    
                    usernameText.setText("");
                    usernameText.setEnabled(false);
                    
                    passwordText.setText("");
                    passwordText.setEnabled(false);
                    
                    roleCombo.setEnabled(false);
                    
                    rememberPWBtn.setEnabled(false);
                    autoLoginBtn.setEnabled(false);
                }
            });
        }
	}

	public static void main(String[] args) {
		new Test().run();
	}

}

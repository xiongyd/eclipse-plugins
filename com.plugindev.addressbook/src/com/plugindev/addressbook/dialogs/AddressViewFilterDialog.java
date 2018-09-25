package com.plugindev.addressbook.dialogs;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.plugindev.addressbook.models.AddressCategory;

public class AddressViewFilterDialog extends Dialog {

	private String namePattern;
	private Collection<AddressCategory> selectedCategories;
	private Text namePatternField;
	private HashMap categoryFields;
	
	public AddressViewFilterDialog(Shell containerShell, String namePattern, AddressCategory[] addressCategories) {
		super(containerShell);
		this.namePattern = namePattern;
		this.selectedCategories = new HashSet<>();
		for (int i=0;i<addressCategories.length;i++) {
			this.selectedCategories.add(addressCategories[i]);
		}
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		container.setLayout(gridLayout);
		
		Label filterLabel = new Label(container, SWT.NONE);
		filterLabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 2, 1));
		filterLabel.setText("输入一个名字过滤器(* = 任何字符串 "+" ? = 任何单个字符"+"\n或者置空以不选择任何过滤器");		
		
		Label nameLabel = new Label(container, SWT.NONE);
		nameLabel.setLayoutData(new GridData(GridData.END, GridData.CENTER, false, false));
		nameLabel.setText("名字");
		
		namePatternField = new Text(container, SWT.BORDER);
		namePatternField.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
	
		Label typesLabel = new Label(container, SWT.NONE);
		typesLabel.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 2, 1));
		typesLabel.setText("选择将要选择的AddressItem类别");
		
		Composite checkBoxComposite = new Composite(container, SWT.NONE);
		GridData gridData1 = new GridData(GridData.FILL, GridData.FILL, false, false, 2, 1);
		gridData1.horizontalIndent = 20;
		checkBoxComposite.setLayoutData(gridData1);
		
		GridLayout typeCheckboxLayout = new GridLayout();
		typeCheckboxLayout.numColumns = 2;
		checkBoxComposite.setLayout(typeCheckboxLayout);
		
		createCheckboxes(checkBoxComposite);
		initContent();
		return container;
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		// TODO Auto-generated method stub
		super.configureShell(newShell);
		newShell.setText("\"地址本\"视图过滤");
	}

	private void initContent() {
		namePatternField.setText(namePattern!=null ? namePattern : "");
		namePatternField.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				namePattern = namePatternField.getText();
			}
		});
		
		AddressCategory[] allCategories = AddressCategory.getTypes();
		for (int i=0;i<allCategories.length;i++) {
			AddressCategory eachType = allCategories[i];
			Button button = (Button)categoryFields.get(eachType);
			button.setSelection(selectedCategories.contains(eachType));
		}
	}

	private void createCheckboxes(Composite parent) {
		categoryFields = new HashMap();
		AddressCategory[] allCategories = AddressCategory.getTypes();
		for (int i=0;i<allCategories.length;i++) {
			AddressCategory category = allCategories[i];
			Button button = new Button(parent, SWT.CHECK);
			button.setText(category.getCategoryName());
			categoryFields.put(category, button);
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (button.getSelection()) {
						selectedCategories.add(category);
					} else {
						selectedCategories.remove(category);
					}
				}
			});
		}
	}

	public String getNamePattern() {
		return this.namePattern;
	}

	public AddressCategory[] getSelectedCategories() {
		return this.selectedCategories.toArray(new AddressCategory[selectedCategories.size()]);
	}
}

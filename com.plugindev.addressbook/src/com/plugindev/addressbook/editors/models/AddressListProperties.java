package com.plugindev.addressbook.editors.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

public class AddressListProperties implements IDetailsPage {

	protected IManagedForm mform;
	private AddressList input;
	private Button[][] choices;
	private Text[] texts;
	private Map<Text,String> textMap;
	private Map<Button,String> buttonMap;
	
	private Section s1;
	
	private boolean updated;
	
	private static final String RTEXT_DATA =
			"<form><p>本项内容用来设置每个人员的个人信息，"+
			"这个信息将会在其他两个页面中以不同的方式显示</p>"+
			"<p>如果有问题请联系作者<b>张鹏</b>.</p>"+
			"<li>email:<a>nemo.zhp@gmail.com</a></li>"+
			"<li>网址：http://www.blogjava.net/nemo-zhp</li>"+
			"</form>";
	
	public AddressListProperties() {
		
	}
	
	@Override
	public void initialize(IManagedForm form) {
		this.mform = form;
		this.textMap = new HashMap<Text,String>();
		this.buttonMap = new HashMap<Button,String>();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void commit(boolean onSave) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean setFormInput(Object input) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFocus() {
		choices[0][0].setFocus();
	}

	@Override
	public boolean isStale() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void refresh() {
		update();
	}

	private void update() {
		
		//13.8.1 已修改的编辑器 BEGIN
		if (updated == false) { //当调用此方法是updated为false,表明不为初始化或重新读取模型时的更新
			updated = true;
		}
		//13.8.1 已修改的编辑器 END
		
		//更新选择项
		for (int i=0;i<choices.length;i++) {
			for (int j=0;j<choices[i].length;j++) {
				boolean selected = input != null && input.getIntValue(buttonMap.get(choices[i][j]))==j;
				choices[i][j].setSelection(selected);
			}
		}
		
		for (int i=0;i<texts.length;i++) {
			String key = textMap.get(texts[i]);
			String txt = (input != null && input.getStringValue(key) != null) ? input.getStringValue(key) : "";
			texts[i].setText(txt);
		}
		
		//13.8.1 已修改的编辑器
		updated = false; //修改完毕,updated改为false
		
	}

	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection) selection;
		if (ssel.size() == 1) {
			input = (AddressList)ssel.getFirstElement();
			/*
			 * 在第十五章加入下面的代码，将文本和描述从setContent()中固定实现变为随着选择的不同动态改变
			 * 修复当改变AddressList时，DetailsPart的标题和描述不随之改变的BUG
			 */
			s1.setText(input.getName());
			s1.setDescription(input.getDescription());
		}
		else
			input = null;
		
		update();
	}

	@Override
	public void createContents(Composite parent) {
		TableWrapLayout layout = new TableWrapLayout();
		layout.topMargin = 5;
		layout.leftMargin = 5;
		layout.rightMargin = 2;
		layout.bottomMargin = 2;
		parent.setLayout(layout);
	}
	

	protected void createSection(Composite parent,
			List<String> stringKeys, Map<String,Object[]> choiceKeysMap) {
		FormToolkit toolkit = mform.getToolkit();
		s1 = toolkit.createSection(parent, Section.DESCRIPTION|Section.TITLE_BAR);
		s1.marginHeight = 10;
		
		TableWrapData td = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		td.grabHorizontal = true;
		s1.setLayoutData(td);
		
		Composite client = toolkit.createComposite(s1);
		GridLayout glayout = new GridLayout();
		glayout.marginWidth = glayout.marginHeight = 0;
		glayout.numColumns = 2;
		client.setLayout(glayout);
		
		createChoiceItem(toolkit, client, choiceKeysMap);
		createTextItem(toolkit, client, stringKeys);
		
		FormText rtext = toolkit.createFormText(parent, true);
		rtext.setText(RTEXT_DATA, true, false);
		td = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		td.grabHorizontal = true;
		rtext.setLayoutData(td);
		
		toolkit.paintBordersFor(s1);
		s1.setClient(client);
	}

	private void createTextItem(FormToolkit toolkit, Composite parent, List<String> textKeys) {
		
		texts = new Text[textKeys.size()];
		Iterator<String> iter = textKeys.iterator();
		
		//遍历TextItemContents
		//为每个TextItemContents添加一个标签和文本框
		//并且为每个TextItemContents添加一个修改监听器
		for (int i=0;iter.hasNext();i++) {
			String key = (String)iter.next();
			toolkit.createLabel(parent, key);
			texts[i] = toolkit.createText(parent, "");
			GridData gd = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_BEGINNING);
			gd.widthHint = 10;
			texts[i].setLayoutData(gd);
			textMap.put(texts[i], key);
			
			//为文本框添加修改监听器
			texts[i].addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					if (updated == true) { //当此时updated为true,表明为初始化或重新读取模型,因而不调用update()方法
						return;
					}
					
					Text text = (Text)e.getSource();
					if (input != null) {
						input.setStringValue(textMap.get(text), text.getText());
					}
				}
			});
			createSpacer(toolkit, parent, 2);
		}
		
	}

	private void createChoiceItem(FormToolkit toolkit, Composite parent, Map<String, Object[]> choiceKeysMap) {
		//有choiceKeysMap.size()个选择项
		Iterator<String> iter = choiceKeysMap.keySet().iterator();
		choices = new Button[choiceKeysMap.size()][];
		
		//遍历ChoiceItemContents
		//为每个ChoiceItemContents添加一个标签和多个选项按钮
		//并且为每个ChoiceItemContents添加一个选择监听器
		for (int i=0;iter.hasNext();i++) {
			GridData gd;
			
			//创建选择适配器
			SelectionListener choiceListener = new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					if (updated == true) {
						return;
					}
					
					Button button = (Button)e.getSource();
					Integer value = (Integer)e.widget.getData();
					if (input != null) {
						input.setIntValue(buttonMap.get(button), value);
					}
				}
			};
			
			String key = (String)iter.next();
			
			//创建Label
			Label label = toolkit.createLabel(parent, key+": ");
			GridData gdata = new GridData(GridData.FILL_HORIZONTAL);
			gdata.horizontalSpan = 2;
			label.setLayoutData(gdata);
			
			Object[] choiceStrings = (Object[])choiceKeysMap.get(key);
			choices[i] = new Button[choiceStrings.length];
			
			//为选择按钮添加选择监听器
			//在同一个choiceContentItem中的一组选择按钮对应相同的选择监听器
			for (int j=0;j<choices[i].length;j++) {
				choices[i][j] = toolkit.createButton(parent, (String)choiceStrings[j], SWT.RADIO);
				choices[i][j].setData(new Integer(j));
				buttonMap.put(choices[i][j], key);
				
				//为每个按钮添加选择监听器
				choices[i][j].addSelectionListener(choiceListener);
				
				gd = new GridData();
				gd.horizontalSpan = 1;
				choices[i][j].setLayoutData(gdata);
			}
			createSpacer(toolkit, parent, 2);
		}
		
	}

	private void createSpacer(FormToolkit toolkit, Composite parent, int span) {
		Label spacer = toolkit.createLabel(parent, "");
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		spacer.setLayoutData(gd);
	}
}

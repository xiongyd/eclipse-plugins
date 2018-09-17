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
			"<form><p>����������������ÿ����Ա�ĸ�����Ϣ��"+
			"�����Ϣ��������������ҳ�����Բ�ͬ�ķ�ʽ��ʾ</p>"+
			"<p>�������������ϵ����<b>����</b>.</p>"+
			"<li>email:<a>nemo.zhp@gmail.com</a></li>"+
			"<li>��ַ��http://www.blogjava.net/nemo-zhp</li>"+
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
		
		//13.8.1 ���޸ĵı༭�� BEGIN
		if (updated == false) { //�����ô˷�����updatedΪfalse,������Ϊ��ʼ�������¶�ȡģ��ʱ�ĸ���
			updated = true;
		}
		//13.8.1 ���޸ĵı༭�� END
		
		//����ѡ����
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
		
		//13.8.1 ���޸ĵı༭��
		updated = false; //�޸����,updated��Ϊfalse
		
	}

	@Override
	public void selectionChanged(IFormPart part, ISelection selection) {
		IStructuredSelection ssel = (IStructuredSelection) selection;
		if (ssel.size() == 1) {
			input = (AddressList)ssel.getFirstElement();
			/*
			 * �ڵ�ʮ���¼�������Ĵ��룬���ı���������setContent()�й̶�ʵ�ֱ�Ϊ����ѡ��Ĳ�ͬ��̬�ı�
			 * �޸����ı�AddressListʱ��DetailsPart�ı������������֮�ı��BUG
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
		
		//����TextItemContents
		//Ϊÿ��TextItemContents���һ����ǩ���ı���
		//����Ϊÿ��TextItemContents���һ���޸ļ�����
		for (int i=0;iter.hasNext();i++) {
			String key = (String)iter.next();
			toolkit.createLabel(parent, key);
			texts[i] = toolkit.createText(parent, "");
			GridData gd = new GridData(GridData.FILL_HORIZONTAL|GridData.VERTICAL_ALIGN_BEGINNING);
			gd.widthHint = 10;
			texts[i].setLayoutData(gd);
			textMap.put(texts[i], key);
			
			//Ϊ�ı�������޸ļ�����
			texts[i].addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					if (updated == true) { //����ʱupdatedΪtrue,����Ϊ��ʼ�������¶�ȡģ��,���������update()����
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
		//��choiceKeysMap.size()��ѡ����
		Iterator<String> iter = choiceKeysMap.keySet().iterator();
		choices = new Button[choiceKeysMap.size()][];
		
		//����ChoiceItemContents
		//Ϊÿ��ChoiceItemContents���һ����ǩ�Ͷ��ѡ�ť
		//����Ϊÿ��ChoiceItemContents���һ��ѡ�������
		for (int i=0;iter.hasNext();i++) {
			GridData gd;
			
			//����ѡ��������
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
			
			//����Label
			Label label = toolkit.createLabel(parent, key+": ");
			GridData gdata = new GridData(GridData.FILL_HORIZONTAL);
			gdata.horizontalSpan = 2;
			label.setLayoutData(gdata);
			
			Object[] choiceStrings = (Object[])choiceKeysMap.get(key);
			choices[i] = new Button[choiceStrings.length];
			
			//Ϊѡ��ť���ѡ�������
			//��ͬһ��choiceContentItem�е�һ��ѡ��ť��Ӧ��ͬ��ѡ�������
			for (int j=0;j<choices[i].length;j++) {
				choices[i][j] = toolkit.createButton(parent, (String)choiceStrings[j], SWT.RADIO);
				choices[i][j].setData(new Integer(j));
				buttonMap.put(choices[i][j], key);
				
				//Ϊÿ����ť���ѡ�������
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

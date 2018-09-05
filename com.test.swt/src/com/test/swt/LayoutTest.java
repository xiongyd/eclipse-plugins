package com.test.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class LayoutTest {

	public static void main(String[] args) {
		Display display = new Display();
		Color color = new Color(display,255,0,0);
		
		Shell sh1 = new Shell(display);
		sh1.setText("FillLayout-��䲼��");
		sh1.setBounds(100,100,800,400);
		sh1.setLayout(new FillLayout());
		for (int i=0;i<10;i++) {
			Button btn = new Button(sh1,SWT.PUSH);
			btn.setText("btn"+i);
		}
		sh1.open();
		
		Shell sh2 = new Shell(display);
		sh2.setText("RowLayout-�в���");
		sh2.setBounds(900,100,400,400);
		sh2.setLayout(new RowLayout());
		for (int i=0;i<10;i++) {
			Button btn = new Button(sh2,SWT.PUSH);
			btn.setText("btn"+i);
			Image img = new Image(null,"icons\\gl.jpg");
			btn.setImage(img);
		}
		sh2.open();
		
		Shell sh3 = new Shell(display);
		sh3.setText("GridLayout-���񲼾�");
		sh3.setBounds(100,500,600,400);
		GridLayout layout3 = new GridLayout();
		layout3.numColumns = 3;
		sh3.setLayout(layout3);
		for (int i=0;i<10;i++) {
			Button btn = new Button(sh3,SWT.PUSH);
			btn.setText("btn"+i);
		}
		sh3.open();
		
		/**  
		FormLayout-������
		 �ṩһ��FormData�Ĳ��ַ�ʽ��ͨ��FormAttachmentʵ��
		 �������Ҫ������������һ���ǿ�ȣ�left/right�����߸߶ȣ�top/bottom���İٷֱȣ��ڶ�������������Լ��ϵ�ֵ������Ǹ��������Ǽ�ȥ������ֵ��
		�����ṩControl���͵Ĳ�����Ҳ���ǿؼ����͵Ĳ����������һ������ָ��һ���ؼ�����������ָ�����Ǹ�bottom����ô�����Զ���ȡ����ؼ���Ӧ�ĸ߶ȣ��ڽ��мӼ���
		�����ͱ�֤�ˣ�ĳЩ�ؼ������λ�ñ��ֲ��䡣
		 */
		Shell sh4 = new Shell(display);
		sh4.setText("FormLayout-������");
		sh4.setBounds(700,500,600,400);
		FormLayout layout4 = new FormLayout();
		sh4.setLayout(layout4);

		Button cancelBtn = new Button(sh4,SWT.PUSH);
		cancelBtn.setText("cancel");
		FormData fd1 = new FormData();
		fd1.right = new FormAttachment(100, -5);
		fd1.bottom = new FormAttachment(100, -5);
		cancelBtn.setLayoutData(fd1);
		
		Button okBtn = new Button(sh4,SWT.PUSH);
		okBtn.setText("ok");
		FormData fd2 = new FormData();
		fd2.right = new FormAttachment(cancelBtn, -5);
		fd2.bottom = new FormAttachment(100, -5);
		okBtn.setLayoutData(fd2);
		
		Text text = new Text(sh4,SWT.MULTI|SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL);
		FormData fd3 = new FormData();
		fd3.top = new FormAttachment(0, 5);
		fd3.bottom = new FormAttachment(cancelBtn, -5);
		fd3.left = new FormAttachment(0, 5);
		fd3.right = new FormAttachment(100, -5);
		text.setLayoutData(fd3);
		Color color2 = new Color(null,255,0,0);
		text.setForeground(color2);
		sh4.open();
		
		
		Shell sh5 = new Shell(display);
		sh5.setText("GridData");
		sh5.setBounds(1300,500,600,400);
		GridLayout layout5 = new GridLayout();
		sh5.setLayout(layout5);
		layout5.numColumns = 3;
		
		Button btn1 = new Button(sh5,SWT.PUSH);
		btn1.setText("btn1");
		GridData gd1 = new GridData(SWT.FILL,SWT.FILL,false,false,1,1);
		gd1.widthHint = 100;
		gd1.heightHint = 100;
		btn1.setLayoutData(gd1);
		
		Button btn2 = new Button(sh5,SWT.PUSH);
		btn2.setText("btn2");
		GridData gd2 = new GridData(SWT.FILL,SWT.FILL,false,false,1,2);
		gd2.widthHint = 100;
		gd2.heightHint = 100;
		btn2.setLayoutData(gd2);
		
		Button btn3 = new Button(sh5,SWT.PUSH);
		btn3.setText("btn3");
		GridData gd3 = new GridData(GridData.FILL_BOTH);
//		gd3.widthHint = 100;
//		gd3.heightHint = 100;
		btn3.setLayoutData(gd3);
		
		Button btn4 = new Button(sh5,SWT.PUSH);
		btn4.setText("btn4");
		GridData gd4 = new GridData(SWT.FILL,SWT.FILL,false,false,1,1);
		gd4.widthHint = 100;
		gd4.heightHint = 100;
		btn4.setLayoutData(gd4);
		
		sh5.open();
		
		while(!sh1.isDisposed() || !sh2.isDisposed() || 
				!sh3.isDisposed() || !sh4.isDisposed() || 
				!sh5.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		display.beep();
		color.dispose();
		display.dispose();
	}
}

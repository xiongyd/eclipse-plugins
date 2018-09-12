package com.plugindev.addressbook.views;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IMemento;

public class AddressViewerSorter extends ViewerSorter {

	//以下TAG是在备忘录(Memento)中使用
	private static final String TAG_DESENDING = "descending";
	private static final String TAG_INDEX = "columnIndex";
	private static final String TAG_CATEGORY = "category";
	private static final String TAG_TRUE = "true";

	private TableViewer viewer;
	private SortInfo[] sortInfos;

	private class SortInfo {
		int columnIndex;
		Comparator comparator;
		boolean descending;
	}

	public AddressViewerSorter(TableViewer viewer, TableColumn[] columns, Comparator[] comparators) {
		this.viewer = viewer;
		this.sortInfos = new SortInfo[columns.length];
		for (int i = 0; i < columns.length; i++) {
			sortInfos[i] = new SortInfo();
			sortInfos[i].columnIndex = i;
			sortInfos[i].comparator = comparators[i];
			sortInfos[i].descending = false;
			createSelectionListener(columns[i], sortInfos[i]);
		}
	}

	private void createSelectionListener(TableColumn tableColumn, SortInfo sortInfo) {
		tableColumn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				sortUsing(sortInfo);
			}
		});
	}

	private void sortUsing(SortInfo sortInfo) {
		if (sortInfo == sortInfos[0]) {
			sortInfos[0].descending = !sortInfos[0].descending;
		} else {
			for (int i = 0; i < sortInfos.length; i++) {
				if (sortInfo == sortInfos[i]) {
					System.arraycopy(sortInfos, 0, sortInfos, 1, i);
					sortInfos[0] = sortInfo;
					sortInfo.descending = false;
					break;
				}
			}
		}
		viewer.refresh();

	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		for (int i = 0; i < sortInfos.length; i++) {
			int result = sortInfos[i].comparator.compare(e1, e2);
			if (result != 0) {
				if (sortInfos[i].descending) {
					return -result;
				}
				return result;
			}
		}
		return 0;
	}

	public void saveState(IMemento memento) {
		for (int i=0;i<sortInfos.length;i++) {
			SortInfo info = sortInfos[i];
			
			IMemento mem = memento.createChild(TAG_CATEGORY);
			mem.putInteger(TAG_INDEX, info.columnIndex);
			if (info.descending) {
				mem.putString(TAG_DESENDING, TAG_TRUE);
			}
		}
	}
	
	public void init(IMemento memento) {
		List<SortInfo> newInfos = new ArrayList<SortInfo>(sortInfos.length);
		IMemento[] mems = memento.getChildren(TAG_CATEGORY);
		for (int i=0;i<mems.length;i++) {
			IMemento mem = mems[i];
			Integer value = mem.getInteger(TAG_INDEX);
			if (value == null)
				continue;
			
			int index = value.intValue();
			if (index<0 || index >= sortInfos.length)
				continue;
			
			SortInfo info = sortInfos[index];
			
			if (newInfos.contains(info)) 
				continue;
			
			info.descending = TAG_TRUE.equals(mem.getString(TAG_DESENDING));
			
			newInfos.add(info);
		}
		
		for (int i=0;i<sortInfos.length;i++) {
			if (!newInfos.contains(sortInfos[i]))
				newInfos.add(sortInfos[i]);
			
			sortInfos = (SortInfo[])newInfos.toArray(new SortInfo[newInfos.size()]);
		}
	}
}

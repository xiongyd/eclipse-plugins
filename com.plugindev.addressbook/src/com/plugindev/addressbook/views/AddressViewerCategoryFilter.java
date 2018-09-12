package com.plugindev.addressbook.views;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.IMemento;

import com.plugindev.addressbook.models.AddressItem;
import com.plugindev.addressbook.util.StringMatcher;

public class AddressViewerCategoryFilter extends ViewerFilter {

	// ������IMemento�еı������
	private static final String TAG_PATTERN = "pattern";
	private static final String TAG_TYPE = "CategoryFilterInfo";

	private StructuredViewer viewer;
	private StringMatcher matcher;
	private String pattern = "";

	public AddressViewerCategoryFilter(StructuredViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		return matcher.match(((AddressItem) element).getCategory().getCategoryName());
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String newPattern) {
		boolean filtering = matcher != null;
		if (newPattern != null && newPattern.trim().length() > 0) {
			pattern = newPattern;
			matcher = new StringMatcher(pattern, true, false);

			if (!filtering) {
				viewer.addFilter(this);
			} else {
				viewer.refresh();
			}
		} else {
			pattern = "";
			matcher = null;
			if (filtering) {
				viewer.removeFilter(this);
			}
		}
	}

	// ��ñ���Ĺ���״̬
	public void init(IMemento memento) {
		IMemento mem = memento.getChild(TAG_TYPE);
		if (mem == null)
			return;
		setPattern(mem.getString(TAG_PATTERN));
	}

	// �������״̬
	public void saveState(IMemento memento) {
		if (pattern.length() == 0)
			return;
		IMemento mem = memento.createChild(TAG_TYPE);
		mem.putString(TAG_PATTERN, pattern);
	}

}

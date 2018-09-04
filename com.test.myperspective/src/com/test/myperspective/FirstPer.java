package com.test.myperspective;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class FirstPer implements IPerspectiveFactory {

	@Override
	public void createInitialLayout(IPageLayout layout) {

		String ea = layout.getEditorArea();
		
		layout.setEditorAreaVisible(false);
		
		layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.LEFT, 0.25f, ea);
		
		IFolderLayout bottom = layout.createFolder("bottom", IPageLayout.BOTTOM, 0.66f, ea);
		bottom.addView(IPageLayout.ID_PROBLEM_VIEW);
	}

}

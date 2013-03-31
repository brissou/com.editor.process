package com.views;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

public class LabelView extends ViewPart {
    private ListViewer viewer;
    
    public LabelView() {
            super();
            
    }
    public void init(IViewSite site) throws PartInitException {
        super.init(site);
        // Normally we might do other stuff here.
}
public void setFocus() {
	viewer.getControl().setFocus();
   }
 
    
	public void createPartControl(Composite parent) {
		// Create viewer.
		viewer = new ListViewer(parent);
		//viewer.setContentProvider(new WordContentProvider());
		//viewer.setLabelProvider(new LabelProvider());
		//viewer.setInput(input);
		viewer.getList().add("Item 1");
		viewer.getList().add("Item 2");
		viewer.getList().add("Item 3");

        // Create menu and toolbars.
		//createActions();
        //createMenu();
        //createToolbar();
        //createContextMenu();
        //hookGlobalActions();
        
        // Restore state from the previous session.
		//restoreState();
     
 }

}
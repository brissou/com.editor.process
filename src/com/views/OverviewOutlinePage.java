package com.views;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import com.ProcessEditor;

public class OverviewOutlinePage extends ContentOutlinePage	 implements ISelectionChangedListener {

	public static final String ID = IPageLayout.ID_OUTLINE;
	
	//
	protected ProcessEditor editor;
	
	
	
	public OverviewOutlinePage(
		ProcessEditor editor
	) {
		super();
		this.editor = editor;
	}
	
	
	/**
	 * 
	 */
	public void selectionChanged(SelectionChangedEvent event) {
        IStructuredSelection sel = (IStructuredSelection)event.getSelection();
        OutlineViewItem func = (OutlineViewItem)sel.getFirstElement();
        if(func!=null){
        	func.selectAndReveal();
        }
    }
	
	
	
	/**
	 * 
	 */
	public void init(IPageSite pageSite) {
		super.init(pageSite);
		pageSite.setSelectionProvider(this);
	}
	
	
	
	/**
	 * 
	 */
	public void setFocus() {
		getTreeViewer().getControl().setFocus();
	}
	
	
	
	/**
	 * 
	 */
	public void createControl(Composite parent) {
		super.createControl(parent);

		TreeViewer viewer= getTreeViewer();
		
		viewer.setContentProvider( new TreeContentProvider(editor ) );
		viewer.setLabelProvider(new OutlineViewLabelProvider() );
	    	
		viewer.setInput( editor.getPrologueEditor() );
	  	viewer.addSelectionChangedListener(this);
	  	viewer.expandAll();
	}
}
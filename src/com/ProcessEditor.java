package com;



import java.io.File;

import org.eclipse.compare.CompareUI;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.internal.TextMergeViewerCreator;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.compare.CompareInput;
import com.views.OverviewOutlinePage;


public class ProcessEditor extends MultiPageEditorPart
                           implements IResourceChangeListener, IPropertyListener {
	//
	final public static int MODULE_PROLOGUE = 0;
	final public static int MODULE_META     = 1;
	final public static int MODULE_DATA     = 2;
	final public static int MODULE_EPILOGUE = 3;
	
	
	// les editor
	private PrologueEditor prologueEditor;
	private MetadataEditor metadataEditor;
	private DataEditor     dataEditor;
	private EpilogueEditor epilogueEditor;
	
	//
	protected boolean dirty = false;

	//
	private ProcessModel process = null; 
	
	private OverviewOutlinePage overviewOutlinePage;
	/**
	 * Creates a multi-page editor example.
	 */
	public ProcessEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
		addPropertyListener(this);
	}

	
	/**
	 * Creates page 0 of the multi-page editor,
	 * which contains a text editor.
	 */
	void createPage0() {
		try {
			prologueEditor = new PrologueEditor();
			IStorage storage = new StringStorage(process.getProlog().toString());
			IStorageEditorInput input = new StringInput(storage);
			int index = addPage(prologueEditor, input);
			setPageText(index, "Prologue");


		} catch (PartInitException e) {
			ErrorDialog.openError(
				getSite().getShell(),
				"Error creating nested text editor",
				null,
				e.getStatus());
		}
	}
	
	/**
	 * Creates page 1 of the multi-page editor,
	 * which allows you to change the font used in page 2.
	 */
	void createPage1() {

		/*Composite composite = new Composite(getContainer(), SWT.NONE);
		GridLayout layout = new GridLayout();
		composite.setLayout(layout);
		layout.numColumns = 2;

		Button fontButton = new Button(composite, SWT.NONE);
		GridData gd = new GridData(GridData.BEGINNING);
		gd.horizontalSpan = 2;
		fontButton.setLayoutData(gd);
		fontButton.setText("Change Font...");
		
		fontButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				setFont();
			}
		});

		int index = addPage(composite);
		setPageText(index, "Properties");*/
		try {
			metadataEditor = new MetadataEditor();
			IStorage storage = new StringStorage(process.getMetadata().toString());
			IStorageEditorInput input = new StringInput(storage);
			int index = addPage(metadataEditor, input);
			setPageText(index, "Meta Data");

		} catch (PartInitException e) {
			ErrorDialog.openError(
				getSite().getShell(),
				"Error creating nested text editor",
				null,
				e.getStatus());
		}

	}

	
	/**
	 * Creates page 2 of the multi-page editor,
	 * which shows the sorted text.
	 */
	void createPage2() {
		/*Composite composite = new Composite(getContainer(), SWT.NONE);
		FillLayout layout = new FillLayout();
		composite.setLayout(layout);
		text = new StyledText(composite, SWT.H_SCROLL | SWT.V_SCROLL);
		text.setEditable(false);

		int index = addPage(composite);
		setPageText(index, "Preview");*/
		try {
			dataEditor = new DataEditor();
			IStorage storage = new StringStorage(process.getData().toString());
			IStorageEditorInput input = new StringInput(storage);
			int index = addPage(dataEditor, input);
			setPageText(index, "Data");

		} catch (PartInitException e) {
			ErrorDialog.openError(
				getSite().getShell(),
				"Error creating nested text editor",
				null,
				e.getStatus());
		}

	}
	
	
	/**
	 * Creates page 2 of the multi-page editor,
	 * which shows the sorted text.
	 */
	void createPage3() {
		/*Composite composite = new Composite(getContainer(), SWT.NONE);
		FillLayout layout = new FillLayout();
		composite.setLayout(layout);
		text = new StyledText(composite, SWT.H_SCROLL | SWT.V_SCROLL);
		text.setEditable(false);

		int index = addPage(composite);
		setPageText(index, "Preview");*/
		try {
			epilogueEditor = new EpilogueEditor();
			IStorage storage = new StringStorage(process.getEpilog().toString());
			IStorageEditorInput input = new StringInput(storage);
			int index = addPage(epilogueEditor, input);
			setPageText(index, "Epilogue");

		} catch (PartInitException e) {
			ErrorDialog.openError(
				getSite().getShell(),
				"Error creating nested text editor",
				null,
				e.getStatus());
		}

	}
	
	
	/**
	 * Creates the pages of the multi-page editor.
	 */
	protected void createPages() {
		createPage0();
		createPage1();
		createPage2();
		createPage3();
	}
	
	
	/**
	 * The <code>MultiPageEditorPart</code> implementation of this 
	 * <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}
	
	
	public boolean isDirty() {
		return dirty;
	}
      

	
	/**
	 * Saves the multi-page editor's document.
	 */
	public void doSave(IProgressMonitor monitor) {
		// on sauvegarde le temps des tests
		try {
			System.out.println("Sauvegarde de " + this.process.getRawPath() );
			//doSave(monitor);
			this.process.save(this);
			//dirty = !dirty;
			firePropertyChange(PROP_DIRTY);
			//dirty = false;
			//firePropertyChange(PROP_DIRTY);
		} catch(PartInitException e) {
			ErrorDialog.openError(
    			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				"Error saving nested text editor",
				e.getMessage(),
				e.getStatus()
			);
		} finally {
			System.out.println("fin Sauvegarde " );
		}
		//TextMergeViewer tv = new TextMergeViewer
		//CompareUI.openCompareEditor(
		//	new CompareInput( this )
		//);
	}
	
	
	/**
	 * Saves the multi-page editor's document as another file.
	 * Also updates the text for page 0's tab, and updates this multi-page editor's input
	 * to correspond to the nested editor's.
	 */
	public void doSaveAs() {
		IEditorPart editor = getEditor(0);
		editor.doSaveAs();
		setPageText(0, editor.getTitle());
		setInput(editor.getEditorInput());
	}
	
	
	/* (non-Javadoc)
	 * Method declared on IEditorPart
	 */
	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}
	
	public void setActivePage(int newPage) {
		super.setActivePage(newPage);
	}

	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>IFileEditorInput</code>.
	 */
	public void init(
		IEditorSite  site,
		IEditorInput editorInput
	) throws PartInitException {
		//if (!(editorInput instanceof IFileEditorInput))
		//	throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		try {
			if (editorInput instanceof FileStoreEditorInput) {
				this.process = new ProcessModel(((FileStoreEditorInput)editorInput).getURI().getRawPath());
				this.process.load();
			} else
			if (editorInput instanceof FileEditorInput) {
				this.process = new ProcessModel(((FileEditorInput)editorInput).getURI().getRawPath());
				this.process.load();
			}
			super.init(site, editorInput);
			try {
				setPartName( new File(this.process.getRawPath()).getName() );
			} catch(Exception e) {
				
			}
		} catch(PartInitException e) {
			ErrorDialog.openError(
    			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				"Error creating nested text editor",
				e.getMessage(),
				e.getStatus()
			);
		}
	}
	
	/* (non-Javadoc)
	 * Method declared on IEditorPart.
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}
	
	
	/**
	 * Calculates the contents of page 2 when the it is activated.
	 */
	public void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
	}
	
	
	/**
	 * Closes all project files on project close.
	 */
	public void resourceChanged(final IResourceChangeEvent event){
		if(event.getType() == IResourceChangeEvent.PRE_CLOSE){
			Display.getDefault().asyncExec(new Runnable(){
				public void run(){
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
					// a revoir
					for (int i = 0; i<pages.length; i++){
						if(((FileEditorInput)prologueEditor.getEditorInput()).getFile().getProject().equals(event.getResource())){
							IEditorPart editorPart = pages[i].findEditor(prologueEditor.getEditorInput());
							pages[i].closeEditor(editorPart,true);
						}
					}
				}            
			});
		}
	}

	
	public Object getAdapter(Class type) {
	       if (type == IContentOutlinePage.class)
	              return getOverviewOutlinePage();
	      return null;
	}
	 
	private OverviewOutlinePage getOverviewOutlinePage() {
		if (null == overviewOutlinePage ){
			overviewOutlinePage = new OverviewOutlinePage(this);
		}
		return overviewOutlinePage;
	}


	public TextEditor getPrologueEditor() {
		return prologueEditor;
	}


	public void setPrologueEditor(PrologueEditor prologueEditor) {
		this.prologueEditor = prologueEditor;
	}


	public TextEditor getMetadataEditor() {
		return metadataEditor;
	}


	public void setMetadataEditor(MetadataEditor metadataEditor) {
		this.metadataEditor = metadataEditor;
	}


	public TextEditor getDataEditor() {
		return dataEditor;
	}


	public void setDataEditor(DataEditor dataEditor) {
		this.dataEditor = dataEditor;
	}


	public TextEditor getEpilogueEditor() {
		return epilogueEditor;
	}


	public void setEpilogueEditor(EpilogueEditor epilogueEditor) {
		this.epilogueEditor = epilogueEditor;
	}
	
	public IDocument getDocument(int module) {
		IDocument res = null;
		switch(module) {
			case MODULE_PROLOGUE:
				res = this.prologueEditor.getDocumentProvider().getDocument(this.prologueEditor.getEditorInput());
				break;
			case MODULE_META:
				res = this.metadataEditor.getDocumentProvider().getDocument(this.metadataEditor.getEditorInput());
				break;
			case MODULE_DATA:
				res = this.dataEditor.getDocumentProvider().getDocument(this.dataEditor.getEditorInput());
				break;
			case MODULE_EPILOGUE:
				res = this.epilogueEditor.getDocumentProvider().getDocument(this.epilogueEditor.getEditorInput());
				break;
		}
		return res;
	}


	@Override
	public void propertyChanged(Object arg0, int arg1) {
		dirty = !dirty;
		//System.out.println(">propertyChange=" + arg0.getClass()+" "+(arg1==PROP_DIRTY)+" "+isDirty()  );		
	}
	
	
}

package com.compare;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.CompareViewerPane;
import org.eclipse.compare.CompareViewerSwitchingPane;
import org.eclipse.compare.IModificationDate;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.Splitter;
import org.eclipse.compare.contentmergeviewer.ContentMergeViewer;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.internal.ChangePropertyAction;
import org.eclipse.compare.internal.CompareContentViewerSwitchingPane;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PageBook.PageBookLayout;

import com.ProcessEditor;

public class CompareInput extends CompareEditorInput {

	   
	class CompareItem implements IStreamContentAccessor,
            ITypedElement, IModificationDate {
		
      private String contents, name;
      private long time;
      
      CompareItem(String name, String contents) {
         this.name = name;
         this.contents = contents;
         //this.time = time;
      }
      public InputStream getContents() throws CoreException {
         return new ByteArrayInputStream(contents.getBytes());
      }
      public Image getImage() {return null;}
      public long getModificationDate() {return time;}
      public String getName() {return name;}
      public String getString() {return contents;}
      public String getType() {return ITypedElement.TEXT_TYPE;}
   }

	private ProcessEditor processEditor;
	
	public CompareInput(ProcessEditor processEditor) {
		super(new CompareConfiguration());
		this.processEditor = processEditor;
	}
      
	protected CompareViewerSwitchingPane createContentViewerSwitchingPane(Splitter parent, int style, CompareEditorInput cei) {
		System.out.println(">ibi");
		return super.createContentViewerSwitchingPane( parent, style,cei);
		
		
	}; 
	
	@Override
	public Viewer createDiffViewer(Composite parent) {
		System.out.println(">ibi2");
		//return super.createDiffViewer(parent);
		return new TextMergeViewer(parent, getCompareConfiguration() );
	}
	
	protected  CompareViewerPane 	createStructureInputPane(Composite parent) {
		System.out.println(">ibi3");
		CompareViewerPane p = super.createStructureInputPane(parent);
		return p;
	}
	
	
	public Control createContents(Composite parent) {
		return super.createContents(parent);
		//return new TextMergeViewer(parent, getCompareConfiguration() );
		
		/*Layout l = new RowLayout(SWT.VERTICAL);

		parent.setLayout( l );
		Button label1 = new Button(parent, SWT.BORDER);
		label1.setText("Salut");
		int headerHeight= label1.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).y;
		label1.setBounds(0, 0, 300, headerHeight);
		
		Control f = super.createContents(parent);
		System.out.println(">CompareInputCreateContents>" + parent.getClass() );
		System.out.println(">CompareInputCreateContents>" + f.getClass() );
        for(Control c : ((Composite)f).getChildren() ) {
        	System.out.println(">" + c.getClass() );
        }
		
		
		return f;*/
		/*final Splitter fComposite = new Splitter(parent, SWT.VERTICAL);

        //createOutlineContents(fComposite, SWT.HORIZONTAL);

        final CompareViewerPane pane = new CompareViewerPane(fComposite, SWT.NONE);

        CompareViewerPane contentMergeViewer = new CompareViewerPane(pane, SWT.FLAT);
        pane.setContent(contentMergeViewer.getContent());

        //contentMergeViewer.setInput(inputSnapshot);
        // MOD klliu bug 15529 replace "Td Table" to "Table"
        // ModelContentMergeDiffTab diffTabLeft = contentMergeViewer.diffTabLeft;
        // repaintingTreePart(diffTabLeft);
        // ModelContentMergeDiffTab diffTabRight = contentMergeViewer.diffTabRight;
        // repaintingTreePart(diffTabRight);

        final int structureWeight = 30;
        final int contentWeight = 70;
        fComposite.setWeights(new int[] { structureWeight, contentWeight });

        return fComposite;*/

		
	/*	Composite fComposite = new Composite(parent, SWT.BORDER );
		Color     couleur    = new Color(parent.getDisplay(),131,133,131);  
		fComposite.setBackground(couleur);
		
		GridLayout layout = new GridLayout(2, true);
		fComposite.setLayout(layout);
		Button label1 = new Button(fComposite, SWT.BORDER);
		label1.setText("First Name");
		Button label2 = new Button(fComposite, SWT.BORDER);
		label2.setText("Second Name");
		
		Control control = super.createContents(fComposite);
		control.setBackground(new Color(parent.getDisplay(),200,200,131));
		GridData gd2 = new GridData();
		gd2.horizontalAlignment = GridData.FILL;
		gd2.verticalAlignment = GridData.FILL_BOTH;
		gd2.heightHint = SWT.DEFAULT;
		gd2.horizontalSpan=2;
		control.setLayoutData( gd2 );		

		parent.layout(true);
		
		//parent.pack();
		return fComposite;*/
		
/*		Composite fComposite = new Composite(parent, SWT.BORDER );
		Color     couleur    = new Color(parent.getDisplay(),131,133,131);  
		fComposite.setBackground(couleur);
		
		
		Button label1 = new Button(fComposite, SWT.BORDER);
		label1.setText("First Name");
		label1.setBounds(10, 10, 100, 25);
		
		Button label2 = new Button(fComposite, SWT.BORDER);
		label2.setText("Second Name");
		label2.setBounds(110, 10, 100, 25);

		//fComposite.layout();
		System.out.println(">>>>>" + fComposite.getBounds().width );
		System.out.println(">>>>>" + fComposite.getBounds().height );
		Control control = super.createContents(fComposite);
		control.setBounds(0, 30, 200, 200);
		
		//parent.pack();
		return fComposite;*/
		
		/*
		Composite fComposite = new Composite(parent, SWT.BORDER );
		Color     couleur    = new Color(parent.getDisplay(),131,133,131);  
		fComposite.setBackground(couleur);
		
		Button label1 = new Button(fComposite, SWT.BORDER);
		label1.setText("First Name");
		label1.setBounds(10, 10, 100, 25);
		
		Button label2 = new Button(fComposite, SWT.BORDER);
		label2.setText("Second Name");
		label2.setBounds(110, 10, 100, 25);

		Control control = super.createContents(fComposite);
		
		
		fComposite.layout();
		
		//parent.pack();
		return fComposite;*/

		 // marche 
	/*	System.out.println(">>>" + parent.getBounds().width);
		System.out.println(">>>" + parent.getBounds().height);
		GridLayout layout = new GridLayout(1, true);
		parent.setLayout(layout);
		Button label1 = new Button(parent, SWT.BORDER);
		label1.setText("First Name");
		GridData gd1 = new GridData();
		gd1.horizontalAlignment = GridData.FILL;
		label1.setLayoutData( gd1 );
		
		
		Control control = super.createContents(parent);
		GridData gd2 = new GridData();
		gd2.horizontalAlignment = GridData.FILL;
		gd2.verticalAlignment = GridData.CENTER;
		control.setLayoutData( gd2 );
		
		return parent;*/
		

		
    /*    Control control = super.createContents(parent);
        Splitter fComposite = (Splitter)control;
        
        Color jaune = parent.getDisplay().getSystemColor(SWT.COLOR_YELLOW);
        fComposite.setBackground(jaune);

        System.out.println(">CompareInputCreateContents>" + parent.getClass() );
        System.out.println(">CompareInputCreateContents>" + parent.getLayout().getClass() );
        
        //PageBookLayout ll = (PageBookLayout)parent.getLayout();
        //new Button(parent, SWT.PUSH).setText("B1");
        
        System.out.println(">CompareInputCreateContents>" + control.getClass() );
        System.out.println(">CompareInputCreateContents>" + control.getLayoutData() );
        
        //for(Control c : fComposite.getChildren() ) {
        //	System.out.println(">" + c.getClass()+" "+c.getToolTipText() );
        //}
        
        
        
        return control;*/

	}



@Override	
public void contributeToToolBar(ToolBarManager toolBarManager) {
	// ne marche pas pour l instant
 	  System.out.println(">Salut 1");
     
     toolBarManager.add(new Separator());
     toolBarManager.add(new Action(
        "&New",
        ImageDescriptor.createFromFile(null, "icons/sample.gif")) {
      public void run() {
    	  System.out.println(">Salut 2");
      }
    } );
     
     //toolBarManager.add(fShowPseudoConflicts);
   }

	@Override
	protected Object prepareInput(IProgressMonitor pm) {
		
        CompareConfiguration cc = getCompareConfiguration();

        cc.setLeftLabel("Salut");
        cc.setRightLabel("Bonsoir");

		
		CompareItem ancestor = new CompareItem("Common", "contents");
		
		// ok
		//CompareItem left     = new CompareItem("Left", "new contents");
		//CompareItem right    = new CompareItem("Right", "old contents");
		
		CompareItem left     = new CompareItem("Left", processEditor.getDocument(ProcessEditor.MODULE_EPILOGUE ).get() );
		CompareItem right    = new CompareItem("Right", "old contents");
		
		
		return new DiffNode(null, Differencer.CONFLICTING, ancestor, left, right);
	}
	
}
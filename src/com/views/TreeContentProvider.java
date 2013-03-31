package com.views;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.editors.text.TextEditor;

import com.ProcessEditor;


public class TreeContentProvider implements ITreeContentProvider {
	
	private OutlineViewItem root;
	private ProcessEditor   editor;
	
	public TreeContentProvider(
		ProcessEditor editor
	) {
		//
		this.editor = editor;

		//
		root = new OutlineViewItem("root", -1);
		root.addChildren("Processus", -1, null, null);
		root.addChildren("Cubes", -1, null, null);
	}
	
	
	
	@Override
	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		if (arg2==null) {
			return;
		}
		System.out.println("inputChanged>"+arg2.getClass());
		parse( editor.getDocument( ProcessEditor.MODULE_PROLOGUE), editor.getPrologueEditor(), "prologue");
		parse( editor.getDocument( ProcessEditor.MODULE_META),     editor.getMetadataEditor(), "meta");
		parse( editor.getDocument( ProcessEditor.MODULE_DATA),     editor.getDataEditor(),     "data");
		parse( editor.getDocument( ProcessEditor.MODULE_EPILOGUE), editor.getEpilogueEditor(), "epilogue");
		
	}
	
	
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	@Override
	public boolean hasChildren(Object arg0) {
		if (arg0 instanceof OutlineViewItem) {
			return ((OutlineViewItem)arg0).getChildren().length>0;
		}
		return false;
	}

	
	
	@Override
	public Object getParent(Object arg0) {
		if (arg0 instanceof OutlineViewItem) {
			return ((OutlineViewItem)arg0).getChildren();
		}
		return null;
	}
	
	
	
	@Override
	public Object[] getElements(Object arg0) {
		return root.getChildren();
	}
	
	
	
	@Override
	public Object[] getChildren(Object arg0) {
		if (arg0 instanceof OutlineViewItem){
			Object[] ret=((OutlineViewItem)arg0).getChildren();
			return ret;
		}
		return null;				
	}
	

	
	protected void insertItem(OutlineViewItem owner, String key,int iLine, int offset, String module, TextEditor moduleEditor) {
        //System.out.println("-----------------------------------------------");
        //System.out.println("text="+text);
        //System.out.println(">>>"+_s);

        OutlineViewItem p = null;
        for(OutlineViewItem item : owner.getChildren() ) {
        	if (key.equalsIgnoreCase(item.getText())) {
        		p=item;
        		break;
        	}
        }
        if (p==null) {
        	p = owner.addChildren(key, offset, editor, moduleEditor);
        }
        p.addChildren(module+" - "+(iLine+1), offset,  editor, moduleEditor);

	}
	
	
	
	protected void parseLineV0(OutlineViewItem owner,  String line, int iLine, String command, int offset, String module, TextEditor moduleEditor) {
        int i = line.toLowerCase().indexOf(command);
        if (i==-1) {
        	return;
        }

        int i2 = line.toLowerCase().indexOf(",", i );
        if (i2==-1) {
        	i2 = line.toLowerCase().indexOf(");", i );
        	if (i2==-1) {
        		System.out.println("parseLineV0="+line);
        		return;
        	}
        }
        String _s = line.substring(i+command.length(), i2).trim();
        insertItem( owner, _s, iLine, offset, module, moduleEditor);
	}

	
	protected void parseLineV1(OutlineViewItem owner,  String line, int iLine, String command, int offset, String module, TextEditor moduleEditor) {
		int i = line.toLowerCase().indexOf(command);
		if (i==-1) {
			return;
		}

		String   _s  = line.substring(i+command.length()).trim();
        String[] _ss = _s.split(",");
        insertItem( owner, _ss[1].trim(), iLine, offset, module, moduleEditor);
	}

	

	protected void parse(IDocument document, TextEditor moduleEditor, String module) {
		
		OutlineViewItem[] _p = root.getChildren();
		
		OutlineViewItem process = _p[0];
		OutlineViewItem cube    = _p[1];
		
		String _p0 = "executeprocess(";
		String _p1 = "cellgetn(";
		String _p2 = "cellgets(";
		String _p3 = "cellputn(";
		String _p4 = "cellputs(";
				
		
        int lines = document.getNumberOfLines();
        
        for (int iLine = 0; iLine < lines; iLine++) {
            int offset;
            try {

                offset = document.getLineOffset(iLine);
                int length=document.getLineLength(iLine);
             
                String text=document.get(offset, length);
                int i = -1;
                
                // execute process
                parseLineV0(process, text, iLine, _p0, offset, module, moduleEditor);
                
                // cellGetN, cellGetS
                parseLineV0(cube, text, iLine, _p1, offset, module, moduleEditor);
                parseLineV0(cube, text, iLine, _p2, offset, module, moduleEditor);
                
                // cellPutN, cellPutS
                parseLineV1(cube, text, iLine, _p3, offset, module, moduleEditor);
                parseLineV1(cube, text, iLine, _p4, offset, module, moduleEditor);

            } catch (BadLocationException x) {
            }
        }
        
        
    }

}

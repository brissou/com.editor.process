package com;


import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

class MyHyperlink implements IHyperlink {

	private String location;
	private IRegion region;

	
	public MyHyperlink(IRegion region, String text) {
		this.region= region;
		this.location = text;
	}

	
	
	@Override
	public IRegion getHyperlinkRegion() {
		System.out.println(">getHyperlinkRegion>"+region.getOffset()+" "+region.getLength());
		return region;
	}

	
	@Override
	public void open()  {
		System.out.println(">Open>1>location="+location);
		if(location!=null) {
			System.out.println(">Open>2");
			if (location.startsWith("'")) {
				String _s = "";
				try {
				// le nom du process est en dur
				//(FileStoreEditorInput)editorInput).getURI().getRawPath());
				String _ss = location.substring(1, location.length() - 1);
				_ss = _ss.trim();
				_s = "/amont/" + _ss + ".pro";
				System.out.println(">>>>open>"+_s);
				
				//
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				
				//
				IEditorPart editorPart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
				IFileEditorInput input = (IFileEditorInput)editorPart.getEditorInput() ;
				IFile file = input.getFile();
				IProject activeProject = file.getProject();
				IFile f2 = activeProject.getFile( _s );
				if (!f2.exists()) {
					System.out.println("MyHyperlink.open>"+f2.getName()+" inconnu");
					throw new PartInitException("Processus inconnu "+_s );
				}
				//
				System.out.println(">>activeProject=" + activeProject );
			    	IDE.openEditor( page, f2 );
			    } catch ( PartInitException e ) {
			        //Put your exception handler here if you wish to
			    	e.printStackTrace();
			    	ErrorDialog.openError(
			    			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
							"Error creating nested text editor",
							"Fichier introuvable" + _s,
							e.getStatus()
						);
			    	//throw new Exception(e);
			    }
			    
				
			} else {
				// le nom du process est dans une variable
			}
			//int offset=MyAST.get().getOffset(location);
			//editor.selectAndReveal(offset,0); <--
			//editor.setFocus();
		}
	}
	
	
	@Override
	public String getTypeLabel() {
		return null;
	}

	
	@Override
	public String getHyperlinkText() {
		System.out.println(">getHyperlinkText>"+location);
		return "Ibi>" + this.location;
	}
}
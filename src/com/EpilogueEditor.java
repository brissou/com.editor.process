package com;

import org.eclipse.ui.editors.text.TextEditor;

public class EpilogueEditor extends TextEditor {
	
	public EpilogueEditor() {
		setSourceViewerConfiguration( new ProcessEditorConfiguration() );
	}
public void editorDirtyStateChanged() {
   firePropertyChange(PROP_DIRTY);
   }

}

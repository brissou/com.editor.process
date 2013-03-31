package com;

import org.eclipse.ui.editors.text.TextEditor;

public class DataEditor extends TextEditor {
	
	public DataEditor() {
		setSourceViewerConfiguration( new ProcessEditorConfiguration() );
	}
	
public void editorDirtyStateChanged() {
   firePropertyChange(PROP_DIRTY);
   }

}

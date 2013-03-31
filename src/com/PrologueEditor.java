package com;

import org.eclipse.ui.editors.text.TextEditor;

public class PrologueEditor extends TextEditor {
	
	public PrologueEditor() {
		setSourceViewerConfiguration( new ProcessEditorConfiguration() );
	}
	
	public void editorDirtyStateChanged() {
		firePropertyChange(PROP_DIRTY);
	}

}

package com;

import org.eclipse.ui.editors.text.TextEditor;

public class MetadataEditor extends TextEditor {
	
	public MetadataEditor() {
		setSourceViewerConfiguration( new ProcessEditorConfiguration() );
	}
public void editorDirtyStateChanged() {
   firePropertyChange(PROP_DIRTY);
   }

}

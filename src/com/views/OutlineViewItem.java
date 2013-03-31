package com.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.editors.text.TextEditor;

import com.ProcessEditor;


public class OutlineViewItem {
	private String                text;
	private int                   offset;
	private OutlineViewItem       parent;
	private List<OutlineViewItem> children;
	private ProcessEditor         editor;
	private TextEditor            moduleEditor;
	
	
	
	public OutlineViewItem(String text, int offset) {
		this.text = text;
		this.parent = null;
		this.children = null;
		this.offset = offset;
		this.editor=null;
		this.moduleEditor=null;
	}
	
	public OutlineViewItem addChildren(String text, int offset, ProcessEditor editor, TextEditor moduleEditor) {
		if (this.children==null) {
			this.children = new ArrayList<OutlineViewItem>();
		}
		OutlineViewItem item = new OutlineViewItem(text, offset);
		item.setParent( this );
		item.setEditor( editor );
		item.setModuleEditor( moduleEditor );
		this.children.add( item );
		return item;
	}
	
	public OutlineViewItem[] getChildren() {
		if (this.children==null) {
			this.children = new ArrayList<OutlineViewItem>();
		}
		return this.children.toArray(new OutlineViewItem[0] );
	}
	
	public void selectAndReveal() {
		if (editor==null) {
			return;
		}
		if (moduleEditor==null) {
			return;
		}
		moduleEditor.selectAndReveal(offset, 0);
		if (moduleEditor==editor.getPrologueEditor()) {
			editor.setActivePage(0);
		} else
		if (moduleEditor==editor.getMetadataEditor()) {
			editor.setActivePage(1);
		} else
		if (moduleEditor==editor.getDataEditor()) {
			editor.setActivePage(2);
		} else
		if (moduleEditor==editor.getEpilogueEditor()) {
			editor.setActivePage(3);
		}

	}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public ProcessEditor getEditor() {
		return editor;
	}

	public void setEditor(ProcessEditor editor) {
		this.editor = editor;
	}

	public TextEditor getModuleEditor() {
		return moduleEditor;
	}

	public void setModuleEditor(TextEditor moduleEditor) {
		this.moduleEditor = moduleEditor;
	}

	public OutlineViewItem getParent() {
		return parent;
	}

	public void setParent(OutlineViewItem parent) {
		this.parent = parent;
	}
}

package com;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

public class ProcessEditorConfiguration extends SourceViewerConfiguration {
	
	@Override
	public IPresentationReconciler getPresentationReconciler(
		ISourceViewer sourceViewer
	) {
		PresentationReconciler pr = new PresentationReconciler();
		DefaultDamagerRepairer ddr = new DefaultDamagerRepairer(
			new	ProcessEditorScanner()
		);
		pr.setDamager(ddr, IDocument.DEFAULT_CONTENT_TYPE);
		pr.setRepairer(ddr, IDocument.DEFAULT_CONTENT_TYPE);
		return pr;
	}

/*	@Override
	public ITextHover getTextHover(
		ISourceViewer sv, 
		String contentType
	) {
		return new ITextHover() {
			
			@Override
			public IRegion getHoverRegion(ITextViewer tv, int off) {
				return new org.eclipse.jface.text.Region(off, 0);
			}
			
			@Override
			public String getHoverInfo(ITextViewer tv, IRegion r) {
		         try {
			            IDocument doc = tv.getDocument();
			            System.out.println(">>getOffset=" + r.getOffset()+" "+r.getLength() );
			            //EscriptModel em = EscriptModel.getModel(doc, null);
			            //return em.getElementAt(r.getOffset()).getHoverHelp();
			            return "Salut";
			         }
			         catch (Exception e) {            
			            return ""; 
			         }
			}
		};
		
	}*/
	
	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant assistant= new ContentAssistant();
		assistant.enableAutoActivation(true);
		assistant.setAutoActivationDelay(500);
		assistant.setProposalPopupOrientation(IContentAssistant.PROPOSAL_OVERLAY);
		assistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_ABOVE);
		//assistant.setContextInformationPopupBackground(JavaEditorEnvironment.getJavaColorProvider().getColor(new RGB(150, 150, 0)));
		assistant.setProposalSelectorBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));

		// Create content assistant processor
		IContentAssistProcessor processor = new ProcessCompletionProcessor();
		assistant.setContentAssistProcessor( processor, IDocument.DEFAULT_CONTENT_TYPE);
		// TODO Auto-generated method stub
		return assistant;
	}

	//@Override
	//public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
	//	// TODO Auto-generated method stub
	//	return super.getHyperlinkDetectors(sourceViewer);
	//}

	@Override
	public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
		return new IHyperlinkDetector[] { new MyHyperlinkDetector() };
	}
	
	
}

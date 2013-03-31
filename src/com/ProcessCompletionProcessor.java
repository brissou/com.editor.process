package com;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;


public class ProcessCompletionProcessor implements IContentAssistProcessor {

//## Actions ##
//ContentAssistProposal.label=Content Assist@Ctrl+SPACE
//ContentAssistProposal.tooltip=Content Assist
//ContentAssistProposal.image=
//ContentAssistProposal.description=Content Assist
//
//ContentAssistTip.label=Content Tip@Ctrl+SHIFT+SPACE
//ContentAssistTip.tooltip=Content Tip
//ContentAssistTip.image=
//ContentAssistTip.description=Content Tip
//
//DefineFoldingRegion.label=Define Folding Region
//DefineFoldingRegion.tooltip=Define Folding Region
//DefineFoldingRegion.image=
//DefineFoldingRegion.description=Define Folding Region
//
//TogglePresentation.label=Change Presentation
//TogglePresentation.tooltip=Enable/Disable Segmented Source Viewer
//TogglePresentation.image=togglepresentation.gif
//TogglePresentation.description=Enable/Disable Segmented Source Viewer
//
//OutlinePage.segment.title_pattern={0} [{1}::]({2})
//
//AutoIndent.error.bad_location_1=JavaAutoIndentStrategy.getAutoIndentString: BadLocationException
//AutoIndent.error.bad_location_2=JavaAutoIndentStrategy.calcShiftBackReplace: BadLocationException
//
//CompletionProcessor.ContextInfo.display.pattern=proposal {0} at position {1}
//CompletionProcessor.ContextInfo.value.pattern=proposal {0} valid from {1} to {2}
//CompletionProcessor.Proposal.ContextInfo.pattern={0} valid 5 characters around insertion point
//CompletionProcessor.Proposal.hoverinfo.pattern=Java keyword: {0}
//
//JavaTextHover.emptySelection=empty selection

	@Override
	public ICompletionProposal[] computeCompletionProposals(
		ITextViewer arg0,
		int documentOffset
	) {
		
		String[] fgProposals = {
			"AttrDelete(dimName, attrName);",
			"AttrInsert(dimName, prevAttr, attrName, type);",
			"AttrPutN(value, dimName, elName, attrName);",
			"AttrPutS(value, dimName, elName, attrName);",
			"AddCubeDependency(BaseCube, DependentCube);",
			"CellGetN(Cube, e1, e2 [,...en]);",
			"CellGetS(Cube, e1, e2 [,...en]);",
			"CellIsUpdateable(Cube, e1, e2 [,...en]);"
		};
		
		String[] fgProposals2 = {
			" This function deletes an element attribute from the TM1 database",
			" This function deletes an element attribute from the TM1 database",
			" This function deletes an element attribute from the TM1 database",
			" This function deletes an element attribute from the TM1 database",
			" This function deletes an element attribute from the TM1 database",
			" This function deletes an element attribute from the TM1 database",
			" This function deletes an element attribute from the TM1 database",
			" This function deletes an element attribute from the TM1 database"
		};
		
		ICompletionProposal[] result= new ICompletionProposal[fgProposals.length];
		for (int i= 0; i < fgProposals.length; i++) {
			//result[i]= new CompletionProposal(
			//	fgProposals[i],
			//	arg1,
			//	0,
			//	fgProposals[i].length()
			//);
			IContextInformation info = new ContextInformation(
				fgProposals[i],
				fgProposals2[i]
			);
			
			result[i] = new CompletionProposal(
				fgProposals[i],
				documentOffset,
				0,
				fgProposals[i].length(),
				null,
				fgProposals[i],
				info,
				fgProposals2[i]
	);

		}
		return result;
	}

	
	
	@Override
	public IContextInformation[] computeContextInformation(
		ITextViewer arg0,
		int documentOffset
	) {
		String[] fgProposals2 = {
			" This function deletes an element attribute from the TM1 database",
			" This function deletes an element attribute from the TM1 database",
			" This function deletes an element attribute from the TM1 database",
			" This function deletes an element attribute from the TM1 database",
			" This function deletes an element attribute from the TM1 database",
			" This function deletes an element attribute from the TM1 database",
			" This function deletes an element attribute from the TM1 database",
			" This function deletes an element attribute from the TM1 database"
		};

		IContextInformation[] result= new IContextInformation[fgProposals2.length];
		for (int i= 0; i < result.length; i++) {
			result[i]= new ContextInformation(
				""+i,
				fgProposals2[i]
			);
		}
		return result;
	}

	
	
	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[] { '.', '(' };
	}

	
	
	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}

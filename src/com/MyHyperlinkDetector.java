package com;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;

final public class MyHyperlinkDetector implements IHyperlinkDetector {
	
	public IHyperlink processComplete(
		ITextViewer viewer,
		IRegion region
	) {
		// ne marche parpour l'instant
		IRegion lineInfo;
		String line;
		
		IDocument document = viewer.getDocument();
		int offset = region.getOffset();
		
		try {
			lineInfo= document.getLineInformationOfOffset(offset);
			line= document.get(lineInfo.getOffset(), lineInfo.getLength());
		} catch (BadLocationException ex) {
			return null;
		}
		
		String _p = "ExecuteProcess(";
		
		if (!line.contains(_p)) {
			return null;
		}
		
		System.out.println(">hyperlink.offset="+offset+" "+lineInfo.getOffset());

		int i = line.indexOf(_p);
		
		//int offsetInLine = offset - lineInfo.getOffset();
		//int macroLength = line.length() - _p.length();
		//int macroSeparatorOffset = _p.length();
		
		int     end       = 0;
		int b = i+_p.length();
		String  line2     = line;
		IRegion lineInfo2 = null;
		int nbCariageReturn = 0;
		
		for(;;) {
			end = line2.indexOf(");", b );
			if (end>-1) {
				break;
			}
			// plusieurs lignes ...
			try {
				lineInfo2 = document.getLineInformationOfOffset( offset + line.length()-2 );
				line2     = document.get(lineInfo2.getOffset(), lineInfo2.getLength());
				line += line2;
				b=0;
				System.out.println("---------------");
				System.out.println( "line=" + line );
				System.out.println( "line2=" + line2 );
				System.out.println("---------------");
			} catch (BadLocationException ex) {
				//return null;
				break;
			}
			nbCariageReturn++;
		}
		end = line.indexOf(");", i+_p.length()+2*nbCariageReturn );
		
		String m  = line.substring(i, i + _p.length() - 1 );
		String m2 = line.substring(i + _p.length(), end );
		
		String mmName = "";
		if (m2.indexOf(",")>-1) {
			String[] a = m2.split(",");
			mmName = a[0].trim();
		} else {
			mmName = m2;
		}
		System.out.println("---------------");
		System.out.println( "line="+line );
		System.out.println("---------------");
		System.out.println(">macroName=\n"+mmName );
		
		IRegion mRegion = new Region(lineInfo.getOffset()+i, end - i);
		//int begin= line.indexOf("<");
		//int end = line.indexOf(">");
		//if(end<0 || begin<0 || end==begin+1) {
		//	return null;
		//}

		//String text    = line.substring(begin+1,end+1);
		//IRegion macroRegion = new Region(
		//	lineInfo.getOffset()+macroSeparatorOffset, macroLength
		//);
		return new MyHyperlink(mRegion,mmName );

	}

	
	
	public IHyperlink processName(
		ITextViewer viewer,
		IRegion region
	) {
		// ne marche parpour l'instant
		IRegion lineInfo;
		String line;
			
		IDocument document = viewer.getDocument();
		int offset = region.getOffset();
			
		try {
			lineInfo= document.getLineInformationOfOffset(offset);
			line= document.get(lineInfo.getOffset(), lineInfo.getLength());
		} catch (BadLocationException ex) {
			return null;
		}
			
		String _p = "ExecuteProcess(";
		
		if (!line.contains(_p)) {
			return null;
		}
			
		System.out.println(">hyperlink.offset="+offset+" "+lineInfo.getOffset());

		int i = line.indexOf(_p);
			
		int end = line.indexOf(");", i+_p.length() );
		if (end<0) {
			end = line.length() - i;
		}
			
		//String m  = line.substring(i, i + _p.length() - 1 );
		String m2 = line.substring(i + _p.length(), end );
			
		String mmName = "";
		if (m2.indexOf(",")>-1) {
			String[] a = m2.split(",");
			mmName = a[0].trim();
		} else {
			mmName = m2;
		}
		System.out.println("---------------");
		System.out.println( "line="+line );
		System.out.println("---------------");
		System.out.println(">macroName=\n"+mmName );
			
		IRegion mRegion = new Region(lineInfo.getOffset()+line.indexOf(mmName, i), mmName.length() );

		//
		return new MyHyperlink(mRegion,mmName );

	}
	

	public IHyperlink processNameV2(
		ITextViewer viewer,
		IRegion region
	) {
		IDocument document = viewer.getDocument();
		int offset = region.getOffset();

		// vers la gauche
		int offsetLeft = 0;
		int offsetRight = 1;
		int offsetB = offset;
		char c = 0;
		String[] tmp = {"", ""};
		int iTmp = 1;
		for(;;) {
			if (iTmp<0) {
				break;
			}
			try {
				c = document.getChar(offset - offsetLeft);
			} catch (BadLocationException ex) {
				break;
			}
			if (c=='(') {
				iTmp--;
				if (iTmp<0) {
					break;
				}
			} else
			if (c=='\n') {
				iTmp--;
				if (iTmp<0) {
					break;
				}
			} else
			if (c==' ') {
				iTmp--;
				if (iTmp<0) {
					break;
				}
				for(;;) {
					try {
						c = document.getChar(offset - offsetLeft);
					} catch (BadLocationException ex) {
						break;
					}
					if ((c!=' ') && (c!='\n')) {
						break;
					}
					offsetLeft++;
					tmp[iTmp] = c + tmp[iTmp];
				}
			} else
			if (c==',') {
				// ne doit pas etre present car le nom du process est le premier argument
				iTmp=-1;
				continue;
			} else
			if (c=='=') {
				// ne doit pas etre present car le nom du process est le premier argument
				iTmp=-1;
				continue;
			}
			offsetLeft++;
			if (iTmp==1) {
				offsetB--;
			}
			tmp[iTmp] = c + tmp[iTmp];
		}
			
			
		// si tmp[0] ne commence par ExecuteProcess alors exit
		if (tmp[0].toLowerCase().startsWith("executeprocess(")==false) {
			return null;
		}
			
		// on continue a droite
		for(;;) {
			try {
				c = document.getChar(offset + offsetRight);
			} catch (BadLocationException ex) {
				break;
			}
			if (c==' ') {
				break;
			} else
			if (c==')') {
				break;
			} else
			if (c==',') {
				break;
			}
			offsetRight++;
			tmp[1] += c;
		}
		System.out.println("-0--------------------------");
		System.out.println( tmp[0] );
		System.out.println("-1--------------------------");
		System.out.println( tmp[1] );
		System.out.println("---------------------------");

		IRegion mRegion = new Region(offsetB + 1, tmp[1].length() );
		return new MyHyperlink(mRegion, tmp[1] );
	}
	
	public IHyperlink[] detectHyperlinks(
		ITextViewer viewer,
		IRegion region,
		boolean canShowMultipleHyperlinks
	) {
		IHyperlink hl0 = processNameV2(viewer, region);
		if (hl0==null) {
			return null;
		}
		return new IHyperlink[] { hl0 };
	}
}
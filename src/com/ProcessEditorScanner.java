package com;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;


public class ProcessEditorScanner extends RuleBasedScanner {
	private static final Color COLOR_KEYWORD;
	private static final Color COLOR_KEYWORD_TM1;
	private static final Color COLOR_COMMENT;
	private static final Color COLOR_OTHER;
	
	private static final String [] KEYWORDS = new String[] {
		"IF", "ELSE", "ELSEIF", "ENDIF", "ExecuteProcess", "ExecuteCommand", "StringGlobalVariable",
		"WHILE", "END","GetProcessErrorFileDirectory","ElementSecurityPut","StringToNumber"
	};
	
	private static final String [] KEYWORDTM1S = new String[] {
		"DIMSIZ", "DIMIX", "SUBST","DAYNO","DATE","CellPutN","CellPutS","CellGetN",
		"CellGetS","ATTRDELETE","ATTRINSERT","ATTRPUTS"
	};

	static {
		Display d = PlatformUI.getWorkbench().getDisplay();
		COLOR_KEYWORD     = new Color(d, 44, 48, 201);
		COLOR_KEYWORD_TM1 = new Color(d, 128, 0, 0);
		COLOR_COMMENT     = new Color(d, 0, 128, 0);
		COLOR_OTHER       = new Color(d, 0, 0, 0);
	}
	
/**
* Initialise le scanner.
*/
	public ProcessEditorScanner() {
		//
		Token keyword = new Token(new TextAttribute(COLOR_KEYWORD, null, SWT.BOLD));
		
		//
		Token keywordTM1 = new Token(new TextAttribute(COLOR_KEYWORD_TM1, null, SWT.BOLD));

		//
		Token comment = new Token(new TextAttribute(COLOR_COMMENT,null, SWT.ITALIC));
		
		//
		Token other = new Token(new TextAttribute(COLOR_OTHER));
		
		//
		WordRule rule = new WordRule(
			new IWordDetector() {
				@Override
				public boolean isWordPart(char c) {
					return Character.isJavaIdentifierPart(c);
				}
				@Override
				public boolean isWordStart(char c) {
					return Character.isJavaIdentifierStart(c);
				}
			},
			other,
			true
		);
		
		//CasedWordRule wordRule = new CasedWordRule(wordDetector, defaultToken, ruleSet.getKeywords().ignoreCase()); 
/*		WordRule rule2 = new WordRule(
			new IWordDetector() {
				@Override
				public boolean isWordPart(char c) {
					return Character.isJavaIdentifierPart(c);
				}
				@Override
				public boolean isWordStart(char c) {
					return Character.isJavaIdentifierPart(c);
				}
			},
			keyword,
			true
		);*/
		for (String k : KEYWORDS) {
			rule.addWord(k, keyword);
		}
		for (String k : KEYWORDTM1S) {
			rule.addWord(k, keywordTM1);
		}
		
		setRules(
			new IRule[] {
				rule,
				new SingleLineRule("#", null, comment, (char)0, true)
			}
		);
	}
	
	
}

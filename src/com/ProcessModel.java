package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.PartInitException;

public class ProcessModel {
	private StringBuffer prolog;
	private StringBuffer metadata;
	private StringBuffer data;
	private StringBuffer epilog;
	private StringBuffer before;
	private StringBuffer after;
	
	private String rawPath;
	
	public ProcessModel(String rawPath) {
		this.rawPath  = rawPath;
		this.prolog   = new StringBuffer();
		this.metadata = new StringBuffer();
		this.data     = new StringBuffer();
		this.epilog   = new StringBuffer();
		this.before   = new StringBuffer();
		this.after    = new StringBuffer();
	}

	
	public void save(ProcessEditor editor) throws PartInitException {
		
		//parse( editor.getDocument( ProcessEditor.MODULE_PROLOGUE), editor.getPrologueEditor(), "prologue");
		//parse( editor.getDocument( ProcessEditor.MODULE_META),     editor.getMetadataEditor(), "meta");
		//parse( editor.getDocument( ProcessEditor.MODULE_DATA),     editor.getDataEditor(),     "data");
		//parse( editor.getDocument( ProcessEditor.MODULE_EPILOGUE), editor.getEpilogueEditor(), "epilogue");

		BufferedWriter br   = null;
		try {
			OutputStream       ips  = new FileOutputStream(this.rawPath); 
			OutputStreamWriter ipsr = new OutputStreamWriter(ips);
		
			br = new BufferedWriter(ipsr);
			
			//
			br.write(before.toString() );
	
			//
			IDocument d0 = editor.getDocument( ProcessEditor.MODULE_PROLOGUE);
			int       l0 = d0.getNumberOfLines();
			String    t0 = d0.get(0, d0.getLength() );
			br.write("\n572,"+l0);
			br.write("\n");
			br.write(  t0 );

			//
			IDocument d1 = editor.getDocument( ProcessEditor.MODULE_META);
			int       l1 = d1.getNumberOfLines();
			String    t1 = d1.get(0, d1.getLength() );
			br.write("\n573,"+l1); 
			br.write("\n");
			br.write(  t1 );
			
			//
			IDocument d2 = editor.getDocument( ProcessEditor.MODULE_DATA);
			int       l2 = d2.getNumberOfLines();
			String    t2 = d2.get(0, d2.getLength() );
			br.write("\n574,"+l2); 
			br.write("\n");
			br.write(  t2 );

			//
			IDocument d3 = editor.getDocument( ProcessEditor.MODULE_EPILOGUE);
			int       l3 = d3.getNumberOfLines();
			String    t3 = d3.get(0, d3.getLength() );
			br.write("\n575,"+l3); 
			br.write("\n");
			br.write(  t3 );

			//
			br.write("\n");
			br.write(after.toString() );
			
			
		} catch(FileNotFoundException e) {
			System.out.println("***** process introuvable " + this.rawPath +"   *****");
			throw new PartInitException("process inconnu " + this.rawPath );
		}	catch (Exception e){
			e.printStackTrace();
			
		} finally {
			try {
				if (br!=null) {
					br.close();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	public void load() throws PartInitException {
		
		//
		EModule module = EModule.BEFORE;
		BufferedReader br   = null;
		try {
			//
			InputStream       ips  = new FileInputStream(this.rawPath); 
			InputStreamReader ipsr = new InputStreamReader(ips);
			
			//
			br = new BufferedReader(ipsr);
			int ct = 0;
			int total = 0;
			for(;;) {
				String ligne = br.readLine();
				if (ligne==null) {
					break;
				}
				//process.incTotalLigne();
				
				//----------------------------------------------
				if (ligne.indexOf("572,")==0) {
					// module prologue
					module = EModule.PROLOGUE;
					String[] s0 = ligne.split(",");
					continue;
					//System.out.println("-------------------------------------------------------------");
					//System.out.println(">prologue l=" + process.getTotalLigne()+" nb_ligne=" + s0[1]);
				} else
				if (ligne.indexOf("573,")==0) {
					// module meta
					module = EModule.META;
					String[] s0 = ligne.split(",");
					continue;
					//System.out.println("-------------------------------------------------------------");
					//System.out.println(">meta l=" + process.getTotalLigne()+" nb_ligne=" + s0[1]);
				} else
				if (ligne.indexOf("574,")==0) {
					// module data
					module = EModule.DATA;
					String[] s0 = ligne.split(",");
					continue;
					//System.out.println("-------------------------------------------------------------");
					//System.out.println(">data l=" + process.getTotalLigne()+" nb_ligne=" + s0[1]);
				} else
				if (ligne.indexOf("575,")==0) {
					// module epilogue
					module = EModule.EPILOGUE;
					String[] s0 = ligne.split(",");
					continue;
					//System.out.println("-------------------------------------------------------------");
					//System.out.println(">epilogue l=" + process.getTotalLigne()+" nb_ligne=" + s0[1]);
				} else
				if (ligne.indexOf("576,")==0) {
					// module epilogue
					module = EModule.AFTER;
					String[] s0 = ligne.split(",");
					//continue;
					//System.out.println("-------------------------------------------------------------");
					//System.out.println(">epilogue l=" + process.getTotalLigne()+" nb_ligne=" + s0[1]);
				}
				

				// ligne commentaire
				//if (ligne.trim().startsWith("#")) {
				//	continue;
				//}
				
				//----------------------------------------------
				switch(module) {
					case OTHER:
						break;
					case BEFORE:
						if (before.length()>0) {
							before.append("\n");
						}
						before.append( ligne );
						break;
					case AFTER:
						if (after.length()>0) {
							after.append("\n");
						}
						after.append( ligne );
						break;
					case PROLOGUE:
						//if (prolog.length()>0) {
							prolog.append("\n");
						//}
						prolog.append( ligne );
						//extractProcessName(process, ligne);
						//extractVariable(process, ligne);
						//extractRCube(process, ligne);
						//extractWCube(process, ligne);
						break;
					case META:
						//if (metadata.length()>0) {
							metadata.append("\n");
						//}
						metadata.append( ligne );
						//extractProcessName(process, ligne);
						//extractVariable(process, ligne);
						//extractRCube(process, ligne);
						//extractWCube(process, ligne);
						break;
					case DATA:
						//if (data.length()>0) {
							data.append("\n");
						//}
						data.append( ligne );
						//extractProcessName(process, ligne);
						//extractVariable(process, ligne);
						//extractRCube(process, ligne);
						//extractWCube(process, ligne);
						break;
					case EPILOGUE:
						//if (epilog.length()>0) {
							epilog.append("\n");
						//}
						epilog.append( ligne );
						//extractProcessName(process, ligne);
						//extractVariable(process, ligne);
						//extractRCube(process, ligne);
						//extractWCube(process, ligne);
						break;
				}
				
			}
		} catch(FileNotFoundException e) {
			System.out.println("***** process introuvable " + this.rawPath +"   *****");
			throw new PartInitException("process inconnu " + this.rawPath );
		}	catch (Exception e){
			e.printStackTrace();
			
		} finally {
			try {
				if (br!=null) {
					br.close();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	public StringBuffer getMetadata() {
		return metadata;
	}

	public void setMetadata(StringBuffer metadata) {
		this.metadata = metadata;
	}

	public StringBuffer getProlog() {
		return prolog;
	}


	public void setProlog(StringBuffer prolog) {
		this.prolog = prolog;
	}


	public StringBuffer getData() {
		return data;
	}


	public void setData(StringBuffer data) {
		this.data = data;
	}


	public StringBuffer getEpilog() {
		return epilog;
	}


	public void setEpilog(StringBuffer epilog) {
		this.epilog = epilog;
	}
	
	public String getRawPath() {
		return rawPath;
	}

	public void setRawPath(String rawPath) {
		this.rawPath = rawPath;
	}

	public StringBuffer getBefore() {
		return before;
	}

	public StringBuffer getAfter() {
		return after;
	}

}

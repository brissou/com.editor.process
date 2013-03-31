package com.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.SocketException;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class ServeurFTP {

	//
	protected String     host;
	protected String     defaultFolder;
	protected String     user;
	protected String     pwd;
	protected FTPClient  ftp;
	protected int        port;
	
	
	public ServeurFTP() {
		this.host = "dsddv542";
		this.user = "cho";
		this.pwd = "cho";
		this.port = 21;
		
		this.ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
	}
	
	protected void connect() throws SocketException, IOException {
		
		//
		ftp.connect(host, port);
		if(!ftp.login(user, pwd)) {
			throw new IOException("Connection au serveur <"+host+"> impossible!");
		}

		//
		ftp.enterLocalActiveMode();

		// Transfert en mode binaire
		ftp.setFileType(FTP.BINARY_FILE_TYPE);
		ftp.setFileTransferMode( FTP.BINARY_FILE_TYPE );
		
	}

	public void disconnect() throws IOException {
		ftp.logout();
		ftp.disconnect();
	}

		
	public void sendFile(
		String localFileName,
		String remoteFileName
	) throws Exception {

		//-
		FileInputStream fis   = null;
		OutputStream    os    = null;
		long            total = 0;

		try {
			//-
			System.out.println("send_1 :"+localFileName + " [remoteFileName="+remoteFileName+"]" );
			connect();
			
			//-
			fis = new FileInputStream(localFileName);
			os  = ftp.storeFileStream( remoteFileName );
			
			//
			byte buf[]     = new byte[ 16 * 1024 ];
			int  bytesRead = 0;
			for(;;) {
				bytesRead = fis.read(buf);
				if (bytesRead==-1) {
					break;
				}
				os.write(buf, 0, bytesRead);
				total += bytesRead;
			}
			os.flush();

			
		} catch (Exception e) {
			throw e;
			
		} finally {
			System.out.println("send_1 :total transfert " + total );
			if (fis!=null) {
				fis.close();
			}
			if (os!=null) {
				os.close();
			
			}
			disconnect();

		}

	}

}

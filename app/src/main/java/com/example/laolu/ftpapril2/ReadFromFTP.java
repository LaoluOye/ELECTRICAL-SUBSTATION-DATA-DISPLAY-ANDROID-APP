package com.example.laolu.ftpapril2;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.InputStream;
import java.net.InetAddress;

/**
 * Created by Laolu on 24/04/2015.
 */
public class ReadFromFTP {
    private String _server;
    private String _username;
    private String _password;

    public ReadFromFTP(String server, String username, String password) {
        _server = server;
        _username = username;
        _password = password;

    }
    public String ReadFileFromFTP(String filepath){
        String fileContent= "";

        FTPClient ftpclient = new FTPClient();

        try{
            // to connect
            ftpclient.connect(InetAddress.getByName(_server));//get the ftp server address
            ftpclient.enterLocalPassiveMode();//tells the server to open a data port to which the client will connect to conduct data transfers.
            ftpclient.setFileType(FTP.ASCII_FILE_TYPE);//for text file
            ftpclient.login(_username, _password);// retrieves login details from arguments array
            //response = ftpclient.getStatus();
            FTPFile[] files = ftpclient.listFiles(filepath);//retrieves file
            InputStream inputStream = ftpclient.retrieveFileStream (filepath);// streams file
            //reading the file and putting the text into a string
            java.util.Scanner scanner= new java.util.Scanner(inputStream).useDelimiter("\\A");//
            fileContent= scanner.hasNext() ? scanner.next():"";//passing contents of scanner into fileContent with compressed if else


            ftpclient.completePendingCommand();

            ftpclient.logout();
            ftpclient.disconnect();
        }
        catch(Exception e){

        }

        return fileContent;


    }
}

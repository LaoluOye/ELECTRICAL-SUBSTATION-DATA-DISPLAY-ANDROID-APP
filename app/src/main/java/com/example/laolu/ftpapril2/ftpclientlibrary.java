package com.example.laolu.ftpapril2;

/**
 * Created by Laolu on 31/03/2015.
 */
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by Laolu on 31/03/2015.
 */

public class ftpclientlibrary {

    private String _host;
    private String _username;
    private String _password;
    private int _port;
    private FTPClient ftpClient = null;
    private static  final String TAG= "tag";

    public ftpclientlibrary(String host, String username, String password, int port){
        _host= host;
        _username = username;
        _password = password;
        _port = port;
    }



    public Boolean Connect() {
        Boolean result = false;

        ftpClient=null;


        try {
            ftpClient = new FTPClient();
            Log.d(TAG, "Connecting");
            ftpClient.connect(InetAddress.getByName(_host), _port);
            Log.d(TAG, "Server Replied");

            if(FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                boolean status = ftpClient.login(_username, _password);
                ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
                ftpClient.enterLocalPassiveMode();
                result = true;
                Log.d(TAG, "Connected!");
            }
        }
        catch (IOException ex) {
            Log.d(TAG, "Error occurred while connecting");

        }
        return result;
    }
    public boolean Close(){
        Boolean result = false;
        try{
            ftpClient.logout();
            ftpClient.disconnect();
            return true;
        }
        catch (Exception e) {
            Log.d(TAG, "Error occurred while disconnecting");
        }
        return result;
    }
    public boolean downloadfile(String srcFilePath, String desFilePath) {
        boolean status = false;
        try {
            FileOutputStream desFileStream = new FileOutputStream(desFilePath);
            status = ftpClient.retrieveFile(srcFilePath, desFileStream);
            desFileStream.close();
        }
        catch(Exception e){
            Log.d(TAG, "Download has failed!");
        }
        return status;
    }

    public String getcurrentworkingdirectory() {
        try {
            String workingDir = ftpClient.printWorkingDirectory();
            return workingDir;
        } catch (Exception ex) {
            Log.d(TAG, "Error, Could not get Working Directory" + ex.toString());

        }
        return null;
    }
}

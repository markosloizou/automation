import java.io.*;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FTPUploader extends Thread{
    private String ftpURL;
    private String user;
    private String password;
    private ArrayList<String> files;
    private String ftpUrl = "ftp://%s:%s@%s/%s;type=i";
    private FTPClient client;
    private boolean loggedin;
    private static int guiUploadRateRefreshTime = 500;
    private static int packetSize = 16384*2;
    private long totalUploadSize = 0;
    private int previousPercentage = -1;
    private long previousTime = System.currentTimeMillis();
    private long previousUploadSize = 0;

    public FTPUploader(Agency a, ArrayList<String> filesToUpload){
        AgencyDetailFactory agencyDetailFactory = new AgencyDetailFactory();
        AgencyDetails details = agencyDetailFactory.GetAgencyRequirments(a);
        CredentialsDBManager credentialsDB = new CredentialsDBManager("abc");
        loggedin = false;
        ftpURL = details.getFTPURL();
        files = filesToUpload;

        client = new FTPClient();
        AgencyCredentials AC = credentialsDB.getFTPcredentials(a);
        user = AC.getUname();
        password = new String(AC.getFTPpwd());
    }

    public boolean login(){
        try{
            client.connect(ftpURL);
            loggedin = client.login(user,password);
        } catch(IOException ioe) {
            //TODO handle exception
            ioe.printStackTrace();
        }
        return loggedin;
    }

    public boolean uploadFiles(){
        if(!loggedin) {
            if(!login()){
                //TODO add error message in the  GUI?
                System.out.println("Cannot log in to agency");
                return false;
            }
        }
        int count = 0;
        for(String f: files) {
            boolean uploaded = uploadFile(f);
            if(uploaded) count++;
        }
        try {
            if (client.isConnected()) {
                client.logout();
                client.disconnect();
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
        if(count == files.size()) return  true;
        else return false;
    }

    private boolean uploadFile(String file){
        final File localFile = new File(file);
        client.enterLocalPassiveMode(); //enter passive mode allows connection to the server which may be blocked by
                                        //the firewall. By entering passive mode the port is opened on the server for
                                        //client to connect and is not blocked by the firewall.
/*
        CopyStreamAdapter streamListener = new CopyStreamAdapter() {
            //private long previousTime = System.currentTimeMillis();
            //private int packetsSent = 0;


            @Override
            public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {
                //this method will be called every time some bytes are transferred
               // packetsSent++;
                //if(previousTime - System.currentTimeMillis() <  guiUploadRateRefreshTime );

                //estimate single sample speed?
               // long timeNow = System.currentTimeMillis();
                //long dt = previousTime-timeNow;
               // previousTime = timeNow;

                //double time = (double) dt;
                //time = time/1000; //convert to seconds

                //double rate = packetsSent*packetSize/time; //Bytes per second sent;
                int percent = (int)(totalBytesTransferred*100/localFile.length());
                // update your progress bar with this percentage

                System.out.println("Done: " + percent + "% \t\t");// + "Upload speed: " + rate + " B/s");
                System.out.flush();
            }

        };*/

        try {
            client.setFileType(FTP.BINARY_FILE_TYPE);

            String name = localFile.getName();
            //TODO add the unique ID to the beginning of the remote filename
            String remoteFile = name;
            InputStream inputStream = new FileInputStream(localFile);

            OutputStream outputStream = client.storeFileStream(remoteFile);

            byte[]  bytesIn = new byte[packetSize];
            int read = 0;
            totalUploadSize = localFile.length();

            long uploaded = 0;
            previousPercentage = 0;
            previousTime = System.currentTimeMillis();
            previousUploadSize = 0;

            while((read = inputStream.read(bytesIn)) != -1){
                outputStream.write(bytesIn, 0, read);
                uploaded += packetSize;
                printProgress(uploaded);
            }
            inputStream.close();
            outputStream.close();

            boolean completed = client.completePendingCommand();
            if(completed){
                System.out.println("Uploaded file");
            }

        } catch(IOException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }

        return true;
    }

    private void printProgress(long uploaded){
        int percentage = (int)(uploaded*100/totalUploadSize);
        try {
            if (previousPercentage != percentage) {
                long currentTime = System.currentTimeMillis();
                long dt = currentTime - previousTime;
                float speed = (uploaded - previousUploadSize)  / dt;

                previousUploadSize = uploaded;
                previousTime = currentTime;
                previousPercentage = percentage;

                System.out.println("Progress: " + percentage + "%\t\tSpeed: " + speed + "KB/s");
            }
        } catch(ArithmeticException e){/**/}
    }


}

import java.io.*;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.io.CopyStreamAdapter;

public class FTPUploader extends Thread{
    private String ftpURL;
    private String user;
    private String password;
    private ArrayList<String> files;
    private String ftpUrl = "ftp://%s:%s@%s/%s;type=i";
    private FTPClient client;
    private boolean loggedin = false;
    private static int guiUploadRateRefreshTime = 500;
    private static int packetSize = 4096;

    public FTPUploader(Agency a, ArrayList<String> filesToUpload){
        AgencyDetailFactory agencyDetailFactory = new AgencyDetailFactory();
        AgencyDetails details = agencyDetailFactory.GetAgencyRequirments(a);

        ftpURL = details.getFTPURL();
        files = filesToUpload;

        client = new FTPClient();

    }

    public boolean Login(){
        if(loggedin == true){
            return true;
        }
        boolean login = false;
        try{
            client.connect(ftpURL);
            login = client.login(user,password);
        } catch(IOException ioe) {
            //TODO handle exception
        }
        if(login == true) loggedin = true;
        return login;
    }

    public void uploadFiles(){
        if(!loggedin) {
            if(!Login()){
                //TODO add error message in the  GUI?
                return;
            }
        }

        for(String f: files) {
            uploadFile(f);
        }
        try {
            if (client.isConnected()) {
                client.logout();
                client.disconnect();
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    private void uploadFile(String file){
        final File localFile = new File(file);
        client.enterLocalPassiveMode(); //enter passive mode allows connection to the server which may be blocked by
                                        //the firewall. By entering passive mode the port is opened on the server for
                                        //client to connect and is not blocked by the firewall.

        CopyStreamAdapter streamListener = new CopyStreamAdapter() {
            private long previousTime = System.currentTimeMillis();
            private int packetsSent = 0;

            @Override
            public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize) {
                //this method will be called everytime some bytes are transferred
                packetsSent++;
                if(previousTime - System.currentTimeMillis() <  guiUploadRateRefreshTime ) return;

                //estimate single sample speed?
                long timeNow = System.currentTimeMillis();
                long dt = previousTime-timeNow;
                previousTime = timeNow;

                double time = (double) dt;
                time = time/1000; //convert to seconds

                double rate = packetsSent*packetSize/time; //Bytes per second sent;
                int percent = (int)(totalBytesTransferred*100/localFile.length());
                // update your progress bar with this percentage

                System.out.println("Done: " + percent + "% \t\t" + "Upload speed: " + rate + " B/s");
            }

        };

        client.setCopyStreamListener(streamListener);

        try {
            client.setFileType(FTP.BINARY_FILE_TYPE);

            String name = localFile.getName();
            //TODO add the unique ID to the beginning of the remote filename
            String remoteFile = name;
            InputStream inputStream = new FileInputStream(localFile);

            OutputStream outputStream = client.storeFileStream(remoteFile);

            byte[]  bytesIn = new byte[packetSize];
            int read = 0;

            while((read = inputStream.read(bytesIn)) != -1){
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();

            boolean completed = client.completePendingCommand();
            if(completed){
                System.out.println("Uploaded file");
            }

        } catch(IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}

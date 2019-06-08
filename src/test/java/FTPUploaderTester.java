import junit.framework.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FTPUploaderTester  extends TestCase{


    @Test
    public void testLogin(){
        ArrayList<String> al = new ArrayList<String>();
        FTPUploader uploader = new FTPUploader(Agency.TEST, al);
        assertEquals(true, uploader.login());
    }

    @Test
    public void testSmallFileUpload() {
        ArrayList<String> images = new ArrayList<String>();
        images.add("./test_images/small.jpg");
        FTPUploader uploader = new FTPUploader(Agency.TEST, images);
        boolean logedin = uploader.login();

        assertEquals(true, logedin);

        assertEquals(true, uploader.uploadFiles() );
    }


    @Test
    public void testBigFileUpload() {
        ArrayList<String> images = new ArrayList<String>();
        images.add("./test_images/big.jpg");
        FTPUploader uploader = new FTPUploader(Agency.TEST, images);
        boolean logedin = uploader.login();

        assertEquals(true, logedin);

        assertEquals(true, uploader.uploadFiles() );
    }


}

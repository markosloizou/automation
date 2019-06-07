import java.util.ArrayList;
import java.util.Arrays;

public class TestDetails extends AgencyDetails{
    TestDetails() {
        agencyContributorURL = "https://dlptest.com/ftp-test/";
        FTPURL = "ftp.dlptest.com";


        //  IMAGES SECTION
        AcceptedFormats = new ArrayList<ImageFormat>(Arrays.asList(ImageFormat.JPEG, ImageFormat.TIFF, ImageFormat.EPS));

        //File size and megapixels limits
        JPEGfileSizeLimit = 50;
        TIFFfileSizeLimit = 4000;
        EPSfileSizeLimit = 50;
        minMP = 4;
        maxMP = Integer.MAX_VALUE;

        //dimension limits
        minPixelsX = 1;
        maxPixelsX = Integer.MAX_VALUE;
        minPixelsY = 1;
        maxPixelsY = Integer.MAX_VALUE;


        //keyword limits
        minKeywordNumber = 8; //TODO make sure this is correct
        maxKeywordNumber = 50;


        //Description Limits
        minDescriptionCharacterNumber = 20;//TODO make sure this is correct
        maxDescriptionCharacterNumber = 200;//TODO make sure this is correct


        //  Title Limits
        maxTitleLengthInChars = Integer.MAX_VALUE;
        minTitleLengthInChars = 0;
        maxTitleLengthInWords = Integer.MAX_VALUE;
        minTitleLengthInWords = 0;
    }
}

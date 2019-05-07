import java.util.ArrayList;
import java.util.Arrays;

public class ShutterstockDetails extends AgencyDetails {

    String agencyContributorURL = "https://contributor-accounts.shutterstock.com/login";
    String FTPURL =  "ftp.shutterstock.com";


    //  IMAGES SECTION
    ArrayList<ImageFormat> AcceptedFormats =  new ArrayList<ImageFormat>(Arrays.asList(ImageFormat.JPEG, ImageFormat.TIFF, ImageFormat.EPS));

    //File size and megapixels limits
    int JPEGfileSizeLimit;
    int TIFFfileSizeLimit;
    int EPSfileSizeLimit;
    int minMP;
    int maxMP;
    //dimension limits
    int minPixelsX;
    int maxPixelsX;
    int minPixelsY;
    int maxPixelsY;


    //keyword limits
    int minKeywordNumber;
    int maxKeywordNumber;


    //Description Limits
    int minDescriptionCharacterNumber;
    int maxDescriptionCharacterNumber;


    //  Title Limits
    int maxTitleLengthInChars;
    int minTitleLengthInChars;
    int maxTitleLengthInWords;
    int minTitleLengthInWords;

}

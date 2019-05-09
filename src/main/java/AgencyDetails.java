import java.io.*;
import java.util.ArrayList;

public abstract class AgencyDetails {

    protected String agencyContributorURL;
    protected String FTPURL;

    // IMAGE SECTION

    protected  ArrayList<ImageFormat> AcceptedFormats;

    protected int JPEGfileSizeLimit;
    protected int TIFFfileSizeLimit;
    protected int EPSfileSizeLimit;
    protected int minMP;
    protected int maxMP;

    //dimension limits
    protected int minPixelsX;
    protected int maxPixelsX;
    protected int minPixelsY;
    protected int maxPixelsY;


    //keyword limits
    protected int minKeywordNumber;
    protected int maxKeywordNumber;


    /* Description Limits */
    protected int minDescriptionCharacterNumber;
    protected int maxDescriptionCharacterNumber;


    //  Title Limits
    protected int maxTitleLengthInChars;
    protected int minTitleLengthInChars;
    protected int maxTitleLengthInWords;
    protected int minTitleLengthInWords;


    //All the getters

    public String getAgencyURL() {
        return agencyContributorURL;
    }

    public String getFTPURL() {
        return FTPURL;
    }


    public int getMinMP() {
        return minMP;
    }

    public int getMaxMP() {
        return maxMP;
    }

    public int getMinPixelsX() {
        return minPixelsX;
    }

    public int getMaxPixelsX() {
        return maxPixelsX;
    }

    public int getMinPixelsY() {
        return minPixelsY;
    }

    public int getMaxPixelsY() {
        return maxPixelsY;
    }

    public int getMinKeywordNumber() {
        return minKeywordNumber;
    }

    public int getMaxKeywordNumber() {
        return maxKeywordNumber;
    }

    public int getMinDescriptionCharacterNumber() {
        return minDescriptionCharacterNumber;
    }

    public int getMaxDescriptionCharacterNumber() {
        return maxDescriptionCharacterNumber;
    }

    public int getMaxTitleLengthInChars() {
        return maxTitleLengthInChars;
    }

    public int getMinTitleLengthInChars() {
        return minTitleLengthInChars;
    }

    public int getMaxTitleLengthInWords() {
        return maxTitleLengthInWords;
    }

    public int getMinTitleLengthInWords() {
        return minTitleLengthInWords;
    }

    public int getTIFFfileSizeLimit() {
        return TIFFfileSizeLimit;
    }

    public int getEPSfileSizeLimit() {
        return EPSfileSizeLimit;
    }

    public int getJPEGFileSizeLimit() {
        return JPEGfileSizeLimit;
    }


    public boolean checkIfImageTypeIsAccepted(ImageFormat format)
    {
        for(ImageFormat f : AcceptedFormats){
            if(f == format){
                return true;
            }
        }

        return false;
    }
}

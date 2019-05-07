import java.io.*;
import java.util.ArrayList;

public abstract class AgencyDetails {

    private String agencyContributorURL;
    private String FTPURL;

    // IMAGE SECTION

    private  ArrayList<ImageFormat> AcceptedFormats;

    private int JPEGfileSizeLimit = 50;
    private int TIFFfileSizeLimit = 4000;
    private int EPSfileSizeLimit = 50;
    private int minMP = 4;
    private int maxMP = Integer.MAX_VALUE;

    //dimension limits
    private int minPixelsX;
    private int maxPixelsX;
    private int minPixelsY;
    private int maxPixelsY;


    //keyword limits
    private int minKeywordNumber;
    private int maxKeywordNumber;


    /* Description Limits */
    private int minDescriptionCharacterNumber;
    private int maxDescriptionCharacterNumber;


    //  Title Limits
    private int maxTitleLengthInChars;
    private int minTitleLengthInChars;
    private int maxTitleLengthInWords;
    private int minTitleLengthInWords;


    //All the getters

    public String getAgencyURL() {
        return agencyContributorURL;
    }

    public String getFTPURL() {
        return FTPURL;
    }

    public int getFileSizeLimit() {
        return JPEGfileSizeLimit;
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

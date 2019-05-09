import junit.framework.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShutterstockDetailsTest extends TestCase{
    ShutterstockDetails ss = new ShutterstockDetails();

    @Test
    public void testContributorURL()
    {
        assertEquals(ss.getAgencyURL(), "https://contributor-accounts.shutterstock.com/login");
    }
    @Test
    public void testFTPURL()
    {
        assertEquals(ss.getFTPURL(), "ftp.shutterstock.com");
    }
    @Test
    public void testMaxKeywordNumber()
    {
        assertEquals(ss.getMaxKeywordNumber(), 50);
    }

    @Test
    public void testMinKeywordNumber()
    {
        assertEquals(ss.getMinKeywordNumber(), 8);
    }

    @Test
    public void testMinMp()
    {
        assertEquals(ss.getMinMP(), 4);
    }

    @Test
    public void testMaxMp()
    {
        assertEquals(ss.getMaxMP(), Integer.MAX_VALUE);
    }

    @Test
    public void testMinPixlesX()
    {
        assertEquals(ss.getMinPixelsX(), 1);
    }
    @Test
    public void testMaxPixelsX()
    {
        assertEquals(ss.getMaxPixelsX(), Integer.MAX_VALUE);
    }

    @Test
    public void testMinPixlesY()
    {
        assertEquals(ss.getMinPixelsY(),1);
    }

    @Test
    public void testMaxPixelsY()
    {
        assertEquals(ss.getMaxPixelsY(), Integer.MAX_VALUE);
    }

    @Test
    public void testMaxTitleLengthInChars()
    {
        assertEquals(ss.getMaxTitleLengthInChars(), Integer.MAX_VALUE);
    }


    @Test
    public void testMinTitleLengthInChars()
    {
        assertEquals(ss.getMinTitleLengthInChars(), 0);
    }

    @Test
    public void testMaxTitleLengthInWords()
    {
        assertEquals(ss.getMaxTitleLengthInWords(), Integer.MAX_VALUE);
    }


    @Test
    public void testMinTitleLengthInWords()
    {
        assertEquals(ss.getMinTitleLengthInWords(), 0);
    }
}

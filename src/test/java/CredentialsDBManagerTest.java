import junit.framework.*;
import org.junit.Test;
import static org.junit.Assert.*;


public class CredentialsDBManagerTest extends  TestCase{
    CredentialsDBManager DBM = new CredentialsDBManager("abc");
    @Test
    public void TableExistsTest(){
        assertEquals(DBM.tableExists(), true);
    }

    @Test
    public void TestFTPUser(){
        String password = "5p2tvn92R0di8FdiLCfzeeT0b";

        AgencyCredentials AC = new AgencyCredentials(Agency.TEST, "dlpuser@dlptest.com", password.toCharArray(),"dlpuser@dlptest.com", password.toCharArray());

        assertEquals(true,DBM.saveFTPcredentials(AC));
    }

    @Test
    public void GetTestFTPUser()
    {
        String password = "5p2tvn92R0di8FdiLCfzeeT0b";
        AgencyCredentials AC_true = new AgencyCredentials(Agency.TEST, "dlpuser@dlptest.com", password.toCharArray(),"dlpuser@dlptest.com", password.toCharArray());

        AgencyCredentials AC = DBM.getFTPcredentials(Agency.TEST);


        assertEquals(AC_true.getAgency().getCode(), AC.getAgency().getCode());
        assertEquals(AC_true.getUname(), AC.getUname());
        assertEquals(AC_true.getPwd(), AC.getPwd());
        assertEquals(AC_true.getFTPuname(), AC.getFTPuname());
        assertEquals(AC_true.getFTPpwd(), AC.getFTPpwd());
    }


}

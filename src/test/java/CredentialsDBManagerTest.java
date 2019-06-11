import junit.framework.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import static org.junit.Assert.*;


public class CredentialsDBManagerTest extends  TestCase{
    CredentialsDBManager DBM = new CredentialsDBManager("abc");

    @Test
    public void testConnection()
    {
        assertEquals(true, DBM.connectToDB());
    }
    @Test
    public void testTableExists(){
        DBM.createTable();
        assertEquals(DBM.tableExists(), true);
    }

    @Test
    public void testFTPUserSave(){
        String password = "5p2tvn92R0di8FdiLCfzeeT0b";

        AgencyCredentials AC_true = new AgencyCredentials(Agency.TEST, "dlpuser@dlptest.com", password.toCharArray(),"dlpuser@dlptest.com", password.toCharArray());
        DBM.saveFTPcredentials(AC_true);

        AgencyCredentials AC = DBM.getFTPcredentials(Agency.TEST);


        assertEquals(AC_true.getAgency().getCode(), AC.getAgency().getCode());
        assertEquals(AC_true.getUname(), AC.getUname());
        assertEquals(null, AC.getPwd());
        assertEquals(AC_true.getFTPuname(), AC.getFTPuname());
        assertEquals(new String(AC_true.getFTPpwd()), new String(AC.getFTPpwd()));
    }

    @Test
    public void testGetFTPUsertest()
    {
        String password = "5p2tvn92R0di8FdiLCfzeeT0b";
        AgencyCredentials AC_true = new AgencyCredentials(Agency.TEST, "dlpuser@dlptest.com", password.toCharArray(),"dlpuser@dlptest.com", password.toCharArray());

        AgencyCredentials AC = DBM.getFTPcredentials(Agency.TEST);


        assertEquals(AC_true.getAgency().getCode(), AC.getAgency().getCode());
        assertEquals(AC_true.getUname(), AC.getUname());
        assertEquals(null, AC.getPwd());
        assertEquals(AC_true.getFTPuname(), AC.getFTPuname());
        assertEquals(new String(AC_true.getFTPpwd()), new String(AC.getFTPpwd()));
    }

    @Test
    public void testGetAgencyUsertest()
    {
        String password = "5p2tvn92R0di8FdiLCfzeeT0b";
        AgencyCredentials AC_true = new AgencyCredentials(Agency.TEST, "dlpuser@dlptest.com", password.toCharArray(),"dlpuser@dlptest.com", password.toCharArray());

        AgencyCredentials AC = DBM.getAgencyCredentials(Agency.TEST);


        assertEquals(AC_true.getAgency().getCode(), AC.getAgency().getCode());
        assertEquals(AC_true.getUname(), AC.getUname());
        assertEquals(new String(AC_true.getPwd()), new String(AC.getPwd()));
        assertEquals(AC_true.getFTPuname(), AC.getFTPuname());
        assertEquals(null, AC.getFTPpwd());
    }

}

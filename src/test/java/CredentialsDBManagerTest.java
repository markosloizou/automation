import junit.framework.*;
import org.junit.Test;
import static org.junit.Assert.*;


public class CredentialsDBManagerTest extends  TestCase{
    CredentialsDBManager DBM = new CredentialsDBManager("abc");
    @Test
    public void TableExistsTest(){
        assertEquals(DBM.tableExists(), false);
    }
}

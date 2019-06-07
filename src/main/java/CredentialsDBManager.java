import java.io.File;
import java.sql.*;

public class CredentialsDBManager {
    private String userPwd;
    private String DBfilename = "credentials.db";
    private Connection connection;
    private String DBURL;
    private String tableName = "credentials";

    public CredentialsDBManager(String pwd) {
        userPwd = pwd;

        String home = System.getProperty("user.home");
        File file = new java.io.File(home + "/sqlite/db/" + DBfilename);
        String url = file.getAbsolutePath();
        DBURL = url;
    }


    private void connectToDB() {
        try {
            connection = DriverManager.getConnection(DBURL);
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean tableExists(){
        if(connection == null)
        {
            connectToDB();
        }

        try {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);

            while (rs.next()) {
                if(rs.getString("TABLE_NAME") == tableName) {
                    return true;
                }
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public  void createTable() {
        if(connection == null)
        {
            connectToDB();
        }

        try{
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS CREDENTIALS"
                    + " (ID             INT PRIMARY KEY     NOT NULL,"
                    + " AGENCY          INT    NOT NULL, "
                    + " UNAME           CHAR(50) NOT NULL,"
                    + "PWD              CHAR(50) NOT NULL,"
                    + "FTPUNAME         CHAR(50) NOT NULL,"
                    + "FTPPWD           CHAR(50) NOT NULL"
                    +  ")";
            stmt.executeUpdate(sql);
        }catch(SQLException e){
            e.printStackTrace();
        }


    }

    public void getFTPcredentials(Agency a)
    {
        if(!tableExists()) createTable();
    }

    public void saveFTPcredentials(AgencyCredentials AC)
    {
        connectToDB();
    }
}


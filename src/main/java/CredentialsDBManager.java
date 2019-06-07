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

    public static void createTable() {

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


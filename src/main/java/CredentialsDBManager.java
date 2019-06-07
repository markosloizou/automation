import java.io.File;
import java.sql.*;

public class CredentialsDBManager {
    private String userPwd;
    private String DBfilename = "stockautomation.db";
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
                    + "(AGENCY          INT   PRIMARY KEY NOT NULL, "
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

    public AgencyCredentials getFTPcredentials(Agency a)
    {
        AgencyCredentials AC = new AgencyCredentials(null, null, null, null, null);
        if(!tableExists()) createTable();
        try{
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM " + tableName + " WHERE AGENCY = " + a + ";";
            ResultSet rc = stmt.executeQuery(sql);
            AC = new AgencyCredentials(Agency.values()[rc.getInt("AGENCY")] , rc.getString("UNAME"), null, rc.getString("FTPUNAME"), rc.getString("FTPPWD").toCharArray()  );
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return AC;
    }

    public boolean saveFTPcredentials(AgencyCredentials AC) {
        boolean check = false;
        if (connection == null){
            connectToDB();
        }
        try{
            // the mysql insert statement
            String query = " insert into users (AGENCY, UNAME, PWD, FTPUNAME, FTPPWD)"
                    + " values (?, ?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt (1,  AC.getAgency().getCode());
            preparedStmt.setString (2,  AC.getUname());
            preparedStmt.setString   (3, String.valueOf( AC.getPwd()) );
            preparedStmt.setString (4,  AC.getFTPuname());
            preparedStmt.setString    (5, String.valueOf(AC.getFTPpwd()));

            check = preparedStmt.execute();
            if(!check)
            {
                System.out.println("Could not save new site credentials");
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return check;
    }
}


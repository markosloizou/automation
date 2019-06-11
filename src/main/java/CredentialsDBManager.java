import java.io.File;
import java.sql.*;


public class CredentialsDBManager {
    private String userPwd;
    private String DBfilename = "stockautomation.db";
    private Connection connection;
    private String DBURL;
    private String tableName = "CREDENTIALS";

    public CredentialsDBManager(String pwd) {
        userPwd = pwd;

        String home = System.getProperty("user.home");
        String filepath =   home + "/IdeaProjects/stockautomator/sqlite/" + DBfilename;
        File file = new java.io.File(filepath);
        String url = file.getAbsolutePath();
        url = "jdbc:sqlite:" + url;

        DBURL = url;
        try {
            Class.forName("org.sqlite.JDBC");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        connection = null;
    }


    public boolean connectToDB() {
        try {

            connection = DriverManager.getConnection(DBURL);
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
            } else {
                System.out.println("Could not connect to database");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (connection != null) {
            return true;
        } else {
            System.out.println("Could not connect to DB");
            return false;
        }
    }

    public boolean tableExists(){
        connectToDB();
        ResultSet rs = null;
        try {
            DatabaseMetaData md = connection.getMetaData();
            rs = md.getTables(null, null, "%", null);

            while (rs.next()) {
                if(rs.getString("TABLE_NAME").equals(tableName)) {
                    return true;
                }
            }


        }catch (SQLException e) {
            e.printStackTrace();

        }  finally{
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    public  void createTable() {
        connectToDB();
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
        }  finally {
            try { connection.close(); } catch (Exception e) { /* ignored */ }
        }


    }

    public AgencyCredentials getFTPcredentials(Agency a) {


        AgencyCredentials AC = new AgencyCredentials(null, null, null, null, null);
        ResultSet rs = null;
        if(!tableExists()) createTable();
        connectToDB();
        try{
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM " + tableName + " WHERE AGENCY = " + a.getCode() + ";";
            rs = stmt.executeQuery(sql);
            AC = new AgencyCredentials(Agency.get(rs.getInt("Agency")), rs.getString("UNAME"), null, rs.getString("FTPUNAME"), rs.getString("FTPPWD").toCharArray()  );
        }catch (SQLException e)
        {
            e.printStackTrace();
        } finally{
            try{
                rs.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return AC;
    }

    public AgencyCredentials getAgencyCredentials(Agency a) {


        AgencyCredentials AC = new AgencyCredentials(null, null, null, null, null);
        ResultSet rs = null;
        if(!tableExists()) createTable();
        connectToDB();
        try{
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM " + tableName + " WHERE AGENCY = " + a.getCode() + ";";
            rs = stmt.executeQuery(sql);
            AC = new AgencyCredentials(Agency.get(rs.getInt("Agency")), rs.getString("UNAME"), rs.getString("PWD").toCharArray(), rs.getString("FTPUNAME"), null );
        }catch (SQLException e)
        {
            e.printStackTrace();
        } finally{
            try{
                rs.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return AC;
    }

    public void saveFTPcredentials(AgencyCredentials AC) {
        PreparedStatement preparedStmt = null;
        if(!tableExists()) createTable();
        connectToDB();
        try{
            // the mysql insert statement
            String query = " insert or replace into " + tableName +  " (AGENCY, UNAME, PWD, FTPUNAME, FTPPWD)"
                    + " values (?, ?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt (1,  AC.getAgency().getCode());
            preparedStmt.setString (2,  AC.getUname());
            preparedStmt.setString   (3, String.valueOf( AC.getPwd()) );
            preparedStmt.setString (4,  AC.getFTPuname());
            preparedStmt.setString    (5, String.valueOf(AC.getFTPpwd()));

            preparedStmt.execute();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }finally {
            if (preparedStmt != null) {
                try {
                    preparedStmt.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) { /* ignored */}
            }
        }

    }
}


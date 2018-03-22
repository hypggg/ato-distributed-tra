package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.sql.DataSource;


 /**
  * Demonstration of how Atomikos and Spring can make
  * regular Java classes transactional. Note that there is no
  * explicit dependency on Atomikos nor Spring in this code.
  *
  */

@Resource
public class Bank 
{

    private DataSource dataSource;

    
    public Bank() {}

    /**
     * This method allows the DataSource to be set.
     * Spring's configuration facilities will call
     * this method for us.
     */
    
    public void setDataSource ( DataSource dataSource )
    {
        this.dataSource = dataSource;
    }

    //
    //Utility methods
    //

    private DataSource getDataSource()
    {
        return this.dataSource;
    }


    private Connection getConnection() throws SQLException
    {
        Connection ret = null;
        if ( getDataSource() != null ) {
            ret = getDataSource().getConnection();
        }
        return ret;
    }

    private void closeConnection ( Connection c )
    throws SQLException
    {
        if ( c != null ) c.close();
    }
    
    
    //
    //Business methods are below
    //

    public long getBalance ( int account )
    throws SQLException
    {
        
        long res = -1;
        Connection conn = null;

        try {
            conn = getConnection();
            Statement s = conn.createStatement();
            String query = "select balance from Accounts where account='"
                +"account"+account+"'";
            ResultSet rs = s.executeQuery ( query );
            if ( rs == null || !rs.next() ) 
                throw new SQLException ( "Account not found: " + account );
            res = rs.getLong ( 1 );
            s.close();
        }
        finally {
            closeConnection ( conn );
        }
        return res;
        
    }

    public void withdraw ( int account , int amount )
        throws Exception
    {

        
        Connection conn = null;

        try {
            conn = getConnection();
            Statement s = conn.createStatement();

            String sql = "update Accounts set balance = balance - " 
                + amount + " where account ='account"+account+"'";
            s.executeUpdate ( sql );
            s.close();
        }
        finally {
            closeConnection ( conn );

        }

    }
    
    public void deposit (int account,int amount) throws Exception{
    	 
        Connection conn = null;

        try {
            conn = getConnection();
            Statement s = conn.createStatement();

            String sql = "update Accounts set balance = balance + " 
                + amount + " where account ='account"+account+"'";
            s.executeUpdate ( sql );
            s.close();
        }
        finally {
            closeConnection ( conn );

        }
    }
    
    
}

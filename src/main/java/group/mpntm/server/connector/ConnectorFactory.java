package group.mpntm.server.connector;

import java.sql.*;

public class ConnectorFactory {
    private static final String USER =   "bolsavaloreslp2";
    private static final String PASS =   "bolsavaloreslp2";

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final String DATABASE =  "BOLSA_VALORES_LP2";
    private static final int PORT = 3306;
    private static final String URL  =   "jdbc:mysql://localhost:" + PORT + "/" + DATABASE;

    private static final String ERROBD = "Erro na conexão com o Banco de Dados: "; 

    public static Connection getConn()
    {   try
        {   Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        }
        catch(ClassNotFoundException | SQLException e) 
        {   throw new RuntimeException(ERROBD + e);
        }
    }

    public static void closeConn(Connection conn)
    {   try
        {   if(conn != null) 
            {   conn.close();
            }
        } 
        catch(SQLException e) 
        {   throw new RuntimeException(ERROBD + e);
        }
    }

    public static void closeConn(Connection conn, PreparedStatement stmt) 
    {   closeConn(conn);
        try
        {   if(stmt != null) 
            {   stmt.close();
            }
        } 
        catch(SQLException e) 
        {   throw new RuntimeException(ERROBD + e);
        }
    }
}

package group.mpntm.server.database.repo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import group.mpntm.server.database.connector.ConnectorFactory;
import group.mpntm.share.cripto.Criptography;

public class RepositoryMySQL {
    private static String login_table_name = "LOGIN";

    public static void main(String[] args) {
        createLogin("admin", "admin");
    }

    public static void createLogin(String name, String password) {

        String sqlInsert = "INSERT INTO " + login_table_name + " VALUES (0, ?, ?);";
        Connection conn = ConnectorFactory.getConn();
        PreparedStatement stmt = null;
        try{
            String encryptPass = Criptography.encryptRSA(password);
            
            stmt = conn.prepareStatement(sqlInsert);
            stmt.setString(1, name);
            System.out.println(encryptPass);
            stmt.setString(2, encryptPass);
            stmt.executeUpdate();
        }
        catch(SQLException e){
            try{   
                conn.rollback();
            }
            catch(SQLException ex){
                System.out.println("Erro ao incluir os dados: " + ex.toString());
            }
        }
        catch (Exception ex){
            System.out.println("Erro na criptografia: "+ ex.getMessage());
        }
        finally{   
            ConnectorFactory.closeConn(conn, stmt);
        }

    }

}

package group.mpntm.server.database.repo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import group.mpntm.server.database.connector.ConnectorFactory;
import group.mpntm.share.cripto.Criptography;

public class RepositoryMySQL {
    private static String login_table_name = "LOGIN";

    public static void main(String[] args) {
        // Test create login for admin
        createLogin("joaobranco", "teste123!");

        // // Test get pass for admin
        // String pass = getPass("admin");
        // System.out.println("Senha do admin criptografada: " + pass);
        // String passDecrypt;
        // try {
        //     passDecrypt = Criptography.decryptRSA(pass);
        // } catch (Exception e) {
        //     passDecrypt = "Deu merda";
        // }
        // System.out.println("Senha do admin descriptografada: " + passDecrypt);

        // // Test get pass for non existent user
        // String pass2 = getPass("nonexistent");
        // System.out.println("Senha do nonexistent criptografada: " + pass2);
    }

    public static void createLogin(String name, String password) {

        String sqlInsert = "INSERT INTO " + login_table_name + " VALUES (0, ?, ?);";
        Connection conn = ConnectorFactory.getConn();
        PreparedStatement stmt = null;
        try{
            String encryptPass = Criptography.encryptRSA(password);
            
            stmt = conn.prepareStatement(sqlInsert);
            stmt.setString(1, name);
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

    public static String getPass(String name){
        String sqlSelect = "SELECT * FROM " + login_table_name + " WHERE name = ?";
        Connection conn = ConnectorFactory.getConn();
        PreparedStatement stmt = null;
        ResultSet rs;
        String pass = "-1";
        try{   
            stmt = conn.prepareStatement(sqlSelect);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            while(rs.next()){
                pass = rs.getString("pass");
            }
        }
        catch(SQLException ex){   
            System.out.println("Erro ao consultar os dados" + ex.toString());
        }
        finally{
            ConnectorFactory.closeConn(conn, stmt);
        }       
        return pass;
    }

}

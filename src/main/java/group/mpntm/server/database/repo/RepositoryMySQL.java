package group.mpntm.server.database.repo;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import group.mpntm.client.Candle;
import group.mpntm.server.database.connector.ConnectorFactory;
import group.mpntm.share.cripto.Criptography;

public class RepositoryMySQL {
    private static String login_table_name = "LOGIN";
    private static String candles_table_name = "CANDLES";

    public static void main(String[] args) {
        // // Test create login for admin
        // createLogin("joaobranco", "teste123!");

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
        // System.out.println("Senha do nonexistent criptografada: " + pass2);]

        // // Test createCandle
        // Candle c = new Candle(1.0 ,2.0, 3.0, 4.0);
        // createCandle(c);

        // // Test deleteCandleTable
        // deleteCandleTable();

        // // Test getAllCandles
        // List<Candle> candles = tailCandles(50);
        // System.out.println("O tamanho eh: " + candles.size());

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
   
    public static void createCandle(Candle candle){
        String sqlInsert = "INSERT INTO " + candles_table_name + " VALUES(?, ?, ?, ?, ?, 0)";
        Connection conn = ConnectorFactory.getConn();
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement(sqlInsert);
            stmt.setDouble(1, candle.getOpen());
            stmt.setDouble(2, candle.getClose());
            stmt.setDouble(3, candle.getHigh());
            stmt.setDouble(4, candle.getLow());
            stmt.setString(5, candle.getDate());
            stmt.executeUpdate();
        }
        catch(SQLException e){
            try{   
                conn.rollback();
            }
            catch(SQLException ex){
                System.out.println("Erro ao incluir os dados" + ex.toString());
            }
        }
        finally{   
            ConnectorFactory.closeConn(conn, stmt);
        }
    }

    public static void deleteCandleTable(){
        String sqlDelete = "DELETE FROM " + candles_table_name + " WHERE date != \"-1\"";
        Connection conn = ConnectorFactory.getConn();
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement(sqlDelete);
            stmt.executeUpdate();
        }
        catch(SQLException e){
            try{   
                conn.rollback();
            }
            catch(SQLException ex){
                System.out.println("Erro ao incluir os dados" + ex.toString());
            }
        }
        finally{   
            ConnectorFactory.closeConn(conn, stmt);
        }
        
    }
   
    public static List<Candle> getAllCandles(){
        List<Candle> dados = new ArrayList<Candle>();
        String sqlSelect = "SELECT * FROM " + candles_table_name;
        Connection conn = ConnectorFactory.getConn();
        PreparedStatement stmt = null;
        ResultSet result_set;
        try{   
            stmt = conn.prepareStatement(sqlSelect);
            result_set = stmt.executeQuery();
            while(result_set.next()){  
                Candle data = new Candle(
                    result_set.getDouble("openValue"),
                    result_set.getDouble("closeValue"),
                    result_set.getDouble("highValue"),
                    result_set.getDouble("lowValue"),
                    result_set.getString("date")
                    );
                    dados.add(data);
                }
            }
            catch(SQLException ex){
                System.out.println("Erro ao buscar todos os dados" + ex.toString());
        }
        finally{   
            ConnectorFactory.closeConn(conn, stmt);
        }   
        return dados;
    }  
    
    public static List<Candle> tailCandles(int n){
        List<Candle> dados = new ArrayList<Candle>();
        String sqlSelect = "SELECT * FROM " + candles_table_name + " ORDER BY idCandle DESC LIMIT " + String.valueOf(n);
        Connection conn = ConnectorFactory.getConn();
        PreparedStatement stmt = null;
        ResultSet result_set;
        try{   
            stmt = conn.prepareStatement(sqlSelect);
            result_set = stmt.executeQuery();
            while(result_set.next()){  
                Candle data = new Candle(
                    result_set.getDouble("openValue"),
                    result_set.getDouble("closeValue"),
                    result_set.getDouble("highValue"),
                    result_set.getDouble("lowValue"),
                    result_set.getString("date")
                    );
                    dados.add(data);
                }
            }
            catch(SQLException ex){
                System.out.println("Erro ao buscar todos os dados" + ex.toString());
        }
        finally{   
            ConnectorFactory.closeConn(conn, stmt);
        }   
        return dados;
    }
}

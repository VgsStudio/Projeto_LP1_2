//package group.mpntm.server.repo;
//
//import main.back.domain.repo.RepositoryInterface;
//import main.back.shared.connector.ConnectorFactory;
//import main.entities.Element;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//import javax.sound.midi.SoundbankResource;
//
//public class UserRepositoryMySQL {
//    private String table_name = "ELEMENTS";
//
//    @Override
//    public ArrayList<Element> getAllElements() {
//        Connection conn = ConnectorFactory.getConn();
//
//        ArrayList<Element> dados = new ArrayList<>();
//        String sqlSelect = "SELECT * FROM " + table_name + ";";
//        PreparedStatement stmt = null;
//        ResultSet result_set;
//
//        try{
//            stmt = conn.prepareStatement(sqlSelect);
//            result_set = stmt.executeQuery();
//            while(result_set.next()){
//                Element prof = new Element(
//                    result_set.getInt("atomicNumber"),
//                    result_set.getString("name"),
//                    result_set.getString("symbol"),
//                    result_set.getInt("neutronNumber"),
//                    result_set.getInt("atomicMass"),
//                    result_set.getString("groupCode"),
//                    result_set.getInt("year"),
//                    result_set.getString("info")
//                );
//                dados.add(prof);
//            }
//        }
//        catch(SQLException ex){
//            System.out.println("Erro ao buscar todos os dados" + ex.toString());
//        }
//        finally{
//            ConnectorFactory.closeConn(conn, stmt);
//        }
//        return dados;
//    }
//
//    @Override
//    public Element getElement(int atomicNumber){
//        Connection conn = ConnectorFactory.getConn();
//
//        String sqlSelect = "SELECT * FROM " + table_name + " WHERE atomicNumber = ?";
//        PreparedStatement stmt = null;
//        ResultSet rs;
//
//        String name;
//        String symbol;
//        int neutronNumber;
//        int atomicMass;
//        String groupCode;
//        int year;
//        String info;
//
//        Element element = null;
//
//        try{
//            stmt = conn.prepareStatement(sqlSelect);
//            stmt.setInt(1, atomicNumber);
//            rs = stmt.executeQuery();
//            if(rs.next()){
//                name = rs.getString(2);
//                symbol = rs.getString(3);
//                neutronNumber = rs.getInt(4);
//                atomicMass = rs.getInt(5);
//                groupCode = rs.getString(6);
//                year = rs.getInt(7);
//                info = rs.getString(8);
//
//                element = new Element(rs.getInt(1), name, symbol, neutronNumber, atomicMass, groupCode, year, info);
//            }
//        }
//        catch(SQLException ex){
//            System.out.println("Erro ao buscar elemento" + ex.toString());
//            element = null;
//        }
//        finally{
//            ConnectorFactory.closeConn(conn, stmt);
//        }
//        return element;
//    }
//
//    @Override
//    public Element updateElement(int atomicNumber, String info){
//        Connection conn = ConnectorFactory.getConn();
//
//        String sqlUpdate = "UPDATE "+table_name+" SET info = ? WHERE atomicNumber = ?;";
//        PreparedStatement stmt = null;
//        try{
//            stmt = conn.prepareStatement(sqlUpdate);
//            stmt.setString(1, info);
//            stmt.setInt(2, Integer.valueOf(atomicNumber));
//            stmt.executeUpdate();
//        }
//        catch(SQLException e){
//            try{
//                conn.rollback();
//            }
//            catch(SQLException ex){
//                System.out.println("Erro ao alterar os dados\n" + ex.toString());
//            }
//        }
//        finally{
//            ConnectorFactory.closeConn(conn, stmt);
//        }
//
//        Element element = getElement(1);
//        return element;
//    }
//
//}

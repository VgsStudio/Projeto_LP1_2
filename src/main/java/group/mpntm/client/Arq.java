package group.mpntm.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;
// Utilizado para pegar tabela de logins local
public class Arq {
    private Scanner in; 
    private Formatter out; 
    private List<String> msgList = new ArrayList<String>();

    public void create(String fileName){
        try {
            out = new Formatter(fileName);
        } catch (SecurityException |FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,"Erro ao criar ou ao acessar o arquivo:" + fileName);
            System.exit(1);
        }
    }


    public void read(String path, String fileName){
      
        try{
            in = new Scanner(new File(path,fileName));
            while(in.hasNext()){
                String a; 
                try {
                    a = in.nextLine();
                    msgList.add(a);

                } catch (FormatterClosedException e) {
                    JOptionPane.showMessageDialog(null,"Erro ao ler o arquivo.");
                    System.exit(1);
                }

            }  

        }catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null,"Arquivo não encontrado.");
            System.exit(1);
        }
        
    }

    public void write(String msg,File file){
        try {
            FileWriter fw = new FileWriter(file,true);
            out = new Formatter(fw);
            out.format("%s \n", msg);
            fw.close();
            out.close();
        } catch ( IOException e) {
            
            e.printStackTrace();
        }
        
    }

    public void close(){
        if(out != null){
            out.close();
            
        }if(in != null){
            in.close();
        }

    }

    public String getMsgS(){
        String msg = msgList.toString();
        return msg;
    }

    public List<String> getMsgList(){
        return msgList; 
    }
    
}

package group.mpntm.client;

import java.io.*;
import java.util.*; 



public class SaveFile {
    private Scanner in;
    private Formatter out;
    private FileWriter writer;
    private List<String> list = new ArrayList<String>();

    public void read(File file) {
        try {
            in = new Scanner(file);
            while (in.hasNext()){
                String line;     
                line = in.nextLine();
                if(line.startsWith("Lang=")){
                   String [] split = line.split("(?:Lang=)");
                    for(String lang: split){
        
                        list.add(lang.trim());
                    }  
                }
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
        }
        
    }

    public void save(String save, File file){
        try {
            writer = new FileWriter(file);
            out = new Formatter(writer);
            out.format("Lang="+save);
            out.flush();
            writer.flush();
            out.close();
            writer.close();
        } catch (Exception e) {
          System.out.println("Error: " + e.getMessage());
        }
    }
    public String getSave(){
        String save = list.get(list.size() - 1);
        return save;
    }
    
   
   
}

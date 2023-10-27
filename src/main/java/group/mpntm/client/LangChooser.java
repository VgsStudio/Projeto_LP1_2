package group.mpntm.client;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;


public class LangChooser {
    private ResourceBundle bn;
    private SaveFile save; 

    private File file = new File("src\\main\\resources","save.txt");

    public LangChooser(String lang){
        chooseLang(lang);
    }

    public LangChooser(){
      chooseLang("Português");
    }

    public void chooseLang(String lang){
        String bundle = "bundle";
        switch ( lang ){
                        case "English":
                            bn = ResourceBundle.getBundle(bundle, Locale.US);
                        break;
                         case  "Português":
                            bn = ResourceBundle.getBundle(bundle, new Locale("pt", "BR"));
                        break;
                        case  "Deutsch":
                            bn = ResourceBundle.getBundle(bundle, new Locale("de", "DE"));
                        break;
                        case  "Español":
                            bn = ResourceBundle.getBundle(bundle, new Locale("es","ES"));
                        break;
                     
                    }
    }

    public ResourceBundle getBn() {
        return bn;
    }
    public String getLastLang(){
        save = new SaveFile();
        save.read(file);
        String langS = save.getSave();  
        return langS;
    }
    public void setLastLang(String lang){
        save = new SaveFile();
        save.save(lang, file);
        
    }

}

package group.mpntm.client;

import java.util.Locale;
import java.util.ResourceBundle;


public class LangChooser {
    private String lang, userLable, passLable, langTxt;   
    private ResourceBundle bn;

    public LangChooser(String lang){
        chooseLang(lang);
    }

    public LangChooser(){
        chooseLang("English");
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
                        default:
                            bn = ResourceBundle.getBundle(bundle, new Locale("pt", "BR"));
                        break;
                    }
    }

    public ResourceBundle getBn() {
        return bn;
    }
}

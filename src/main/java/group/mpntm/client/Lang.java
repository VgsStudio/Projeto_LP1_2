package group.mpntm.client;

import java.util.Locale;
import java.util.ResourceBundle;


public class Lang {
    private String lang, userLable, passLable, langTxt;   
    private ResourceBundle bn;

    public Lang(){
        
                    switch ( index ){
                        case "English":
                            bn = ResourceBundle.getBundle("Bundle", Locale.US);
                        break;
                         case  "Português": 
                            bn = ResourceBundle.getBundle("Bundle", new Locale("pt", "BR"));
                        break;
                        case  "Deutsch": 
                            bn = ResourceBundle.getBundle("Bundle", Locale.GERMAN);
                        break;
                        case  "Español":
                            bn = ResourceBundle.getBundle("Bundle", new Locale("es","ES"));
                        break;
                        case  "日本":
                            bn = ResourceBundle.getBundle("Bundle", Locale.JAPANESE);
                        break;
                        default: 
                            bn = ResourceBundle.getBundle("Bundle", new Locale("pt", "BR"));
                        break;
                    }         

    }
}

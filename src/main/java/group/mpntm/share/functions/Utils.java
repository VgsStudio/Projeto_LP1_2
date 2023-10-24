package group.mpntm.share.functions;

public class Utils {
    public static String[] splitFirstSpace(String str){
        String[] result = new String[] {"",""};
        String user = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' '){
                result[0] = user;
                result[1] = str.substring(i+1);
                return result;
            }
            user = user + str.charAt(i);
        }
        return result;
    }
}

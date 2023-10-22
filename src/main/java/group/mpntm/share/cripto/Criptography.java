package group.mpntm.share.cripto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.RSAKeyGenParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Criptography {
    private static String publicKeyPath = "src\\main\\java\\group\\mpntm\\share\\cripto\\chave.publica";
    private static String privateKeyPath = "src\\main\\java\\group\\mpntm\\share\\cripto\\chave.privada";

    public static void main(String[] args) {
        // try{
        //     generatePubPrivKeys();
        // }
        // catch (Exception e){
        //     e.printStackTrace();
        // }

        String mensagem = "admin";
        try {
            String msgCript = encryptRSA(mensagem);
            String msgDecrypt = decryptRSA(msgCript);

            System.out.println("Mensagem original: " + mensagem);
            System.out.println("Mensagem criptografada: " + msgCript);
            System.out.println("Mensagem descriptografada: " + msgDecrypt);
        } catch (Exception e) {

        }
        
    }

    public static void generatePubPrivKeys() throws   IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, InvalidAlgorithmParameterException {  
        final int RSAKEYSIZE = 1024;
        KeyPairGenerator kpg = KeyPairGenerator.getInstance ("RSA");  
        kpg.initialize (new RSAKeyGenParameterSpec(RSAKEYSIZE, RSAKeyGenParameterSpec.F4));  
        KeyPair kpr = kpg.generateKeyPair();
        PrivateKey   oPriv = kpr.getPrivate();          
        PublicKey    oPub  = kpr.getPublic();  

        //-- Gravando a chave publica em formato serializado  
        File fPub = new File (publicKeyPath);
        ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (fPub));  
        oos.writeObject (oPub);  
        oos.close();  

        //-- Gravando a chave privada em formato serializado  
        File fPvk = new File (privateKeyPath);
        oos = new ObjectOutputStream (new FileOutputStream (fPvk));  
        oos.writeObject (oPriv);  
        oos.close();  
    }

    public static String encryptRSA (String txt) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException, ClassNotFoundException {
        byte[] byteTxt = txt.getBytes("ISO-8859-1");
        File fPub = new File (publicKeyPath);
        ObjectInputStream ois = new ObjectInputStream (new FileInputStream (fPub));  
        PublicKey iPub = (PublicKey) ois.readObject();  
        ois.close();  
        Cipher rsacf = Cipher.getInstance ("RSA");  
        rsacf.init (Cipher.ENCRYPT_MODE, iPub); 
        byte[] binaryCipher = rsacf.doFinal(byteTxt);
        return new String (binaryCipher, "ISO-8859-1");
    }
    
   public static String decryptRSA (String txt) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException, ClassNotFoundException {  
        byte[] byteTxt = txt.getBytes("ISO-8859-1");
        File fPrv = new File (privateKeyPath);
        ObjectInputStream ois = new ObjectInputStream (new FileInputStream (fPrv));  
        PrivateKey iPrv = (PrivateKey) ois.readObject();  
        ois.close();  
        Cipher rsacf = Cipher.getInstance ("RSA");  
        rsacf.init (Cipher.DECRYPT_MODE, iPrv); 
        byte[] binaryText = rsacf.doFinal (byteTxt); 
        return new String (binaryText, "ISO-8859-1");
   }
}

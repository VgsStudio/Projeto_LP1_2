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
    public static void main(String[] args) {
        try{
            String path = "src\\main\\java\\group\\mpntm\\share\\cripto\\";
            generatePubPrivKeys(new File (path+"chave.publica"), new File (path+"chave.privada"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void generatePubPrivKeys(File fPub, File fPvk) throws   IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, InvalidAlgorithmParameterException {  
        final int RSAKEYSIZE = 1024;
        KeyPairGenerator kpg = KeyPairGenerator.getInstance ("RSA");  
        kpg.initialize (new RSAKeyGenParameterSpec(RSAKEYSIZE, RSAKeyGenParameterSpec.F4));  
        KeyPair kpr = kpg.generateKeyPair();
        PrivateKey   oPriv = kpr.getPrivate();          
        PublicKey    oPub  = kpr.getPublic();  

        //-- Gravando a chave publica em formato serializado  
        ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (fPub));  
        oos.writeObject (oPub);  
        oos.close();  

        //-- Gravando a chave privada em formato serializado  
        oos = new ObjectOutputStream (new FileOutputStream (fPvk));  
        oos.writeObject (oPriv);  
        oos.close();  
    }

    public static String encryptRSA (byte[] txt, File fPub) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream (new FileInputStream (fPub));  
        PublicKey iPub = (PublicKey) ois.readObject();  
        ois.close();  
        Cipher rsacf = Cipher.getInstance ("RSA");  
        rsacf.init (Cipher.ENCRYPT_MODE, iPub); 
        byte[] binaryCipher = rsacf.doFinal(txt);
        return new String (binaryCipher, "ISO-8859-1");
    }
    
   public static String decryptRSA (byte[] txt, File fPrv) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException, ClassNotFoundException {  
        ObjectInputStream ois = new ObjectInputStream (new FileInputStream (fPrv));  
        PrivateKey iPrv = (PrivateKey) ois.readObject();  
        ois.close();  
        Cipher rsacf = Cipher.getInstance ("RSA");  
        rsacf.init (Cipher.DECRYPT_MODE, iPrv); 
        byte[] binaryText = rsacf.doFinal (txt); 
        return new String (binaryText, "ISO-8859-1");
   }
}

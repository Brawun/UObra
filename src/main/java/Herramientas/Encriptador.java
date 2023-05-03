/**
 * Encriptador.java
 */
package Herramientas;

// Importaciones
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 * Esta clase permite encapsular herramientas útiles a la hora de querer
 * implementar encriptación.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class Encriptador {

    // Atributos
    private static final String UNICODE_FORMAT = "UTF8";
    public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private final KeySpec ks;
    private final SecretKeyFactory skf;
    private final Cipher cipher;
    byte[] arrayBytes;
    private final String myEncryptionKey;
    private final String myEncryptionScheme;
    SecretKey key;

    /**
     * Método para inicializar la clase, establece ciertos atributos necesarios
     * para el fucionamiento del encriptador, como la contraseña, esquema de
     * encriptación, etc.
     *
     * @throws Exception En caso de algún error.
     */
    public Encriptador() throws Exception {
        myEncryptionKey = "AmoPruebasDeSoftware";
        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
        arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
        ks = new DESedeKeySpec(arrayBytes);
        skf = SecretKeyFactory.getInstance(myEncryptionScheme);
        cipher = Cipher.getInstance(myEncryptionScheme);
        key = skf.generateSecret(ks);
    }

    /**
     * Método para encriptar un String, con el algoritmo DES.
     *
     * @param sinEncriptar Mensaje a encriptar.
     * @return mensaje encriptado
     */
    public String encrypt(String sinEncriptar) {
        if (sinEncriptar.isBlank()) {
            return "";
        } else {
            String encriptado = null;
            try {
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] bytes = sinEncriptar.getBytes(UNICODE_FORMAT);
                byte[] bytesEncriptados = cipher.doFinal(bytes);
                encriptado = new String(Base64.encodeBase64(bytesEncriptados));
            } catch (UnsupportedEncodingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
                Logger.getLogger(Encriptador.class.getName()).log(Level.SEVERE, null, e);
            }
            return encriptado; // Se regresa el mensaje encriptado
        }
    }

    /**
     * Método para desencriptar un String, con el algoritmo DES.
     *
     * @param encriptado Mensaje encriptado.
     * @return mensaje desencriptado
     */
    public String decrypt(String encriptado) {
        if (encriptado.isBlank()) {
            return "";
        } else {
            String desencriptado = null;
            try {
                cipher.init(Cipher.DECRYPT_MODE, key);
                byte[] bytes = Base64.decodeBase64(encriptado.getBytes());
                byte[] plainText = cipher.doFinal(bytes);
                desencriptado = new String(plainText);
            } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
                Logger.getLogger(Encriptador.class.getName()).log(Level.SEVERE, null, e);
            }
            return desencriptado; // Se regresa el mensaje desencriptado
        }
    }
    
//    /**
//     * Método para desencriptar utilizando un Path, con el algoritmo DES.
//     *
//     * @param encriptado Mensaje encriptado.
//     * @return mensaje desencriptado
//     */
//    public Path<Object> decrypt(Path<Object> encriptado) {
//        if (encriptado == null) {
//            return null;
//        } else {
//            String desencriptado = null;
//            String encripto = encriptado.toString();
//            try {
//                cipher.init(Cipher.DECRYPT_MODE, key);
//                byte[] bytes = Base64.decodeBase64(encripto.getBytes());
//                byte[] plainText = cipher.doFinal(bytes);
//                desencriptado = new String(plainText);
//            } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
//                Logger.getLogger(Encriptador.class.getName()).log(Level.SEVERE, null, e);
//            }
//            return desencriptado; // Se regresa el mensaje desencriptado
//        }
//    }
}

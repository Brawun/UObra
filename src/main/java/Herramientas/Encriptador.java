/**
 * Encriptador.java
 */
package Herramientas;

// Importaciones
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

/**
 * Esta clase permite encapsular herramientas útiles a la hora de querer
 * implementar encriptación.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class Encriptador {

    private SecretKeySpec generarClave(String clave) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] claveEnBytes = clave.getBytes(StandardCharsets.UTF_8);
        claveEnBytes = sha.digest(claveEnBytes);
        claveEnBytes = Arrays.copyOf(claveEnBytes, 16);
        return new SecretKeySpec(claveEnBytes, "AES");
    }

    public String encriptarConClave(String texto, String clave) throws Exception {
        SecretKeySpec claveSecreta = generarClave(clave);
        Cipher cifrador = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cifrador.init(Cipher.ENCRYPT_MODE, claveSecreta);
        byte[] textoEnBytes = texto.getBytes(StandardCharsets.UTF_8);
        byte[] textoEncriptado = cifrador.doFinal(textoEnBytes);
        return Base64.getEncoder().encodeToString(textoEncriptado);
    }
    
    public String encrypt(String sinEncriptar) throws Exception {
        String clave = "AmoPruebasDeSoftware";
        return encriptarConClave(sinEncriptar, clave);
    }

    public String desencriptarConClave(String textoEncriptado, String clave) throws Exception {
        SecretKeySpec claveSecreta = generarClave(clave);
        Cipher cifrador = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cifrador.init(Cipher.DECRYPT_MODE, claveSecreta);
        byte[] textoEncriptadoEnBytes = Base64.getDecoder().decode(textoEncriptado);
        byte[] textoDesencriptado = cifrador.doFinal(textoEncriptadoEnBytes);
        return new String(textoDesencriptado, StandardCharsets.UTF_8);
    }
    
    public String decrypt(String encriptado) throws Exception {
        String clave = "AmoPruebasDeSoftware";
        return desencriptarConClave(encriptado, clave);
    }
}

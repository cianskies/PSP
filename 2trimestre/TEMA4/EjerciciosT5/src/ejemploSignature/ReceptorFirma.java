package ejemploSignature;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.util.Base64;

import org.omg.CORBA.portable.InputStream;

public class ReceptorFirma {
	public static void main(String[] args) {
		//Obteenemos la firma
		byte[] firma=extraerDeArchivo("firma.txt");
		//El texto a recibir
		byte[] texto=extraerDeArchivo("cadenaSignture.txt");
		
		//cargamos el almacen
		KeyStore almacen=cargarAlmacen("almacen.pk12","123qwe");
		//posteriormente cargamos la clave publica
		PublicKey clavePublica=extraerClavePublica(almacen);
		System.out.println("La clave publica es "+clavePublica.toString());
		//con la clave publica, comprobamos si la firma es correcta
		
		boolean firmaCorrecta=comprobarFirma(firma, clavePublica, texto);
		if(firmaCorrecta) {
			System.out.println("OK");
		}else {
			System.out.println("Nok");
		}
		
	}
	
	private static byte[] extraerDeArchivo(String rutaArchivo) {
		byte[] archivoExtraido=null;
		Path path=Paths.get(rutaArchivo);
		BufferedReader br;
		try {
			br = Files.newBufferedReader(path);
			String archivoCodificado=br.readLine();
			archivoExtraido=decodificarBase64(archivoCodificado);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return archivoExtraido;
		
	}
	
	private static byte[] decodificarBase64(String codificado) {
		return Base64.getDecoder().decode(codificado);
	}

	private static KeyStore cargarAlmacen(String almacen, String contrasenha) {
		KeyStore ks=null;	
		try {
			ks=KeyStore.getInstance("PKCS12");
			ks.load(new FileInputStream(almacen), contrasenha.toCharArray());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ks;
		
	}
	
	private static PublicKey extraerClavePublica(KeyStore almacen) {
		PublicKey clave=null;
		try {
			clave=(PublicKey) almacen.getCertificate("roberto").getPublicKey();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return clave;
	}

	private static boolean comprobarFirma(byte[] firma, PublicKey clave,byte[] contenidoAComprobar) {
		
		boolean verificado=false;
		//Inicializamos signature
		try {
			Signature signature = Signature.getInstance("SHA256withRSA");
			//iniciamos verificacion con la clave publica
			signature.initVerify(clave);
			//ahora introducimos en signatire el mensaje a verificar
			signature.update(contenidoAComprobar);
			
			//Sera correcto si la firma coincide
			verificado=signature.verify(firma);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return verificado;

		
	}
}

package eu.organicity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

/**
 * This class parses a JSON Web Tokens (JWT) and verfies it agains the Organicity.
 * You can use this class also to use any other certificate by proding it
 * 
 * @author Dennis Boldt
 *
 */
public class JwtParser {

	InputStream is;
	
	/**
	 * Using this constructor, the default accounts.organicity.eu certifcate is used
	 */
	public JwtParser() {
		this.is = getClass().getResourceAsStream("/accounts.organicity.eu.cert.pem");
	}

	/**
	 * Using this constructor, any other certificate can be used
	 * 
	 * @param f
	 * @throws FileNotFoundException
	 */
	public JwtParser(File f) throws FileNotFoundException {
        this.is = new FileInputStream(f);
	}
	
	/*
	 * This reads the public key from the certificate 
	 */
	private PublicKey getPublicKeyFromCert() {
		PublicKey pk = null;

		CertificateFactory f = null;
		try {
			f = CertificateFactory.getInstance("X.509");
			X509Certificate certificate = (X509Certificate) f.generateCertificate(this.is);
			pk = certificate.getPublicKey();
		} catch (CertificateException e) {
			e.printStackTrace();
		}
		return pk;
	}

	/**
	 * 
	 * @param jwt A BASE64 encoded JWT
	 * @return A Claims object, which can be used 
	 * 
	 * @throws ExpiredJwtException If the JWT is expired, this exception is thrown
	 */
	public Claims parseJWT(String jwt) throws ExpiredJwtException{
		return Jwts.parser().setSigningKey(getPublicKeyFromCert()).parseClaimsJws(jwt).getBody();
	}
}

package eu.organicity;
import eu.organicity.JwtParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

public class TestJwtParser {
	public static void main(String[] args) {

		String token = args[0];
		JwtParser fwtparser = new JwtParser();
		
		try {
			Claims claims = fwtparser.parseJWT(token);
			System.out.println("ID: " + claims.getId());
			System.out.println("Subject (User id): " + claims.getSubject());
			System.out.println("Expiration: " + claims.getExpiration());
			System.out.println("Audience: " + claims.get("aud"));

			// Get some user data
			System.out.println("Username: " + claims.get("preferred_username"));
			System.out.println("Name: " + claims.get("name"));
			System.out.println("E-Mail: " + claims.get("email"));

			System.out.println("Component realm: " + claims.get("aud"));
			
		} catch (ExpiredJwtException e) {
			System.err.println(e.getMessage());
		}

	}

}

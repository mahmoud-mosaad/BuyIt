package intelligient.transportation.dao;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenHandler {
	
	public static String createToken(int userId) {
	      String TOKEN_SECRET = "s4T2zOIWHMM1sxq";
	      try {
	          Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
	          String token = JWT.create()
	                  .withClaim("userId", userId)
	                  .withClaim("createdAt", new Date())
	                  .sign(algorithm);
	          return token;
	      } catch (UnsupportedEncodingException exception) {
	          exception.printStackTrace();
	          //log WRONG Encoding message
	      } catch (JWTCreationException exception) {
	          exception.printStackTrace();
	          //log Token Signing Failed
	      }
	      return null;
	  }
	  
	  public static int getUserIdFromToken(String token) {
	  	String TOKEN_SECRET = "s4T2zOIWHMM1sxq";
	      try {
	          Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
	          JWTVerifier verifier = JWT.require(algorithm)
	                  .build();
	          DecodedJWT jwt = verifier.verify(token);
	          return jwt.getClaim("userId").asInt();
	      } catch (UnsupportedEncodingException exception) {
	          exception.printStackTrace();
	          //log WRONG Encoding message
	          return -1;
	      } catch (JWTVerificationException exception) {
	          exception.printStackTrace();
	          //log Token Verification Failed
	          return -1;
	      }
	  }
}

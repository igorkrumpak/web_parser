package si.iitech.lib.security.impl;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static si.iitech.lib.security.impl.SecurityConstants.EXPIRATION_TIME;
import static si.iitech.lib.security.impl.SecurityConstants.TOKEN;
import static si.iitech.lib.security.impl.SecurityConstants.SECRET;
import static si.iitech.lib.security.impl.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			ObjectMapper mapper = new ObjectMapper();
	        JsonNode jsonMap = mapper.readTree(req.getInputStream());
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jsonMap.get("email").asText(),
				 jsonMap.get("password").asText(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		String token = JWT.create().withSubject(((User) auth.getPrincipal()).getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).sign(HMAC512(SECRET.getBytes()));
		res.addHeader(TOKEN, TOKEN_PREFIX + token);
	}
}
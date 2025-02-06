package com.grocery.config;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.grocery.service.impl.CustomUserDetailsService;
import com.grocery.service.impl.JwtUtils;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private JwtUtils jwtUtil;

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;

		if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith("Bearer ")) {

			jwtToken = requestTokenHeader.substring(7);

			try {
				username = this.jwtUtil.extractUsername(jwtToken);
			} catch (ExpiredJwtException e) {
				logger.error("JWT token has expired: {}", e.getMessage());
				sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
				return;
			} catch (Exception e) {
				logger.error("Error extracting username from token: {}", e.getMessage());
				sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Invalid token");
				return;
			}

		} else {
			logger.warn("Invalid token: Authorization token missing or invalid");
			sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Authorization token missing or invalid");
			return;
		}

		// validated
		if (Objects.nonNull(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
			final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if (this.jwtUtil.validateToken(jwtToken, userDetails)) {
				// token is valid

				UsernamePasswordAuthenticationToken usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);
			} else {
				logger.error("Invalid token");
				sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
				return;
			}
		} else {
			logger.error("Token is not valid or not found in context");
			sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
			return;
		}

		filterChain.doFilter(request, response);

	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();
		return path.equals("/register") || path.equals("/generate-token") || path.equals("/statusCheck");
	}

	// Utility method to send error response
	private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
		response.setStatus(statusCode);
		response.setContentType("application/json");
		response.getWriter().write(String.format("{\"message\": \"%s\"}", message));
	}

}

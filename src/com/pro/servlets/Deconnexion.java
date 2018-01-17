package com.pro.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Deconnexion extends HttpServlet {
	public static final String VUE = "/pro/connexion";
	
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/* récupération, puis destruction de la session en cours*/
		HttpSession session = request.getSession();
		session.invalidate();
		
		/*renvoi de la page de connexion (nettoyée de la session) au client*/
		response.sendRedirect(VUE);
		
	}
}

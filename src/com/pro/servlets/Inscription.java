package com.pro.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pro.beans.Utilisateur;
import com.pro.forms.InscriptionForm;

public class Inscription extends HttpServlet {

	public static final String VUE = "/WEB-INF/inscription.jsp" ;
	public static final String ATT_FORM = "form";
	public static final String ATT_USER = "utilisateur";
	

	public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response) ;		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*appel aux procedures de validation du formulaire traitée dans l'objet métier InscriptionForm
		 * Les données du formulaire (map erreurs et message de resultat) sont dans l'objet form
		 * Celles concernant l'utilisateur dont dans le bean. */
		
		InscriptionForm inscForm = new InscriptionForm();
		Utilisateur utilisateur = inscForm.inscrireUtilisateur(request);
		
		request.setAttribute(ATT_FORM, inscForm);
		request.setAttribute(ATT_USER, utilisateur);
		
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response) ;

	}

	
}

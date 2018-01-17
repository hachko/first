package com.pro.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.pro.beans.Fichier;
import com.pro.forms.UploadForm;

public class Upload extends HttpServlet {

	public static final String VUE = "/WEB-INF/upload.jsp";	
	public static final String CHEMIN        = "chemin";	
	public static final String ATT_FICHIER = "fichier";
    public static final String ATT_FORM    = "form";
	
	public static final int TAILLE_TAMPON = 10240; //10 Ko

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		/* Affichage de la page d'envoi de fichiers */
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

		/*
		 * Lecture du paramètre 'chemin' passé à la servlet via la déclaration
		 * dans le web.xml
		 */
		String chemin = this.getServletConfig().getInitParameter( CHEMIN );

		/* Préparation de l'objet formulaire */
		UploadForm form = new UploadForm();

		/* Traitement de la requête et récupération du bean en résultant */
		Fichier fichier = form.enregistrerFichier( request, chemin );

		/* Stockage du formulaire et du bean dans l'objet request */
		request.setAttribute( ATT_FORM, form );
		request.setAttribute( ATT_FICHIER, fichier );

		ServletContext contexte = this.getServletContext();
		RequestDispatcher dispatcher = contexte.getRequestDispatcher(VUE);
		try{
			dispatcher.forward( request, response );
		} catch (ServletException se) {
			se.printStackTrace();
		}

	}

}
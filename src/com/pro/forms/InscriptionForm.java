package com.pro.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.pro.beans.Utilisateur;

public class InscriptionForm {

	public static final String POST_MAIL = "email" ;
	public static final String POST_PWD = "motdepasse" ;
	public static final String POST_PWD_CONF = "confirmation" ; 
	public static final String POST_USR_NAME = "nom" ;


	private Map<String,String> erreurs = new HashMap<String,String>(); 
	private String resultat;


	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public String getResultat() {
		return resultat;
	}

	public void setErreurs(Map<String, String> erreurs) {
		this.erreurs = erreurs;
	}

	public void setResultat(String resultat) {
		this.resultat = resultat;
	}

	public Utilisateur inscrireUtilisateur (HttpServletRequest request) {

		String nom = getValeurChamp(request,POST_USR_NAME);
		String mdp = getValeurChamp(request,POST_PWD);
		String mdpConf = getValeurChamp(request,POST_PWD_CONF);
		String email = getValeurChamp(request,POST_MAIL);
		
		Utilisateur utilisateur = new Utilisateur();
		try {
			validationNom(nom);
		} catch(Exception exNom) {
			setErreur(POST_USR_NAME,exNom.getMessage());
		}
		
		utilisateur.setNom(nom);

		try {
			validationMotsDePasse(mdp, mdpConf);
		} catch(Exception exMdp) {
			setErreur(POST_PWD,exMdp.getMessage());
		}
		
		utilisateur.setMotDePasse(mdp);

		try {
			validationEmail(email);
		} catch(Exception exMail) {
			setErreur(POST_MAIL,exMail.getMessage());
		}
		
		utilisateur.setEmail(email);
		if(erreurs.isEmpty()) this.setResultat("Succès de l'inscription") ;
		else this.setResultat("Echec de l'inscription") ;
		
		return utilisateur;

	}

	private void validationMotsDePasse(String motDePasse, String confirmation) throws Exception {

		//1ere vérif : les champs mdp et confirmation doivent pas être vides
		if (motDePasse != null && motDePasse.trim().length() != 0 && confirmation != null && confirmation.trim().length() != 0) {
			//2e vérif : le mdp et la conf doivent être égaux
			if (!motDePasse.equals(confirmation)) {
				throw new Exception("Les mots de passe entrés sont différents, merci de les saisir à nouveau.");
			}
			//Condition : 3 caractères minimum
			else if (motDePasse.trim().length() < 3) {
				throw new Exception("Les mots de passe doivent contenir au moins 3 caractères.");
			}
		} 
		//mdp ou confirmation vide
		else {
			throw new Exception("Merci de saisir et confirmer votre mot de passe.");
		}
	}


	private void validationNom(String nom) throws Exception {

		//le nom est optionnel, mais s'il est entré, il doit contenir 3 caractères
		if ( nom != null && nom.trim().length() < 3 ) {
			throw new Exception( "Le nom d'utilisateur doit contenir au moins 3 caractères." );
		}

	}

	private void validationEmail(String email) throws Exception {

		//1ere condition : champ non vide
		if( email.trim().length() != 0 && email !=null) {
			//2e condition : regex de malade agrégeant les règles de nommage
			if(!email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" )) {
				throw new Exception("Merci de saisir une adresse mail valide") ;
			}
		} else 
			throw new Exception("Merci de saisir une adresse mail") ;

	}

	private void setErreur(String champ, String message) {

		erreurs.put(champ, message);
	}

	private String getValeurChamp(HttpServletRequest request, String valeurChamp) {

		String valeur = request.getParameter(valeurChamp);
		if(valeur != null && valeur.trim().length()!=0)
			return valeur.trim();
		else
			return null;
	}

}

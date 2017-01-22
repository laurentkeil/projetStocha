package projet;

import java.util.ArrayList;

import umontreal.iro.lecuyer.probdist.StudentDist;
/**
 * 
 * @author Laurent Keil & Dartevelle Jason
 * Classe concernant les formules
 *
 */
public class Formules {
	
	/**
	 * @param arriveeClient (les listes contiennent des temps à un instant donné de chaque client)
	 * @param departClient
	 * @return la durée moyenne entre 2 instants pour chaque client des 2 listes en input.
	 */
	public static double tempsMoyen ( ArrayList<Double> arriveeClient, ArrayList<Double> departClient ) {
		double temps = 0;
		
		for (int i = 0 ; i < departClient.size() ; i++) {
			temps = temps + (departClient.get(i) - arriveeClient.get(i));
		}
		
		return temps / departClient.size();
	}


	/**
	 * @param listeTemps : la liste contenant les temps dont il faut faire la moyenne
	 * @return le temps moyen calculé à partir des temps de la liste envoyés en paramètre
	 */
	public static double tempsMoyen ( ArrayList<Double> listeTemps ) {
		double temps = 0;
		
		for (int i = 0 ; i < listeTemps.size() ; i++) {
			temps = temps + listeTemps.get(i);
		}
		
		return temps / listeTemps.size();
	}
	
	/**
	 * @param listeTemps : la liste contenant les temps dont il faut faire la moyenne
	 * @return le temps moyen calculé à partir des temps de la liste envoyés en paramètre
	 */
	public static double moyenne ( ArrayList<Integer> listeNbr ) {
		double total = 0;
		
		for (int i = 0 ; i < listeNbr.size() ; i++) {
			total = total + listeNbr.get(i);
		}
		
		return total / listeNbr.size();
	}
	
	/**
	 * @param n degré de liberté + 1
	 * @param a taux d'erreur
	 * @return le quantile d'une student de degré de liberté n-1 et d'ordre 1- (a/2)
	 */
	public static double quantileStudent (int n, double a) {
		return StudentDist.cdf(n - 1, 1 - (a/2));
	}
	
	/**
	 * @param MoyNbrGobVendus
	 * @param listeResultat
	 * @return La variance empirique de la serie statistique observee S²(n)
	 */
	private static double varEmpirSerStat(int MoyNbrGobVendus, ArrayList<Resultat> listeResultat){
		double res = 0.0;
		for (int i = 0 ; i < listeResultat.size() ; i++) {
			res = res + Math.pow((listeResultat.get(i).getNbrGobVendus() - MoyNbrGobVendus), 2);		
		}
		res =  res / (listeResultat.size() - 1);
		System.out.println("Variance empirique : " + res);
		return res;

	}

	/**
	 * @param MoyNbrGobVendus : la moyenne observée sur l'ensemble des simulations.
	 * @param listeResultat : liste de résultat contenant le nombre de gobelet vendus pour chaque simulation pour un échantillon de listeResultat.size simulations.
	 * @param a : alpha = le taux d'erreur = 0,05
	 * @return l'intervalle de confiance de l'estimation 
	 */
	public static Double[] intervConfEtPrecis (int MoyNbrGobVendus, ArrayList<Resultat> listeResultat, double a) {
		double quantStud = quantileStudent (listeResultat.size(), a);
		System.out.println("Quantile : " + quantStud);
		double racine = Math.sqrt ( varEmpirSerStat(MoyNbrGobVendus, listeResultat) / listeResultat.size());
		Double[] interv = {(MoyNbrGobVendus - (quantStud * racine)), (MoyNbrGobVendus + (quantStud * racine)), (quantStud * racine / MoyNbrGobVendus)};
		return interv;
	}
	
	/**
	 * 
	 * @param Na nombre de personnes dans la foule
	 * @param Ng nombre de personnes au gobelet
	 * @param Nb nombre de personnes au bar
	 * @return donne le produit des gini pour une distribution
	 */
	public static double gini (int Na, int Ng, int Nb) {
		return Math.pow(6, Na)*Math.pow((float) 1/8, Ng)*Math.pow(5, Nb);
	}
	
	public static double G () {
		int[][] F = {{0,0,5},{0,5,0},{5,0,0},{0,1,4},{1,0,4},{1,4,0},{0,4,1},{4,0,1},
				{4,1,0},{0,2,3},{2,0,3},{2,3,0},{0,3,2},{3,0,2},{3,2,0},{1,1,3},{3,1,1},{1,3,1},{1,2,2},{2,1,2},{2,2,1}};
		double somme = 0;
		for (int i = 0 ; i < F.length ; i++) {
			somme = somme + gini(F[i][0],F[i][1],F[i][2]);
		}
		return somme;
	}
	
	public static double distrStatio (int Na, int Ng, int Nb){
		return (gini(Na, Ng, Nb) / G());
	}
	
}

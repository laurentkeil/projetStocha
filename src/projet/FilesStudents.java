package projet;

import java.util.ArrayList;

import umontreal.iro.lecuyer.probdist.StudentDist;
import umontreal.iro.lecuyer.randvar.BinomialGen;
import umontreal.iro.lecuyer.randvar.ExponentialGen;
import umontreal.iro.lecuyer.randvar.RandomVariateGen;
import umontreal.iro.lecuyer.rng.MRG32k3a;
import umontreal.iro.lecuyer.rng.RandomStream;

/**
 * 
 * @author Laurent Keil & Dartevelle Jason
 * Classe gérant le système et les files d'étudiants
 *
 */
public class FilesStudents {
	
	

	/**
	 * Procédure de nbrSimulations simulations de tpsSimulations minutes du système du rassemblement de nbrPersonnes étudiants contenant une file de bar, une file de vente de gobelet et une foule.
	 * @param nbrSimulations
	 * @param tpsSimulation
	 * @param nbrPersonnes
	 */
	public static void filesStudent (int nbrSimulations, int tpsSimulation, int nbrPersonnes, double serviceGobRate, double serviceBarRate, double departFouleRate, boolean alice, boolean binom) {
		
		ArrayList<Resultat> listeResultat = new ArrayList<Resultat>(); //liste d'objet Resultat contenant les valeurs à calculer de chaque simulation.
		boolean histoProbaZ;
		
		int sommeProbaZ = 0;
		
		long[] seed = {1,5,3,7,2,5,16};//init des semences
		RandomStream nbrPersStream = new MRG32k3a(); //générateur
		MRG32k3a.setPackageSeed(seed); //init des semences
		RandomVariateGen nbrPersGen = new BinomialGen(nbrPersStream, 100, 0.3); //génération binomiale du nbr de personnes pour la question 2
		
		//boucle sur plusieurs simulations
		for (int i = 0 ; i < nbrSimulations ; i++){
				
			//init des semences
			long[] seed1 = {(1+i),2,3,7,5,5,6};
			long[] seed2 = {1,(4+2*i),3,4,4,9};
			long[] seed3 = {1,2,(3+i),1,5,7};
			
			double sommeProbaZTemps = 0; 		
			
			 // liste d'arrivé, d'en service et de départ d'étudiant au bar
			ArrayList<Double> arriveeClientBar = new ArrayList<Double>();
			ArrayList<Double> departClientBar = new ArrayList<Double>();
			ArrayList<Double> debutServiceClientBar = new ArrayList<Double>();
			
			ArrayList<Double> tpsVenteGob = new ArrayList<Double>(); // liste de temps de vente de gobelet pour valeur théorique
			ArrayList<Double> tpsVenteBar = new ArrayList<Double>(); // liste de temps de vente d'une boisson pour valeur théorique
			ArrayList<Double> tpsSoif = new ArrayList<Double>(); // liste de temps entre 2 consommations pour valeur théorique

			//liste de nombre de personnes dans une file.
			ArrayList<Integer> fileGob = new ArrayList<Integer>(); 
			ArrayList<Integer> fileBar = new ArrayList<Integer>(); 
			ArrayList<Integer> fileFoule = new ArrayList<Integer>(); 

			ArrayList<Double> tpsGobVendus = new ArrayList<Double>(); // liste de temps pour chaque gobelet vendu
		
			Scheduler scheduler = new Scheduler();
			
			// RNG init
			
			//service gobelet
			RandomStream serviceGobStream = new MRG32k3a(); //1e générateur
			MRG32k3a.setPackageSeed(seed1); //init des semences
			RandomVariateGen serviceGobGen = new ExponentialGen(serviceGobStream, serviceGobRate); //génération exponentielle des services au gobelet de param uG
	
			//service bar
			RandomStream serviceBarStream = new MRG32k3a(); //2e générateur
			MRG32k3a.setPackageSeed(seed2); //init des semences
			RandomVariateGen serviceBarGen = new ExponentialGen(serviceBarStream, serviceBarRate); //génération exponentielle des services au bar de param uS
			
			//depart foule
			RandomStream departFouleStream = new MRG32k3a(); //3e générateur
			MRG32k3a.setPackageSeed(seed3); //init des semences
			RandomVariateGen departFouleGen = new ExponentialGen(departFouleStream, departFouleRate); //génération exponentielle des depart de foule
			
			
			// Initialisation
			double nextServiceGob = 0;
			double nextServiceBar = 0;
			double nextDepartFoule = 0;
			
			double h = 0; 	//horloge
			if (binom) {
				nbrPersonnes = (int) nbrPersGen.nextDouble(); //nombre de personnes généré aléatoirement à chaque simulation.
			} 
			
			int nbrGob = nbrPersonnes;
			int nbrBar = 0; //files d'attente + services
			int foule = 0;
			
			int nbrGobVendus = 0; //var du nombre de gobelet vendus, soit µ
			
			double tpsPrec = 0;
			
			scheduler.addEvent(0, serviceGobGen.nextDouble()); //ajoute un premier event de service gobelet au scheduler avec un nombre généré aléatoirement de manière exponentielle
			
			while(h < tpsSimulation) { //fait tourner la simulation pendant tpsSimulation unité de temps
				tpsPrec = h;
				Event e = scheduler.nextEvent(); //cherche le prochain event
				h = e.getTime();			 	 // rajoute le temps de l event à l'horloge
				
				if(h < tpsSimulation) {
					
					int etat = e.getEtat();	//suivant l'état dans lequel l'event se trouve, une action différente est menée.
					
					if (etat == 0) { //type = service Gobelet
						
						nbrGobVendus ++; //incrémente le nombre de gobelet vendus
						 tpsGobVendus.add (h); //ajoute le temps de la vente du gobelet.
						nbrGob --; // retire 1 à la file du stand gobelet
						
						if (nbrGob > 0) {				// si la file n'est pas vide
							nextServiceGob = serviceGobGen.nextDouble();
							 tpsVenteGob.add(nextServiceGob); //on ajoute le temps de service du gobelet dans la liste
							scheduler.addEvent(0, h + nextServiceGob ); // un event de service au gobelet pour le prochain
						}
						
						nbrBar ++; //rajoute 1 au système car on passe direct de la file des gobelets au bar
						 arriveeClientBar.add(h); //on ajoute le temps de l'arrivée du client dans la liste
						
						if (nbrBar == 1) {				//si la file est vide, il passe direct en service
							nextServiceBar = serviceBarGen.nextDouble();
							 tpsVenteBar.add(nextServiceBar); //on ajoute le temps de service de la boisson dans la liste
							 debutServiceClientBar.add(h); //on ajoute le temps de service du client dans la liste
							scheduler.addEvent(1, h + nextServiceBar ); // un event de service au bar pour le prochain
						}
					 
					} else if (etat == 1) { //type = service Bar
						
						nbrBar --; // retire 1 au système
						 departClientBar.add(h); //on ajoute le temps de depart du client dans la liste
						
						if (nbrBar > 0) {				// si la file n'est pas vide			
							nextServiceBar = serviceBarGen.nextDouble();
							 tpsVenteBar.add(nextServiceBar); //on ajoute le temps de service de la boisson dans la liste
							 debutServiceClientBar.add(h); //on ajoute le temps de service du client dans la liste
							scheduler.addEvent(1, h + nextServiceBar ); // un event de service au bar pour le prochain
						}
						
						foule ++; // rajoute 1 à la foule car on passe direct dans la foule lorsqu'on est servi 
						nextDepartFoule = departFouleGen.nextDouble();
						 tpsSoif.add(nextDepartFoule); //on ajoute le temps d'attente avant de consommer une autre boisson dans la liste
						scheduler.addEvent(2, h + nextDepartFoule ); // un event d'arrivée au bar pour lorsque celui qui vient d'être servi veuille consommer un nouveau verre
					
					} else { //type = départ foule
						
						foule--; // retire 1 au système
						double ProbaCasserGob = 0.0;
						
						if (alice){
							ProbaCasserGob = 0.25;
						} else {
							ProbaCasserGob = (double) foule / nbrPersonnes;
							
						}
						double rand = Math.random();
						boolean gobeletCasse = (rand <= ProbaCasserGob); //le gobelet est cassé si la valeur random entre 0 et 1 est plus petite que la proba de casser son gobelet.
						
						if (gobeletCasse) { // si le gobelet est cassé, il passe dans la file des gobelets et au service si elle est vide.
							nbrGob++;
							if (nbrGob == 1) {
								nextServiceGob = serviceGobGen.nextDouble();
								 tpsVenteGob.add(nextServiceGob); //on ajoute le temps de service du gobelet dans la liste
								scheduler.addEvent(0, h + nextServiceGob ); // un event de service au gobelet pour le prochain
							}
						} else { 			// si le gobelet n'est pas cassé, il passe dans la file du bar et au service si elle est vide.
							nbrBar ++;
							 arriveeClientBar.add(h); //on ajoute le temps de l'arrivée du client dans la liste
							if (nbrBar == 1) { //si la file est vide
								nextServiceBar = serviceBarGen.nextDouble();
								 tpsVenteBar.add(nextServiceBar); //on ajoute le temps de service de la boisson dans la liste
								 debutServiceClientBar.add(h); //on ajoute le temps de service du client dans la liste
								scheduler.addEvent(1, h + nextServiceBar ); // un event de service au bar pour le prochain
							}
						}
					}

					//System.out.println("event : " + etat);
					
				}
				
				fileBar.add (nbrBar);
				fileGob.add (nbrGob);
				fileFoule.add (foule);
				
				if ( alice && foule == 2 && nbrGob == 1 && nbrBar == 2 ) {
					sommeProbaZTemps = sommeProbaZTemps + (h - tpsPrec); //calcule la somme des temps où l'on stationne dans cet état.
				}
				
				/*
				System.out.println("temps total : " + h);
				System.out.println("taille de la file gobelet : " + nbrGob);
				System.out.println("taille de la file bar : " + nbrBar);
				System.out.println("taille de la foule : " + foule);
				System.out.println("\n________________\n");*/
				
			}

			/*
			 * Pour la question 3-alice, calculer le nombre de simulation où au dernier event il y ai 2 personnes dans la foule, 1 au gobelet et 2 au bar.
			 */
			if (alice && fileFoule.get(fileFoule.size()-1) == 2 && fileGob.get(fileGob.size()-1) == 1 && fileBar.get(fileBar.size()-1) == 2 ) {
				histoProbaZ = true; // ajoute dans une liste la simulation pour laquelle on a la distribution.
				sommeProbaZ = sommeProbaZ + 1; 
			} else {
				histoProbaZ = false;
			}
			
			Resultat res = new Resultat(tpsGobVendus, nbrGobVendus, departClientBar.size() , Formules.tempsMoyen(tpsVenteGob), 
					Formules.tempsMoyen(tpsVenteBar), Formules.tempsMoyen(tpsSoif), Formules.tempsMoyen(arriveeClientBar, departClientBar), Formules.tempsMoyen(arriveeClientBar, debutServiceClientBar), 
					Formules.moyenne(fileBar), Formules.moyenne(fileGob), Formules.moyenne(fileFoule), sommeProbaZ, histoProbaZ, fileFoule, sommeProbaZTemps);
			listeResultat.add(res); //on ajoute les résultats à calculer dans la liste d'objet Resultat.
			
			/*		
			for (int y = 0 ; y < departClientBar.size() ; y++) { //on itère sur les listes en prenant la taille des départs puisque c'est le minimum entre les 3
				System.out.println(arriveeClientBar.get(y) + "\t | " + debutServiceClientBar.get(y) + "\t | " + departClientBar.get(y));	
			}*/
	
		}	
		
		int MoyNbrBoissons = 0;
		int MoyNbrGobVendus = 0;
		double tpsMoyenVenteBar = 0;
		double TpsMoyenVenteGob = 0;
		double TpsMoyenSoif = 0;
		double TpsMoyenSejourBar = 0;
		double TpsMoyenAttenteBar = 0;
		double fileBar = 0;
		double fileGob = 0;
		double fileFoule = 0;
		
		//faire la somme de chaque valeur pour chaque simulation
		for (int j = 0 ; j < nbrSimulations ; j++){
			MoyNbrBoissons = MoyNbrBoissons + listeResultat.get(j).getNbrBoissonsVendues();
			MoyNbrGobVendus = MoyNbrGobVendus + listeResultat.get(j).getNbrGobVendus();
			tpsMoyenVenteBar = tpsMoyenVenteBar + listeResultat.get(j).getTpsMoyenVenteBar();
			TpsMoyenVenteGob = TpsMoyenVenteGob + listeResultat.get(j).getTpsMoyenVenteGob();
			TpsMoyenSoif = TpsMoyenSoif + listeResultat.get(j).getTpsMoyenSoif();
			TpsMoyenSejourBar = TpsMoyenSejourBar + listeResultat.get(j).getTpsMoyenSejourBar();
			TpsMoyenAttenteBar = TpsMoyenAttenteBar + listeResultat.get(j).getTpsMoyenAttenteBar();
			fileBar = fileBar + listeResultat.get(j).getFileBar();
			fileGob = fileGob + listeResultat.get(j).getFileGob();
			fileFoule = fileFoule + listeResultat.get(j).getFileFoule();
			
		}
	
		//on affiche les valeurs calculées moyennes de toutes les simulations.
		System.out.println("\nValeurs calculées : \n___________________\n");
		
		/*
		 * Réponse au 2 premières questions
		 */
		MoyNbrGobVendus = MoyNbrGobVendus / nbrSimulations;
		System.out.println("Nombre moyen de gobelets vendues : " + MoyNbrGobVendus );

		/*
		 * intervalle de confiance de l estimation calculée
		 */
		Double[] intervConf = Formules.intervConfEtPrecis (MoyNbrGobVendus, listeResultat, 0.05);
		System.out.println("Intervalle de confiance : [ " + intervConf[0] + " , " + intervConf[1] + " ]");
		System.out.println("Précision de l'intervalle de confiance :  " + intervConf[2]);
		
		MoyNbrBoissons = MoyNbrBoissons / nbrSimulations;
		System.out.println("\nNombre moyen de boissons vendues : " + MoyNbrBoissons );
		TpsMoyenVenteGob = TpsMoyenVenteGob / nbrSimulations;
		System.out.println("\nTemps moyen de vente d'un gobelet : " + TpsMoyenVenteGob );
		tpsMoyenVenteBar = tpsMoyenVenteBar / nbrSimulations;
		System.out.println("Temps moyen de vente d'une boisson : " + tpsMoyenVenteBar );
		TpsMoyenSoif = TpsMoyenSoif / nbrSimulations;
		System.out.println("Temps moyen entre 2 consommations : " + TpsMoyenSoif );
		TpsMoyenAttenteBar = TpsMoyenAttenteBar / nbrSimulations;
		System.out.println("\nTemps moyen d'attente dans la file du bar : " + TpsMoyenAttenteBar );
		TpsMoyenSejourBar = TpsMoyenSejourBar / nbrSimulations;
		System.out.println("Temps moyen de séjour dans la file du bar : " + TpsMoyenSejourBar );
		fileGob = fileGob / nbrSimulations;
		System.out.println("\nNombre de personnes moyen dans la file du stand gobelet : " + fileGob );
		fileBar = fileBar / nbrSimulations;
		System.out.println("Nombre de personnes moyen dans la file du bar : " + fileBar );
		fileFoule = fileFoule / nbrSimulations;
		System.out.println("Nombre de personnes moyen dans la foule : " + fileFoule );
		
		double rhoBar = (serviceGobRate*23 + (departFouleRate*0.98)*2) / (serviceBarRate*75); //le service au stand gobelet induit son départ et l'arrivée au bar immédiate.
		//System.out.println("\nTemps de séjour moyen théorique dans la file du bar (E[S]) = " + ((1 / (serviceBarRate)*75) / (1 - rhoBar)));
		
		if (alice) {
			System.out.println("Estimateur de la probabilité d'observer la distribution stationnaire pi(2,1,2) : " + (double) sommeProbaZ / nbrSimulations);
			System.out.println("Calcul théorique de la distribution stationnaire : " + Formules.distrStatio(2, 1, 2));
			
			//
			solu3(sommeProbaZ, nbrSimulations);
			
		}
		
		BarGraph graphe = new BarGraph(listeResultat, nbrSimulations, alice, tpsSimulation);
		graphe.setVisible(true);
		
		
			/*
			double rho = arrivalRate / serviceRate;
			
			System.out.println("Temps de séjour moyen théorique (E[S]) = " + ((1 / serviceRate) / (1 - rho)));	
			System.out.println("Temps d'attente moyen théorique (E[W]) = " + (((1 / serviceRate) * rho) / (1 - rho)));				
			System.out.println("\nTaille moyenne de la file d'attente théorique (E[L]) = " + (( ( (1 / serviceRate)  ) / (1 - rho) ) ) * arrivalRate);
			System.out.println("Nombre de client moyen dans la file d'attente théorique (E[Lq]) = " + ((((1 / serviceRate) * rho) / (1 - rho))) * arrivalRate);	
			*/
	}
	
	/**
	 * programme principal
	 * @param args
	 * @author keil laurent
	 */
	public static void main(String[] args) {

		//constantes
		int nbrSimulations = 100;
		int tpsSimulation = 900;
		int nbrPersonnes = 100;

		double serviceGobRate = 1./3.; //taux de service gobelet µG
		double serviceBarRate = 0.2; //taux de service de bar µS
		double departFouleRate = 0.125; //taux de depart de foule lambda
		
		
		filesStudent(nbrSimulations, tpsSimulation, nbrPersonnes, serviceGobRate, serviceBarRate, departFouleRate, false, false);
		
		nbrPersonnes = 0;
		
		filesStudent(nbrSimulations, tpsSimulation, nbrPersonnes, serviceGobRate, serviceBarRate, departFouleRate, false, true);
		

		nbrSimulations = 10000;
		tpsSimulation = 10000;
		nbrPersonnes = 5;

		serviceGobRate = 2.0; //taux de service gobelet (1/0.5) µG
		serviceBarRate = 0.2; //taux de service de bar µS
		departFouleRate = 1./6.; //taux de depart de foule lambda
		
		filesStudent(nbrSimulations, tpsSimulation, nbrPersonnes, serviceGobRate, serviceBarRate, departFouleRate, true, false);
		

	}
	
	/**
	 * affiche les solutions.
	 * @param sommeProba
	 * @param nbrSimulations
	 */
	public static void solu3(int sommeProba, int nbrSimulations){
		
		int taux=0;
		double variance=0;
		double moyenne = sommeProba / nbrSimulations;//
		StudentDist student = new StudentDist(10); //10 degrés de liberté
		double minS = - student.inverseF(1-(0.05/2));//0,05 erreurs
        double maxS = student.inverseF(1-(0.05/2));//0,05 erreurs

        System.out.println(" - Borne min = " + minS +" - Borne MAX = " + maxS);
        if(minS <= moyenne && moyenne <= maxS) {
			System.out.println("L'hypothèse H0 est comprise entre les bornes. Alice a donc raison.");
		}
		
	}
	
	

}
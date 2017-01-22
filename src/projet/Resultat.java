package projet;

import java.util.ArrayList;
/**
 *
 * @author Laurent Keil & Dartevelle Jason
 *Classe contenant les résultats d'une simulation
 */
public class Resultat {

	private ArrayList<Double> tpsGobVendus;
	private int nbrGobVendus;
	private int nbrBoissonsVendues; 
	private double tpsMoyenVenteGob; 
	private double tpsMoyenVenteBar; 
	private double tpsMoyenSoif;
	private double tpsMoyenSejourBar;
	private double tpsMoyenAttenteBar;
	private double fileBar;
	private double fileGob;
	private double fileFoule;
	private int sommeProbaZ;
	private boolean histoProbaZ;
	private ArrayList<Integer> listeFoule;
	private double sommeProbaZtemps;
	

	public Resultat() {
		
	}
	
	public Resultat(ArrayList<Double> tpsGobVendus, int nbrGobVendus, int nbrBoissonsVendues, double tpsMoyenVenteGob, double tpsMoyenVenteBar, double tpsMoyenSoif, double tpsMoyenSejourBar, double tpsMoyenAttenteBar, double moyFileBar, double moyFileGob, double moyFileFoule, int sommeProbaZ, boolean histoProbaZ, ArrayList<Integer> listeFoule, double sommeProbaZtemps) {
		this.setTpsGobVendus(tpsGobVendus);
		this.setNbrGobVendus(nbrGobVendus);
		this.setNbrBoissonsVendues(nbrBoissonsVendues); 
		this.setTpsMoyenVenteGob(tpsMoyenVenteGob); 
		this.setTpsMoyenVenteBar(tpsMoyenVenteBar);
		this.setTpsMoyenSoif(tpsMoyenSoif);
		this.setTpsMoyenSejourBar(tpsMoyenSejourBar);
		this.setTpsMoyenAttenteBar(tpsMoyenAttenteBar);
		this.setFileBar(moyFileBar);
		this.setFileGob(moyFileGob);
		this.setFileFoule(moyFileFoule);
		this.setSommeProbaZ(sommeProbaZ);
		this.setHistoProbaZ(histoProbaZ);
		this.setListeFoule(listeFoule);
		this.setSommeProbaZtemps(sommeProbaZtemps);
	}
	

	
	/**
	 * @return the tpsGobVendus
	 */
	public ArrayList<Double> getTpsGobVendus() {
		return tpsGobVendus;
	}

	/**
	 * @param tpsGobVendus the tpsGobVendus to set
	 */
	public void setTpsGobVendus(ArrayList<Double> tpsGobVendus) {
		this.tpsGobVendus = tpsGobVendus;
	}

	/**
	 * @return the nbrGobVendus
	 */
	public int getNbrGobVendus() {
		return nbrGobVendus;
	}

	/**
	 * @param nbrGobVendus the nbrGobVendus to set
	 */
	public void setNbrGobVendus(int nbrGobVendus) {
		this.nbrGobVendus = nbrGobVendus;
	}

	/**
	 * @return the tpsMoyenVenteBar
	 */
	public double getTpsMoyenVenteBar() {
		return tpsMoyenVenteBar;
	}

	/**
	 * @param tpsMoyenVenteBar the tpsMoyenVenteBar to set
	 */
	public void setTpsMoyenVenteBar(double tpsMoyenVenteBar) {
		this.tpsMoyenVenteBar = tpsMoyenVenteBar;
	}

	/**
	 * @return the tpsMoyenSoif
	 */
	public double getTpsMoyenSoif() {
		return tpsMoyenSoif;
	}

	/**
	 * @param tpsMoyenSoif the tpsMoyenSoif to set
	 */
	public void setTpsMoyenSoif(double tpsMoyenSoif) {
		this.tpsMoyenSoif = tpsMoyenSoif;
	}

	/**
	 * @return the tpsMoyenVenteGob
	 */
	public double getTpsMoyenVenteGob() {
		return tpsMoyenVenteGob;
	}

	/**
	 * @param tpsMoyenVenteGob the tpsMoyenVenteGob to set
	 */
	public void setTpsMoyenVenteGob(double tpsMoyenVenteGob) {
		this.tpsMoyenVenteGob = tpsMoyenVenteGob;
	}

	/**
	 * @return the nbrBoissonsVendues
	 */
	public int getNbrBoissonsVendues() {
		return nbrBoissonsVendues;
	}

	/**
	 * @param nbrBoissonsVendues the nbrBoissonsVendues to set
	 */
	public void setNbrBoissonsVendues(int nbrBoissonsVendues) {
		this.nbrBoissonsVendues = nbrBoissonsVendues;
	}

	/**
	 * @return the tpsMoyenSejourBar
	 */
	public double getTpsMoyenSejourBar() {
		return tpsMoyenSejourBar;
	}

	/**
	 * @param tpsMoyenSejourBar the tpsMoyenSejourBar to set
	 */
	public void setTpsMoyenSejourBar(double tpsMoyenSejourBar) {
		this.tpsMoyenSejourBar = tpsMoyenSejourBar;
	}

	/**
	 * @return the tpsMoyenAttenteBar
	 */
	public double getTpsMoyenAttenteBar() {
		return tpsMoyenAttenteBar;
	}

	/**
	 * @param tpsMoyenAttenteBar the tpsMoyenAttenteBar to set
	 */
	public void setTpsMoyenAttenteBar(double tpsMoyenAttenteBar) {
		this.tpsMoyenAttenteBar = tpsMoyenAttenteBar;
	}

	/**
	 * @return the fileBar
	 */
	public double getFileBar() {
		return fileBar;
	}

	/**
	 * @param fileBar the fileBar to set
	 */
	public void setFileBar(double fileBar) {
		this.fileBar = fileBar;
	}

	/**
	 * @return the fileGob
	 */
	public double getFileGob() {
		return fileGob;
	}

	/**
	 * @param fileGob the fileGob to set
	 */
	public void setFileGob(double fileGob) {
		this.fileGob = fileGob;
	}

	/**
	 * @return the fileFoule
	 */
	public double getFileFoule() {
		return fileFoule;
	}

	/**
	 * @param fileFoule the fileFoule to set
	 */
	public void setFileFoule(double fileFoule) {
		this.fileFoule = fileFoule;
	}

	/**
	 * @return the histoProbaZ
	 */
	public boolean getHistoProbaZ() {
		return histoProbaZ;
	}

	/**
	 * @param histoProbaZ the histoProbaZ to set
	 */
	public void setHistoProbaZ(boolean histoProbaZ) {
		this.histoProbaZ = histoProbaZ;
	}

	/**
	 * @return the sommeProbaZ
	 */
	public int getSommeProbaZ() {
		return sommeProbaZ;
	}

	/**
	 * @param sommeProbaZ the sommeProbaZ to set
	 */
	public void setSommeProbaZ(int sommeProbaZ) {
		this.sommeProbaZ = sommeProbaZ;
	}

	/**
	 * @return the listeFoule
	 */
	public ArrayList<Integer> getListeFoule() {
		return listeFoule;
	}

	/**
	 * @param listeFoule the listeFoule to set
	 */
	public void setListeFoule(ArrayList<Integer> listeFoule) {
		this.listeFoule = listeFoule;
	}

	/**
	 * @return the sommeProbaZtemps
	 */
	public double getSommeProbaZtemps() {
		return sommeProbaZtemps;
	}

	/**
	 * @param sommeProbaZtemps the sommeProbaZtemps to set
	 */
	public void setSommeProbaZtemps(double sommeProbaZtemps) {
		this.sommeProbaZtemps = sommeProbaZtemps;
	}
	

}

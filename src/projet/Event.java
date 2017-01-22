package projet;

/**
 * @author Laurent Keil & Dartevelle Jason
 * Classe donnant pour chaque object un event avec son �tat et le temps associ�s.
 */
public class Event {

	/*event : 	0 = stand gobelet
				1 = bar
				2 = soir�e */
	private Integer etat;
	private Double time;
	
	Event(Integer e, Double t) {
		this.etat = e;
		this.time = t;
	}
	
	public Integer getEtat() {
		return this.etat;
	}
	
	public void setEtat(int etat) {
		this.etat = etat;
	}
	
	public Double getTime() {
		return this.time;
	}
	
	public void setTime(double time) {
		this.time = time;
	}

}

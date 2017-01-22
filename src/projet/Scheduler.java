package projet;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Laurent Keil & Dartevelle Jason
 * Classe de planification d'évèvenements, contenant une liste d'event et les ajoute ou les retrouve.
 */
public class Scheduler {

	private ArrayList<Event> scheduler = new ArrayList<Event>();
	
	
	public Event nextEvent() {
		if(scheduler==null)
			return null;
		
		Iterator<Event> it = scheduler.iterator();
		
		Event first = it.next();
		while(it.hasNext()) {
			Event tmp = it.next();
			if(first.getTime() > tmp.getTime())
				first=tmp;
		}
		scheduler.remove(first);
		return first;
	}
	
	public void addEvent(Integer etat, double time) {
		Event event = new Event(etat, time);
		scheduler.add(event);
	}
	
}

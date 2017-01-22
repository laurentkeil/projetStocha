package projet;

import java.awt.*; 
import java.awt.event.*; 
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*; 

import org.jfree.chart.*; 
import org.jfree.chart.plot.*; 
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Classe permettant l'affichage et l'enregistrement de graphiques.
 * @author Laurent Keil & Dartevelle Jason
 *
 */
public class BarGraph extends JFrame { 
  private JPanel pnl; 

  public BarGraph(ArrayList<Resultat> result, int nbrSimulations, boolean alice, int tpsSimulation) { 
	  
	  	/*init du panel*/
	    addWindowListener(new WindowAdapter() { 
		      public void windowClosing(WindowEvent e) { 
			        dispose(); 
			        System.exit(0); 
		      } 
	    }); 
	    pnl = new JPanel(new BorderLayout()); 
	    setContentPane(pnl); 
	    setSize(800, 400);

	    /* mise en place des données */
	    XYSeries series1 = new XYSeries("nombre de gobelets pour la simulation x");
	    for (int i=0; i < nbrSimulations ; i++) {
	        series1.add(i+1, result.get(i).getNbrGobVendus() ); //100 simulation en x et le nombre de gobelets en y.
	    }
        XYSeriesCollection datasetGob = new XYSeriesCollection();
        datasetGob.addSeries(series1);
        
        
	    XYSeries series = new XYSeries("nombre de gobelets moyen pour les i premières simulations en x");
	    int res = 0;
	    for (int i=0; i < nbrSimulations; i++) {
	    	res = result.get(i).getNbrGobVendus() + res;
	        series.add(i+1, res/(i+1)  ); //100 simulation en x et le nombre de gobelets en y.
	    }
        XYSeriesCollection datasetGobMoy = new XYSeriesCollection();
        datasetGob.addSeries(series);
        datasetGobMoy.addSeries(series);
        

	    XYSeries series2 = new XYSeries("nombre de gobelets pour le temps x");
	    for (int i=0; i < result.get(0).getTpsGobVendus().size(); i++) {
	        series2.add((double) result.get(0).getTpsGobVendus().get(i), i+1); //l'heure de la vente du gobelet en x et i, le nombre de gobelets en y.
	    }
        XYSeriesCollection datasetGobTime = new XYSeriesCollection();
        datasetGobTime.addSeries(series2);
	    
        DefaultPieDataset datasetPie = new DefaultPieDataset();
        datasetPie.setValue("File du stand gobelet", result.get(0).getFileGob());
        datasetPie.setValue("File du bar", result.get(0).getFileBar());
        datasetPie.setValue("Foule", result.get(0).getFileFoule());
        
        
        /* création des graphiques */
        JFreeChart chart1 = ChartFactory.createXYLineChart(
        	"Evolution du nombre de gobelets vendus en fonction du nombre de simulations effectuées", 
  	  	    "Nombre de simulations", "Nombre de gobelets",                      // x et y axis label
            datasetGob,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        ); 
        
        JFreeChart chart = ChartFactory.createXYLineChart(
            	"Evolution du nombre moyen de gobelets vendus en fonction du nombre de simulations effectuées", 
      	  	    "Nombre de simulations", "Nombre de gobelets",                      // x et y axis label
                datasetGobMoy,                  // data
                PlotOrientation.VERTICAL,
                true,                     // include legend
                true,                     // tooltips
                false                     // urls
            ); 
	    
	    JFreeChart histo1 = ChartFactory.createHistogram("Evolution du nombre de gobelets vendus en fonction du nombre de simulations effectuées", 
	  	      "Nombre de simulations", "Nombre de gobelets", datasetGobMoy, PlotOrientation.VERTICAL, false, false, false);	    
	    
	    JFreeChart chart2 = ChartFactory.createXYLineChart(
	        	"Evolution du nombre de gobelets vendus en fonction temps", 
	  	  	    "Temps", "Nombre de gobelets",                      // x et y axis label
	            datasetGobTime,                  // data
	            PlotOrientation.VERTICAL,
	            true,                     // include legend
	            true,                     // tooltips
	            false                     // urls
	        );        
		    
		JFreeChart histo2 = ChartFactory.createHistogram("Evolution du nombre de gobelets vendus en fonction temps", 
	  	  	    "Temps", "Nombre de gobelets", datasetGobTime, PlotOrientation.VERTICAL, false, false, false);	    
		    
	    JFreeChart pieChart = ChartFactory.createPieChart("Proportion d'étudiants dans chaque file", 
	       datasetPie, true, true, false); 
	    
	    
	    
	    ChartPanel Panel = new ChartPanel (chart); 
	    ChartPanel cPanel = new ChartPanel (chart1); 
	    ChartPanel tPanel = new ChartPanel (histo1); 
	    ChartPanel cPanel2 = new ChartPanel (chart2); 
	    ChartPanel tPanel2 = new ChartPanel (histo2); 
	    ChartPanel pPanel = new ChartPanel (pieChart); 
	    
	    
	    try {
	        ChartUtilities.saveChartAsPNG(new File("chart.PNG"), chart, 1000, 500);
	        ChartUtilities.saveChartAsPNG(new File("chart1.PNG"), chart1, 1000, 500);
	        ChartUtilities.saveChartAsPNG(new File("histogram1.PNG"), histo1, 1000, 500);
	        ChartUtilities.saveChartAsPNG(new File("chart2.PNG"), chart2, 1000, 500);
	        ChartUtilities.saveChartAsPNG(new File("histogram2.PNG"), histo2, 1000, 500);		    
	        ChartUtilities.saveChartAsPNG(new File("pie.PNG"), pieChart, 1000, 500);
	    } catch (IOException e) {}	 
	    
	    

        /*Question Alice*/
        if(alice) {
        	
	        XYSeries seriesAlice = new XYSeries("Accumulation du nombre de fois que l'on a observé pi(2,1,2) jusqu'à la simulation x");
	        XYSeries seriesAliceMoy = new XYSeries("Nombre moyen de fois que l'on a observé pi(2,1,2) jusqu'à la simulation x");
	        XYSeries seriesAliceMoyTemps = new XYSeries("Nombre moyen de fois que l'on a observé pi(2,1,2) dans chacune des simulations déjà observée");		    
	        double sommeProbaZ = 0;
	        for (int i=0; i < nbrSimulations; i++) {
		    	seriesAlice.add(i+1, result.get(i).getSommeProbaZ() ); //nbrSimulations simulation en x et le nombre de fois que survient pi(2,1,2) jusque là en y.
		    	seriesAliceMoy.add(i+1, (double) result.get(i).getSommeProbaZ() / (i+1) ); //nbrSimulations simulation en x et le nombre de fois que survient pi(2,1,2) jusque là en y.		    
		    	sommeProbaZ = sommeProbaZ + (result.get(i).getSommeProbaZtemps() / tpsSimulation); //la somme des moyennes des temps stationnaires de chaque simulation.
		    	seriesAliceMoyTemps.add(i+1, sommeProbaZ / (i+1));
	        }
	        XYSeriesCollection datasetAlice = new XYSeriesCollection();
	        datasetAlice.addSeries(seriesAlice);
	        XYSeriesCollection datasetAliceMoy = new XYSeriesCollection();
	        datasetAliceMoy.addSeries(seriesAliceMoy);
	        XYSeriesCollection datasetAliceMoyTemps = new XYSeriesCollection();
	        datasetAliceMoyTemps.addSeries(seriesAliceMoyTemps);
	        
	        XYSeries seriesAlice1 = new XYSeries("Présence de l'observation de pi(2,1,2) à la simulation x");
		    for (int i=0; i < nbrSimulations; i++) {
		    	if(result.get(i).getHistoProbaZ()) {
		    		seriesAlice1.add(i,  1); //la nbrSimulations e simulation en x et 1 si survient pi(2,1,2) à la fin de cette simulation en y.	    		  		    	
		    	} else {
		    		seriesAlice1.add(i,  0); //la nbrSimulations e simulation en x et 0 si pi(2,1,2) ne survient pas à la fin de cette simulation en y.	    		  		    			    	
		    	}
		    		
		    }
	        XYSeriesCollection datasetAlice1 = new XYSeriesCollection();
	        datasetAlice1.addSeries(seriesAlice1);
	        
	        
	        
	        XYSeries seriesAliceFoule = new XYSeries("Moyenne du nombre de personnes dans la foule (ni dans la file du bar, ni celle des gobelets) pour 1 simulation");
		    int sommeFoule = 0;
	        for (int i=0; i < 500; i++) {
		    		int indiceObservation = (result.get(0).getListeFoule().size() - 500) + i;
		    		sommeFoule = sommeFoule + result.get(0).getListeFoule().get(indiceObservation);
		    		seriesAliceFoule.add(indiceObservation,  (double) sommeFoule / (i+1)); //le numéro de l'observation en x et la moyenne du nbr de personne dans la foule en y    		  		    		    		
		    }
	        XYSeriesCollection datasetAliceFoule = new XYSeriesCollection();
	        datasetAliceFoule.addSeries(seriesAliceFoule);
	        
	        XYSeries seriesAliceFouleMoy = new XYSeries("Moyenne du nombre de personnes dans la foule (ni dans la file du bar, ni celle des gobelets) pour toutes les simulation");
		    double sommeFouleMoy = 0;
	        for (int i=0; i < 500; i++) {
	    			int indiceObservation = 0;
	    			double sommeTmp = 0;
	        		for (int j=0; j < result.size(); j++) { //moyenne entre toutes les simulations
	        			indiceObservation = (result.get(j).getListeFoule().size() - 500) + i;
	        			sommeTmp = sommeTmp + result.get(j).getListeFoule().get(indiceObservation);
	        		}
	    			indiceObservation = (result.get(0).getListeFoule().size() - 500) + i;
		    		sommeFouleMoy = sommeFouleMoy + sommeTmp/nbrSimulations;
		    		seriesAliceFouleMoy.add(indiceObservation,  (double) sommeFouleMoy / (i+1)); //le numéro de l'observation en x et la moyenne du nbr de personne dans la foule en y    		  		    		    		
		    }
	        datasetAliceFoule.addSeries(seriesAliceFouleMoy);
	        
	        
	        /* Graphiques Alice */

		    JFreeChart histoAlice = ChartFactory.createHistogram("Evolution du nombre de fois que l'on observe pi(2,1,2) en fonction du nombre de simulations", 
		  	  	    "Nombre de simulation", "Somme Z(i) (2,1,2)", datasetAlice, PlotOrientation.VERTICAL, false, false, false);	  
		    
	
	        JFreeChart chartAlice = ChartFactory.createXYLineChart(
	            	"Evolution du nombre moyen de fois que l'on observe pi(2,1,2) en fonction du nombre de simulations", 
	      	  	    "Nombre de simulations", "Moyenne du nombre d'observation de pi(2,1,2)",                      // x et y axis label
	                datasetAliceMoy,                  // data
	                PlotOrientation.VERTICAL,
	                true,                     // include legend
	                true,                     // tooltips
	                false                     // urls
	            ); 
	        
	        JFreeChart chartAliceTemps = ChartFactory.createXYLineChart(
	            	"Evolution de la distribution stationnaire (2,1,2) en fonction du nombre de simulations", 
	      	  	    "Nombre de simulations", "Moyenne du nombre d'observation de pi(2,1,2)",    // x et y axis label
	                datasetAliceMoyTemps,                  // data
	                PlotOrientation.VERTICAL,
	                true,                     // include legend
	                true,                     // tooltips
	                false                     // urls
	            ); 
	
		    JFreeChart histoAlice1 = ChartFactory.createHistogram("Observation de pi(2,1,2) en fonction de la simulation", 
		  	  	    "Simulation", "Z(simulation) (2,1,2)", datasetAlice1, PlotOrientation.VERTICAL, false, false, false);	    
		    
		    JFreeChart chartAliceFoule = ChartFactory.createXYLineChart(
	            	"Evolution du nombre moyen de personnes dans la foule en fonction du nombre d'évènement observé.", 
	      	  	    "Nombre d'observations", "Nombre moyen de personnes dans la foule",                      // x et y axis label
	                datasetAliceFoule,                  // data
	                PlotOrientation.VERTICAL,
	                true,                     // include legend
	                true,                     // tooltips
	                false                     // urls
	            ); 
		    

		    ChartPanel tPanelAlice = new ChartPanel (histoAlice); 
		    ChartPanel tPanelAliceMoy = new ChartPanel (chartAlice); 
		    ChartPanel tPanelAliceMoyTemps = new ChartPanel (chartAliceTemps); 
		    ChartPanel tPanelAliceFoule = new ChartPanel (chartAliceFoule); 
		    ChartPanel tPanelAlice1 = new ChartPanel (histoAlice1); 
		    try {
		        ChartUtilities.saveChartAsPNG(new File("histoAlice.PNG"), histoAlice, 1000, 500);
		        ChartUtilities.saveChartAsPNG(new File("chartAlice.PNG"), chartAlice, 1000, 500);
		        ChartUtilities.saveChartAsPNG(new File("chartAliceTps.PNG"), chartAliceTemps, 1000, 500);
		        ChartUtilities.saveChartAsPNG(new File("chartAliceFoule.PNG"), chartAliceFoule, 1000, 500);
		        ChartUtilities.saveChartAsPNG(new File("histoAlice1.PNG"), histoAlice1, 1000, 500);
		    } catch (IOException e) {}			   
		        
        }
        pnl.add(cPanel);     
        
    
  } 

  
}
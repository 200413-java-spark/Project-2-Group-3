package dataVisualization;

public class Visualization {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello World");
		
		// Display Scatter Graph
		String csvName = "C:/Users/music/Documents/workspace-spring-tool-suite-4-4.6.1.RELEASE/dataVisualization/sample.csv";
		String tableTitle = "Test Title";
		String xName = "x-axis";
		String yName = "y-axis";
		Plotter p = new Plotter(csvName, tableTitle, xName, yName) ;
		p.genScatter();
		
		}
}

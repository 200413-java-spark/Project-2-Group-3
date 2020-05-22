package dataVisualization;

import tech.tablesaw.aggregate.*;
import tech.tablesaw.api.*;
import tech.tablesaw.columns.*;
import tech.tablesaw.io.*;
import tech.tablesaw.joining.*;
import tech.tablesaw.plotting.*;
import tech.tablesaw.api.plot.*;
import tech.tablesaw.*;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.ScatterPlot;
import tech.tablesaw.plotly.components.Figure;
import tech.tablesaw.selection.*;
import tech.tablesaw.sorting.*;
import tech.tablesaw.table.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import tech.tablesaw.plotly.components.Page;
import tech.tablesaw.plotly.display.Browser;

public class Plotter {
	private String csvName;
	private String tableTitle;
	private String xName;
	private String yName;

	// Constructor
	public Plotter(String csvName) {
		this.csvName = csvName;
	}

	// Constructor Overload
	public Plotter(String csvName, String tableTitle, String xName, String yName) {
		this.csvName = csvName;
		this.tableTitle = tableTitle;
		this.xName = xName;
		this.yName = yName;
	}

	public void genScatter() {
		try	
		{
			Table tableName = Table.read().csv(csvName);
			Figure fig = ScatterPlot.create("aa", tableName, "x", "y");
			Plot.show(fig);
			// tableTitle, tableName, xName, yName)
		} 
		catch( Exception e)
		{
			e.printStackTrace();
		}

	}

	// Getter and Setters
	public String getCsvName() {
		return csvName;
	}

	public void setCsvName(String csvName) {
		this.csvName = csvName;
	}

	public String getTableTitle() {
		return tableTitle;
	}

	public void setTableTitle(String tableTitle) {
		this.tableTitle = tableTitle;
	}

	public String getxName() {
		return xName;
	}

	public void setxName(String xName) {
		this.xName = xName;
	}

	public String getyName() {
		return yName;
	}

	public void setyName(String yName) {
		this.yName = yName;
	}
}

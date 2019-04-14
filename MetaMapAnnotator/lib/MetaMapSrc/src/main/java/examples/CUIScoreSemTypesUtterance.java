package examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

import java.util.List;
import java.util.ArrayList;

import gov.nih.nlm.nls.metamap.AcronymsAbbrevs;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.Result;
import gov.nih.nlm.nls.metamap.Utterance;
import gov.nih.nlm.nls.metamap.PCM;
import gov.nih.nlm.nls.metamap.Mapping;
import gov.nih.nlm.nls.metamap.Ev;

public class CUIScoreSemTypesUtterance {
  /** MetaMap api instance */
  MetaMapApi api;

  /**
   * Creates a new <code>CUIScoreSemTypesUtterance</code> instance.
   *
   */
  public CUIScoreSemTypesUtterance() {
    this.api = new MetaMapApiImpl();
  }
  
  /**
   * Creates a new <code>CUIScoreSemTypesUtterance</code> instance using specified host and port.
   *
   * @param serverHostname hostname of MetaMap server.
   * @param serverPort     listening port used by MetaMap server.
   */
  public CUIScoreSemTypesUtterance(String serverHostname, int serverPort) {
    this.api = new MetaMapApiImpl();
    this.api.setHost(serverHostname);
    this.api.setPort(serverPort);
  }

  void setTimeout(int interval) {
    this.api.setTimeout(interval);
  }

  /**
   * Process terms using MetaMap API and send results to PrintStream.
   * <p>
   * cui|score|semtypelist|conceptname|utterance
   * <p>
   * @param terms input terms
   * @param out output printer
   */
  void process(String terms, PrintStream out, int verbosity) 
   throws Exception {
    System.out.println("options: " + api.getOptions());
    List<Result> resultList = api.processCitationsFromString(terms);
    for (Result result: resultList) {
      if (result != null) {
	/** write result as: cui|score|semtypes|utterance */
	for (Utterance utterance: result.getUtteranceList()) {
	  for (PCM pcm: utterance.getPCMList()) {
	    for (Mapping map: pcm.getMappingList()) {
	      for (Ev mapEv: map.getEvList()) {
		StringBuilder sb = new StringBuilder();
		sb.append(mapEv.getSemanticTypes().get(0));
		for (String semType: 
		       mapEv.getSemanticTypes().subList(1,mapEv.getSemanticTypes().size())) {
			 sb.append(":").append(semType);
		}
		out.println(mapEv.getConceptId() + "|" +
			    mapEv.getScore() + "|" +
			    sb.toString() + "|" +
			    mapEv.getConceptName() + "|" +
			    utterance.getString());
	      }
	    }
	  }
	}
      }
    }
  }


  /**
   * @param inFile file of sentences, each separated by a blank line.
   * @param out output print stream for results.
   * @param serverOptions options for MetaMap Server.
   * @param verbosity ...
   */
  public void process(File inFile, PrintStream out, int verbosity) throws Exception {
    BufferedReader ib = new BufferedReader(new FileReader(inFile));
    String line;
    int count = 0;
    while ((line = ib.readLine()) != null) {
      if (!line.trim().equals("")) {
	process(line, out, verbosity);
	count++;
	if (count % 100 == 0)
	  System.out.print(".");
      }
    }
    ib.close();
  }

  /**
   * Process terms using MetaMap API and send results to PrintStream.
   * <p>
   * cui|score|semtypelist|conceptname|utterance
   * <p>
   * @param terms input terms
   * @param out output printer
   * @param serverOptions options to pass to metamap server before processing input text.
   */
  void process(File infile, PrintStream out, List<String> serverOptions, int verbosity) 
    throws Exception
  {
    if (serverOptions.size() > 0) {
      api.setOptions(serverOptions);
    }
    process(infile, out, verbosity);
  }

  /**
   * Process terms using MetaMap API and send results to PrintStream.
   * <p>
   * cui|score|semtypelist|conceptname|utterance
   * <p>
   * @param terms input terms
   * @param out output printer
   * @param serverOptions options to pass to metamap server before processing input text.
   */
  void process(File inFile, PrintStream out, String serverOptions, int verbosity) 
    throws Exception
  {
    if (serverOptions.length() > 0) {
      api.setOptions(serverOptions);
    }
    process(inFile, out, verbosity);
  }

 public static void main(String[] args) 
    throws Exception
  {
    if (args.length > 1) {
      CUIScoreSemTypesUtterance instance = new CUIScoreSemTypesUtterance();
      // List<String> options = new ArrayList<String>();
      // options.add("-y");  // use WSD
      // options.add("-k");
      // options.add("dsyn");
      instance.process(new File(args[0]),new PrintStream(new File(args[1])), "-yk dsyn", 0);
    } else {
      System.out.println("usage: examples.CUIScoreSemTypesUtterance infilename outfilename");
    }
  }


}




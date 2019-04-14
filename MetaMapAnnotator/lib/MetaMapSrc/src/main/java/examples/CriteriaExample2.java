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

public class CriteriaExample2 {
  /** MetaMap api instance */
  MetaMapApi api;

  /**
   * Creates a new <code>CriteriaExample2</code> instance.
   *
   */
  public CriteriaExample2() {
    this.api = new MetaMapApiImpl();
  }

  void process(String terms, PrintStream out, int verbosity) throws Exception {
    System.out.println("options: " + api.getOptions());
    api.setOptions("-y");
    if (terms.trim().length() > 0) {
      List<Result> resultList = api.processCitationsFromString(terms);
      for (Result result : resultList) {
	if (result != null) {
	  /** write result as: cui|score|semtypes|utterance */
	  for (Utterance utterance : result.getUtteranceList()) {
	    for (PCM pcm : utterance.getPCMList()) {
	      for (Mapping map : pcm.getMappingList()) {
		for (Ev mapEv : map.getEvList()) {
		  StringBuilder sb = new StringBuilder();
		  sb.append(mapEv.getSemanticTypes().get(0));
		  for (String semType : mapEv.getSemanticTypes().subList(1, mapEv.getSemanticTypes().size())) {
		    sb.append(":").append(semType);
		  }
		  out.println(mapEv.getConceptId() + "|"
			      + mapEv.getScore() + "|"
			      + sb.toString() + "|"
			      + mapEv.getConceptName() + "|"
			      + utterance.getString());
		}
	      }
	    }
	    out.println();
	  }
	}
      }
    }
  }

  public static void main(String[] args) 
    throws Exception {
    if (args.length > 1) {
      CriteriaExample2 ceInstance = new CriteriaExample2();
      PrintStream out = new PrintStream(args[1]);
      BufferedReader br = new BufferedReader(new FileReader(args[0]));
      String line;
      while ((line = br.readLine()) != null) {
	ceInstance.process(line, out, 0);
      }
      br.close();
      out.close();
    } else {
      System.err.println("usage: example.CriteriaExample inputfile outputfile");
    }
  }
 }

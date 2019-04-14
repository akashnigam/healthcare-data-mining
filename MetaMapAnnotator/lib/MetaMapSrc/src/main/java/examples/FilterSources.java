
// example
package examples;

import java.io.*;
import java.util.*;

import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.Result;
import gov.nih.nlm.nls.metamap.Utterance;
import gov.nih.nlm.nls.metamap.PCM;
import gov.nih.nlm.nls.metamap.Mapping;
import gov.nih.nlm.nls.metamap.Ev;


/**
 *
 */

public class FilterSources {

  static void process(String terms, PrintStream out, String options, MetaMapApi api) 
    throws Exception
  {
    api.setOptions(options);
    System.out.println("options: " + api.getOptions());
    if (terms.trim().length() > 0) {
      List<Result> resultList = api.processCitationsFromString(terms);
      for (Result result : resultList) {
	if (result != null) {
	  /** write result as: cui|score|semtypes|sources|utterance */
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
			      + mapEv.getSources() + "|"
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
    MetaMapApi api = new MetaMapApiImpl();
    String options = "-R ICD9CM,MTHICD9";
    PrintStream out = System.out;
    process("No Clinical diagnosis of acne vulgaris",out,options,api);
  }
}

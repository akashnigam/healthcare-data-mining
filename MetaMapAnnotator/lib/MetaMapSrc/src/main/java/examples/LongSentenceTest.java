
//
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

public class LongSentenceTest {

  public static void process(String terms, PrintStream out, String options, MetaMapApi api) 
    throws Exception
  {
    if (options.trim().length() > 1) {
      api.setOptions(options);
      System.out.println("options: " + api.getOptions());
    }
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
		  Set<String> sourceSet = new HashSet<String>();
		  sourceSet.addAll(mapEv.getSources());
		  if (sourceSet.contains("ICD9CM") || 
		      sourceSet.contains("MTHICD9")) {
		  out.println(mapEv.getConceptId() + "|"
			      + mapEv.getScore() + "|"
			      + sb.toString() + "|"
			      + mapEv.getConceptName() + "|"
			      + mapEv.getSources() + "|"
			      + utterance.getString());
		  }
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
    String options = "";
    PrintStream out = System.out;
    process("Malignant melanoma, or simply melanoma, is a cancerous tumor that is formed from the skin's pigment cells (melanocytes). It is also sometimes called black skin cancer. Melanoma is relatively rare, but more aggressive than other types of skin cancer: about 5 out of every 100 skin cancers are melanomas, but they cause the majority of all deaths resulting from skin cancer.",out,options,api);
  }
}

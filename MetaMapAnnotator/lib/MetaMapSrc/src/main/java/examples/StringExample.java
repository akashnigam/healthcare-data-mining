package examples;

import java.util.List;

import gov.nih.nlm.nls.metamap.AcronymsAbbrevs;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.Result;
import gov.nih.nlm.nls.metamap.Utterance;
import gov.nih.nlm.nls.metamap.PCM;
import gov.nih.nlm.nls.metamap.Mapping;
import gov.nih.nlm.nls.metamap.Ev;

public class StringExample {

  public static void main(String[] args) 
    throws Exception {
    MetaMapApi api = new MetaMapApiImpl();
    
    String serverhost = MetaMapApi.DEFAULT_SERVER_HOST;
    int serverport = MetaMapApi.DEFAULT_SERVER_PORT;
    int timeout = -1;
    System.out.println(serverhost+":"+serverport+":"+timeout);
    api.setHost(serverhost);
    api.setPort(serverport);
    api.setTimeout(timeout);
    
    String inputTerm = "abdominal abcess";
    if (args.length > 0) {
      StringBuilder sb = new StringBuilder();
      for (String arg: args) {
	sb.append(arg).append(" ");
      }
      inputTerm = sb.toString().trim();
    }
    List<Result> results =
      api.processCitationsFromString(inputTerm);
    System.out.println("input term: " + inputTerm);
    for (Result result: results) {
      if (result != null) {
	for (Utterance utterance: result.getUtteranceList()) {
	  // use mappings to get best match
	  for (PCM pcm: utterance.getPCMList()) {	    
	    for (Mapping map: pcm.getMappingList()) {
	      for (Ev mapEv: map.getEvList()) {
		// get cui (concept unique identifier) for mapping
		System.out.println("  Preferred Name: " + mapEv.getPreferredName() +
				   ", cui:" + mapEv.getConceptId());
	      }
	    }
	  }
	}
      }
    }
  }
}

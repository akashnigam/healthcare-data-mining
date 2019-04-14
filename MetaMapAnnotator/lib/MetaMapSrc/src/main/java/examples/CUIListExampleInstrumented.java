package examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

import java.lang.Exception;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.Result;
import gov.nih.nlm.nls.metamap.Utterance;
import gov.nih.nlm.nls.metamap.PCM;
import gov.nih.nlm.nls.metamap.Mapping;
import gov.nih.nlm.nls.metamap.Ev;

public class CUIListExampleInstrumented {
  /** MetaMap api instance */
  MetaMapApi api;
  long rrTime = 0;
  long eTime = 0;
  long utteranceCount = 0;
  long mapCount = 0;
  long evCount = 0;

  /**
   * Creates a new <code>CUIListExampleInstrumented</code> instance.
   */
  public CUIListExampleInstrumented() {
    this.api = new MetaMapApiImpl();
  }

  Set<String> process(String text)
    throws Exception
  {
    Set<String> cuiSet = new HashSet<String>();
    api.setOptions("-y");
    // System.out.println("options: " + api.getOptions());
    if (text.trim().length() > 0) {
      long startTime = System.currentTimeMillis();
      List<Result> resultList = api.processCitationsFromString(text);
      long endTime = System.currentTimeMillis();
      rrTime = rrTime + (endTime - startTime);
      startTime = System.currentTimeMillis();
      for (Result result : resultList) {
	if (result != null) {
	  /** Add cuis in evterms to cui set */
	  for (Utterance utterance : result.getUtteranceList()) {
	    utteranceCount++;
	    for (PCM pcm : utterance.getPCMList()) {
	      for (Mapping map : pcm.getMappingList()) {
		mapCount++;
		for (Ev mapEv : map.getEvList()) {
		  evCount++;
		  cuiSet.add(mapEv.getConceptId());
		}
	      }
	    }
	  }
	}
      }
      endTime = System.currentTimeMillis();
      eTime = eTime + (endTime - startTime);
    }
    return cuiSet;
  }

  public static void main(String[] args) 
    throws Exception {
    if (args.length > 0) {
      Set<String> cuiSet = new HashSet<String>();
      CUIListExampleInstrumented ceInstance = new CUIListExampleInstrumented();
      BufferedReader br = new BufferedReader(new FileReader(args[0]));
      String line;
      while ((line = br.readLine()) != null) {
	cuiSet.addAll(ceInstance.process(line));
      }
      br.close();
      for (String cui: cuiSet) {
	System.out.println(cui);
      }
      System.err.println("MetaMap server request/response time: " + ceInstance.rrTime + " milliseconds");
      System.err.println("Extracting response time: " + ceInstance.eTime + " milliseconds");
      System.err.println("Number of Utterance terms: " + ceInstance.utteranceCount );
      System.err.println("Number of Mapping terms: " + ceInstance.mapCount );
      System.err.println("Number of Mapping Evaluation terms: " + ceInstance.evCount );
      System.err.println("Number of Unique Concepts: " + cuiSet.size());

    } else {
      System.err.println("usage: example.CUIListExample inputfile");
    }
  }
}

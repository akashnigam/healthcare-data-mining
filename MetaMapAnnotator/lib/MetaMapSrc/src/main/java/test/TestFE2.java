package test;

import se.sics.prologbeans.*;
import java.io.*;
import java.util.List;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.Result;
import gov.nih.nlm.nls.metamap.Utterance;
import gov.nih.nlm.nls.metamap.Negation;
import gov.nih.nlm.nls.metamap.AcronymsAbbrevs;
import gov.nih.nlm.nls.metamap.PCM;
import gov.nih.nlm.nls.metamap.Ev;
import gov.nih.nlm.nls.metamap.Mapping;
import gov.nih.nlm.nls.metamap.Position;
import gov.nih.nlm.nls.metamap.ConceptPair;

/**
 * An example of reading an input file to a string, passing the string
 * to the api, and then writing the result to an output file in a
 * human readable form .
 * <p>
 * Created: Tue May 19 09:42:22 2009
 *
 * @author <a href="mailto:wrogers@nlm.nih.gov">Willie Rogers</a>
 * @version 1.0
 */
public class TestFE2 {

  private PrologSession session = new PrologSession();

  /**
   * Creates a new <code>TestFE2</code> instance.
   *
   */
  public TestFE2() {

  }

  void process(String inputFilename, String outputFilename)
  {
    try {
      StringBuffer sb = new StringBuffer();
      BufferedReader br = new BufferedReader(new FileReader(inputFilename));
      String line;
      while ((line = br.readLine()) != null) {
	sb.append(line).append("\n");
      }
      br.close();
      String input = sb.toString();
      System.out.println("input: " + input);

      MetaMapApi api = new MetaMapApiImpl(0);
      List<Result> resultList = api.processCitationsFromString(input);
      for (Result result: resultList) {
	if (result != null) {
	  PrintWriter pw = new PrintWriter(outputFilename);
	  List<AcronymsAbbrevs> aaList = result.getAcronymsAbbrevsList();
	  pw.println("Acronyms and Abbreviations:");
	  if (aaList.size() > 0) {
	    for (AcronymsAbbrevs e: aaList) {
	      pw.println("Acronym: " + e.getAcronym());
	      pw.println("Expansion: " + e.getExpansion());
	      pw.println("Count list: " + e.getCountList());
	      pw.println("CUI list: " + e.getCUIList());
	    }
	  } else {
	    pw.println(" None.");
	  }

	  pw.println("Negations:");
	  List<Negation> negList = result.getNegationList();
	  if (negList.size() > 0) {
	    for (Negation e: negList) {
	      pw.println("type: " + e.getType());
	      pw.print("Trigger: " + e.getTrigger() + ": [");
	      for (Position pos: e.getTriggerPositionList()) {
		pw.print(pos  + ",");
	      }
	      pw.println("]");
	      pw.print("ConceptPairs: [");
	      for (ConceptPair pair: e.getConceptPairList()) {
		pw.print(pair + ",");
	      }
	      pw.println("]");
	      pw.print("ConceptPositionList: [");
	      for (Position pos: e.getConceptPositionList()) {
		pw.print(pos + ",");
	      }
	      pw.println("]");
	    }
	  } else {
	    pw.println(" None.");
	  }
	  for (Utterance utterance: result.getUtteranceList()) {
	    pw.println("Utterance:");
	    pw.println(" Id: " + utterance.getId());
	    pw.println(" Utterance text: " + utterance.getString());
	    pw.println(" Position: " + utterance.getPosition());
	  
	    for (PCM pcm: utterance.getPCMList()) {
	      pw.println("Phrase:");
	      pw.println(" text: " + pcm.getPhrase().getPhraseText());

	      pw.println("Candidates:");
	      for (Ev ev: pcm.getCandidateList()) {
		pw.println(" Candidate:");
		pw.println("  Score: " + ev.getScore());
		pw.println("  Concept Id: " + ev.getConceptId());
		pw.println("  Concept Name: " + ev.getConceptName());
		pw.println("  Preferred Name: " + ev.getPreferredName());
		pw.println("  Matched Words: " + ev.getMatchedWords());
		pw.println("  Semantic Types: " + ev.getSemanticTypes());
		pw.println("  is Head?: " + ev.isHead());
		pw.println("  is Overmatch?: " + ev.isOvermatch());
		pw.println("  Sources: " + ev.getSources());
		pw.println("  Positional Info: " + ev.getPositionalInfo());
	      }
	      pw.println("Mappings:");
	      for (Mapping map: pcm.getMappingList()) {
		pw.println(" Mapping:");
		pw.println("  Map Score: " + map.getScore());
		for (Ev mapEv: map.getEvList()) {
		  pw.println("   Score: " + mapEv.getScore());
		  pw.println("   Concept Id: " + mapEv.getConceptId());
		  pw.println("   Concept Name: " + mapEv.getConceptName());
		  pw.println("   Preferred Name: " + mapEv.getPreferredName());
		  pw.println("   Matched Words: " + mapEv.getMatchedWords());
		  pw.println("   Semantic Types: " + mapEv.getSemanticTypes());
		  pw.println("   is Head?: " + mapEv.isHead());
		  pw.println("   is Overmatch?: " + mapEv.isOvermatch());
		  pw.println("   Sources: " + mapEv.getSources());
		  pw.println("   Positional Info: " + mapEv.getPositionalInfo());
		}
	      }
	    }
	  }
	  pw.close();
	} else {
	  System.out.println("NULL result instance! ");
	}
      }
    } catch (Exception e) {
      System.out.println("Error when querying Prolog Server: " +
			 e.getMessage() + '\n');
    }
  }

  public static void main(String[] args) {
    TestFE2 frontEnd = new TestFE2();
    
    if (args.length < 2) {
      System.out.println("usage: TestFE2 <inputfile> <outputfile>");
      System.exit(0);
    }
    frontEnd.process(args[0], args[1]);
  }
}

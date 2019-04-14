package examples;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import java.util.Arrays;
import java.util.List;
import java.util.Iterator;

import gov.nih.nlm.nls.metamap.AcronymsAbbrevs;
import gov.nih.nlm.nls.metamap.ConceptPair;
import gov.nih.nlm.nls.metamap.Ev;
import gov.nih.nlm.nls.metamap.Mapping;
import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.Negation;
import gov.nih.nlm.nls.metamap.PCM;
import gov.nih.nlm.nls.metamap.Position;
import gov.nih.nlm.nls.metamap.Result;
import gov.nih.nlm.nls.metamap.Utterance;

//
/**
 *
 */

public class Example {
  /** MetaMap api instance */
  MetaMapApi api;

  /**
   * Creates a new <code>Example</code> instance.
   */
  public Example() {
    this.api = new MetaMapApiImpl();
  }

  public static String join(List<String> stringList, String joinString) {
    StringBuilder sb = new StringBuilder();
    Iterator<String> iter = stringList.iterator();
    if (iter.hasNext()) { sb.append(iter.next()); }
    while ( iter.hasNext() ) {
      sb.append(joinString);
      sb.append(iter.next());
    }
    return sb.toString();
  }

  String pprintBracketedList(List<String> stringList, String joinString) {
    return "[" + join(stringList, joinString) + "]";
  }

  /**
   *<pre>
   * Column 1: File name
   * Column 2: Phrase the CUI came from.
   * Column 3: The first number is the character index in the line which the phrase starts. The second number is the length of the phrase. There is not a line number which the phrase came from.
   * Column 4: Confidence Score
   * Column 5: CUI ID
   * Column 6: Positive/Negated
   * Column 7: CUI Name
   * Column 8: Semantic types
   * Column 10: CUI preferred name
   * Column 11: Words matched to the CUI. For example if the CUI C0205164(Major) was found in the phrase "major resection" the matched word would be "major".
   * Column 12: Sources the CUI came from (MESH, ...)
   * </pre>
   */
  public void process(String infilename, String outfilename)
    throws IOException, Exception
  {
    PrintWriter pw = new PrintWriter(new FileWriter(outfilename));
    List<Result> resultList = this.api.processCitationsFromFile(infilename);
    for (Result result : resultList) {
      if (result != null) {
	/** Add cuis in evterms to cui set */
	for (Utterance utterance : result.getUtteranceList()) {
	  for (PCM pcm : utterance.getPCMList()) {
	    for (Mapping map : pcm.getMappingList()) {
	      for (Ev mapEv : map.getEvList()) {
		pw.println(infilename + "|" + 
			   pcm.getPhrase().getPhraseText() + "|(" +
			   pcm.getPhrase().getPosition().getX() + "," + pcm.getPhrase().getPosition().getY() + ")|" +
			   mapEv.getScore() + "|" + 
			   mapEv.getConceptId() + "|" +
			   ((mapEv.getNegationStatus() == 0) ? "POS" : "NEG") + "|" +
			   mapEv.getConceptName () + 
			   pprintBracketedList(mapEv.getSemanticTypes(), ",") + "|" +
			   mapEv.getPreferredName() + "|" +
			   pprintBracketedList(mapEv.getMatchedWords(), ",") + "|" +
			   pprintBracketedList(mapEv.getSources(), ",") + "|");
	      }
	    }
	  }
	}
      }
    }
  }



  /**
   *
   * @param args - Arguments passed from the command line
   *
   */
  public static void main(String[] args) 
    throws IOException, Exception
  {
    if (args.length > 0) {
      String infilename = join(Arrays.asList(args)," ");
      String outfilename = infilename + ".out";
      Example inst = new Example();
      inst.process(infilename, outfilename);
    } else {
      System.out.println("usage: examples.Example inputfilename");
    }
  }
}

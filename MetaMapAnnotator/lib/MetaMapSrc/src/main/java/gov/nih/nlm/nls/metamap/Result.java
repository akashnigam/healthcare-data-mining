package gov.nih.nlm.nls.metamap;

import java.util.List;
import java.io.PrintStream;
import se.sics.prologbeans.*;

/**
 * A container for the machine output representation of a MetaMap
 * result.
 *
 *  <pre>
 *  List<Result> resultList = api.processCitationsFromString(terms);
 *
 *  for (Result result: resultList) {
 *    List&lt;AcronymsAbbrevs> aaList = result.getAcronymsAbbrevs();
 *    System.out.println("Acronyms and Abbreviations:");
 *    if (aaList.size() > 0) {
 *      for (AcronymsAbbrevs e: aaList) {
 *        String acronymText = e.getAcronym();
 *        ...
 *      }
 *    }
 *  
 *
 *    List&lt;Negation> negList = result.getNegations();
 *    if (negList.size() > 0) {
 *      for (Negation e: negList) {
 *        System.out.println("type: " + e.getType());
 *      }
 *    }
 *
 *    for (Utterance utterance: result.getUtteranceList()) {
 *      for (PCM pcm: utterance.getPCMList()) {
 *        String phraseText = pcm.getPhrase().getPhraseText()
 *        for (Ev ev: pcm.getCandidateList()) {
 *        int score = ev.getScore();
 *          ...
 *        }
 *        for (gov.nih.nlm.nls.metamap.Map map: pcm.getMappings)) {
 *          for (Ev mapEv: map.getEvList()) {
 *            int score = ev.getScore();
 *            ...
 *          }
 *        }
 *      }
 *    }
 *  </pre>
 *<p>
 * Created: Wed Apr 29 15:44:14 2009
 *
 * @author <a href="mailto:wrogers@nlm.nih.gov">Willie Rogers</a>
 * @version 1.0
 */
public interface Result {
  /** 
   * Assign inputText that produced this result.
   * @param theInputText the input text */
  void setInputText(String theInputText);
  /** 
   * Get input text that produced this result.
   * @return theInputText the input text */
  String getInputText();

  /**
   * Return a prolog beans list of terms representing machine output.
   * @return prolog beans list ({@link PBTerm}) of machine output terms */
  PBTerm getMMOPBlist();
  /**
   * Return the raw string representation of MetaMap machine output.
   * @return a {@link String} containing the result as MetaMap
   * machine output.
   */
  String getMachineOutput();
  /**
   * Print machine output to stream.
   * @param out stream to send output.
   */
  void traverse(PrintStream out);

  /**
   * Return a list of Acronym and Abbreviations objects.
   * @return a list of {@link AcronymsAbbrevs} objects */
  List<AcronymsAbbrevs> getAcronymsAbbrevs() throws Exception ;

  /**
   * Return a list of Acronym and Abbreviations objects.
   * @return a list of {@link AcronymsAbbrevs} objects */
  List<AcronymsAbbrevs> getAcronymsAbbrevsList() throws Exception ;

  /**
   * Return a list of {@link Negation} objects.
   * @return a list of {@link Negation} objects */
  List<Negation> getNegations() throws Exception ;

  /**
   * Return a list of {@link Negation} objects.
   * @return a list of {@link Negation} objects */
  List<Negation> getNegationList() throws Exception ;

  /**
   * Return a list of {@link Utterance} objects.
   * @return a list of {@link Utterance} objects */
  List<Utterance> getUtteranceList() throws Exception ;
}

package gov.nih.nlm.nls.metamap;

import java.util.List;
import se.sics.prologbeans.PBTerm;

/**
 * Representation of MetaMap Evaluation (Ev) instance.
 *<p>
 *<pre>
 * for (Utterance utterance: result.getUtteranceList()) {
 *   for (PCM pcm: utterance.getPCMList()) {
 *     for (Mapping map: pcm.getMappingList()) {
 *       for (Ev mapEv: map.getEvList()) {
 *   	   System.out.println("   Score: " + mapEv.getScore());
 * 	   System.out.println("   Concept Id: " + mapEv.getConceptId());
 * 	   System.out.println("   Concept Name: " + mapEv.getConceptName());
 * 	   System.out.println("   Preferred Name: " + mapEv.getPreferredName());
 * 	   System.out.println("   Matched Words: " + mapEv.getMatchedWords());
 * 	   System.out.println("   Semantic Types: " + mapEv.getSemanticTypes());
 *         System.out.println("   MatchMap: " + mapEv.getMatchMap());
 * 	   System.out.println("   is Head?: " + mapEv.isHead());
 * 	   System.out.println("   is Overmatch?: " + mapEv.isOvermatch());
 * 	   System.out.println("   Sources: " + mapEv.getSources());
 * 	   System.out.println("   Positional Info: " + mapEv.getPositionalInfo());
 * 	   System.out.println("   Pruning Status: " + mapEv.getPruningStatus());
 *       }
 *     }
 *   }
 * }
 * </pre>
 *<p>
 * Created: Wed Apr 29 16:01:18 2009
 *
 * @author <a href="mailto:wrogers@nlm.nih.gov">Willie Rogers</a>
 * @version 1.0
 */
public interface Ev extends MetaMapElement {
  int getScore() throws Exception;
  String getConceptId() throws Exception;
  String getConceptName() throws Exception;
  String getPreferredName() throws Exception;
  List<String> getMatchedWords() throws Exception;
  List<String> getSemanticTypes() throws Exception;
  /** 
   * This returns a recursive list of objects where the elements can
   * be Integer classes or List classes containing Lists or Integers.
   * @return List of Objects.
   */
  List<Object> getMatchMap() throws Exception;
  /** 
   * This returns a list of MatchMap objects
   * @return List of MatchMap.
   */
  List<MatchMap> getMatchMapList() throws Exception;
  boolean isHead() throws Exception;
  boolean isOvermatch() throws Exception;
  List<String> getSources() throws Exception;
  List<Position> getPositionalInfo() throws Exception;
  PBTerm getTerm() throws Exception;

  /**
   * Get pruning status of ev term.
   * <p>
   * <ul>
   *   <li> 1: if the candidate was excluded,
   *   <li> 2: if the candidate was pruned, and
   *   <li> 0: otherwise, i.e., if the candidate was retained.
   * </ul>
   */
  int getPruningStatus() throws Exception;

  
  /**
   * Get negation status of ev term.
   * <p>
   * <ul>
   *   <li> 1: if the candidate is negated
   *   <li> 0: if the candidate is not-negated.
   * </ul>
   */
  int getNegationStatus() throws Exception;
}

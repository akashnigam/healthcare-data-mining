package gov.nih.nlm.nls.metamap;

import java.util.List;

/**
 * Describe interface MatchMap here.
 *
 *
 * Created: Fri Apr 16 10:36:52 2010
 *
 * @author <a href="mailto:wrogers@nlm.nih.gov">Willie Rogers</a>
 * @version 1.0
 */
public interface MatchMap extends MetaMapElement {
  /** The position within the phrase words of the first matching word
   * @return word position */
  int getPhraseMatchStart() ;
  /** The position within the phrase words of the last matching word
   * @return word position*/
  int getPhraseMatchEnd();
  /** The position within the concept words of the first matching word
   * @return word position*/
  int getConceptMatchStart();
  /** The position within the concept words of the last matching word
   * @return word position*/
  int getConceptMatchEnd();
  /** The degree of lexical variation between the words in the
   * candidate concept and the words in the phrase; the computation
   * of this value is explained on pp. 2-3 of MetaMap Evaluation.
   * @return lexical variation of match*/
  int getLexMatchVariation();

  /** 
   * This returns a list of representation of match map.
   * @return List of Objects.
   */
  List<Object> getListRepr() throws Exception;
}
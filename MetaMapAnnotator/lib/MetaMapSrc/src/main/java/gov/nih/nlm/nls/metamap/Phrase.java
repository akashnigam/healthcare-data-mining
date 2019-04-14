package gov.nih.nlm.nls.metamap;

import se.sics.prologbeans.PBTerm;

/**
 * A representation of MetaMap Phrase structure.
 *
 * <p>
 * Created: Mon May 11 15:17:15 2009
 *
 * @author <a href="mailto:wrogers@nlm.nih.gov">Willie Rogers</a>
 * @version 1.0
 */
public interface Phrase extends MetaMapElement {
  /**
   * Get original text of phrase.
   * @return original text of phrase.
   */
  String getPhraseText() throws Exception;
  /** Get the result of the syntax analysis of the phrase by the
   * minimal commitment parser.
   * See  <a href="http://skr.nlm.nih.gov/papers/references/metamap.tech.pdf">
   * MetaMap Technical Notes, Preliminary processing 
   * (http://skr.nlm.nih.gov/papers/references/metamap.tech.pdf, pp 3-4)</a>
   * for more information 
   * @return Syntax analysis output of the minimal commitment parser. */
  String getMincoManAsString();
  /* Get the result of the syntax analysis of the phrase by the
   * minimal commitment parser as a PrologBeans Term Object */
  PBTerm getMincoMan();
  /** Get position of phrase in the input text. 
   * @return position object containing position of phrase in the input text. 
   */
  Position getPosition() throws Exception;
}

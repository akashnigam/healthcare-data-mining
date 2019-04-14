package gov.nih.nlm.nls.metamap;

import se.sics.prologbeans.*;

/**
 * Implementation class of Phrase interface.
 * <p>
 * Created: Fri May 22 09:12:28 2009
 *
 * @author <a href="mailto:wrogers@nlm.nih.gov">Willie Rogers</a>
 * @version 1.0
 */
public class PhraseImpl implements Phrase {
  PBTerm phraseTerm;
  
  /**
   * Creates a new <code>PhraseImpl</code> instance.
   *
   */
  public PhraseImpl(PBTerm aPhraseTerm) {
    this.phraseTerm = aPhraseTerm;
  }
  /**
   * Get original text of phrase.
   * @return original text of phrase.
   */
  public String getPhraseText() throws Exception {
    return TermUtils.getAtomArgument(this.phraseTerm, 1);
  }
  /** Return the result of the syntax analysis of the phrase by the
   * minimal commitment parser
   * See  <a href="http://skr.nlm.nih.gov/papers/references/metamap.tech.pdf">
   * MetaMap Technical Notes, Preliminary processing 
   * (http://skr.nlm.nih.gov/papers/references/metamap.tech.pdf, pp 3-4)</a>
   * for more information 
   * @return Syntax analysis output of the minimal commitment parser. */
  public String getMincoManAsString() {
    return TermUtils.getArgumentAsString(this.phraseTerm, 2);
  }

  public PBTerm getMincoMan() {
    return this.phraseTerm.getArgument(2);
  }

  /** Get position of phrase in the input text. 
   * @return position object containing position of phrase in the input text. 
   */
  public Position getPosition() throws Exception {
    return TermUtils.getPositionArgument(this.phraseTerm, 3);
  }
  public String toString() {
    try {
      return "phrase: " + this.getPhraseText() + "," + 
	this.getMincoManAsString()  + "," + 
	this.getPosition();
    } catch (Exception e) {
      e.printStackTrace();	// TODO: should use RunTimeException
    }
    return "n/a";
  }
}

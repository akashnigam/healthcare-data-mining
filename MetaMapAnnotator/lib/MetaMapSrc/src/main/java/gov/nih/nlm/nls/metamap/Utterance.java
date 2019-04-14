package gov.nih.nlm.nls.metamap;

import java.util.List;

/**
 * Representation of MetaMap Utterance elements.
 * <p>
 *  <pre>
 *  Utterance utterance: result.getUtteranceList().get(0);
 *  for (PCM pcm: utterance.getPCMList()) {
 *    String phraseText = pcm.getPhrase().getPhraseText()
 *    for (Ev ev: pcm.getCandidateList()) {
 *      int score = ev.getScore();
 *      ...
 *    }
 *    for (gov.nih.nlm.nls.metamap.Map map: pcm.getMappings)) {
 *      for (Ev mapEv: map.getEvList()) {
 *        int score = ev.getScore();
 *        ...
 *      }
 *    }
 *  </pre>
 * <p>
 * Created: Mon May 11 15:17:56 2009
 *
 * @author <a href="mailto:wrogers@nlm.nih.gov">Willie Rogers</a>
 * @version 1.0
 */
public interface Utterance extends MetaMapElement {
  /** Get id of utterance.
   * @return id of utterance */
  String getId() throws Exception;
  /** Get content of utterance. @return utterance string */
  String getString() throws Exception;
  /** Get character position of utterance input text. 
   * @return position of utterance in input text */
  Position getPosition() throws Exception;
  /**
   * Return a list of {@link PCM} ({@link Phrase}/{@link Candidates}/{@link Mapping}s)
   * container objects associated with the utterance.
   * @return a list of {@link PCM} (Phrase/Candidate/Mapping) container objects.
   */
  List<PCM> getPCMList() throws Exception;
}

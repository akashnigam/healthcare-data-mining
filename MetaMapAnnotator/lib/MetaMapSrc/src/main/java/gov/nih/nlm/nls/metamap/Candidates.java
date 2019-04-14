package gov.nih.nlm.nls.metamap;

import java.util.List;

/**
 * An interface for representing a MetaMap candidate term.
 * A list of Ev terms that are candidates for mapping.
 * <p>
 * <pre>
 * for (Utterance utterance: result.getUtteranceList()) {
 *   for (PCM pcm: utterance.getPCMList()) {
 *     ...
 *     for (Ev candidateEv: .pcm.getCandidates().getEvList()) {
 *   	 System.out.println("   Score: " + candidateEv.getScore());
 * 	 System.out.println("   Concept Id: " + candidateEv.getConceptId());
 * 	 System.out.println("   Concept Name: " + candidateEv.getConceptName());
 *       ...
 *     }
 *     ...
 *   }
 * }
 * </pre>
 * <p>
 * Created: Tue Jun 26 09:41:43 2012
 *
 * @author <a href="mailto:wjrogers@mail.nih.gov">Willie Rogers</a>
 * @version 1.0
 */
public interface Candidates extends MetaMapElement {
  /**
   * Get total count of candidates. 
   * @return total count of candidates 
   */
  int getTotalCandidatesCount();

  /**
   * Get count of excluded candidates. 
   * @return count of excluded candidates 
   */
  int getExcludedCandidateCount();

  /**
   * Get count of pruned candidates. 
   * @return count of pruned candidates 
   */
  int getPrunedCandidateCount();

  /**
   * Get count of remaining candidates. 
   * @return count of remaining candidates 
   */
  int getRemainingCandidates();

  /**
   * Return the list of candidate ({@link Ev}) instances associated with the {@link Candidates} instance.
   *
   * @return a list of candidate Ev instances.
   */
  List<Ev> getEvList() throws Exception;
}

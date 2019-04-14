package gov.nih.nlm.nls.metamap;

import java.util.List;

/**
 * An interface for representing a MetaMap map term.  Essentially, a
 * score and a list of Ev terms. 
 * <p>
 * <pre>
 * for (Utterance utterance: result.getUtteranceList()) {
 *   for (PCM pcm: utterance.getPCMList()) {
 *  ...
 *     for (Mapping map: pcm.getMappingList()) {
 *       System.out.println(" Map Score: " + map.getScore());
 *       for (Ev mapEv: map.getEvList()) {
 *   	   System.out.println("   Score: " + mapEv.getScore());
 * 	   System.out.println("   Concept Id: " + mapEv.getConceptId());
 * 	   System.out.println("   Concept Name: " + mapEv.getConceptName());
 *         ...
 *       }
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * Created: Thu May 21 17:00:23 2009
 *
 * @author <a href="mailto:wrogers@nlm.nih.gov">Willie Rogers</a>
 * @version 1.0
 */
public interface Mapping extends MetaMapElement {
  int getScore() throws Exception;
  List<Ev> getEvList() throws Exception;
}

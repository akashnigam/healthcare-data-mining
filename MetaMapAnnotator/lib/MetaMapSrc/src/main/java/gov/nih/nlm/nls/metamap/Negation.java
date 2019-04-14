package gov.nih.nlm.nls.metamap;

import java.util.List;

/**
 * Represention of MetaMap Negation elements.
 *
 * <p>
 * Created: Mon May 11 15:16:37 2009
 *
 * @author <a href="mailto:wrogers@nlm.nih.gov">Willie Rogers</a>
 * @version 1.0
 */
public interface Negation extends MetaMapElement {
  String getType() throws Exception;
  String getTrigger() throws Exception;
  List<Position> getTriggerPositionList() throws Exception;
  /**
   * Provides a list of {@link ConceptPair} objects for the negation, each
   * represented by the concept's id (cui) and preferred
   * name.
   * @return list of <name>ConceptPair</name> objects
   */
  List<ConceptPair> getConceptPairList() throws Exception;
  List<Position> getConceptPositionList() throws Exception;
  /** 
   * This method is deprecated,
   * @return always returns null
   * @deprecated
   *
   * The preferred method {@link #getConceptPairList()} provides a
   * list of concepts for the negation, each represented by the 
   * concept's id (cui) and preferred name.
   */
  String getConceptId() throws Exception;
}

package gov.nih.nlm.nls.metamap;

import java.util.List;

/**
 * The interface for MetaMap Acronyms and Abbreviations.
 *
 *<p>
 * Created: Mon May 11 15:21:34 2009
 *
 * @author <a href="mailto:wrogers@nlm.nih.gov">Willie Rogers</a>
 * @version 1.0
 */
public interface AcronymsAbbrevs extends MetaMapElement {
  /** Get acronym or abbreviation */
  String getAcronym();
  /** Get the expansion of the acronym or abbreviation */
  String getExpansion();

  /**
   *  Returns the CountList of the Acronym and Abbreviation Object.
   *
   * CountList is a list containing four integers, e.g.: ([1,2,3,11]):
   * <ol>
   *  <li> the number of tokens in the acronym (1)
   *  <li>the character length of the acronym (2)
   *  <li>the number of tokens (including whitespace tokens) in the expansion (3)
   *  <li>the character length of the expansion (11) 
   * </ol>
   * @return CountList vector for Acronym and Abbreviation structure  
   */
  List<Integer> getCountList();
  /** Get the concept ids of the acronym or abbreviation */
  List<String> getCUIList();
}

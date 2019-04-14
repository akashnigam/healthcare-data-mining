package gov.nih.nlm.nls.metamap;

import java.util.List;
import java.util.ArrayList;
import se.sics.prologbeans.*;

/**
 * Implementation of container for a Phrase, Candidates, and Mappings set (PCM).
 *
 * <p>
 * Created: Thu May 21 17:50:44 2009
 *
 * @author <a href="mailto:wrogers@nlm.nih.gov">Willie Rogers</a>
 * @version 1.0
 */
public class PCMBase implements PCM {
  PBTerm phraseTerm;
  PBTerm candidatesTerm;
  PBTerm mappingsTerm;

  /**
   * Creates a new <code>PCMBase</code> instance.
   *
   */
  public PCMBase(PBTerm aPhraseTerm,
		 PBTerm aCandidatesTerm,
		 PBTerm aMappingsTerm) {
    this.phraseTerm      = aPhraseTerm;
    this.candidatesTerm  = aCandidatesTerm;
    this.mappingsTerm    = aMappingsTerm;
  }

  // Implementation of gov.nih.nlm.nls.metamap.PCM

  /**
   * Get {@link Phrase} associated with {@link Utterance}.
   *
   * @return a <code>Phrase</code> value
   */
  public final Phrase getPhrase() throws Exception {
    return new PhraseImpl(this.phraseTerm);
  }

  /**
   * Return the {@link Candidates} instance associated with the {@link Phrase}.
   *
   * @return a Candidates instances containing a List of candidate Ev instances.
   */
  public Candidates getCandidatesInstance()  throws Exception {
    return new CandidatesImpl(this.candidatesTerm);
  }

  /**
   * get candidates list
   *
   * @return a <code>List</code> value
   * @deprecated
   */
  public final List<Ev> getCandidates() throws Exception  {
    List<Ev> evList = new ArrayList<Ev>();

    PBTerm prologList = this.candidatesTerm.getArgument(5);
    PBTerm term = prologList;
    for (int i = 1; i <= prologList.length(); i++) {
      evList.add(new EvImpl(term.head()));
      term = term.tail();
    }
    return evList;
  }

  /**
   * get candidates list
   *
   * @return a <code>List</code> value
   */
  public final List<Ev> getCandidateList() throws Exception  {
    List<Ev> evList = new ArrayList<Ev>();
    /* In order to maintain consistency with MetaMap's human-readable
     * output, the
     * <pre>
     * candidates(<ListOfCandidates>)
     * </pre>
     * term now includes the counts of total, excluded, pruned, and
     * remaining candidates: i.e.,
     * <pre>
     * candidates(TotalCandidatesCount, ExcludedCandidateCount,
     *            PrunedCandidateCount, RemainingCandidates,
     *            <ListOfCandidates>)
     * </pre>
     */
    PBTerm prologList = this.candidatesTerm.getArgument(5);
    PBTerm term = prologList;
    for (int i = 1; i <= prologList.length(); i++) {
      evList.add(new EvImpl(term.head()));
      term = term.tail();
    }
    return evList;
  }

  /**
   * Get list of {@link Mapping} instances associated with {@link Phrase}
   *
   * @return a <code>List</code> value
   * @exception Exception if an error occurs
   * @deprecated
   */
  public final List<Map> getMappings() throws Exception {
    List<Map> mapList = new ArrayList<Map>();
    PBTerm prologList = this.mappingsTerm.getArgument(1);
    PBTerm term = prologList;
    for (int i = 1; i <= prologList.length(); i++) {
      mapList.add(new MappingImpl(term.head()));
      term = term.tail();
    }
    return mapList;
  }

  

  /**
   * Return the Mapping list associated with the Phrase. 
   *
   * @return a <code>List</code> value
   * @exception Exception if an error occurs
   */
  public final List<Mapping> getMappingList() throws Exception {
    List<Mapping> mapList = new ArrayList<Mapping>();
    PBTerm prologList = this.mappingsTerm.getArgument(1);
    PBTerm term = prologList;
    for (int i = 1; i <= prologList.length(); i++) {
      mapList.add(new MappingImpl(term.head()));
      term = term.tail();
    }
    return mapList;
  }

  class MatchMapImpl implements MatchMap {
    // The match map is represented in prolog result as a set of nested lists:
    // organization:
    //      [[[phrase-match-start, phrase-match-end],[concept-match-start,concept-match-end], lexical-variation]]
    //  Example 1.: This mapping shows word 1 of the phrase maps to word 1 of the concept with 0 lexical variation
    //
    // [[[1,1],[1,1],0]]
    //    ^^^ Match up of words in TEXT
    //           ^^^ Match up of words in STRING
    //                 ^ Variation
    //
    //
    // Example 2.: This shows word 2 of the phrase maps to word 1 of the concept with 0 lexical variation and word 3
    //              of the text maps to word 2 of the concept with 0 lexical variation.
    //
    // [[2,2],[1,1],0],[[3,3],[2,2],0]

    // this class is actually the sublist of the match map of the form [[pms,pme],[cms,cme],lv]

    protected PBTerm prologList;

    public PBTerm getPrologList() {
      return this.prologList;
    }
    
    public MatchMapImpl(PBTerm prologList) {
      this.prologList = prologList;
    }

    /** The position within the phrase words of the first matching word
     * @return word position */
    public int getPhraseMatchStart() {
      PBTerm matchPBList = TermUtils.getListElement(prologList, 1);
      return (int)TermUtils.getListElement(matchPBList, 1).intValue();
    }
    /** The position within the phrase words of the last matching word
     * @return word position */
    public int getPhraseMatchEnd() {
      PBTerm matchPBList = TermUtils.getListElement(prologList, 1);
      return (int)TermUtils.getListElement(matchPBList, 2).intValue();
    }
    /** The position within the concept words of the first matching word
     * @return word position */
    public int getConceptMatchStart() {
      PBTerm matchPBList = TermUtils.getListElement(prologList, 2);
      return (int)TermUtils.getListElement(matchPBList, 1).intValue();
    }
    /** The position within the concept words of the last matching word
     * @return word position
     */
    public int getConceptMatchEnd() {
      PBTerm matchPBList = TermUtils.getListElement(prologList, 2);
      return (int)TermUtils.getListElement(matchPBList, 2).intValue();
    }
    /** The degree of lexical variation between the words in the
     * candidate concept and the words in the phrase; the computation
     * of this value is explained on pp. 2-3 of MetaMap Evaluation.
     * @return lexical variation of match
     */
    public int getLexMatchVariation() {
      if (this.prologList.length() > 2) {
	if (TermUtils.getListElement(prologList, 3).isInteger())
	  return (int)TermUtils.getListElement(prologList, 3).intValue();
	else 
	  return 0;
      } else {
	return 0;
      }
    }

    /** 
     * This returns a list of representation of match map.
     * @return List of Objects.
     */
    public List<Object> getListRepr() throws Exception {
      return TermUtils.getMatchMapTree(this.prologList);
    }

    public String toString() {
      return "[[phrase start: " + this.getPhraseMatchStart() +
	", phrase end: " + this.getPhraseMatchEnd() +
	"], [concept start: " + this.getPhraseMatchStart() +
	", concept end: " + this.getPhraseMatchEnd() +
	"], lexical variation: " + this.getLexMatchVariation() + "]";
    }
  }

  class EvImpl implements Ev {
    PBTerm evTerm;
    public EvImpl(PBTerm newEvTerm) throws Exception {
      if (newEvTerm.isCompound())
	this.evTerm = newEvTerm;
      else
	throw new Exception("supplied term is not a compound term.");
    }
    public int getScore() throws Exception { 
      return (int)TermUtils.getIntegerArgument(this.evTerm, 1);
    }
    public String getConceptId() throws Exception {
      return TermUtils.getAtomArgument(this.evTerm, 2);
    }
    public String getConceptName() throws Exception  {
      return TermUtils.getAtomArgument(this.evTerm, 3);
    }
    public String getPreferredName() throws Exception  { 
      return TermUtils.getAtomArgument(this.evTerm, 4);
    }
    public List<String> getMatchedWords() throws Exception  { 
      return TermUtils.getAtomStringListArgument(this.evTerm, 5);
    }
    public List<String> getSemanticTypes() throws Exception  {
      return TermUtils.getAtomStringListArgument(this.evTerm, 6);
    }

  /** 
   * This returns a recursive list of objects where the elements can
   * be Integer classes or List classes containing Lists or Integers.
   * @return List of Objects.
   */
    public List<Object> getMatchMap() throws Exception {
      PBTerm prologList = this.evTerm.getArgument(7);
      return TermUtils.getMatchMapTree(prologList);
    }

    /** 
     * This returns a list of MatchMap objects
     * @return List of MatchMap.
     */
    public List<MatchMap> getMatchMapList() throws Exception {
      PBTerm prologList = this.evTerm.getArgument(7);
      List<MatchMap> matchMapList = new ArrayList<MatchMap>();
      PBTerm term = prologList;
      for (int i = 1; i <= prologList.length(); i++) {
	if (term.head().isListCell()) {
	  matchMapList.add(new MatchMapImpl(term.head()));
	}
	term = term.tail();
      }
      return matchMapList;
    }

    public boolean isHead() throws Exception  { 
      return TermUtils.getAtomArgument(this.evTerm, 10).equals("yes");
    }
    public boolean isOvermatch() throws Exception  {       
      return TermUtils.getAtomArgument(this.evTerm, 11).equals("yes");
    }
    public List<String> getSources() throws Exception  {
      return TermUtils.getAtomStringListArgument(this.evTerm, 12);
    }
    public List<Position> getPositionalInfo() throws Exception  { 
      return TermUtils.getPositionListArgument(this.evTerm, 13);
    }
    public int getPruningStatus() throws Exception  { 
      return (int)TermUtils.getIntegerArgument(this.evTerm, 14);
    }
    public int getNegationStatus() throws Exception  { 
      return (int)TermUtils.getIntegerArgument(this.evTerm, 15);
    }
    public boolean isNegated() throws Exception  { 
      return ((int)TermUtils.getIntegerArgument(this.evTerm, 15)) == 1;
    }


    /** get underlying Prolog Term */
    public PBTerm getTerm() {
      return this.evTerm;
    }
    public String toString() {
      try {
      return this.getScore() + ","
	+ this.getConceptId() + ","
	+ this.getConceptName() + ","
	+ this.getPreferredName() + ","
	+ this.getMatchedWords() + ","
	+ this.getSemanticTypes() 
	// + this.getMatchMap() + ","
	+ ", isHead: " + this.isHead()
	+ ", isOverMatch: " + this.isOvermatch()
	+ "," + this.getSources()
	+ "," + this.getPositionalInfo() 
	+ ", pruning status: " + this.getPruningStatus() + ".";
      } catch (Exception e) {
	throw new RuntimeException(e);
      }
    }
  }

  class CandidatesImpl implements Candidates {
    PBTerm candidatesTerm;
    public CandidatesImpl(PBTerm newCandidatesTerm) throws Exception {
      if (newCandidatesTerm.isCompound())
	this.candidatesTerm = newCandidatesTerm;
      else
	throw new Exception("supplied term is not a compound term.");
    }
    public int getTotalCandidatesCount() { return (int)this.candidatesTerm.getArgument(1).intValue(); }
    public int getExcludedCandidateCount() { return (int)this.candidatesTerm.getArgument(2).intValue(); }
    public int getPrunedCandidateCount() { return (int)this.candidatesTerm.getArgument(3).intValue(); }
    public int getRemainingCandidates() { return (int)this.candidatesTerm.getArgument(4).intValue(); }
    public List<Ev> getEvList() throws Exception {
      List<Ev> evList = new ArrayList<Ev>();
      PBTerm prologList = this.candidatesTerm.getArgument(5);
      PBTerm term = prologList;
      for (int i = 1; i <= prologList.length(); i++) {
	evList.add(new EvImpl(term.head()));
	term = term.tail();
      }
      return evList;
    }
  }

  class MappingImpl implements Map, Mapping {
    PBTerm mapTerm;
    public MappingImpl(PBTerm newMapTerm) throws Exception {
      if (newMapTerm.isCompound())
	this.mapTerm = newMapTerm;
      else
	throw new Exception("supplied term is not a compound term.");
    }
    public int getScore() throws Exception { 
      return (int)TermUtils.getIntegerArgument(this.mapTerm, 1);
    }
    public List<Ev> getEvList() 
      throws Exception 
    {
      List<Ev> evList = new ArrayList<Ev>();
      PBTerm prologList = this.mapTerm.getArgument(2);
      PBTerm term = prologList;
      for (int i = 1; i <= prologList.length(); i++) {
	evList.add(new EvImpl(term.head()));
	term = term.tail();
      }
      return evList;
    }
  }
}

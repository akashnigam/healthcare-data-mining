package examples;

import se.sics.prologbeans.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

import java.lang.Exception;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.Result;
import gov.nih.nlm.nls.metamap.Mapping;
import gov.nih.nlm.nls.metamap.Ev;
import gov.nih.nlm.nls.metamap.Map;
import gov.nih.nlm.nls.metamap.Mapping;
import gov.nih.nlm.nls.metamap.MatchMap;
import gov.nih.nlm.nls.metamap.Position;
import gov.nih.nlm.nls.metamap.TermUtils;

public class DirectMMO {
  /** MetaMap api instance */
  MetaMapApi api;
  long rrTime = 0;
  long eTime = 0;
  long mapCount = 0;
  long evCount = 0;

  /**
   * This example bypasses translation of the Prolog terms of some
   * parts of the MetaMap Machine Output in an attempt improve
   * performance of client.  In this case, Acronyms and Abbreviations
   * and Negation terms are skipped.
   *
   */
  public DirectMMO() {
    this.api = new MetaMapApiImpl();
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

    public PBTerm prologList;
    
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

  List<Mapping> getMappingList(PBTerm mappingsTerm)
    throws Exception {
    List<Mapping> mapList = new ArrayList<Mapping>();
    PBTerm prologList = mappingsTerm.getArgument(1);
    PBTerm term = prologList;
    for (int i = 1; i <= prologList.length(); i++) {
      mapList.add(new MappingImpl(term.head()));
      term = term.tail();
    }
    return mapList;
  }

  Set<String> process(String text)
    throws Exception
  {
    Set<String> cuiSet = new HashSet<String>();
    api.setOptions("-y");
    // System.out.println("options: " + api.getOptions());
    if (text.trim().length() > 0) {
      long startTime = System.currentTimeMillis();
      List<Result> resultList = api.processCitationsFromString(text);
      long endTime = System.currentTimeMillis();
      rrTime = rrTime + (endTime - startTime);
      startTime = System.currentTimeMillis();
      for (Result result : resultList) {
	if (result != null) {
	  PBTerm mmoTermList = result.getMMOPBlist();
	  for (int i = 1; i < mmoTermList.length() +1; i++) {
	    PBTerm mmoElement = TermUtils.getListElement(mmoTermList, i);
	    if (mmoElement.getName().equals("mappings")) {
	      /* Extract map and ev terms from mappings term */
	      // System.out.println("mmoElement: " + mmoElement);
	      for (Mapping aMap : getMappingList( mmoElement )) {
		mapCount++;
		for (Ev mapEv : aMap.getEvList()) {
		  // System.out.println("getConceptId: " + mapEv.getConceptId());
		  cuiSet.add(mapEv.getConceptId());
		  evCount++;
		}
	      }
	    }
	  }
	}
      }
      endTime = System.currentTimeMillis();
      eTime = eTime + (endTime - startTime);
    }
    return cuiSet;
  }

  public static void main(String[] args) 
    throws Exception {
    if (args.length > 0) {
      Set<String> cuiSet = new HashSet<String>();
      DirectMMO ceInstance = new DirectMMO();
      BufferedReader br = new BufferedReader(new FileReader(args[0]));
      String line;
      while ((line = br.readLine()) != null) {
	cuiSet.addAll(ceInstance.process(line));
      }
      br.close();
      for (String cui: cuiSet) {
	System.out.println(cui);
      }
      System.err.println("MetaMap server request/response time: " + ceInstance.rrTime + " milliseconds");
      System.err.println("Extracting response time: " + ceInstance.eTime + " milliseconds");
      System.err.println("Number of Mapping terms: " + ceInstance.mapCount );
      System.err.println("Number of Mapping Evaluation terms: " + ceInstance.evCount );
      System.err.println("Number of Unique Concepts: " + cuiSet.size());
    } else {
      System.err.println("usage: example.DirectMMO inputfile");
    }
  }
}

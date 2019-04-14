package gov.nih.nlm.nls.metamap;

/**
 * Implementation of Result interface.
 *
 *<p>
 * Created: Wed May 20 14:52:47 2009
 * 
 * @author <a href="mailto:wrogers@nlm.nih.gov">Willie Rogers</a>
 * @version 1.0
 */

import java.util.List;
import java.util.ArrayList;
import java.io.PrintStream;
import se.sics.prologbeans.PBTerm;
import gov.nih.nlm.nls.metamap.TermUtils;

/**
 * An implemention of the <code>Result</code> interface, a container
 * for the result of a MetaMap query.
 *<p>
 * Created: Wed May 20 13:50:35 2009
 *
 * @author <a href="mailto:wrogers@nlm.nih.gov">Willie Rogers</a>
 * @version 1.0
 */
public class ResultImpl implements Result {

  private PBTerm mmoTermList;
  // private static final int ARGS_INDEX             = 1;
  private static final int ACRONYMS_ABBREVS_INDEX = 2;
  private static final int NEGATIONLIST_INDEX     = 3;

  private static final int FIRST_UTTERANCE_INDEX  = 4;
  // private static final int PHRASE_INDEX           = 5;
  // private static final int CANDIDATES_INDEX       = 6;
  // private static final int MAPPINGS_INDEX         = 7;
  public String inputText = "";

  /**
   * Creates a new <code>ResultImpl</code> instance.
   *
   */
  public ResultImpl() {

  }

  /**
   * Creates a new <code>ResultImpl</code> instance.
   * @param mmoTerm a prolog term containing MetaMap machine output.
   */
  public ResultImpl(PBTerm mmoTerm) throws Exception {
    if (mmoTerm.isListCell()) {
      this.mmoTermList = mmoTerm;
    } else {
      throw new Exception("resulting term is not a legal machine output termlist");
    }
  }

  /**
   * Creates a new <code>ResultImpl</code> instance.
   * @param mmoTerm a prolog term containing MetaMap machine output.
   * @param theInputText the input text that produced this result.
   */
  public ResultImpl(PBTerm mmoTerm, String theInputText) throws Exception {
    this.inputText = theInputText;
    if (mmoTerm.isListCell()) {
      this.mmoTermList = mmoTerm;
    } else {
      throw new Exception("resulting term is not a legal machine output termlist");
    }
  }

  // Implementation of gov.nih.nlm.nls.metamap.Result

  /** @param theInputText the input text that produced this result.*/
  public void setInputText(String theInputText) {
    this.inputText = theInputText;
  }
  /** @return theInputText the input text that produced this result. */
  public String getInputText() {
    return this.inputText;
  }

  /** @return prolog beans list of machine output terms */
  public PBTerm getMMOPBlist()
  {
    return this.mmoTermList;
  }

  public void traverse(PrintStream out)
  {
    PBTerm term = this.mmoTermList;
    for (int i = 1; i<this.mmoTermList.length(); i++) {
      out.println(term.head());
      term = term.tail();
    }
  }

  /** @return a <code>String</code> containing the result as MetaMap
   * machine output. */
  public String getMachineOutput()
  {
    StringBuffer sb = new StringBuffer();
    PBTerm term = this.mmoTermList;
    for (int i = 1; i <= this.mmoTermList.length(); i++) {
      PBTerm listTerm = term.head();
      sb.append(listTerm.toString()).append("\n");
      term = term.tail();
    }
    return sb.toString();
  }

  /**
   * Describe <code>getAcronymsAbbrevs</code> method here.
   *
   * @return a <code>List</code> of 
   */
  public final List<AcronymsAbbrevs> getAcronymsAbbrevs()  throws Exception {
    List<AcronymsAbbrevs> aasList = new ArrayList<AcronymsAbbrevs>();
    PBTerm listTerm = TermUtils.getListElement(mmoTermList, ACRONYMS_ABBREVS_INDEX);
    PBTerm prologList = listTerm.getArgument(1);
    PBTerm term = prologList;
    for (int i = 1; i <= prologList.length(); i++) {
      aasList.add(new AcronymsAbbrevsImpl(term.head()));
      term = term.tail();
    }
    return aasList;
  }

  /**
   * Describe <code>getAcronymsAbbrevsList</code> method here.
   *
   * @return a <code>List</code> of 
   */
  public final List<AcronymsAbbrevs> getAcronymsAbbrevsList()  throws Exception {
    List<AcronymsAbbrevs> aasList = new ArrayList<AcronymsAbbrevs>();
    PBTerm listTerm = TermUtils.getListElement(mmoTermList, ACRONYMS_ABBREVS_INDEX);
    PBTerm prologList = listTerm.getArgument(1);
    PBTerm term = prologList;
    for (int i = 1; i <= prologList.length(); i++) {
      aasList.add(new AcronymsAbbrevsImpl(term.head()));
      term = term.tail();
    }
    return aasList;
  }

  /**
   * Describe <code>getNegations</code> method here.
   *
   * @return a <code>List</code> of Negation Instances.
   */
  public final List<Negation> getNegations() throws Exception {
    List<Negation> negList = new ArrayList<Negation>();
    PBTerm listTerm = TermUtils.getListElement(mmoTermList, NEGATIONLIST_INDEX);
    PBTerm prologList = listTerm.getArgument(1);
    PBTerm term = prologList;
    for (int i = 1; i <= prologList.length(); i++) {
      negList.add(new NegationImpl(term.head()));
      term = term.tail();
    }
    return negList;
  }

  /**
   * Describe <code>getNegationList</code> method here.
   *
   * @return a <code>List</code> of Negation Instances.
   */
  public final List<Negation> getNegationList() throws Exception {
    List<Negation> negList = new ArrayList<Negation>();
    PBTerm listTerm = TermUtils.getListElement(mmoTermList, NEGATIONLIST_INDEX);
    PBTerm prologList = listTerm.getArgument(1);
    PBTerm term = prologList;
    for (int i = 1; i <= prologList.length(); i++) {
      negList.add(new NegationImpl(term.head()));
      term = term.tail();
    }
    return negList;
  }

  /**
   * Describe <code>getUtterances</code> method here.
   *
   * @return a list of <code>Utterance</code> terms 
   */
  public List<Utterance> getUtteranceList() throws Exception {
    List<Utterance> utteranceList = new ArrayList<Utterance>();
    for (int i = FIRST_UTTERANCE_INDEX; i<=this.mmoTermList.length(); i++) {
      if (TermUtils.getListElement(this.mmoTermList, i).getName().equals("utterance")) {
    	utteranceList.add(new UtteranceImpl(TermUtils.getListElement(this.mmoTermList, i), i, this));
      }
    }
    return utteranceList;
  }

  /** 
   * Return a list of <code>PCM</code> (Phrase/Candidate/Mapping)
   * objects starting at start in machine output.  It is recommended
   * to use the <code>Utterance</code> implementation of getPCMList
   * rather than this one.
   *
   * @param utterancePosition position of relevant utterance
   * @return list of Phrase/Candidates/Mappings (PCM) objects
   * @deprecated
   */
  private List<PCM> getPCMList(int utterancePosition) throws Exception {
    int start = utterancePosition + 1;
    List<PCM> pcmList = new ArrayList<PCM>();
    for (int i = start; i <= this.mmoTermList.length(); i=i+3) {
      if ( TermUtils.getListElement(this.mmoTermList, i).getName().equals("phrase") ) {
	PBTerm phrase = TermUtils.getListElement(this.mmoTermList, i);
	PBTerm candidates = null;
	PBTerm mappings = null;
	int j=i+1;
	if ( TermUtils.getListElement(this.mmoTermList, j).getName().equals("candidates") ) {
	  candidates = TermUtils.getListElement(this.mmoTermList, j);
	} else {
	  TermUtils.getListElement(this.mmoTermList, j).getName().equals("utterance");
	  break;
	}
	j++;
	if ( TermUtils.getListElement(this.mmoTermList, j).getName().equals("mappings") ) {
	  mappings = TermUtils.getListElement(this.mmoTermList, j);
	}
	pcmList.add(new PCMBase(phrase, candidates, mappings));
      } else {
	TermUtils.getListElement(this.mmoTermList, i).getName().equals("utterance");
	break;
      }
    }
    return pcmList;
  }

  /**
   * Implementation of toString method for Result.
   */
  public String toString() 
  {
    StringBuffer sb = new StringBuffer();
    try {
      sb.append(this.getAcronymsAbbrevsList());
      sb.append(this.getNegationList());
      sb.append(this.getUtteranceList());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return sb.toString();
  }

  /**	  
   * <pre>
   * aas(["ABC"*"American Broadcasting System" *[1,3,5,28]*[],
   *      "CBS"*"Columbia Broadcasting System" *[1,3,5,28]*[]]).
   * </pre>
   * now:
   * <pre>
   * aas(["ABC"*"American Broadcasting System" *(1,3,5,28)*[],
   *      "CBS"*"Columbia Broadcasting System" *(1,3,5,28)*[]]).
   * </pre>
   */
  class AcronymsAbbrevsImpl implements AcronymsAbbrevs {
    PBTerm aasTerm;

    public AcronymsAbbrevsImpl(PBTerm newAATerm) throws Exception {
      if (newAATerm.isCompound())
	aasTerm = newAATerm;
      else
	throw new Exception("supplied term is not a compound term.");
    }
    public String getAcronym() {
      return aasTerm.getArgument(1).getArgument(1).getArgument(1).toString();
    }
    public String getExpansion() {
      return aasTerm.getArgument(1).getArgument(1).getArgument(2).toString();
    };
    public List<Integer> getCountList() {
      try {
	List<Integer> countList = new ArrayList<Integer>();
	PBTerm prologlist = aasTerm.getArgument(1).getArgument(2);
	if (prologlist.isCompound()) {
	  PBTerm cterm = prologlist;
	  int i = 0;
	  while (i <= prologlist.getArity()) {
	    if (cterm.getArgument(1).isInteger()) {
	      countList.add((int)TermUtils.getIntegerArgument(cterm,1));
	      i++;
	      if (cterm.getArgument(2).isCompound()) {
		cterm = cterm.getArgument(2);
	      } else {
		countList.add((int)TermUtils.getIntegerArgument(cterm,2));
	      }
	    }
	  }
	} else {
	  PBTerm term = prologlist;
	  for (int i = 1; i <= prologlist.length(); i++) {
	    countList.add(new Integer((int)term.head().intValue()));
	    term = term.tail();
	  }
	}
	return countList;
      } catch (Exception e) {
	throw new RuntimeException(e);
      }
    };
    public List<String> getCUIList() {
      List<String> cuiList = new ArrayList<String>();
      /* get the second argument which contains the prolog list
       * containing the cuis */
      PBTerm prologlist = aasTerm.getArgument(2);
      PBTerm term = prologlist;
      for (int i = 1; i <= prologlist.length(); i++) {
	cuiList.add(term.head().toString());
	term = term.tail();
      }
      return cuiList;
    };
    public String toString()
    {
      StringBuffer sb = new StringBuffer();
      sb.append("Acronym: ").append(this.getAcronym());
      sb.append(", Expansion: ").append(this.getExpansion());
      sb.append(", Count list: ").append(this.getCountList());
      sb.append(", CUI list: ").append(this.getCUIList());
      return sb.toString();
    }
  }

  class NegationImpl implements Negation {

    class ConceptPairImpl implements ConceptPair
    {
      String conceptId;
      String preferredName;
      ConceptPairImpl(String theConceptId, String thePreferredName)
      {
	this.conceptId = theConceptId;
	this.preferredName = thePreferredName;
      }
      public String getConceptId() { return conceptId; }
      public String getPreferredName() { return preferredName; }
      public String toString() {
	return "(" + conceptId + "," + preferredName + ")";
      }
    }

    PBTerm negTerm;
    public NegationImpl(PBTerm newNegTerm) throws Exception {
      if (newNegTerm.isCompound())
	negTerm = newNegTerm;
      else
	throw new Exception("supplied term is not a compound term.");
    }
    public String getType() throws Exception { 
      return TermUtils.getAtomArgument(this.negTerm, 1);
    }
    public String getTrigger() throws Exception { 
      return TermUtils.getAtomArgument(this.negTerm, 2);
    } 
    public List<Position> getTriggerPositionList() throws Exception { 
      return TermUtils.getPositionListArgument(this.negTerm, 3);      
    }
    public List<ConceptPair> getConceptPairList() throws Exception {
      List<ConceptPair> pairList = new ArrayList<ConceptPair>();
      PBTerm plist = this.negTerm.getArgument(4);
      PBTerm pTerm = plist;
      for (int i = 1; i <= plist.length(); i++) {
      	PBTerm term = pTerm.head();
      	pairList.add
      	  (new ConceptPairImpl(term.getArgument(1).toString(),
      			       term.getArgument(2).toString()));
	pTerm = pTerm.tail();
      }
      return pairList;
    }
    public List<Position> getConceptPositionList() throws Exception {
      return TermUtils.getPositionListArgument(this.negTerm, 5);      
    }
    /** 
     * @return always returns null
     * @deprecated
     */
    @Deprecated
    public String getConceptId() throws Exception {
       return null;		// not implemented
    }
  }

  class UtteranceImpl implements Utterance {
    PBTerm utteranceTerm;
    int mmoListPosition;
    private ResultImpl result;

    /**
     * @param newUtteranceTerm PrologBeans term representation of utterance term.
     * @param listPosition position of term in machine output term list.
     * @param theResult the MetaMap result.
     */
    public UtteranceImpl(PBTerm newUtteranceTerm,
			 int listPosition,
			 ResultImpl theResult)
      throws Exception
    {
      if (newUtteranceTerm.isCompound()) {
	this.utteranceTerm = newUtteranceTerm;
	this.mmoListPosition = listPosition;
	this.result = theResult;
      } else {
	throw new Exception("supplied pterm is not a compound term.");
      }
    }
    public String getId() throws Exception { 
      return TermUtils.getAtomArgument(this.utteranceTerm, 1);
    }
    public String getString() throws Exception {
      return TermUtils.getStringArgument(this.utteranceTerm, 2);
    }
    public Position getPosition() throws Exception { 
      return TermUtils.getPositionArgument(this.utteranceTerm, 3);
    }

    /**
     * Return a list of <code>PCM</code> (Phrase/Candidate/Mapping)
     * objects associated with the utterance.
     * @return a list of <code>PCM</code> (Phrase/Candidate/Mapping) objects.
     */
    public List<PCM> getPCMList() throws Exception {
      return this.result.getPCMList(this.mmoListPosition);
    }
    public int getMMOPosition() throws Exception {
      return this.mmoListPosition;
    }
    public String toString() {
      try {
	return "utterance: " + this.getId() + "," + this.getString() + "," + this.getPosition();
      } catch (Exception e) {
	e.printStackTrace();	// TODO: should use RunTimeException
      }
      return "n/a";
    }
  }
}

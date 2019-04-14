
//
package examples;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;

import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.Result;
import gov.nih.nlm.nls.metamap.Mapping;
import gov.nih.nlm.nls.metamap.Ev;
import gov.nih.nlm.nls.metamap.Mapping;
import gov.nih.nlm.nls.metamap.Utterance;
import gov.nih.nlm.nls.metamap.PCM;
import gov.nih.nlm.nls.metamap.Phrase;
import gov.nih.nlm.nls.metamap.MatchMap;
import gov.nih.nlm.nls.metamap.Position;
import gov.nih.nlm.nls.metamap.TermUtils;

import se.sics.prologbeans.*;

/**
 * A crude example of accessing the MincoMan term using the modified
 * Phrase and PhraseImpl classes.
 */

public class MincoManExample {
  
  public List<PBTerm> listArgs(PBTerm compoundTerm) {
    List<PBTerm> elements = new ArrayList<PBTerm>();
    for (int i = 1; i <= compoundTerm.getArity(); i++) {
      elements.add(compoundTerm.getArgument(i));
    }
    return elements;
  }

  public List<PBTerm> listElements(PBTerm listTerm) {
    List<PBTerm> elements = new ArrayList<PBTerm>();
    for (int i = 1; i <= listTerm.length(); i++) {
      elements.add(TermUtils.getListElement(listTerm, i));
    }
    return elements;
  }

  public List<String> listAtomTermses(PBTerm mincoManTerm) {
    List<String> atomTermsList = new ArrayList<String>();
    if (mincoManTerm.isListCell()) {
      for (PBTerm elem: listElements(mincoManTerm)) {
	List<String> subMatches = listAtomTermses(elem);
	atomTermsList.addAll(subMatches);
      }
    } else if (mincoManTerm.isCompound()) {
      atomTermsList.add("arg:" + mincoManTerm.getName());
      for (PBTerm elem: listArgs(mincoManTerm)) {
	List<String> subMatches = listAtomTermses(elem);
	atomTermsList.addAll(subMatches);
      }
    } else if (mincoManTerm.isAtom()) {
      atomTermsList = new ArrayList<String>();
      atomTermsList.add(mincoManTerm.getName());
      return atomTermsList;
    }
    return atomTermsList;
  }

  public List<String> listInputMatches(PBTerm mincoManTerm) {
    List<String> termlist = new ArrayList<String>();
    List<String> atomTermsList = listAtomTermses(mincoManTerm);
    Iterator<String> iter = atomTermsList.iterator();
    while (iter.hasNext()) {
      if (iter.next().equals("arg:inputmatch")) {
	termlist.add(iter.next());
      }
    }
    return termlist;
  }

  /**
   *
   * @param args - Arguments passed from the command line
   **/
  public static void main(String[] args)
    throws Exception {
    MetaMapApi mmApiInstance = new MetaMapApiImpl();
    MincoManExample inst = new MincoManExample();
    List <Result> resultList =
      mmApiInstance.processCitationsFromString("There was no sign of pneumonia.");
    for (Result result: resultList) {
      for (Utterance utterance: result.getUtteranceList()) {
	for (PCM pcm: utterance.getPCMList()) {
	  PBTerm mincoManTerm = pcm.getPhrase().getMincoMan();
	  List<String> inputMatches = inst.listInputMatches(mincoManTerm);
	  for (String match: inputMatches) {
	    System.out.println(match);
	  }
	}
      }
    }
  }
}


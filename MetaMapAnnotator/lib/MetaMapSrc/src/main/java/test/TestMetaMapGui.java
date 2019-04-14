
//
package test;

import java.util.List;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import gov.nih.nlm.nls.metamap.MetaMapApi;
import gov.nih.nlm.nls.metamap.MetaMapApiImpl;
import gov.nih.nlm.nls.metamap.AcronymsAbbrevs;
import gov.nih.nlm.nls.metamap.ConceptPair;
import gov.nih.nlm.nls.metamap.Ev;
import gov.nih.nlm.nls.metamap.Mapping;
import gov.nih.nlm.nls.metamap.Negation;
import gov.nih.nlm.nls.metamap.PCM;
import gov.nih.nlm.nls.metamap.Position;
import gov.nih.nlm.nls.metamap.Result;
import gov.nih.nlm.nls.metamap.Utterance;


/**
 *
 */

public class TestMetaMapGui implements ActionListener {
  private JTextArea text = new JTextArea(20, 40);
  private JTextField input = new JTextField(36);
  private JButton evaluate = new JButton("Evaluate");
  MetaMapApi api = new MetaMapApiImpl();

  public TestMetaMapGui() {
    JFrame frame = new JFrame("Prolog Evaluator");
    Container panel = frame.getContentPane();
    panel.add(new JScrollPane(text), BorderLayout.CENTER);
    JPanel inputPanel = new JPanel(new BorderLayout());
    inputPanel.add(input, BorderLayout.CENTER);
    inputPanel.add(evaluate, BorderLayout.EAST);
    panel.add(inputPanel, BorderLayout. SOUTH);
    text.setEditable(false);
    evaluate.addActionListener(this);
    input.addActionListener(this);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  public void actionPerformed(ActionEvent event) {
    try {
      List<Result> resultList = this.api.processCitationsFromString(input.getText());
      if (resultList != null) {
	for (Result result: resultList) {
	  List<AcronymsAbbrevs> aaList = result.getAcronymsAbbrevs();
	  if (aaList.size() > 0) {
	    text.append("\nAcronyms and Abbreviations:");
	    for (AcronymsAbbrevs e: aaList) {
	      text.append("\nAcronym: " + e.getAcronym());
	      text.append("\nExpansion: " + e.getExpansion());
	      text.append("\nCount list: " + e.getCountList());
	      text.append("\nCUI list: " + e.getCUIList());
	    }
	  } else {
	    text.append("\n None.");
	  }
	  for (Utterance utterance: result.getUtteranceList()) {
	    text.append("\nUtterance:");
	    text.append("\n Id: " + utterance.getId());
	    text.append("\n Utterance text: " + utterance.getString());
	    text.append("\n Position: " + utterance.getPosition());
	    for (PCM pcm: utterance.getPCMList()) {
	      text.append("\nPhrase:");
	      text.append("\n text: " + pcm.getPhrase().getPhraseText());
	      text.append("\nCandidates:");
	      for (Ev ev: pcm.getCandidateList()) {
		text.append("\n Candidate:");
		text.append("\n  Score: " + ev.getScore());
		text.append("\n  Concept Id: " + ev.getConceptId());
		text.append("\n  Concept Name: " + ev.getConceptName());
		text.append("\n  Preferred Name: " + ev.getPreferredName());
		text.append("\n  Matched Words: " + ev.getMatchedWords());
		text.append("\n  Semantic Types: " + ev.getSemanticTypes());
		text.append("\n  MatchMap: " + ev.getMatchMap());
		text.append("\n  MatchMap alt. repr.: " + ev.getMatchMapList());
		text.append("\n  is Head?: " + ev.isHead());
		text.append("\n  is Overmatch?: " + ev.isOvermatch());
		text.append("\n  Sources: " + ev.getSources());
		text.append("\n  Positional Info: " + ev.getPositionalInfo());
	      }
	      text.append("\nMappings:");
	      for (Mapping map: pcm.getMappingList()) {
		text.append("\n Map Score: " + map.getScore());
		for (Ev mapEv: map.getEvList()) {
		  text.append("\n   Score: " + mapEv.getScore());
		  text.append("\n   Concept Id: " + mapEv.getConceptId());
		  text.append("\n   Concept Name: " + mapEv.getConceptName());
		  text.append("\n   Preferred Name: " + mapEv.getPreferredName());
		  text.append("\n   Matched Words: " + mapEv.getMatchedWords());
		  text.append("\n   Semantic Types: " + mapEv.getSemanticTypes());
		  text.append("\n   MatchMap: " + mapEv.getMatchMap());
		  text.append("\n   MatchMap alt. repr.: " + mapEv.getMatchMapList());
		  text.append("\n   is Head?: " + mapEv.isHead());
		  text.append("\n   is Overmatch?: " + mapEv.isOvermatch());
		  text.append("\n   Sources: " + mapEv.getSources());
		  text.append("\n   Positional Info: " + mapEv.getPositionalInfo());
		}
	      }
	    }
	  }
	}
      } else {
	text.append("\nNo result");
      }
    } catch (Exception e) {
      text.append("\nError when querying Prolog Server: " +
                  e.getMessage() + '\n');
    }
  }

  public static void main(String[] args) {
    new TestMetaMapGui();
  } 
}

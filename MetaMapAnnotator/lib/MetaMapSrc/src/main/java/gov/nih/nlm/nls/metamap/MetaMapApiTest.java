package gov.nih.nlm.nls.metamap;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import se.sics.prologbeans.PrologSession;

/**
 * MetaMapApiTest: An implementation of MetaMapApi Test Class.
 * 
 * <p>
 * Created: Wed May 20 15:54:08 2009
 *
 * @author <a href="mailto:wrogers@nlm.nih.gov">Willie Rogers</a>
 * @version 1.0
 */
public class MetaMapApiTest {
  /** MetaMap api instance */
  MetaMapApi api;

  /**
   * Creates a new <code>MetaMapApiTest</code> instance.
   *
   */
  public MetaMapApiTest() {
    this.api = new MetaMapApiImpl();
  }

  /**
   * Creates a new <code>MetaMapApiTest</code> instance using specified host and port.
   *
   * @param serverHostname hostname of MetaMap server.
   * @param serverPort     listening port used by MetaMap server.
   */
  public MetaMapApiTest(String serverHostname, int serverPort) {
    this.api = new MetaMapApiImpl();
    this.api.setHost(serverHostname);
    this.api.setPort(serverPort);
  }

  void setTimeout(int interval) {
    this.api.setTimeout(interval);
  }

  /**
   * Process terms using MetaMap API and display result to standard output.
   *
   * @param terms input terms
   * @param out output printer
   * @param serverOptions options to pass to metamap server before processing input text.
   */
  void process(String terms, PrintStream out, List<String> serverOptions) 
    throws Exception
  {
    if (serverOptions.size() > 0) {
      api.setOptions(serverOptions);
    }
    List<Result> resultList = api.processCitationsFromString(terms);
    for (Result result: resultList) {
      if (result != null) {
	out.println("input text: ");
	out.println(" " + result.getInputText());
	List<AcronymsAbbrevs> aaList = result.getAcronymsAbbrevsList();
	if (aaList.size() > 0) {
	  out.println("Acronyms and Abbreviations:");
	  for (AcronymsAbbrevs e: aaList) {
	    out.println("Acronym: " + e.getAcronym());
	    out.println("Expansion: " + e.getExpansion());
	    out.println("Count list: " + e.getCountList());
	    out.println("CUI list: " + e.getCUIList());
	  }
	}
	List<Negation> negList = result.getNegationList();
	if (negList.size() > 0) {
	  out.println("Negations:");
	  for (Negation e: negList) {
	    out.println("type: " + e.getType());
	    out.print("Trigger: " + e.getTrigger() + ": [");
	    for (Position pos: e.getTriggerPositionList()) {
	      out.print(pos  + ",");
	    }
	    out.println("]");
	    out.print("ConceptPairs: [");
	    for (ConceptPair pair: e.getConceptPairList()) {
	      out.print(pair + ",");
	    }
	    out.println("]");
	    out.print("ConceptPositionList: [");
	    for (Position pos: e.getConceptPositionList()) {
	      out.print(pos + ",");
	    }
	    out.println("]");
	  }
	}
	for (Utterance utterance: result.getUtteranceList()) {
	  out.println("Utterance:");
	  out.println(" Id: " + utterance.getId());
	  out.println(" Utterance text: " + utterance.getString());
	  out.println(" Position: " + utterance.getPosition());

	  for (PCM pcm: utterance.getPCMList()) {
	    out.println("Phrase:");
	    out.println(" text: " + pcm.getPhrase().getPhraseText());
	    out.println(" Minimal Commitment Parse: " + pcm.getPhrase().getMincoManAsString());
	    out.println("Candidates:");
	    
	    for (Ev ev: pcm.getCandidatesInstance().getEvList()) {
	      out.println(" Candidate:");
	      out.println("  Score: " + ev.getScore());
	      out.println("  Concept Id: " + ev.getConceptId());
	      out.println("  Concept Name: " + ev.getConceptName());
	      out.println("  Preferred Name: " + ev.getPreferredName());
	      out.println("  Matched Words: " + ev.getMatchedWords());
	      out.println("  Semantic Types: " + ev.getSemanticTypes());
	      out.println("  MatchMap: " + ev.getMatchMap());
	      out.println("  MatchMap alt. repr.: " + ev.getMatchMapList());
	      out.println("  is Head?: " + ev.isHead());
	      out.println("  is Overmatch?: " + ev.isOvermatch());
	      out.println("  Sources: " + ev.getSources());
	      out.println("  Positional Info: " + ev.getPositionalInfo());
	      out.println("  Pruning Status: " + ev.getPruningStatus());
	      out.println("  Negation Status: " + ev.getNegationStatus());
	    }

	    out.println("Mappings:");
	    for (Mapping map: pcm.getMappingList()) {
	      out.println(" Map Score: " + map.getScore());
	      for (Ev mapEv: map.getEvList()) {
		out.println("   Score: " + mapEv.getScore());
		out.println("   Concept Id: " + mapEv.getConceptId());
		out.println("   Concept Name: " + mapEv.getConceptName());
		out.println("   Preferred Name: " + mapEv.getPreferredName());
		out.println("   Matched Words: " + mapEv.getMatchedWords());
		out.println("   Semantic Types: " + mapEv.getSemanticTypes());
		out.println("   MatchMap: " + mapEv.getMatchMap());
		out.println("   MatchMap alt. repr.: " + mapEv.getMatchMapList());
		out.println("   is Head?: " + mapEv.isHead());
		out.println("   is Overmatch?: " + mapEv.isOvermatch());
		out.println("   Sources: " + mapEv.getSources());
		out.println("   Positional Info: " + mapEv.getPositionalInfo());
		out.println("   Pruning Status: " + mapEv.getPruningStatus());
		out.println("   Negation Status: " + mapEv.getNegationStatus());
	      }
	    }
	  }
	}
      } else {
	out.println("NULL result instance! ");
      }
    }
    this.api.resetOptions();
  }

  /** print information about server options */
  public static void printHelp() {
    System.out.println("usage: gov.nih.nlm.nls.metamap.MetaMapApiTest [options] terms|inputFilename");
    System.out.println("  allowed metamap options: ");
    System.out.println("    -C : use relaxed model ");
    System.out.println("    -A : use strict model ");
    System.out.println("    -d : no derivational variants");
    System.out.println("    -D : all derivational variants");
    System.out.println("    -a : allow Acronym/Abbreviation variants");
    System.out.println("    -K : ignore stop phrases.");
    System.out.println("    -I : allow Large N");
    System.out.println("    -r : threshold ");
    System.out.println("    -i : ignore word order");
    System.out.println("    -Y : prefer multiple concepts");
    System.out.println("    -b : compute/display all mappings");
    System.out.println("    -X : truncate candidates mapping");
    System.out.println("    -y : use WSD ");
    System.out.println("    -z : use term processing ");
    System.out.println("    -o : allow overmatches ");
    System.out.println("    -g : allow concept gaps");
    System.out.println("    -8 : dynamic variant generation");
    System.out.println("    -@ --WSD <hostname> : Which WSD server to use.");
    System.out.println("    -J --restrict_to_sts <semtypelist> : restrict to semantic types");
    System.out.println("    -Q --composite_phrases <integer>");
    System.out.println("    -R --restrict_to_sources <sourcelist> : restrict to sources");
    System.out.println("    -S --tagger <sourcelist> : Which tagger to use.");
    System.out.println("    -V --mm_data_version <name> : version of MetaMap data to use.");
    System.out.println("    -Z --mm_data_year <name> : year of MetaMap data to use.");
    System.out.println("    -k --exclude_sts <semtypelist> : exclude semantic types");
    System.out.println("    -e --exclude_sources <sourcelist> : exclude semantic types");
    System.out.println("    -r --threshold <integer> : Threshold for displaying candidates.");
    System.out.println("    --blanklines <integer> : The number of empty or whitespace-only");
    System.out.println("                             lines required to end a citation.");
    System.out.println("API options:");
    System.out.println("    --metamap_server_host <hostname> : use MetaMap server on specified host");
    System.out.println("    --metamap_server_port <port number> : use MetaMap server on specified host");
    System.out.println("    --metamap_server_timeout <interval> : wait for MetaMap server for specified interval.");
    System.out.println("                                          interval of 0 will wait indefinitely.");
    System.out.println("Program options:");
    System.out.println("    --input <filename> : get input from file.");
    System.out.println("    --output <filename> : send output to file.");
  }

  /** @param inFile File class referencing input file. */
  static String readInputFile(File inFile) 
    throws java.io.FileNotFoundException,
	   java.io.IOException
  {
    BufferedReader ib = new BufferedReader(new FileReader(inFile));
    StringBuffer inputBuf = new StringBuffer();
    String line = "";
    while ((line = ib.readLine()) != null) {
      inputBuf.append(line).append('\n');
    }
    ib.close();
    return inputBuf.toString();
  }

  public static void main(String[] args) 
    throws Exception
  {
    String serverhost = MetaMapApi.DEFAULT_SERVER_HOST; 
    int serverport = MetaMapApi.DEFAULT_SERVER_PORT; 	// default port
    int timeout = -1; // use default timeout
    String inFilename = null;
    InputStream input = System.in;
    PrintStream output = System.out;
    if (args.length < 1) {
      printHelp();
      System.exit(0);
    }
    StringBuffer termBuf = new StringBuffer();
    List<String> options = new ArrayList<String>();
    int i = 0; 
    while (i < args.length) {
      if (args[i].charAt(0) == '-') {
	if (args[i].equals("-h") || args[i].equals("--help") || args[i].equals("-?")) {
	  printHelp();
	  System.exit(0);
	} else if ( args[i].equals("-%") || args[i].equals("--XML") ) {
	  options.add(args[i]); i++;
	  options.add(args[i]);
	} else if ( args[i].equals("-@") || args[i].equals("--WSD") ) {
	  options.add(args[i]); i++;
	  options.add(args[i]);
	} else if ( args[i].equals("-J") || args[i].equals("--restrict_to_sts") )  {
	  options.add(args[i]); i++;
	  options.add(args[i]);
	} else if ( args[i].equals("-Q") || args[i].equals("--composite_phrases") ) {
	  options.add(args[i]); i++;
	  options.add(args[i]);
	} else if ( args[i].equals("-R") || args[i].equals("--restrict_to_sources") ) {
	  options.add(args[i]); i++;
	  options.add(args[i]);
	} else if ( args[i].equals("-S") || args[i].equals("--tagger") ) {
	  options.add(args[i]); i++;
	  options.add(args[i]);
	} else if ( args[i].equals("-V") || args[i].equals("--mm_data_version") ) {
	  options.add(args[i]); i++;
	  options.add(args[i]);
	} else if ( args[i].equals("-Z") || args[i].equals("--mm_data_year") ) {
	  options.add(args[i]); i++;
	  options.add(args[i]);
	} else if ( args[i].equals("-e") || args[i].equals("--exclude_sources") ) {
	  options.add(args[i]); i++;
	  options.add(args[i]);
	} else if ( args[i].equals("-k") || args[i].equals("--exclude_sts") )  {
	  options.add(args[i]); i++;
	  options.add(args[i]);
	} else if ( args[i].equals("-r") || args[i].equals("--threshold") ) {
	  options.add(args[i]); i++;
	  options.add(args[i]);
	} else if  ( args[i].equals("-i") || args[i].equals("--ignore_word_order") ) {
	  options.add(args[i]); 
	} else if ( args[i].equals("--prune") ) {
	  options.add(args[i]); i++;
	  options.add(args[i]);
	} else if ( args[i].equals("--blanklines") ) {	  
	  options.add(args[i]); i++;
	  options.add(args[i]);
	} else if ( args[i].equals("--UDA") ) {	  
	  options.add(args[i]); i++;
	  options.add(args[i]);
	} else if ( args[i].equals("--metamap_server_host") ) {
          i++;
          serverhost = args[i];
	} else if ( args[i].equals("--metamap_server_port") ) {
          i++;
          serverport = Integer.parseInt(args[i]);
        } else if (args[i].equals("--metamap_server_timeout") ) {
          i++;
          timeout = Integer.parseInt(args[i]);
	} else if (args[i].equals("--input") ) {
          i++;
	  inFilename = args[i];
	  System.out.println("output file: " + args[i]);
	} else if (args[i].equals("--output") ) {
          i++;
          output = new PrintStream(args[i]);
	  System.out.println("output file: " + args[i]);
	} else {
	  options.add(args[i]);
	}
      } else {
	termBuf.append(args[i]).append(" "); 
      }
      i++;
    }
    System.out.println("serverport: " + serverport);
    MetaMapApiTest frontEnd = new MetaMapApiTest(serverhost, serverport);
    System.out.println("options: " + options);
    System.out.println("terms: " + termBuf);
    if (timeout > -1) {
      frontEnd.setTimeout(timeout);
    }
    if (inFilename != null) {
      File inFile = new File(inFilename.trim()); 
      if (inFile.exists()) {
	System.out.println("input file: " + inFilename);
	frontEnd.process(readInputFile(inFile), output, options);
      } else {
	System.out.println("input file: " + inFilename + " does not exist!");
      }
    } else if (termBuf.length() > 0) {
      File inFile = new File(termBuf.toString().trim()); 
      if (inFile.exists()) {
	System.out.println("input file: " + termBuf);
	frontEnd.process(readInputFile(inFile), output, options);
      } else {
	frontEnd.process(termBuf.toString(), output, options);
      }
      frontEnd.api.disconnect();
    } else {
      printHelp();
      System.exit(0);
    }
  }
}

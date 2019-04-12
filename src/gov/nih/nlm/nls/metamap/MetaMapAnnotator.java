/*
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with
 * this program. If not, see <https://www.gnu.org/licenses/>.
 */
package gov.nih.nlm.nls.metamap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 * This class is the main class which reads the output of the web scrapper,
 * annotates the input and dumps the concepts, relationships identified into the
 * database.
 *
 * @author Sandeep Nadella
 */
public class MetaMapAnnotator {

    public static final String PATH_TO_SCRAPPER_CSV_FOLDER = "/Users/sanadell/Projects/SWM/healthcare-data-mining/scraper/data/";
    MetaMapApi api;
    
    /**
     * Creates new instance with the given mmserver config details
     *
     * @param serverhost the value of server host
     * @param serverport the value of server port
     */
    public MetaMapAnnotator(String serverhost, int serverport) {
        this.api = new MetaMapApiImpl();
        this.api.setHost(serverhost);
        this.api.setPort(serverport);
    }

    /**
     * Creates new instance of the MetaMap API
     *
     */
    public MetaMapAnnotator() {
        this.api = new MetaMapApiImpl();
    }

    /**
     * Entry point for the MetaMap Annotator program execution
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            readFromCSV();
        } catch (Exception ex) {
            Logger.getLogger(MetaMapAnnotator.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    /**
     * Set the timeout
     *
     * @param interval
     */
    void setTimeout(int interval) {
        this.api.setTimeout(interval);
    }

    /**
     * Reads from CSV file and triggers the MetaMap annotator for each post
     *
     * @throws FileNotFoundException
     * @throws IOException
     * @throws Exception
     */
    private static void readFromCSV() throws FileNotFoundException, IOException, Exception {
        File dir = new File(PATH_TO_SCRAPPER_CSV_FOLDER);

        File[] csvFilesList = dir.listFiles((File directory, String filename) -> filename.endsWith(".csv"));
        for (File file : csvFilesList) {
            Reader in = new FileReader(file);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                String disease = record.get(0);
                String postLink = record.get(1);
                String postHeading = record.get(2);
                String postContent = record.get(3);
                //clean off non ascii characters
                disease = stripNonASCII(disease);
                postHeading = stripNonASCII(postHeading);
                postContent = stripNonASCII(postContent);

                System.out.println("----------------------------------------------------");
                System.out.println(disease);
                System.out.println(postHeading);
                System.out.println(postContent);
                System.out.println("----------------------------------------------------");
                triggerMetaMap(disease, postHeading, postContent);
            }
        }
    }

    /**
     * Removes all non ASCII characters from the inputText, trims and returns
     * the given string.
     *
     * @param inputText
     *
     * @return
     */
    private static String stripNonASCII(String inputText) {
        inputText = inputText.replaceAll("[^\\x00-\\x7F]", "");
        inputText = inputText.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
        inputText = inputText.replaceAll("\\p{C}", "");
        inputText = inputText.trim();
        return inputText;
    }

    /**
     * Triggers the MetaMap API with the given text content. Throws an Exception if the MetaMap server is not running.
     * 
     * @param category
     * @param postHeading
     * @param postContent
     * @throws Exception 
     */
    private static void triggerMetaMap(String category, String postHeading, String postContent) throws Exception {
        String serverhost = MetaMapApi.DEFAULT_SERVER_HOST;
        int serverport = MetaMapApi.DEFAULT_SERVER_PORT;
        int timeout = -1;

        PrintStream output = System.out;
        MetaMapAnnotator frontEnd = new MetaMapAnnotator(serverhost, serverport);
        List<String> options = new ArrayList<>();
        options.add("--negex");
        options.add("-A");
        options.add("-I");
        options.add("-Y");
        options.add("-y");

        System.out.println("options: " + options);
        if (timeout > -1) {
            frontEnd.setTimeout(timeout);
        }
        frontEnd.process(postContent, output, options);
        frontEnd.api.disconnect();
    }

    /**
     * Triggers the MetaMap annotator for the given text content and prints the output to the given out print stream.
     * 
     * @param terms
     * @param out
     * @param serverOptions
     *
     * @throws Exception
     */
    public void process(String terms, PrintStream out, List<String> serverOptions) throws Exception {
        if (serverOptions.size() > 0) {
            api.setOptions(serverOptions);
        }
        List<Result> resultList = api.processCitationsFromString(terms);
        for (Result result : resultList) {
            if (result != null) {
                out.println("input text: ");
                out.println(" " + result.getInputText());
                List<AcronymsAbbrevs> aaList = result.getAcronymsAbbrevsList();
                if (aaList.size() > 0) {
                    out.println("Acronyms and Abbreviations:");
                    aaList.stream().map((e) -> {
                        out.println("Acronym: " + e.getAcronym());
                        return e;
                    }).map((e) -> {
                        out.println("Expansion: " + e.getExpansion());
                        return e;
                    }).map((e) -> {
                        out.println("Count list: " + e.getCountList());
                        return e;
                    }).forEachOrdered((e) -> {
                        out.println("CUI list: " + e.getCUIList());
                    });
                }
                List<Negation> negList = result.getNegationList();
                if (negList.size() > 0) {
                    out.println("Negations:");
                    for (Negation e : negList) {
                        out.println("type: " + e.getType());
                        out.print("Trigger: " + e.getTrigger() + ": [");
                        for (Position pos : e.getTriggerPositionList()) {
                            out.print(pos + ",");
                        }
                        out.println("]");
                        out.print("ConceptPairs: [");
                        for (ConceptPair pair : e.getConceptPairList()) {
                            out.print(pair + ",");
                        }
                        out.println("]");
                        out.print("ConceptPositionList: [");
                        for (Position pos : e.getConceptPositionList()) {
                            out.print(pos + ",");
                        }
                        out.println("]");
                    }
                }
                for (Utterance utterance : result.getUtteranceList()) {
                    out.println("Utterance:");
                    out.println(" Id: " + utterance.getId());
                    out.println(" Utterance text: " + utterance.getString());
                    out.println(" Position: " + utterance.getPosition());

                    for (PCM pcm : utterance.getPCMList()) {
                        out.println("Phrase:");
                        out.println(" text: " + pcm.getPhrase().getPhraseText());
                        out.println(" Minimal Commitment Parse: " + pcm.getPhrase().getMincoManAsString());
                        out.println("Candidates:");

                        for (Ev ev : pcm.getCandidatesInstance().getEvList()) {
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
                        for (Mapping map : pcm.getMappingList()) {
                            out.println(" Map Score: " + map.getScore());
                            for (Ev mapEv : map.getEvList()) {
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
}

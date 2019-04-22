/*
 * Copyright (C) 2019 Sandeep Nadella <vnadell1@asu.edu>.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package gov.nih.nlm.nls.metamap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections4.iterators.PeekingIterator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import se.sics.prologbeans.PBTerm;
import org.apache.commons.text.similarity.JaroWinklerDistance;

/**
 * This class is the main class which reads the output of the web scrapper,
 * annotates the input and dumps the concepts, relationships identified into the
 * database.
 *
 * @author Sandeep Nadella Email: vnadell1@asu.edu
 */
public class MetaMapAnnotator {

    private static Properties configProp = new Properties();

    // String Constants
    private static final String EMPTY_STRING = "";
    private static final String PATH_TO_RESOURCES = "./resources/";

    MetaMapApi api;
    private static List<String> ignoredWords = new ArrayList<String>();
    private static List<String> includePOSTags = new ArrayList<String>();

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
            init();
            processWebUserPostData();
        } catch (Exception ex) {
            Logger.getLogger(MetaMapAnnotator.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

    }

    /**
     * Load the initial setup and config data
     *
     * @throws IOException
     */
    private static void init() throws IOException {
        File configFile = new File(PATH_TO_RESOURCES + "MetaMapAnnotatorConfig.properties");
        FileInputStream configStream = new FileInputStream(configFile);
        configProp.load(configStream);
        populateIgnoredWordsList();
        populatePOSTags();
    }

    /**
     * Populate the words to be ignored from the CSV file into a list
     *
     */
    private static void populateIgnoredWordsList() {
        Reader in = null;
        try {
            File file = new File(PATH_TO_RESOURCES + configProp.getProperty("ignored_words_file_name"));
            in = new FileReader(file);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                ignoredWords.add(record.get(0));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MetaMapAnnotator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MetaMapAnnotator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(MetaMapAnnotator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Populate the POS tags to be included from the CSV file into a list
     *
     */
    private static void populatePOSTags() {
        Reader in = null;
        try {
            File file = new File(PATH_TO_RESOURCES + configProp.getProperty("include_pos_tags_file_name"));
            in = new FileReader(file);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                includePOSTags.add(record.get(0));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MetaMapAnnotator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MetaMapAnnotator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(MetaMapAnnotator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Set the timeout
     *
     * @param interval
     */
    private void setTimeout(int interval) {
        this.api.setTimeout(interval);
    }

    /**
     * Reads from CSV file and triggers the MetaMap annotator for each post
     *
     * @throws FileNotFoundException
     * @throws IOException
     * @throws Exception
     */
    private static void processWebUserPostData() throws FileNotFoundException, IOException, Exception {
        File dir = new File(configProp.getProperty("web_scraper_csv_folder"));
                String serverhost = MetaMapApi.DEFAULT_SERVER_HOST;
        int serverport = MetaMapApi.DEFAULT_SERVER_PORT;
        int timeout = -1;

        PrintStream output = System.out;
        MetaMapAnnotator frontEnd = new MetaMapAnnotator(serverhost, serverport);
        List<String> options = new ArrayList<>();
        options.add("-y");  // Use word sense disambiguation https://metamap.nlm.nih.gov/Docs/FAQ/WSD.pdf Adds overhead to processing
        options.add("--restrict_to_sts");   // Retain only Concepts with Specified Semantic Types. https://metamap.nlm.nih.gov/SemanticTypesAndGroups.shtml
        options.add("dsyn,sosy,topp,clnd,bpoc");
        options.add("--unique_acros_abbrs_only");   // Restricts the generation of acronym/abbreviation (AA) variants to those forms with unique expansions.
        options.add("--no_derivational_variants");  // Prevents the use of any derivational variation in the computation of word variants. This option exists because derivational variants can involve a significant change in meaning.
        options.add("--TAGGER_SERVER");
        options.add("localhost");
        //disable below option if slow
        options.add("--composite_phrases");
        options.add("4");

//        System.out.println("options: " + options);
        if (timeout > -1) {
            frontEnd.setTimeout(timeout);
        }
        File[] csvFilesList = dir.listFiles((File directory, String filename) -> filename.endsWith(".csv"));
        for (File file : csvFilesList) {
            Reader in = new FileReader(file);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            String outputFile = PATH_TO_RESOURCES + file.getName();
            CSVFormat csvFileFormat = CSVFormat.EXCEL.withHeader("PostNumber", "DiseaseId", "DiseaseName", "SymptomId", "SymptomName", "TreatmentId", "TreatmentName", "DrugId", "DrugName", "BodypartId", "BodypartName");
            CSVPrinter csvFilePrinter;
            try (FileWriter fileWriter = new FileWriter(outputFile)) {
                csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
                for (CSVRecord record : records) {
                    long recordNumber = record.getRecordNumber();
                    String diseaseCategory = record.get(0);
                    String postLink = record.get(1);
                    String postHeading = record.get(2);
                    String postContent = record.get(3);

                    //clean off non ASCII characters
                    diseaseCategory = stripNonASCII(diseaseCategory);
                    postHeading = stripNonASCII(postHeading);
                    postContent = stripNonASCII(postContent);

                    System.out.println("----------------------------------------------------");
//                System.out.println(disease_name);
//                System.out.println(postHeading);
//                System.out.println(postContent);
//                System.out.println("----------------------------------------------------");
                    triggerMetaMap(frontEnd, output, options, csvFilePrinter, recordNumber, diseaseCategory, postHeading, postContent);
                    System.out.println("----------------------------------------------------");
                }
                fileWriter.flush();
            }
            csvFilePrinter.close();
        }
        
        frontEnd.api.disconnect();

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
        String result = inputText;
        result = result.replaceAll("[^\\x00-\\x7F]", EMPTY_STRING);
        result = result.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", EMPTY_STRING);
        result = result.replaceAll("\\p{C}", EMPTY_STRING);
        result = result.trim();
        return result;
    }

    /**
     * Triggers the MetaMap API with the given text content. Throws an Exception
     * if the MetaMap server is not running.
     *
     * @param category
     * @param postHeading
     * @param postContent
     *
     * @throws Exception
     */
    private static void triggerMetaMap(MetaMapAnnotator mmFrontEnd, PrintStream output,List<String> options, CSVPrinter csvFilePrinter, long recordNumber, String category, String postHeading, String postContent) throws Exception {

        if (!"".equals(postContent) && !"".equals(category)) {
            mmFrontEnd.process(csvFilePrinter, recordNumber, category, postContent, output, options);
        }
    }

    /**
     * Triggers the MetaMap annotator for the given text content and prints the
     * output to the given out print stream.
     *
     * @param terms
     * @param out
     * @param serverOptions
     *
     * @throws Exception
     */
    private void process(CSVPrinter csvFilePrinter, long recordNumber, String category, String terms, PrintStream out, List<String> serverOptions) throws Exception {
        if (serverOptions.size() > 0) {
            api.setOptions(serverOptions);
        }
        int diseasesCount = 0;
        HashMap<String, String> diseaseDict = new HashMap<>();
        HashMap<String, String> symptomDict = new HashMap<>();
        HashMap<String, String> treatmentDict = new HashMap<>();
        HashMap<String, String> drugsDict = new HashMap<>();
        HashMap<String, String> bodyPartDict = new HashMap<>();
        List<Result> categoryMM = api.processCitationsFromString(category);
        String categoryMMName = EMPTY_STRING;
        String categoryMMId = EMPTY_STRING;
        for (Result result : categoryMM) {
            if (result != null) {
                for (Utterance utterance : result.getUtteranceList()) {
                    for (PCM pcm : utterance.getPCMList()) {
                        for (Mapping map : pcm.getMappingList()) {
                            for (Ev mapEv : map.getEvList()) {
                                if (mapEv.getSemanticTypes().contains("dsyn")) {
                                    categoryMMName = mapEv.getPreferredName();
                                    categoryMMId = mapEv.getConceptId();
                                }
                            }
                        }
                    }
                }

            }

        }
        List<Result> resultList = api.processCitationsFromString(terms);

        for (Result result : resultList) {
            if (result != null) {
//                out.println("Input text: ");
//                out.println(" " + result.getInputText());
                for (Utterance utterance : result.getUtteranceList()) {
//                    out.println("Utterance:");
//                    out.println(" Id: " + utterance.getId());
//                    out.println(" Utterance text: " + utterance.getString());
//                    out.println(" Position: " + utterance.getPosition());
                    for (PCM pcm : utterance.getPCMList()) {
                        out.println(" Phrase: " + pcm.getPhrase().getPhraseText());
//                        out.println(" Minimal Commitment Parse: " + pcm.getPhrase().getMincoManAsString());
//                        HashMap<String, String> wordTagList = listInputMatches(pcm.getPhrase().getMincoMan());
                        out.println("  Mappings:");
                        for (Mapping map : pcm.getMappingList()) {
//                        out.println(" Map Score: " + map.getScore());
                            for (Ev mapEv : map.getEvList()) {
                                boolean filterOut = false;
                                for (String ignoreWord : ignoredWords) {
                                    for (String matchedWord : mapEv.getMatchedWords()) {
                                        if (matchedWord.toLowerCase().equals(ignoreWord.toLowerCase())) {
                                            if (mapEv.getMatchedWords().size() == 1) {
                                                filterOut = true;
                                            }
                                        }
                                    }
                                }
//                                if (!includePOSTags.contains(getPOSMatch(wordTagList, mapEv.getMatchedWords().get(0)))) {
//                                    filterOut = true;
//                                }
//                                if (mapEv.getSemanticTypes().toString().contains("dsyn")) {
//                                    if (!"".equals(getPOSMatch(wordTagList, mapEv.getMatchedWords().get(0)))) {
//                                        if (!getPOSMatch(wordTagList, mapEv.getMatchedWords().get(0)).equals("noun")) {
//                                            filterOut = true;
//                                        }
//                                    }
//
//                                }
//                                if (isAcronym(mapEv.getPreferredName(), mapEv.getMatchedWords().toString())) {
//                                    filterOut = true;
//                                }
                                if (!filterOut) {
//                                out.println("   Score: " + mapEv.getScore());
                                    out.println("   Filter Status: " + filterOut);
                                    out.println("   Concept Id: " + mapEv.getConceptId());
//                                out.println("   Term: " + mapEv.getTerm());
                                    out.println("   Concept Name: " + mapEv.getConceptName());
                                    out.println("   Preferred Name: " + mapEv.getPreferredName());
                                    out.println("   Matched Words: " + mapEv.getMatchedWords());
                                    out.println("   Semantic Types: " + mapEv.getSemanticTypes());
//                                out.println("   MatchMap: " + mapEv.getMatchMap());
//                                out.println("   MatchMap alt. repr.: " + mapEv.getMatchMapList());
//                                out.println("   is Head?: " + mapEv.isHead());
//                                out.println("   is Overmatch?: " + mapEv.isOvermatch());
//                                out.println("   Sources: " + mapEv.getSources());
//                                out.println("   Positional Info: " + mapEv.getPositionalInfo());
//                                out.println("   Pruning Status: " + mapEv.getPruningStatus());
//                                out.println("   Negation Status: " + mapEv.getNegationStatus());
                                    String diseaseName = EMPTY_STRING, symptomName = EMPTY_STRING, diseaseId = EMPTY_STRING, symptomId = EMPTY_STRING, treatmentName = EMPTY_STRING, treatmentId = EMPTY_STRING, drugName = EMPTY_STRING, drugId = EMPTY_STRING, bodyPartName = EMPTY_STRING, bodypartId = EMPTY_STRING;
                                    if (mapEv.getSemanticTypes().contains("dsyn")) {
                                        diseaseName = mapEv.getPreferredName();
                                        diseaseId = mapEv.getConceptId();
                                        diseaseDict.put(diseaseId, diseaseName);
                                        diseasesCount += 1;
                                    }
                                    if (mapEv.getSemanticTypes().contains("sosy")) {
                                        symptomName = mapEv.getPreferredName();
                                        symptomId = mapEv.getConceptId();
                                        symptomDict.put(symptomId, symptomName);
                                    }
                                    if (mapEv.getSemanticTypes().contains("topp")) {
                                        treatmentName = mapEv.getPreferredName();
                                        treatmentId = mapEv.getConceptId();
                                        treatmentDict.put(treatmentId, treatmentName);
                                    }
                                    if (mapEv.getSemanticTypes().contains("clnd")) {
                                        drugName = mapEv.getPreferredName();
                                        drugId = mapEv.getConceptId();
                                        drugsDict.put(drugId, drugName);
                                    }
                                    if (mapEv.getSemanticTypes().contains("bpoc")) {
                                        bodyPartName = mapEv.getPreferredName();
                                        bodypartId = mapEv.getConceptId();
                                        bodyPartDict.put(bodypartId, bodyPartName);

                                    }
                                }
                            }
                        }
                    }
                }

            } else {
                out.println("The result instance is NULL!");
            }
        }
        if (!EMPTY_STRING.equals(categoryMMId)) {
            if (diseasesCount == 0) {
                diseaseDict.put(categoryMMId, categoryMMName);
            }
            if (diseasesCount > 1) {
                diseaseDict.clear();
                diseaseDict.put(categoryMMId, categoryMMName);
            }
        }
        if (diseaseDict.size() == 1) {
            for (Map.Entry<String, String> entry : diseaseDict.entrySet()) {
                csvFilePrinter.printRecord(recordNumber, entry.getKey(), entry.getValue(), EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
            }
            for (Map.Entry<String, String> entry : symptomDict.entrySet()) {
                csvFilePrinter.printRecord(recordNumber, EMPTY_STRING, EMPTY_STRING, entry.getKey(), entry.getValue(), EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
            }
            for (Map.Entry<String, String> entry : treatmentDict.entrySet()) {
                csvFilePrinter.printRecord(recordNumber, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, entry.getKey(), entry.getValue(), EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING);
            }
            for (Map.Entry<String, String> entry : drugsDict.entrySet()) {
                csvFilePrinter.printRecord(recordNumber, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, entry.getKey(), entry.getValue(), EMPTY_STRING, EMPTY_STRING);
            }
            for (Map.Entry<String, String> entry : bodyPartDict.entrySet()) {
                csvFilePrinter.printRecord(recordNumber, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, EMPTY_STRING, entry.getKey(), entry.getValue());
            }
        }
        this.api.resetOptions();
    }

    /**
     * Returns the arguments of a compound term
     *
     * @param compoundTerm
     *
     * @return
     */
    private List<PBTerm> listArgs(PBTerm compoundTerm) {
        List<PBTerm> elements = new ArrayList<PBTerm>();
        for (int i = 1; i <= compoundTerm.getArity(); i++) {
            elements.add(compoundTerm.getArgument(i));
        }
        return elements;
    }

    /**
     * Returns the elements of the list term
     *
     * @param listTerm
     *
     * @return
     */
    private List<PBTerm> listElements(PBTerm listTerm) {
        List<PBTerm> elements = new ArrayList<PBTerm>();
        for (int i = 1; i <= listTerm.length(); i++) {
            elements.add(TermUtils.getListElement(listTerm, i));
        }
        return elements;
    }

    /**
     * Returns the atom terms of the given MincoMan term
     *
     * @param mincoManTerm
     *
     * @return
     */
    private List<String> listAtomTermses(PBTerm mincoManTerm) {
        List<String> atomTermsList = new ArrayList<String>();
        if (mincoManTerm.isListCell()) {
            for (PBTerm elem : listElements(mincoManTerm)) {
                List<String> subMatches = listAtomTermses(elem);
                atomTermsList.addAll(subMatches);
            }
        } else if (mincoManTerm.isCompound()) {
            atomTermsList.add("arg:" + mincoManTerm.getName());
            for (PBTerm elem : listArgs(mincoManTerm)) {
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

    /**
     * Returns the POS tag for the given word
     *
     * @param posTagList
     * @param matchedWord
     *
     * @return
     */
    private String getPOSMatch(HashMap<String, String> posTagList, String matchedWord) {
        for (HashMap.Entry<String, String> posTagEntry : posTagList.entrySet()) {
            String key = posTagEntry.getKey();
            if (key.toLowerCase().equals(matchedWord.toLowerCase())) {
                return posTagEntry.getValue();
            }
        }
        for (HashMap.Entry<String, String> posTagEntry : posTagList.entrySet()) {
            String key = posTagEntry.getKey();
            if (key.toLowerCase().contains(matchedWord.toLowerCase())) {
                return posTagEntry.getValue();
            }
        }
        for (HashMap.Entry<String, String> posTagEntry : posTagList.entrySet()) {
            String key = posTagEntry.getKey();
            if (matchedWord.toLowerCase().contains(key.toLowerCase())) {
                return posTagEntry.getValue();
            }
        }
        return EMPTY_STRING;
    }

    /**
     * Returns true if the given two strings are NOT Acronyms that is if they
     * are highly dissimilar
     *
     * @param conceptName
     * @param matchedWords
     *
     * @return
     */
    private boolean isAcronym(String conceptName, String matchedWords) {
        String match = matchedWords;
        match = match.substring(1, match.length() - 1);

        if (!(conceptName.toLowerCase().contains(match.toLowerCase()) || match.toLowerCase().contains(conceptName.toLowerCase()))) {
            return true;
        }
        JaroWinklerDistance dist = new JaroWinklerDistance();
        double measure = dist.apply(conceptName, matchedWords);
        if (measure > 0.8) {
            return false;
        }
        return false;
    }

    /**
     * Returns a Map with the word and corresponding POS tag for the given
     * MincoMan term
     *
     * @param mincoManTerm
     *
     * @return
     */
    private HashMap<String, String> listInputMatches(PBTerm mincoManTerm) {
        HashMap<String, String> termlist = new HashMap<String, String>();
        List<String> atomTermsList = listAtomTermses(mincoManTerm);
        PeekingIterator<String> iter = new PeekingIterator<>(atomTermsList.iterator());
        while (iter.hasNext()) {

            if (iter.next().equals("arg:inputmatch")) {
                String tag, word = EMPTY_STRING;
                word = iter.next();
                while (iter.hasNext()) {
                    String p = iter.peek();
                    if (!p.startsWith("arg:")) {
                        word = word + " " + iter.next();
                    } else if (p.startsWith("arg:tag")) {
                        iter.next();
                        tag = iter.next();
                        termlist.put(word, tag);
                    } else {
                        break;
                    }
                }
            }
        }
        return termlist;
    }
}

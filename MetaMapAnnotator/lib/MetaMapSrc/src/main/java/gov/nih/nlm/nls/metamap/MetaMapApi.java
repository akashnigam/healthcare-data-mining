package gov.nih.nlm.nls.metamap;

import java.io.Reader;
import java.util.List;
import se.sics.prologbeans.PrologSession;

/**
 * This provides a generic interface to access the MetaMap server:
 *
 * <pre>
 *  MetaMapApi api = new MetaMapApiImpl();
 *  if (theOptions.size() > 0) {
 *     api.setOptions(theOptions);
 *  }
 *  List<Result> resultList = api.processCitationsFromString(terms);
 *  for (Result result: resultList) {
 *    if (result != null) {
 *      ... inspect result ...
 *    }
 *  }
 * </pre>
 * <p>
 * MetaMap options available in the api: 
 * <pre>
 *   -@ --WSD &lt;hostname&gt;   : Which WSD server to use.
 *   -8 --dynamic_variant_generation : dynamic variant generation
 *   -A --strict_model               : use strict model 
 *   -C --relaxed_model              : use relaxed model 
 *   -D --all_derivational_variants  : all derivational variants
 *   -J --restrict_to_sts &lt;semtypelist&gt;   : restrict to semantic types
 *   -K --ignore_stop_phrases        : ignore stop phrases.
 *   -R --restrict_to_sources &lt;sourcelist&gt; : restrict to sources
 *   -S --tagger &lt;sourcelist&gt;          : Which tagger to use.
 *   -V --mm_data_version &lt;name&gt;       : version of MetaMap data to use.
 *   -X --truncate_candidates_mappings : truncate candidates mapping
 *   -Y --prefer_multiple_concepts  : prefer multiple concepts
 *   -Z --mm_data_year &lt;name&gt;          : year of MetaMap data to use.
 *   -a --all_acros_abbrs           : allow Acronym/Abbreviation variants
 *   -b --compute_all_mappings      : compute/display all mappings
 *   -d --no_derivational_variants  : no derivational variants
 *   -e --exclude_sources &lt;sourcelist&gt; : exclude semantic types
 *   -g --allow_concept_gaps        : allow concept gaps
 *   -i --ignore_word_order         : ignore word order
 *   -k --exclude_sts &lt;semtypelist&gt;    : exclude semantic types
 *   -l --allow_large_n             : allow Large N
 *   -o --allow_overmatches : allow overmatches 
 *   -r --threshold &lt;integer&gt;          : Threshold for displaying candidates. 
 *   -y --word_sense_disambiguation : use WSD 
 *   -z --term_processing : use term processing 
 * </pre>
 *
 * <p>
 * If the function setOptions is used to set options arguments to be
 * applied to processCitationsFromString(String),
 * processCitationsFromReader(Reader), and
 * processCitationsFromFile(String) the state of set options remains
 * in force until the function resetOptions is called.
 * <p>
 * Use the functions processCitationsFromString(String,String),
 * processCitationsFromReader(String, Reader), and
 * processCitationsFromFile(String, String) to set the options for
 * one request.
 *
 * Created: Wed Apr 29 14:09:20 2009
 *
 * @author <a href="mailto:wrogers@nlm.nih.gov">Willie Rogers</a>
 * @version 1.0
 */
public interface MetaMapApi {

  /** Default time out for api session */
  public static final int DEFAULT_TIMEOUT = 0;
  /** default hostname of MetaMap server */
  public static final String DEFAULT_SERVER_HOST = "localhost";
  /** default port of MetaMap server */
  public static final int DEFAULT_SERVER_PORT = 8066;

  /** Get the server's current option settings. 
   * <p>
   * @return string containing long format of options.
   */
  String getOptions();

  /**
   * Set MetaMap server options using a string of form:
   *
   * <pre>
   *    "-option1 optional-argument1 -option2 optional-argument2"
   * </pre>
   * examples:
   * <pre>
   *    "-yD" or "-y -D" or "-J SNOMEDCT" or "--restrict_to_sources SNOMEDCT"
   *  </pre>
   * <p>
   * Set MetaMap server options. State of options are preserved in
   * subsequent invocations of processCitationsFromString(String),
   * processCitationsFromReader(Reader), and
   * processCitationsFromFile(String).
   * <p>
   * @param optionString a string of MetaMap options
   */
  void setOptions(String optionString);

  /**    
   * @param options options string
   */
  void setOptions(String[] options);

  /**
   * Set MetaMap server options.
   * <p>
   * list of the form:
   * <pre>
   *  ["-y", "-D"] or ["-yD"]
   * </pre>
   *
   * @param options a list of options
   */
  void setOptions(List<String> options);

  /**
   * Set MetaMap server options.
   * <p>
   * This method sends preprocessed options to MetaMap server.  The
   * parameter optionListString is in the form of:
   * <pre>
   *   "[" + "'" + option + "'" + "," + + "'" + option + "'" + ... "]"
   * </pre>
   * E.G.:
   * <pre>
   *    optionListString = "['-y','-D','-i']"; 
   * </pre>
   *  or:
   * <pre>
   *    optionListString = "['-yDi']"; 
   * </pre>
   * sets options -y -D and -i.
   *   
   * <p>
   * @param optionListString a string of MetaMap options
   * @deprecated
   */
  void invokeSetOptions(String optionListString);

  /**
   * Un-set options 
   * <p>
   * string is of the form:
   * <pre>
   *    "-option1 optional-argument1 -option2 optional-argument2"
   * </pre>
   * E.G.:
   * <pre>
   *    "-yD" or "-y -D" or 
   *  </pre>
   *
   * <p>
   * This is deprecated, use resetOptions instead.
   * @param optionString a string of MetaMap options
   * @deprecated
   */
 void unsetOptions(String optionString);

  /**
   * Un-set options 
   *<p>
   * The list is a list of strings of the form:
   *
   * <pre>
   *  ["-y", "-D"] or ["-yD"]
   * </pre>
   *
   * <p>
   * This is deprecated, use resetOptions instead.
   * @param options a list of options
   * @deprecated
   */
  void unsetOptions(List<String> options);

  /** Reset options to defaults */
  void resetOptions();

  /** process a string containing one or more documents - unicode (utf8) is not supported
   * @param aString a file of documents
   * @return a list of one of more result instances
   */
  public List<Result> processCitationsFromString(String aString);

  
  /** process a string containing one or more documents - unicode (utf8) is not supported
   * <p>
   * using string of form:
   * <pre>
   *  "-y -D" or "-yD"
   * </pre>
   * @param optionString options string
   * @param aString a string containing documents to be processed.
   * @return a list of one of more result instances
   */
  public List<Result> processCitationsFromString(String optionString, String aString);

  /** Process a ASCII text stream reader of one or more documents - unicode (utf8) is not supported
   * @param inputReader a reader stream of documents
   * @return a list of one or more result instances
   */
  public List<Result> processCitationsFromReader(Reader inputReader);

  /** Process a ASCII text stream reader of one or more documents - unicode (utf8) is not supported
   * <p>
   * using string of form:
   * <pre>
   *  "-y -D" or "-yD"
   * </pre>
   * @param optionString options string
   * @param inputReader a reader stream of documents
   * @return a list of one or more result instances
   */
  public List<Result> processCitationsFromReader(String optionString, Reader inputReader);


  /** Process a ASCII text file of one or more documents - unicode (utf8) is not supported
   * @param inputFilename the filename of a file of documents
   * @return a list of one or more result instances
   */
  public List<Result> processCitationsFromFile(String inputFilename);

  /** Process a ASCII text file of one or more documents - unicode (utf8) is not supported
   * <p>
   * using string of form:
   * <pre>
   *  "-y -D" or "-yD"
   * </pre>
   * @param optionString options string
   * @param inputFilename the filename of a file of documents
   * @return a list of one or more result instances
   */
  public List<Result> processCitationsFromFile(String optionString, String inputFilename);

  /** 
   * Return Prolog session associated with this instance of the API. 
   * @return prolog beans session associated with api. */
  PrologSession getSession();

 /**
   * Set time for api prolog session.
   * @param timeout time in milliseconds to wait for prolog server before timing out.
   */
  public void setTimeout(int timeout);

  /**
   * Use MetaMap server on specified port
   * @param port of MetaMap server to use.
   */
  public void setPort(int port);

  /**
   * Use MetaMap server on specified host
   * @param hostname hostname of non-local metamap server
   */
  public void setHost(String hostname);

  /** disconnect from server. */
  void disconnect();
}

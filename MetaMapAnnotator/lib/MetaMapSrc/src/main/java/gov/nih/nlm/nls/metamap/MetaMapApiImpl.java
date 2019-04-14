package gov.nih.nlm.nls.metamap;

import java.io.*;
import java.net.ConnectException;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import se.sics.prologbeans.*;

/**
 * The default implementation of the MetaMapApi interface.
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
 * <p>
 * Created: Wed Apr 29 14:59:15 2009
 *
 * @author <a href="mailto:wrogers@nlm.nih.gov">Willie Rogers</a>
 * @version 1.0
 */
public class MetaMapApiImpl implements MetaMapApi {

  private PrologSession session = new PrologSession();
  protected boolean connected = false;

  private String currentOptionListString = null;

  /** Instantiate api using the default timeout. */
  public MetaMapApiImpl() {
    this.session.setTimeout(DEFAULT_TIMEOUT);
  }

  /** Instantiate api using specified timeout.
   * @param timeout time in milliseconds to wait for prolog server before timing out.
   */
  public MetaMapApiImpl(int timeout) {
    this.session.setTimeout(timeout);
  }

  /** Instantiate api using MetaMap server specified by hostname 
   * @param serverHostname hostname of metamap server
   */
  public MetaMapApiImpl(String serverHostname) {
    this.session.setTimeout(DEFAULT_TIMEOUT);
    session.setHost(serverHostname);
  }

  /** Instantiate api using MetaMap server specified by hostname 
   * @param serverHostname hostname of metamap server
   * @param port port of metamap server
   */
  public MetaMapApiImpl(String serverHostname, int port) {
    this.session.setTimeout(DEFAULT_TIMEOUT);
    session.setPort(port);
  }

  /**
   * Instantiate api using MetaMap server specified by hostname with specified timeout.
   * @param serverHostname hostname of metamap server 
   * @param port port metamap server
   * @param timeout time in milliseconds to wait for prolog server before timing out.
   */
  public MetaMapApiImpl(String serverHostname, int port, int timeout) {
    this.session.setTimeout(timeout);
    session.setHost(serverHostname);
    session.setPort(port);
  }

  /** 
   * Return Prolog session associated with this instance of the API. 
   * @return Prolog session.
   */
  public PrologSession getSession() {
    return this.session;
  }

  /**
   * Set time for api prolog session.
   * @param timeout time in milliseconds to wait for prolog server before timing out.
   */
  public void setTimeout(int timeout) {
    session.setTimeout(timeout);
  }

  /**
   * Use MetaMap server on specified port
   * @param port of MetaMap server to use.
   */
  public void setPort(int port) {
    session.setPort(port);
  }

  /**
   * Use MetaMap server on specified host
   * @param hostname hostname of non-local metamap server
   */
  public void setHost(String hostname) {
    session.setHost(hostname);
  }

  /** Get the server's current option settings. 
   * @return string containing long format of options.
   */
  public String getOptions() {
    return this.currentOptionListString;
  }

  public String createOptionListString(String[] options) {
    StringBuffer optionsSb = new StringBuffer();
    optionsSb.append("[");
    for ( int i = 0; i < options.length; i++ ) {
      optionsSb.append("'").append(options[i]).append("'");
      if (i < (options.length - 1)) optionsSb.append(",");
    }
    optionsSb.append("]");
    return optionsSb.toString();
  }

  /**
   * @param optionString a string of MetaMap options
   * @return a string of MetaMap options reformated for PrologBeans.
   */
  public String createOptionListString(String optionString) {
    String[] options = optionString.split(" ");
    return this.createOptionListString(options);
  }

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
   *
   * @param optionString a string of MetaMap options
   */
  public void setOptions(String optionString) {
    this.saveOptions(this.createOptionListString(optionString));
  }


  /**    
   * Set MetaMap server options using array of form:
   *
   * <pre>
   *  ["-y", "-D"] or ["-yD"]
   * </pre>
   * @param options an array of  options
   */
  public void setOptions(String[] options) {
    this.saveOptions(this.createOptionListString(options));
  }

  /**
   * Set options using list of form:
   *
   * <pre>
   *  ["-y", "-D"] or ["-yD"]
   * </pre>
   *
   * @param options a list of  options
   */
  public void setOptions(List<String> options) {
    StringBuffer sb = new StringBuffer();
    sb.append("[");
    for (Iterator<String> optIter = options.iterator(); optIter.hasNext(); ) {
      sb.append("'").append(optIter.next()).append("'");
      if (optIter.hasNext()) sb.append(",");
    }
    sb.append("]");
    this.saveOptions(sb.toString());
  }

  /**
   * Save MetaMap server options.
   *
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
   * @param optionListString a string of MetaMap options
   */
  public void saveOptions(String optionListString) {
    this.currentOptionListString = optionListString;
  }


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
   * @param optionListString a string of MetaMap options
   * @deprecated
   */
  @Deprecated
  public void invokeSetOptions(String optionListString) {
    this.currentOptionListString = optionListString;
  }

  /**
   * Un-set options using a string of form:
   *   * <pre>
   *    "-option1 optional-argument1 -option2 optional-argument2"
   * </pre>
   * E.G.:
   * <pre>
   *    "-yD" or "-y -D" or 
   *  </pre>
   *
   * @param optionString a string of MetaMap options
   * @deprecated
   */
  @Deprecated
  public void unsetOptions(String optionString) {
    
  }

  /**
   * Un-set options using list of form:
   *
   * <pre>
   *  ["-y", "-D"] or ["-yD"]
   * </pre>
   *
   * @param options a list of options
   * @deprecated
   */
  @Deprecated
  public void unsetOptions(List<String> options) {
    StringBuffer sb = new StringBuffer();
    sb.append("[");
    for (Iterator<String> optIter = options.iterator(); optIter.hasNext(); ) {
      sb.append("'").append(optIter.next()).append("'");
      if (optIter.hasNext()) sb.append(",");
    }
    sb.append("]");
  }

  /** Reset options to defaults */
  public void resetOptions() {
    this.currentOptionListString = null;
  }

  private List<Result> processCitationsFromStringWithNoOptions(String aString)
  {
    List<Result> newResultList = new ArrayList<Result>();
    if (Utils.isPureAscii(aString)) {
      /* split string into multiple citations for content separated by blank lines. */
      String[] citations = aString.split("\n[\\s]*\n");
      for (int i = 0; i < citations.length; i++) {
	if (citations[i].trim().length() > 0) {
	  Result newResult = null;
	  try {
	    if (! this.connected) {
	      this.session.connect();
	      this.connected = true;
	    }
	    Bindings bindings = new Bindings().bind("E", citations[i]);
	    QueryAnswer answer =
	      session.executeQuery("process_string(E,Output)", bindings);
	    PBTerm 	  result = answer.getValue("Output");
	    if (result != null) {
	      newResult = new ResultImpl(result, citations[i]);
	      newResultList.add(newResult);
	    } else {
	      System.err.println("Error: " + answer.getError() + "\n");
	    }
	  } catch (ConnectException e) {
	    System.err.println("Error when querying Prolog Server: " +
			       e.getMessage() + '\n');
	    throw new RuntimeException(e.getMessage() + 
				       ": Check to see if mmserver is running, or if port and hostname specified for the mmserver are correct.", e);
	  } catch (Exception e) {
	    System.err.println("Error when querying Prolog Server: " +
			       e.getMessage() + '\n');
	    e.printStackTrace();
	    throw new RuntimeException(e);
	  }
	}
      }
      return newResultList;
    } else {
      System.err.println("The input string contains non-ascii characters: " + aString);
      return newResultList;
    }
  }

  /** process a string containing one or more documents - unicode (utf8) is not supported
   * @param aString a file of documents
   * @return a list of one of more result instances, which may be empty.
   */
  public List<Result> processCitationsFromString(String aString) {
    if (this.currentOptionListString != null && this.currentOptionListString.length() > 1) {
      return processCitationsFromStringWithOptionListString(currentOptionListString, aString);
    } 
    return processCitationsFromStringWithNoOptions(aString);
  }

  private List<Result> processCitationsFromStringWithOptionListString
    (String optionListString, String aString) {
    List<Result> newResultList = new ArrayList<Result>();
    if (Utils.isPureAscii(aString)) {
      /* split string into multiple citations for content separated by blank lines. */
      String[] citations = aString.split("\n[\\s]*\n");
      for (int i = 0; i < citations.length; i++) {
	Result newResult = null;
	try {
	  if (! this.connected) {
	    this.session.connect();
	    this.connected = true;
	  }
	  Bindings bindings = new Bindings().bind("Request", optionListString +
						  "|" + citations[i]);
	  QueryAnswer answer =
	    session.executeQuery("process_request(Request,Response)", bindings);
	  PBTerm result = answer.getValue("Response");
	
	  if (result != null) {
	    newResult = new ResultImpl(result, citations[i]);
	    newResultList.add(newResult);
	  } else {
	    System.err.println("Error: " + answer.getError() + "\n");
	  }
	} catch (ConnectException e) {
	  System.err.println("Error when querying Prolog Server: " +
			     e.getMessage() + '\n');
	  throw new RuntimeException(e.getMessage() + 
				     ": Check to see if mmserver is running, or if port and hostname specified for the mmserver are correct.", e);
	} catch (Exception e) {
	  System.err.println("Error when querying Prolog Server: " +
			     e.getMessage() + '\n');
	  e.printStackTrace();
	  throw new RuntimeException(e);
	}
      }
      return newResultList;
    } else {
      System.err.println("The input string contains non-ascii characters: " + aString);
      return newResultList;
    }
  }

  public List<Result> processCitationsFromString(String optionString, String aString) {
    if ((this.currentOptionListString != null) && (this.currentOptionListString.length() > 1)) {
      String optionListString = this.createOptionListString(optionString);
      return processCitationsFromStringWithOptionListString(optionListString, aString);
    } else {
      return processCitationsFromStringWithNoOptions(aString);
    }
  }
     
  /**
   * Process a ASCII text stream reader of one or more documents -
   * unicode (utf8) is not supported.


   * @param inputReader a reader stream of documents
   * @return a list of one or more result instances
   */
  public List<Result> processCitationsFromReader(Reader inputReader) {
    try {
      StringBuffer sb = new StringBuffer();
      BufferedReader br = new BufferedReader(inputReader);
      String line;

      while ((line = br.readLine()) != null) {
	if (Utils.isPureAscii(line)) {
	  sb.append(line).append("\n");
	} else {
	  System.err.println("Skipping line containing non-ascii characters:" + line );
	}
      }
      return processCitationsFromString(sb.toString());
    } catch (Exception e) {
      System.err.println("Error when querying Prolog Server: " +
			 e.getMessage() + '\n');
      throw new RuntimeException(e);
    }
  }

  public List<Result> processCitationsFromReader(String optionString, Reader inputReader) {
    try {
      StringBuffer sb = new StringBuffer();
      BufferedReader br = new BufferedReader(inputReader);
      String line;
      while ((line = br.readLine()) != null) {
	if (Utils.isPureAscii(line)) {
	sb.append(line).append("\n");
	} else {
	  System.err.println("Skipping line containing non-ascii characters:" + line );
	}
      }
      return processCitationsFromString(optionString, sb.toString());
    } catch (Exception e) {
      System.err.println("Error when querying Prolog Server: " +
			 e.getMessage() + '\n');
      throw new RuntimeException(e);
    }
  }
  
  /** Process a ASCII text file of one or more documents - unicode (utf8) is not supported
   * @param inputFilename the filename of a file of documents
   * @return a list of one or more result instances
   */
  public List<Result> processCitationsFromFile(String inputFilename) {
    try {
      return processCitationsFromReader(new FileReader(inputFilename));
    } catch (Exception e) {
      System.err.println("Error when querying Prolog Server: " +
			 e.getMessage() + '\n');
      throw new RuntimeException(e);
    }
  }

  public List<Result> processCitationsFromFile(String optionString, String inputFilename) {
    try {
      return processCitationsFromReader(optionString, new FileReader(inputFilename));
    } catch (Exception e) {
      System.err.println("Error when querying Prolog Server: " +
			 e.getMessage() + '\n');
      throw new RuntimeException(e);
    }
  }

  /** Disconnect from Prolog server. */
  public void disconnect() {
    if (this.session.isConnected()) {
      this.session.disconnect();
      this.connected = false;
    }
  }

  protected void finalize() {
    // System.err.println("finalize");
    // System.err.flush();
    disconnect();
  }
}

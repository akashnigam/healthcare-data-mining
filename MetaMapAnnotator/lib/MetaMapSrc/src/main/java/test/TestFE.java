package test;

import gov.nih.nlm.nls.metamap.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

/**
 * An example of using the api to read an input file and then writing
 * the result to an output file.
 * <p>
 * Created: Tue May 19 09:42:22 2009
 *
 * @author <a href="mailto:wrogers@nlm.nih.gov">Willie Rogers</a>
 * @version 1.0
 */
public class TestFE {


  /**
   * Creates a new <code>TestFE</code> instance.
   *
   */
  public TestFE() {

  }
  
  void process(String inputFilename, String outputFilename /*, List theOptions*/)
  {
    List<String> theOptions = new ArrayList<String>();
    try {
      MetaMapApi api = new MetaMapApiImpl();
      api.setTimeout(0);
      String options = api.getOptions();
      System.out.println("Options: " + options);
      List<Result> resultList = api.processCitationsFromFile(inputFilename);
      for (Result result: resultList) {
	if (result != null) {
	  PrintWriter pw = new PrintWriter(outputFilename);
	  pw.println("result: " + result.getMachineOutput());
	  pw.close();
	}
      }
    } catch (Exception e) {
      System.out.println("Error when querying Prolog Server: " +
			 e.getMessage() + '\n');
    }
  }

  public static void main(String[] args) {
    TestFE frontEnd = new TestFE();
    
    if (args.length < 2) {
      System.out.println("usage: TestFE <inputfile> <outputfile>");
      System.exit(0);
    }
    frontEnd.process(args[0], args[1]);
  }
}

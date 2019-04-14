package gov.nih.nlm.nls.metamap;

import se.sics.prologbeans.*;

public class AutoGen {
  private PrologSession session = new PrologSession();

  public AutoGen() {
    session.setTimeout(30000);
  }

  /** process a string containing one or more documents - unicode (utf8) is not supported
   * @param aString a file of documents
   * @return a result instance
   */
  public PBTerm processString(String aString) {
    PBTerm result = null;
    try {
      System.out.println("aString: " + aString);
      Bindings bindings = new Bindings().bind("E", aString);
      System.out.println("bindings: " + bindings.toString());
      QueryAnswer answer =
	session.executeQuery("process_string(E,Output)", bindings);
      result = answer.getValue("Output");
      System.out.println("answer: " + result.toString());
      if (result == null) {
        System.err.println("Error: " + answer.getError() + "\n");
      }
    } catch (Exception e) {
      System.err.println("Error when querying Prolog Server: " +
			 e.getMessage() + '\n');
      e.printStackTrace();
    }
    return result;
  }

  public void inspect(PBTerm aTerm, int level) {
    if (aTerm.isListCell()) {
      this.traverselist(aTerm, level+1);
    } else if (aTerm.isString()) {
      System.out.print( aTerm.getName());
    } else if (aTerm.isAtom()) {
      System.out.print( aTerm.getName());
    } else if (aTerm.isInteger()) {
      System.out.print( aTerm.getName());
    } else if (aTerm.isCompound()) {
      System.out.print( aTerm.getName() + "(");
      for (int i = 1; i < aTerm.getArity(); i++) {
	PBTerm subTerm = aTerm.getArgument(i);
	this.inspect(subTerm, level+1);
	if (i < aTerm.getArity()) System.out.print( "," ) ;
      }
      System.out.println(")");
    }
  }

  public void traverselist(PBTerm pbList, int level)
  {
    System.out.print( "[");
    for (int i = 1; i< pbList.length(); i++) {
      PBTerm aTerm = TermUtils.getListElement(pbList, i);
      this.inspect(aTerm, level+1);
      if (i < pbList.length()) System.out.print( "," ) ;
    }
    System.out.print("]");
  }

  public static void main(String[] args) 
    throws Exception
  {
    AutoGen frontEnd = new AutoGen();
    
    if (args.length < 1) {
      System.out.println("usage: gov.nih.nlm.nls.metamap.AutoGen term");
      System.exit(0);
    }
    StringBuffer sb = new StringBuffer();
    for (String arg: args) {
      sb.append(arg).append(" ");
    }
    PBTerm mmoTerm = frontEnd.processString(sb.toString());
    System.out.println("mmoTerm: " + mmoTerm);
    frontEnd.inspect(mmoTerm, 0);
  }
}
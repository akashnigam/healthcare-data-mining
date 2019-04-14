package gov.nih.nlm.nls.metamap;

import se.sics.prologbeans.*;

/**
 * Position implementation class.
 *
 * <p>
 * Created: Thu May 21 12:14:17 2009
 *
 * @author <a href="mailto:wrogers@nlm.nih.gov">Willie Rogers</a>
 * @version 1.0
 */
public class PositionImpl implements Position {

  long x;
  long y;

  /**
   * Creates a new <code>Position</code> instance.
   * @param x x coord
   * @param y y coord
   */
  public PositionImpl(int x, int y) { this.x = (long)x; this.y = (long)y; }

  /**
   * Creates a new <code>Position</code> instance.
   * @param x x coord
   * @param y y coord
   */
  public PositionImpl(long x, long y) { this.x = x; this.y = y; }

  public PositionImpl(PBTerm posTerm) {
    this.x = posTerm.getArgument(1).intValue();
    this.y = posTerm.getArgument(2).intValue();
  }

  /** @return x coord */
  public int getX() { return (int)this.x; }
  /** @return y coord */
  public int getY() { return (int)this.y; }
  /** @return string representing object */
  public String toString() { return "(" + x + ", " + y + ")"; }
}

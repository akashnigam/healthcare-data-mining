package gov.nih.nlm.nls.metamap;

/**
 * Representation of MetaMap Positional Information.
 *
 * <p>
 * Created: Mon May 11 15:15:04 2009
 *
 * @author <a href="mailto:wrogers@nlm.nih.gov">Willie Rogers</a>
 * @version 1.0
 */
public interface Position extends MetaMapElement {
  /** @return x coord */
  int getX();
  /** @return y coord */
  int getY();
}

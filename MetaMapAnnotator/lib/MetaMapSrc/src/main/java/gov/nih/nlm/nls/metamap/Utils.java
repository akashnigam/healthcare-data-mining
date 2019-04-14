package gov.nih.nlm.nls.metamap;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharacterCodingException;

/**
 * Describe class Utils here.
 *
 *
 * Created: Thu Jan  7 08:40:30 2010
 *
 * @author <a href="mailto:wrogers@nlm.nih.gov">Willie Rogers</a>
 * @version 1.0
 */
public class Utils {

  /** Get pmid portion of metamap id.
   * @param id string in form of <pmid>.<location>.<sequence number>
   * @return string containing pmid portion of metamap id.
   */
  public static String getPmidFromId(String id) {
    return id.substring(0,id.indexOf("."));
  }

  /** Get location portion of metamap id.
   * @param id string in form of <pmid>.<location>.<sequence number>
   * @return string containing location portion of metamap id.
   */
  public static String getLocationFromId(String id) {
    return id.substring(id.indexOf("."),id.lastIndexOf("."));
  }

  /** Get sequence number portion of metamap id.
   * @param id string in form of <pmid>.<location>.<sequence number>
   * @return string containing sequence number portion of metamap id.
   */
  public static String getSequenceNumberFromId(String id) {
    return id.substring(id.lastIndexOf("."));
  }
  
  /**
   * From Real's howto
   * http://www.rgagnon.com/javadetails/java-0536.html
   */
  public static boolean isPureAscii(String v) {
    byte bytearray []  = v.getBytes();
    CharsetDecoder d = Charset.forName("US-ASCII").newDecoder();
    try {
      CharBuffer r = d.decode(ByteBuffer.wrap(bytearray));
      r.toString();
    }
    catch(CharacterCodingException e) {
      return false;
    }
    return true;
  }
}

package ca.jrvs.apps.practice;

public interface RegexExc {
  /**
   * return true if filename extension is jpeg or jpg
   * @param filename
   * @return true or false
   */
  boolean matchJpeg(String filename);

  /**
   * return true if ip is valid
   * @param ip
   * @return true or false
   */
  boolean matchIp(String ip);

  /**
   * return true if line is empty (empty, white space, tabs, etc.)
   * @param line
   * @return true or false
   */
  boolean isEmptyLine(String line);
}

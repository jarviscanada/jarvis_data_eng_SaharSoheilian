package ca.jrvs.apps.practice;

public class RegexExcImp implements RegexExc {
  @Override
  public boolean matchJpeg(String filename) {
    String FILENAME_PATTERN = "^[-a-zA-Z0-9]+\\.(jpg|jpeg)$";
    return filename.matches(FILENAME_PATTERN);
  }

  @Override
  public boolean matchIp(String ip) {
    String IP_PATTERN = "\\b(?:(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(?:25[0-5]|2[0-4]\\"
                        + "d|[01]?\\d\\d?)\\b";
    return ip.matches(IP_PATTERN);
  }

  @Override
  public boolean isEmptyLine(String line) {
    String LINE_PATTERN = "^\\s*$";
    return line.matches(LINE_PATTERN);
  }
}

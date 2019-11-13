package pl.jblew.ahpaaclient.ui.advicelist;

public class ThanksCountTextGenerator {
  public static String getThanksCountText(int count) {
    if (count == 1) return "1 podziękowanie";
    else if (count >= 2 && count <= 4) return count + " podziękowania";
    else return count + " podziękowań";
  }
}

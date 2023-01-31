package viffpdf;

import com.itextpdf.kernel.colors.Color;

public class Configuration {
  int venue_font_Size;
  
  Color dColor;
  
  Color bColor;
  
  Color vColor;
  
  Color sColor;
  
  Color hColor;
  
  Color fColor;
  
  Color oColor;
  
  Color eColor;
  
  int masterFont;
  
  public Configuration(int venueFontSize, Color dColor, Color bColor, Color vColor, Color sColor, Color hColor, Color fColor, Color oColor, Color eColor, int masterFont) {
    this.venue_font_Size = venueFontSize;
    this.dColor = dColor;
    this.bColor = bColor;
    this.vColor = vColor;
    this.sColor = sColor;
    this.hColor = hColor;
    this.fColor = fColor;
    this.oColor = oColor;
    this.eColor = eColor;
    this.masterFont = masterFont;
  }
  
  public void load() {}
}

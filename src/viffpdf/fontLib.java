package viffpdf;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class fontLib implements Serializable {
  ArrayList<PdfFont> fonts = new ArrayList<>();
  
  public fontLib() {
    try {
      PdfFont Helvetica = PdfFontFactory.createFont("Helvetica");
      this.fonts.add(Helvetica);
      PdfFont NeueHaas = PdfFontFactory.createFont("fonts/NeueHaas.otf");
      this.fonts.add(NeueHaas);
      PdfFont Calibri = PdfFontFactory.createFont("fonts/Calibri.ttf");
      this.fonts.add(Calibri);
      PdfFont Arial = PdfFontFactory.createFont("fonts/Arial.ttf");
      this.fonts.add(Arial);
      PdfFont Garamond = PdfFontFactory.createFont("fonts/Garamond.ttf");
      this.fonts.add(Garamond);
      PdfFont Geneva = PdfFontFactory.createFont("fonts/Geneva.ttf");
      this.fonts.add(Geneva);
      PdfFont Verdana = PdfFontFactory.createFont("fonts/Verdana.ttf");
      this.fonts.add(Verdana);
      PdfFont AvantGarde = PdfFontFactory.createFont("fonts/AvantGarde.ttf");
      this.fonts.add(AvantGarde);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public ArrayList<PdfFont> getFontLib() {
    return this.fonts;
  }
}

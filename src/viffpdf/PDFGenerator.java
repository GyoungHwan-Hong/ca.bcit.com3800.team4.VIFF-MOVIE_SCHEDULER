package viffpdf;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.ILeafElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.FontKerning;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.renderer.CellRenderer;
import com.itextpdf.layout.renderer.DrawContext;
import com.itextpdf.layout.renderer.IRenderer;
import com.itextpdf.layout.renderer.TableRenderer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.TreeMap;

public class PDFGenerator {
  String destPath;
  
  int venueFontSize;
  
  Color dColor;
  
  Color bColor;
  
  Color vColor;
  
  Color sColor;
  
  Color hColor;
  
  Color fColor;
  
  Color oColor;
  
  Color eColor;
  
  int masterFont;
  
  float rowHeight;
  
  PdfFont font;
  
  ArrayList<PageTable> pageList = new ArrayList<>();
  
  ArrayList<VenueTable> venueList = new ArrayList<>();
  
  ArrayList<VenueDateTable> screenTimeList = new ArrayList<>();
  
  ArrayList<DayTable> dayList = new ArrayList<>();
  
  ArrayList<Date> dateList = new ArrayList<>();
  
  HashMap<String, ColorData> colorList;
  
  TreeMap<String, SectionData> sectionList;
  
  private final int TABLE_MARGIN = 3;
  
  private final int number_of_columns = 1080;
  
  private static final float PAGE_WIDTH = 651.0F;
  
  private static final float PAGE_HEIGHT = 736.0F;
  
  private static final int QUARTER_HOUR = 15;
  
  private static final int HALF_HOUR = 30;
  
  private static final int THREE_QUARTERS_HOUR = 45;
  
  private static final int HOUR = 60;
  
  public PDFGenerator(String dest, AllTable table, Configuration config) throws IOException {
    setDest(dest);
    File file = new File(dest);
    file.getParentFile().mkdirs();
    this.pageList = table.PList;
    this.dayList = table.DList;
    this.venueList = table.VTList;
    this.screenTimeList = table.VDTList;
    this.dateList = table.dateList;
    this.venueFontSize = config.venue_font_Size;
    this.dColor = config.dColor;
    this.bColor = config.bColor;
    this.vColor = config.vColor;
    this.sColor = config.sColor;
    this.hColor = config.hColor;
    this.fColor = config.fColor;
    this.oColor = config.oColor;
    this.eColor = config.eColor;
    this.rowHeight = ((VenueDateTable)this.screenTimeList.get(0)).thisHeight;
    this.colorList = table.colorList;
    this.sectionList = table.sectionList;
    fontLib fontLib = new fontLib();
    ArrayList<PdfFont> fonts = fontLib.fonts;
    this.font = fonts.get(config.masterFont);
    generate();
  }
  
  public void setDest(String dest) {
    if (!dest.endsWith(".pdf"))
      dest = String.valueOf(dest) + ".pdf"; 
    this.destPath = dest;
  }
  
  public void generate() throws FileNotFoundException {
    PdfWriter writer = new PdfWriter(this.destPath);
    PdfDocument pdf = new PdfDocument(writer);
    pdf.addEventHandler("StartPdfPage", new PageBackgroundsEventHandler());
    Document document = new Document(pdf, (new PageSize(651.0F, 736.0F)).rotate());
    document.setFontProvider(document.getFontProvider());
    document.setMargins(5.0F, 5.0F, 0.0F, 5.0F);
    SimpleDateFormat fmt = new SimpleDateFormat("EEEEEEE, MMMMMMMM dd", Locale.US);
    PageTable.pageCount = 1;
    PageTable.dayCount = 0;
    for (PageTable pt : this.pageList) {
      float heightCounter = 0.0F;
      for (int i = 0; i < pt.numOfDays; i++) {
        Table dayTable = createSchedule(pt.dayList.get(i), fmt.format(((DayTable)pt.dayList.get(i)).dayDate));
        document.add((IBlockElement)dayTable);
        heightCounter += pt.thisHeight;
      } 
      if (pt.thisHeight <= 651.0F) {
        Table rect = new Table(1080);
        rect.useAllAvailableWidth().setHeight(651.0F - pt.thisHeight);
        rect.setBackgroundColor(ColorConstants.BLACK);
        heightCounter += 651.0F - pt.thisHeight;
        document.add((IBlockElement)rect);
      } 
      System.out.println("Page height counter: " + pt.thisHeight);
      System.out.println(651.0F);
    } 
    document.close();
  }
  
  protected class PageBackgroundsEventHandler implements IEventHandler {
    public void handleEvent(Event event) {
      PdfDocumentEvent docEvent = (PdfDocumentEvent)event;
      PdfPage page = docEvent.getPage();
      PdfCanvas canvas = new PdfCanvas(page);
      Rectangle rect = page.getPageSize();
      canvas.saveState().setFillColor(ColorConstants.BLACK)
        .rectangle(rect.getLeft(), rect.getBottom(), rect.getWidth(), rect.getHeight()).fillStroke()
        .restoreState();
    }
  }
  
  private Table createSchedule(DayTable table, String date) {
    float heightCounter = 0.0F;
    DashedBorder dashedBorder1 = new DashedBorder(0.5F);
    DashedBorder dashedBorder2 = new DashedBorder(0.75F);
    int number_of_columns = 1080;
    int max_table_width = 960;
    String[] times = { 
        "10:00AM", "11:00AM", "12:00PM", "1:00PM", "2:00PM", "3:00PM", "4:00PM", "5:00PM", "6:00PM", 
        "7:00PM", 
        "8:00PM", "9:00PM", "10:00PM", "11:00PM", "12:00AM", "1:00AM" };
    Table schedule_table = new Table(number_of_columns);
    schedule_table.setKeepTogether(true);
    ((Table)((Table)schedule_table.useAllAvailableWidth().setTextAlignment(TextAlignment.CENTER))
      .setHorizontalAlignment(HorizontalAlignment.CENTER)).setMarginBottom(3.0F);
    schedule_table.setBorder(Border.NO_BORDER);
    Cell dateCell = createDateCell(number_of_columns, date);
    schedule_table.addHeaderCell(dateCell);
    heightCounter += this.rowHeight;
    Cell cell = (Cell)((Cell)((Cell)(new Cell(1, 120)).setBackgroundColor(this.bColor)).setPadding(0.0F)).setBorder(Border.NO_BORDER);
    schedule_table.addHeaderCell(cell);
    heightCounter += this.rowHeight;
    for (int i = 0; i < times.length; i++)
      schedule_table.addHeaderCell(createTimeCell(times[i])); 
    for (VenueDateTable vdt : table.venueSCTList) {
      heightCounter += this.rowHeight;
      Cell vdtCell = new Cell(1, 120);
      vdtCell.setKeepTogether(true);
      vdtCell.setNextRenderer((IRenderer)new FoldedBorderCellRenderer(vdtCell));
      ((Cell)vdtCell.add((IBlockElement)((Paragraph)((Paragraph)((Paragraph)((Paragraph)((Paragraph)(new Paragraph(vdt.thisVenue.getNameShort())).setWidth(schedule_table.getColumnWidth(0)))
          .setFontSize(this.venueFontSize)).setFont(this.font)).setTextAlignment(TextAlignment.CENTER)).setBold())
          .setFontColor(ColorConstants.BLACK)).setPadding(0.0F)).setMargin(0.0F);
      vdtCell.setTextAlignment(TextAlignment.CENTER);
      vdtCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
      vdtCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
      ((Cell)vdtCell.setHeight(this.rowHeight)).setBorder(Border.NO_BORDER);
      vdtCell.setBorderBottom(Border.NO_BORDER);
      schedule_table.addCell(vdtCell);
      int minCounter = 0;
      for (ScreenTimeData sct : vdt.thisVDT) {
        int movieStartBlock = sct.getStartBlock();
        while (movieStartBlock > minCounter) {
          if (minCounter % 15 != 0) {
            Cell cell1 = (Cell)((Cell)((Cell)((Cell)((Cell)((Cell)(new Cell(1, 15 - minCounter % 15)).setPadding(0.0F)).setMargin(0.0F))
              .setBorderTop(Border.NO_BORDER)).setBorderLeft(Border.NO_BORDER))
              .setBorderRight(Border.NO_BORDER)).setBorderBottom(Border.NO_BORDER);
            cell1.setHeight(this.rowHeight);
            cell1.setMargin(0.0F);
            cell1.setPadding(0.0F);
            cell1.setKeepTogether(true);
            if ((minCounter - 30 + 15 - minCounter % 15) % 60 == 0) {
              cell1.setBorderRight((Border)dashedBorder2);
            } else {
              cell1.setBorderRight((Border)dashedBorder1);
            } 
            if (table.venueSCTList.indexOf(vdt) % 2 == 0) {
              cell1.setBackgroundColor(this.eColor);
            } else {
              cell1.setBackgroundColor(this.oColor);
            } 
            minCounter += 15 - minCounter % 15;
            schedule_table.addCell(cell1);
          } 
          Cell emptyCell = (Cell)((Cell)(new Cell(1, 15)).setPadding(0.0F)).setMargin(0.0F);
          emptyCell.setKeepTogether(true);
          emptyCell.setHeight(this.rowHeight);
          ((Cell)((Cell)((Cell)emptyCell.setBorderTop(Border.NO_BORDER)).setBorderBottom(Border.NO_BORDER))
            .setBorderLeft(Border.NO_BORDER)).setBorderRight(Border.NO_BORDER);
          if ((minCounter - 30 + 15) % 60 == 0) {
            emptyCell.setBorderRight((Border)dashedBorder2);
          } else {
            emptyCell.setBorderRight((Border)dashedBorder1);
          } 
          if (table.venueSCTList.indexOf(vdt) % 2 == 0) {
            emptyCell.setBackgroundColor(this.eColor);
          } else {
            emptyCell.setBackgroundColor(this.oColor);
          } 
          schedule_table.addCell(emptyCell);
          minCounter += 15;
        } 
        Cell sctCell = new Cell(1, sct.getLengthMin());
        System.out.println("Minutes:" + sct.getLengthMin());
        sctCell.setKeepTogether(true);
        ((Cell)((Cell)((Cell)sctCell.setBorderTop(Border.NO_BORDER)).setBorderBottom(Border.NO_BORDER))
          .setBorderRight(Border.NO_BORDER)).setBorderLeft(Border.NO_BORDER);
        sctCell.setBorderLeft((Border)new SolidBorder(0.5F));
        sctCell.setBorderTop((Border)new SolidBorder(0.25F));
        sctCell.setBorderBottom((Border)new SolidBorder(0.25F));
        if (this.colorList.containsKey(sct.getSectionCode())) {
          sctCell.setNextRenderer(
              (IRenderer)new ColoredCellRenderer(sctCell, ((ColorData)this.colorList.get(sct.getSectionCode())).getColor()));
        } else {
          sctCell.setBackgroundColor(this.sColor);
        } 
        Paragraph p = new Paragraph();
        ((Paragraph)p.setPadding(0.0F)).setMargin(0.0F);
        Text name = new Text(sct.getMovieName());
        ((Text)((Text)((Text)((Text)((Text)name.setFontSize(this.venueFontSize - 2.0F)).setFont(this.font)).setFontColor(this.fColor))
          .setTextAlignment(TextAlignment.CENTER)).setFontKerning(FontKerning.NO)).setWordSpacing(0.0F);
        Text time = new Text("\n" + sct.getTimeDetails());
        ((Text)((Text)((Text)((Text)((Text)time.setFontSize(this.venueFontSize - 3.0F)).setFont(this.font))
          .setTextAlignment(TextAlignment.CENTER)).setFontKerning(FontKerning.NO)).setWordSpacing(0.0F))
          .setFontColor(ColorConstants.GRAY);
        p.setMultipliedLeading(0.9F);
        p.add((ILeafElement)name);
        p.add((ILeafElement)time);
        sctCell.add((IBlockElement)p);
        sctCell.setHeight(this.rowHeight);
        ((Cell)((Cell)sctCell.setPadding(0.0F)).setMargin(0.0F)).setMaxWidth(0.0F);
        sctCell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        sctCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        minCounter += sct.getLengthMin();
        schedule_table.addCell(sctCell);
      } 
      while (minCounter < max_table_width) {
        if (minCounter % 15 != 0) {
          Cell cell1 = (Cell)((Cell)((Cell)((Cell)((Cell)(new Cell(1, 15 - minCounter % 15)).setPadding(0.0F)).setMargin(0.0F))
            .setBorder(Border.NO_BORDER)).setBorderTop(Border.NO_BORDER))
            .setBorderBottom(Border.NO_BORDER);
          cell1.setHeight(this.rowHeight);
          cell1.setKeepTogether(true);
          if ((minCounter - 30 + 15 - minCounter % 15) % 60 == 0) {
            cell1.setBorderRight((Border)dashedBorder2);
          } else {
            cell1.setBorderRight((Border)dashedBorder1);
          } 
          if (table.venueSCTList.indexOf(vdt) % 2 == 0) {
            cell1.setBackgroundColor(this.eColor);
          } else {
            cell1.setBackgroundColor(this.oColor);
          } 
          minCounter += 15 - minCounter % 15;
          schedule_table.addCell(cell1);
        } 
        Cell fillCell = (Cell)((Cell)(new Cell(1, 15)).setPadding(0.0F)).setMargin(0.0F);
        fillCell.setHeight(this.rowHeight);
        ((Cell)((Cell)((Cell)fillCell.setBorderTop(Border.NO_BORDER)).setBorderBottom(Border.NO_BORDER))
          .setBorderLeft(Border.NO_BORDER)).setBorderRight(Border.NO_BORDER);
        fillCell.setBorder(Border.NO_BORDER);
        if (minCounter + 15 < 960)
          if ((minCounter - 30 + 15) % 60 == 0) {
            fillCell.setBorderRight((Border)dashedBorder2);
          } else {
            fillCell.setBorderRight((Border)dashedBorder1);
          }  
        fillCell.setKeepTogether(true);
        minCounter += 15;
        if (table.venueSCTList.indexOf(vdt) % 2 == 0) {
          fillCell.setBackgroundColor(this.eColor);
        } else {
          fillCell.setBackgroundColor(this.oColor);
        } 
        schedule_table.addCell(fillCell);
      } 
      schedule_table.startNewRow();
    } 
    System.out.println("Day Height: " + heightCounter);
    schedule_table.setNextRenderer((IRenderer)new tableBackgroundRenderer(schedule_table, table));
    return schedule_table;
  }
  
  private Cell createDateCell(int cellWidth, String date) {
    Cell cell = new Cell(1, cellWidth);
    cell.setHeight(this.rowHeight);
    cell.setKeepTogether(true);
    cell.setPadding(0.0F);
    cell.setMargin(0.0F);
    cell.setBorder(Border.NO_BORDER);
    cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
    cell.add((IBlockElement)((Paragraph)((Paragraph)((Paragraph)((Paragraph)(new Paragraph(date)).setFontSize(this.venueFontSize)).setBold()).setFontColor(this.hColor)).setPadding(0.0F))
        .setMargin(0.0F));
    ((Cell)((Cell)((Cell)cell.setTextAlignment(TextAlignment.LEFT)).setBackgroundColor(this.dColor)).setPadding(0.0F)).setPaddingLeft(10.0F);
    return cell;
  }
  
  private Cell createTimeCell(String time) {
    Cell cell = new Cell(1, 60);
    cell.setKeepTogether(true);
    cell.setBorder(Border.NO_BORDER);
    cell.setHeight(this.rowHeight);
    cell.setPadding(0.0F);
    cell.setMargin(0.0F);
    cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
    cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
    ((Cell)((Cell)((Cell)((Cell)((Cell)cell.add((IBlockElement)new Paragraph(time)).setFontSize(this.venueFontSize)).setBold()).setPadding(0.0F)).setMargin(0.0F))
      .setFontColor(ColorConstants.WHITE)).setBackgroundColor(this.bColor);
    return cell;
  }
  
  private class FoldedBorderCellRenderer extends CellRenderer {
    public FoldedBorderCellRenderer(Cell modelElement) {
      super(modelElement);
    }
    
    public void draw(DrawContext drawContext) {
      PdfCanvas canvas = drawContext.getCanvas();
      canvas.saveState().setFillColor(PDFGenerator.this.vColor);
      Rectangle cellRect = getOccupiedAreaBBox();
      canvas.moveTo(cellRect.getX(), (cellRect.getY() + cellRect.getHeight()));
      canvas.lineTo((cellRect.getX() + cellRect.getWidth()), (cellRect.getY() + cellRect.getHeight()));
      canvas.lineTo((cellRect.getX() + cellRect.getWidth()), cellRect.getY());
      canvas.lineTo((cellRect.getX() + 5.0F), cellRect.getY());
      canvas.lineTo(cellRect.getX(), (cellRect.getY() + 5.0F));
      canvas.closePathFillStroke().restoreState();
      canvas.saveState().setFillColor(ColorConstants.BLACK);
      canvas.moveTo(cellRect.getX(), cellRect.getY());
      canvas.lineTo((cellRect.getX() + 5.0F), cellRect.getY());
      canvas.lineTo(cellRect.getX(), (cellRect.getY() + 5.0F));
      canvas.closePathFillStroke().restoreState();
      super.draw(drawContext);
    }
  }
  
  private class ColoredCellRenderer extends CellRenderer {
    private Color color;
    
    public ColoredCellRenderer(Cell modelElement, Color color) {
      super(modelElement);
      this.color = color;
    }
    
    public void draw(DrawContext drawContext) {
      PdfCanvas canvas = drawContext.getCanvas();
      Rectangle cellRect = getOccupiedAreaBBox();
      canvas.saveState().setFillColor(PDFGenerator.this.sColor).setStrokeColor(PDFGenerator.this.sColor).setLineWidth(0.5F);
      float borderMargin = 0.25F;
      canvas.moveTo(cellRect.getX(), cellRect.getY());
      canvas.lineTo((cellRect.getX() + cellRect.getWidth()), cellRect.getY());
      canvas.lineTo((cellRect.getX() + cellRect.getWidth()), (cellRect.getY() + cellRect.getHeight()));
      canvas.lineTo(cellRect.getX(), (cellRect.getY() + cellRect.getHeight()));
      canvas.lineTo(cellRect.getX(), cellRect.getY());
      canvas.closePathFillStroke().restoreState();
      canvas.saveState().setFillColor(this.color).setStrokeColor(this.color);
      canvas.rectangle((cellRect.getX() + cellRect.getWidth() - 2.0F), (cellRect.getY() + borderMargin), 1.8D, (
          cellRect.getHeight() - 2.0F * borderMargin));
      canvas.fillStroke().restoreState();
      super.draw(drawContext);
    }
  }
  
  private class tableBackgroundRenderer extends TableRenderer {
    DayTable day = null;
    
    public tableBackgroundRenderer(Table modelElement, DayTable day) {
      super(modelElement);
      this.day = day;
    }
    
    public void drawBackground(DrawContext drawContext) {
      PdfCanvas canvas = drawContext.getCanvas();
      Rectangle cellRect = getOccupiedAreaBBox();
      double heightCounter = 3.85D;
      double rowHeight1 = PDFGenerator.this.rowHeight + 0.15D;
      int rowCounter = 0;
      for (VenueDateTable vdt : this.day.venueSCTList) {
        if (rowCounter % 2 == 0) {
          canvas.saveState().setFillColor(PDFGenerator.this.eColor).setStrokeColor(PDFGenerator.this.eColor);
          canvas.rectangle(cellRect.getX(), cellRect.getY() + heightCounter, (cellRect.getWidth() - 2.0F), 
              rowHeight1);
          canvas.fillStroke().restoreState();
        } else if (rowCounter % 2 != 0) {
          canvas.saveState().setFillColor(PDFGenerator.this.oColor).setStrokeColor(PDFGenerator.this.oColor);
          canvas.rectangle(cellRect.getX(), cellRect.getY() + heightCounter, (cellRect.getWidth() - 2.0F), 
              rowHeight1);
          canvas.fillStroke().restoreState();
        } 
        heightCounter += rowHeight1;
        rowCounter++;
      } 
    }
  }
}

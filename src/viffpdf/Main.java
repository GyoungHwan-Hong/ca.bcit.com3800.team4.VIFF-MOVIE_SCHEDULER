package viffpdf;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceRgb;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Main extends Application {
  static Configuration config = null;
  
  static configSave saveFile = new configSave();
  
  static Color dColor = Color.rgb(255, 165, 0);
  
  static Color bColor = Color.rgb(0, 0, 0);
  
  static Color vColor = Color.rgb(255, 165, 0);
  
  static Color sColor = Color.rgb(255, 255, 255);
  
  static Color hColor = Color.rgb(255, 255, 255);
  
  static Color fColor = Color.rgb(0, 0, 0);
  
  static Color oColor = Color.GRAY;
  
  static Color eColor = Color.DARKGRAY;
  
  static Color dColorConfig = (Color)new DeviceRgb(255, 165, 0);
  
  static Color bColorConfig = (Color)new DeviceRgb(0, 0, 0);
  
  static Color vColorConfig = (Color)new DeviceRgb(255, 165, 0);
  
  static Color sColorConfig = (Color)new DeviceRgb(255, 255, 255);
  
  static Color fColorConfig = (Color)new DeviceRgb(0, 0, 0);
  
  static Color hColorConfig = (Color)new DeviceRgb(255, 255, 255);
  
  static Color oColorConfig = ColorConstants.GRAY;
  
  static Color eColorConfig = ColorConstants.DARK_GRAY;
  
  static ArrayList<NumField> daysPerPageInput = new ArrayList<>();
  
  static TextField rowHeightConfigInput = new TextField("13");
  
  static Text colorStat;
  
  static Text sectionStat;
  
  static Text venueStat;
  
  static Text screenTimeStat;
  
  static TextFlow logArea;
  
  static RadioButton checkEmpty;
  
  static int masterFont;
  
  static File colorTab;
  
  static File sectionTab;
  
  static File venueTab;
  
  static File screenTimeTab;
  
  static int[] pageLayoutSetting = new int[] { 4, 4, 4, 4, 4, 4, 4, 4 };
  
  static boolean[] emptyRowSetting = new boolean[8];
  
  HashMap<String, ColorData> colorList;
  
  TreeMap<String, SectionData> sectionList;
  
  private ArrayList<Button> fileTypes = new ArrayList<>(4);
  
  private ArrayList<Parser> parsers = new ArrayList<>(4);
  
  private ArrayList<Text> fileStats = new ArrayList<>(4);
  
  private static final int SECTIONS = 0;
  
  private static final int COLORS = 1;
  
  private static final int VENUES = 2;
  
  private static final int SCREEN_TIMES = 3;
  
  public void loaderButton(ActionEvent event, Stage primaryStage) {
    FileChooser fileChooser = new FileChooser();
    File file = fileChooser.showOpenDialog((Window)primaryStage);
    if (file != null) {
      int fileType = this.fileTypes.indexOf(event.getSource());
      Status.print("Opening: " + file.getAbsolutePath());
      try {
        Parser parser = this.parsers.get(fileType);
        Status.print("Loading: " + parser.getFileType());
        Status.printWarning(parser.setData(file));
        Status.print(String.valueOf(parser.getFileType()) + " file opened properly!");
        ((Text)this.fileStats.get(fileType)).setText("Passed");
      } catch (FileNotFoundException e) {
        Status.printError("File Not Found.  Details:");
        Status.printError(e.getMessage());
        ((Text)this.fileStats.get(fileType)).setText("Failed");
      } catch (IllegalArgumentException e) {
        Status.printError("File Not Valid.  Details:");
        Status.printError(e.getMessage());
        ((Text)this.fileStats.get(fileType)).setText("Failed");
      } 
    } else {
      Status.printError("File Open Cancelled.");
    } 
  }
  
  public static void pageSettingUI(Stage primaryStage) {
    GridPane pageSettingLayOut = new GridPane();
    pageSettingLayOut.setHgap(10.0D);
    pageSettingLayOut.setVgap(10.0D);
    Scene pageSettingScene = new Scene((Parent)pageSettingLayOut, 300.0D, 400.0D);
    Text pageNumberText = new Text("Page#");
    pageNumberText.setFont(Font.font("Verdana", FontWeight.BOLD, 15.0D));
    Text daysPerPageText = new Text("Days/Page");
    daysPerPageText.setFont(Font.font("Verdana", FontWeight.BOLD, 15.0D));
    pageSettingLayOut.add((Node)daysPerPageText, 6, 1);
    pageSettingLayOut.add((Node)pageNumberText, 1, 1);
    for (int i = 2; i < 10; i++) {
      Text text = new Text("Page " + (i - 1));
      text.setTextAlignment(TextAlignment.CENTER);
      pageSettingLayOut.add((Node)text, 1, i);
    } 
    daysPerPageInput = new ArrayList<>();
    String[] varNames = { "page1", "page2", "page3", "page4", "page5", "page6", "page7", "page8" };
    for (int n = 2; n < 10; n++) {
      NumField textField = new NumField();
      String defTxt = (n < 10) ? "4" : "0";
      textField.setText(defTxt);
      textField.setId(varNames[n - 2]);
      pageSettingLayOut.add((Node)textField, 6, n);
      daysPerPageInput.add(textField);
    } 
    final Button applyPageSetting = new Button("Apply");
    applyPageSetting.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent arg0) {
            Status.print("Applying days/page setting...");
            for (NumField n : Main.daysPerPageInput) {
              if (n.getText().isEmpty())
                n.setText("0"); 
              Main.pageLayoutSetting[Main.daysPerPageInput.indexOf(n)] = Integer.parseInt(n.getText());
            } 
            Status.print("Days/page setting successfully applied.");
            int counter = 1;
            byte b;
            int i, arrayOfInt[];
            for (i = (arrayOfInt = Main.pageLayoutSetting).length, b = 0; b < i; ) {
              int num = arrayOfInt[b];
              if (num > 0)
                Status.print("Days on page " + counter + ": " + num); 
              counter++;
              b++;
            } 
            Stage stage = (Stage)applyPageSetting.getScene().getWindow();
            stage.hide();
          }
        });
    pageSettingLayOut.add((Node)applyPageSetting, 6, 11);
    Stage pageSettingWindow = new Stage();
    pageSettingWindow.setTitle("Page Setting");
    pageSettingWindow.setResizable(false);
    pageSettingWindow.setScene(pageSettingScene);
    pageSettingWindow.setX(primaryStage.getX() + 50.0D);
    pageSettingWindow.setY(primaryStage.getY() + 10.0D);
    pageSettingWindow.initModality(Modality.APPLICATION_MODAL);
    pageSettingWindow.show();
  }
  
  public void start(final Stage primaryStage) {
    this.parsers.add(0, new SectionParser());
    this.parsers.add(1, new ColorParser());
    this.parsers.add(2, new VenueParser());
    this.parsers.add(3, new ScreenTimeParser());
    rowHeightConfigInput.setMaxWidth(50.0D);
    saveFile.set('d', rgbToCmyk(dColor));
    saveFile.set('b', rgbToCmyk(bColor));
    saveFile.set('v', rgbToCmyk(vColor));
    saveFile.set('s', rgbToCmyk(sColor));
    saveFile.set('h', rgbToCmyk(hColor));
    saveFile.set('f', rgbToCmyk(fColor));
    saveFile.set('o', rgbToCmyk(oColor));
    saveFile.set('e', rgbToCmyk(eColor));
    GridPane loaderGroup = new GridPane();
    loaderGroup.setHgap(10.0D);
    loaderGroup.setVgap(10.0D);
    Button loadColor = new Button("Colors");
    loadColor.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {
            Main.this.loaderButton(e, primaryStage);
          }
        });
    Button loadSection = new Button("Sections");
    loadSection.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {
            Main.this.loaderButton(e, primaryStage);
          }
        });
    Button loadVenue = new Button("Venues");
    loadVenue.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {
            Main.this.loaderButton(e, primaryStage);
          }
        });
    Button loadScreenTime = new Button("Screen Times");
    loadScreenTime.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent e) {
            Main.this.loaderButton(e, primaryStage);
          }
        });
    this.fileTypes.add(loadSection);
    this.fileTypes.add(loadColor);
    this.fileTypes.add(loadVenue);
    this.fileTypes.add(loadScreenTime);
    Text loaderTitle = new Text("Input:");
    loaderTitle.setStyle("-fx-font-weight: bold");
    colorStat = new Text("N/A");
    sectionStat = new Text("N/A");
    venueStat = new Text("N/A");
    screenTimeStat = new Text("N/A");
    this.fileStats.add(0, sectionStat);
    this.fileStats.add(1, colorStat);
    this.fileStats.add(2, venueStat);
    this.fileStats.add(3, screenTimeStat);
    loaderGroup.add((Node)loaderTitle, 1, 0);
    loaderGroup.add((Node)loadColor, 1, 1);
    loaderGroup.add((Node)colorStat, 1, 2);
    loaderGroup.add((Node)loadSection, 2, 1);
    loaderGroup.add((Node)sectionStat, 2, 2);
    loaderGroup.add((Node)loadVenue, 3, 1);
    loaderGroup.add((Node)venueStat, 3, 2);
    loaderGroup.add((Node)loadScreenTime, 4, 1);
    loaderGroup.add((Node)screenTimeStat, 4, 2);
    GridPane.setHalignment((Node)colorStat, HPos.CENTER);
    GridPane.setHalignment((Node)sectionStat, HPos.CENTER);
    GridPane.setHalignment((Node)venueStat, HPos.CENTER);
    GridPane.setHalignment((Node)screenTimeStat, HPos.CENTER);
    GridPane theme = new GridPane();
    theme.setVgap(10.0D);
    theme.setHgap(5.0D);
    Text themeTitle = new Text("Color Config:");
    themeTitle.setStyle("-fx-font-weight: bold");
    Text dayHeader = new Text("Date Bar");
    Text background = new Text("Time Bar");
    Text venue = new Text("Venue Bar");
    Text screenTime = new Text("Movie Block");
    Text dayHeaderText = new Text("Date Text");
    Text font = new Text("Movie Text");
    Text oddRow = new Text("Odd Row");
    Text evenRow = new Text("Even Row");
    final ColorPicker dayPicker = new ColorPicker();
    dayPicker.setMaxWidth(40.0D);
    dayPicker.setValue(dColor);
    dayPicker.setOnAction(new EventHandler() {
          public void handle(Event arg0) {
            ArrayList<Double> list = Main.rgbToCmyk((Color)dayPicker.getValue());
            float c1 = ((Double)list.get(0)).floatValue();
            float m1 = ((Double)list.get(1)).floatValue();
            float y1 = ((Double)list.get(2)).floatValue();
            float k1 = ((Double)list.get(3)).floatValue();
            Main.saveFile.set('d', list);
            Main.dColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
          }
        });
    final ColorPicker backPicker = new ColorPicker();
    backPicker.setMaxWidth(40.0D);
    backPicker.setValue(bColor);
    backPicker.setOnAction(new EventHandler() {
          public void handle(Event arg0) {
            ArrayList<Double> list = Main.rgbToCmyk((Color)backPicker.getValue());
            float c1 = ((Double)list.get(0)).floatValue();
            float m1 = ((Double)list.get(1)).floatValue();
            float y1 = ((Double)list.get(2)).floatValue();
            float k1 = ((Double)list.get(3)).floatValue();
            Main.saveFile.set('b', list);
            Main.bColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
          }
        });
    final ColorPicker venuePicker = new ColorPicker();
    venuePicker.setMaxWidth(40.0D);
    venuePicker.setValue(vColor);
    venuePicker.setOnAction(new EventHandler() {
          public void handle(Event arg0) {
            ArrayList<Double> list = Main.rgbToCmyk((Color)venuePicker.getValue());
            float c1 = ((Double)list.get(0)).floatValue();
            float m1 = ((Double)list.get(1)).floatValue();
            float y1 = ((Double)list.get(2)).floatValue();
            float k1 = ((Double)list.get(3)).floatValue();
            Main.saveFile.set('v', list);
            Main.vColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
          }
        });
    final ColorPicker screenPicker = new ColorPicker();
    screenPicker.setMaxWidth(40.0D);
    screenPicker.setValue(sColor);
    screenPicker.setOnAction(new EventHandler() {
          public void handle(Event arg0) {
            ArrayList<Double> list = Main.rgbToCmyk((Color)screenPicker.getValue());
            float c1 = ((Double)list.get(0)).floatValue();
            float m1 = ((Double)list.get(1)).floatValue();
            float y1 = ((Double)list.get(2)).floatValue();
            float k1 = ((Double)list.get(3)).floatValue();
            Main.saveFile.set('s', list);
            Main.sColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
          }
        });
    final ColorPicker dayTextPicker = new ColorPicker();
    dayTextPicker.setMaxWidth(40.0D);
    dayTextPicker.setValue(hColor);
    dayTextPicker.setOnAction(new EventHandler() {
          public void handle(Event arg0) {
            ArrayList<Double> list = Main.rgbToCmyk((Color)dayTextPicker.getValue());
            float c1 = ((Double)list.get(0)).floatValue();
            float m1 = ((Double)list.get(1)).floatValue();
            float y1 = ((Double)list.get(2)).floatValue();
            float k1 = ((Double)list.get(3)).floatValue();
            Main.saveFile.set('h', list);
            Main.hColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
          }
        });
    final ColorPicker fontPicker = new ColorPicker();
    fontPicker.setMaxWidth(40.0D);
    fontPicker.setValue(fColor);
    fontPicker.setOnAction(new EventHandler() {
          public void handle(Event arg0) {
            ArrayList<Double> list = Main.rgbToCmyk((Color)fontPicker.getValue());
            float c1 = ((Double)list.get(0)).floatValue();
            float m1 = ((Double)list.get(1)).floatValue();
            float y1 = ((Double)list.get(2)).floatValue();
            float k1 = ((Double)list.get(3)).floatValue();
            Main.saveFile.set('f', list);
            Main.fColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
          }
        });
    final ColorPicker oddPicker = new ColorPicker();
    oddPicker.setMaxWidth(40.0D);
    oddPicker.setValue(oColor);
    oddPicker.setOnAction(new EventHandler() {
          public void handle(Event arg0) {
            ArrayList<Double> list = Main.rgbToCmyk((Color)oddPicker.getValue());
            float c1 = ((Double)list.get(0)).floatValue();
            float m1 = ((Double)list.get(1)).floatValue();
            float y1 = ((Double)list.get(2)).floatValue();
            float k1 = ((Double)list.get(3)).floatValue();
            Main.saveFile.set('o', list);
            Main.oColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
          }
        });
    final ColorPicker evenPicker = new ColorPicker();
    evenPicker.setMaxWidth(40.0D);
    evenPicker.setValue(eColor);
    evenPicker.setOnAction(new EventHandler() {
          public void handle(Event arg0) {
            ArrayList<Double> list = Main.rgbToCmyk((Color)evenPicker.getValue());
            float c1 = ((Double)list.get(0)).floatValue();
            float m1 = ((Double)list.get(1)).floatValue();
            float y1 = ((Double)list.get(2)).floatValue();
            float k1 = ((Double)list.get(3)).floatValue();
            Main.saveFile.set('e', list);
            Main.eColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
          }
        });
    ArrayList<TextField> colorInputs = new ArrayList<>();
    final TextField dc = new TextField();
    dc.setMaxWidth(50.0D);
    colorInputs.add(dc);
    final TextField dm = new TextField();
    dm.setMaxWidth(50.0D);
    colorInputs.add(dm);
    final TextField dy = new TextField();
    dy.setMaxWidth(50.0D);
    colorInputs.add(dy);
    final TextField dk = new TextField();
    dk.setMaxWidth(50.0D);
    colorInputs.add(dk);
    final TextField bc = new TextField();
    bc.setMaxWidth(50.0D);
    colorInputs.add(bc);
    final TextField bm = new TextField();
    bm.setMaxWidth(50.0D);
    colorInputs.add(bm);
    final TextField by = new TextField();
    by.setMaxWidth(50.0D);
    colorInputs.add(by);
    final TextField bk = new TextField();
    bk.setMaxWidth(50.0D);
    colorInputs.add(bk);
    final TextField vc = new TextField();
    vc.setMaxWidth(50.0D);
    colorInputs.add(vc);
    final TextField vm = new TextField();
    vm.setMaxWidth(50.0D);
    colorInputs.add(vm);
    final TextField vy = new TextField();
    vy.setMaxWidth(50.0D);
    colorInputs.add(vy);
    final TextField vk = new TextField();
    vk.setMaxWidth(50.0D);
    colorInputs.add(vk);
    final TextField sc = new TextField();
    sc.setMaxWidth(50.0D);
    colorInputs.add(sc);
    final TextField sm = new TextField();
    sm.setMaxWidth(50.0D);
    colorInputs.add(sm);
    final TextField sy = new TextField();
    sy.setMaxWidth(50.0D);
    colorInputs.add(sy);
    final TextField sk = new TextField();
    sk.setMaxWidth(50.0D);
    colorInputs.add(sk);
    final TextField fc = new TextField();
    fc.setMaxWidth(50.0D);
    colorInputs.add(fc);
    final TextField fm = new TextField();
    fm.setMaxWidth(50.0D);
    colorInputs.add(fm);
    final TextField fy = new TextField();
    fy.setMaxWidth(50.0D);
    colorInputs.add(fy);
    final TextField fk = new TextField();
    fk.setMaxWidth(50.0D);
    colorInputs.add(fk);
    final TextField oc = new TextField();
    oc.setMaxWidth(50.0D);
    colorInputs.add(oc);
    final TextField om = new TextField();
    om.setMaxWidth(50.0D);
    colorInputs.add(om);
    final TextField oy = new TextField();
    oy.setMaxWidth(50.0D);
    colorInputs.add(oy);
    final TextField ok = new TextField();
    ok.setMaxWidth(50.0D);
    colorInputs.add(ok);
    final TextField ec = new TextField();
    ec.setMaxWidth(50.0D);
    colorInputs.add(ec);
    final TextField em = new TextField();
    em.setMaxWidth(50.0D);
    colorInputs.add(em);
    final TextField ey = new TextField();
    ey.setMaxWidth(50.0D);
    colorInputs.add(ey);
    final TextField ek = new TextField();
    ek.setMaxWidth(50.0D);
    colorInputs.add(ek);
    final TextField hc = new TextField();
    hc.setMaxWidth(50.0D);
    colorInputs.add(hc);
    final TextField hm = new TextField();
    hm.setMaxWidth(50.0D);
    colorInputs.add(hm);
    final TextField hy = new TextField();
    hy.setMaxWidth(50.0D);
    colorInputs.add(hy);
    final TextField hk = new TextField();
    hk.setMaxWidth(50.0D);
    colorInputs.add(hk);
    Button dB = new Button(");
    dB.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            Color test = null;
            try {
              double c = Double.parseDouble(dc.getText()) / 100.0D;
              double m = Double.parseDouble(dm.getText()) / 100.0D;
              double y = Double.parseDouble(dy.getText()) / 100.0D;
              double k = Double.parseDouble(dk.getText()) / 100.0D;
              test = Main.cmykToRgb(c, m, y, k);
            } catch (NumberFormatException e) {
              Status.printError("Please fill in all of the fields for cmyk.");
              return;
            } 
            if (test != null) {
              ArrayList<Double> l = new ArrayList<>();
              l.add(Double.valueOf(Double.parseDouble(dc.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(dm.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(dy.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(dk.getText()) / 100.0D));
              float c1 = ((Double)l.get(0)).floatValue();
              float m1 = ((Double)l.get(1)).floatValue();
              float y1 = ((Double)l.get(2)).floatValue();
              float k1 = ((Double)l.get(3)).floatValue();
              Main.dColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
              Main.saveFile.set('d', l);
            } 
            dayPicker.setValue(test);
          }
        });
    Button bB = new Button(");
    bB.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            Color test = null;
            try {
              double c = Double.parseDouble(bc.getText()) / 100.0D;
              double m = Double.parseDouble(bm.getText()) / 100.0D;
              double y = Double.parseDouble(by.getText()) / 100.0D;
              double k = Double.parseDouble(bk.getText()) / 100.0D;
              test = Main.cmykToRgb(c, m, y, k);
            } catch (NumberFormatException e) {
              Status.printError("Please fill in all of the fields for cmyk.");
              return;
            } 
            if (test != null) {
              ArrayList<Double> l = new ArrayList<>();
              l.add(Double.valueOf(Double.parseDouble(bc.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(bm.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(by.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(bk.getText()) / 100.0D));
              float c1 = ((Double)l.get(0)).floatValue();
              float m1 = ((Double)l.get(1)).floatValue();
              float y1 = ((Double)l.get(2)).floatValue();
              float k1 = ((Double)l.get(3)).floatValue();
              Main.bColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
              Main.saveFile.set('b', l);
            } 
            backPicker.setValue(test);
          }
        });
    Button vB = new Button(");
    vB.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            Color test = null;
            try {
              double c = Double.parseDouble(vc.getText()) / 100.0D;
              double m = Double.parseDouble(vm.getText()) / 100.0D;
              double y = Double.parseDouble(vy.getText()) / 100.0D;
              double k = Double.parseDouble(vk.getText()) / 100.0D;
              test = Main.cmykToRgb(c, m, y, k);
            } catch (NumberFormatException e) {
              Status.printError("Please fill in all of the fields for cmyk.");
              return;
            } 
            if (test != null) {
              ArrayList<Double> l = new ArrayList<>();
              l.add(Double.valueOf(Double.parseDouble(vc.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(vm.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(vy.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(vk.getText()) / 100.0D));
              float c1 = ((Double)l.get(0)).floatValue();
              float m1 = ((Double)l.get(1)).floatValue();
              float y1 = ((Double)l.get(2)).floatValue();
              float k1 = ((Double)l.get(3)).floatValue();
              Main.vColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
              Main.saveFile.set('v', l);
            } 
            venuePicker.setValue(test);
          }
        });
    Button sB = new Button(");
    sB.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            Color test = null;
            try {
              double c = Double.parseDouble(sc.getText()) / 100.0D;
              double m = Double.parseDouble(sm.getText()) / 100.0D;
              double y = Double.parseDouble(sy.getText()) / 100.0D;
              double k = Double.parseDouble(sk.getText()) / 100.0D;
              test = Main.cmykToRgb(c, m, y, k);
            } catch (NumberFormatException e) {
              Status.printError("Please fill in all of the fields for cmyk.");
              return;
            } 
            if (test != null) {
              ArrayList<Double> l = new ArrayList<>();
              l.add(Double.valueOf(Double.parseDouble(sc.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(sm.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(sy.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(sk.getText()) / 100.0D));
              float c1 = ((Double)l.get(0)).floatValue();
              float m1 = ((Double)l.get(1)).floatValue();
              float y1 = ((Double)l.get(2)).floatValue();
              float k1 = ((Double)l.get(3)).floatValue();
              Main.sColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
              Main.saveFile.set('s', l);
            } 
            screenPicker.setValue(test);
          }
        });
    Button hB = new Button(");
    hB.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            Color test = null;
            try {
              double c = Double.parseDouble(hc.getText()) / 100.0D;
              double m = Double.parseDouble(hm.getText()) / 100.0D;
              double y = Double.parseDouble(hy.getText()) / 100.0D;
              double k = Double.parseDouble(hk.getText()) / 100.0D;
              test = Main.cmykToRgb(c, m, y, k);
            } catch (NumberFormatException e) {
              Status.printError("Please fill in all of the fields for cmyk.");
              return;
            } 
            if (test != null) {
              ArrayList<Double> l = new ArrayList<>();
              l.add(Double.valueOf(Double.parseDouble(hc.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(hm.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(hy.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(hk.getText()) / 100.0D));
              float c1 = ((Double)l.get(0)).floatValue();
              float m1 = ((Double)l.get(1)).floatValue();
              float y1 = ((Double)l.get(2)).floatValue();
              float k1 = ((Double)l.get(3)).floatValue();
              Main.hColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
              Main.saveFile.set('h', l);
            } 
            dayTextPicker.setValue(test);
          }
        });
    Button fB = new Button(");
    fB.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            Color test = null;
            try {
              double c = Double.parseDouble(fc.getText()) / 100.0D;
              double m = Double.parseDouble(fm.getText()) / 100.0D;
              double y = Double.parseDouble(fy.getText()) / 100.0D;
              double k = Double.parseDouble(fk.getText()) / 100.0D;
              test = Main.cmykToRgb(c, m, y, k);
            } catch (NumberFormatException e) {
              Status.printError("Please fill in all of the fields for cmyk.");
              return;
            } 
            if (test != null) {
              ArrayList<Double> l = new ArrayList<>();
              l.add(Double.valueOf(Double.parseDouble(fc.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(fm.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(fy.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(fk.getText()) / 100.0D));
              float c1 = ((Double)l.get(0)).floatValue();
              float m1 = ((Double)l.get(1)).floatValue();
              float y1 = ((Double)l.get(2)).floatValue();
              float k1 = ((Double)l.get(3)).floatValue();
              Main.fColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
              Main.saveFile.set('f', l);
            } 
            fontPicker.setValue(test);
          }
        });
    Button oB = new Button(");
    oB.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            Color test = null;
            try {
              double c = Double.parseDouble(oc.getText()) / 100.0D;
              double m = Double.parseDouble(om.getText()) / 100.0D;
              double y = Double.parseDouble(oy.getText()) / 100.0D;
              double k = Double.parseDouble(ok.getText()) / 100.0D;
              test = Main.cmykToRgb(c, m, y, k);
            } catch (NumberFormatException e) {
              Status.printError("Please fill in all of the fields for cmyk.");
              return;
            } 
            if (test != null) {
              ArrayList<Double> l = new ArrayList<>();
              l.add(Double.valueOf(Double.parseDouble(oc.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(om.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(oy.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(ok.getText()) / 100.0D));
              float c1 = ((Double)l.get(0)).floatValue();
              float m1 = ((Double)l.get(1)).floatValue();
              float y1 = ((Double)l.get(2)).floatValue();
              float k1 = ((Double)l.get(3)).floatValue();
              Main.oColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
              Main.saveFile.set('o', l);
            } 
            oddPicker.setValue(test);
          }
        });
    Button eB = new Button(");
    eB.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            Color test = null;
            try {
              double c = Double.parseDouble(ec.getText()) / 100.0D;
              double m = Double.parseDouble(em.getText()) / 100.0D;
              double y = Double.parseDouble(ey.getText()) / 100.0D;
              double k = Double.parseDouble(ek.getText()) / 100.0D;
              test = Main.cmykToRgb(c, m, y, k);
            } catch (NumberFormatException e) {
              Status.printError("Please fill in all of the fields for cmyk.");
              return;
            } 
            if (test != null) {
              ArrayList<Double> l = new ArrayList<>();
              l.add(Double.valueOf(Double.parseDouble(ec.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(em.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(ey.getText()) / 100.0D));
              l.add(Double.valueOf(Double.parseDouble(ek.getText()) / 100.0D));
              float c1 = ((Double)l.get(0)).floatValue();
              float m1 = ((Double)l.get(1)).floatValue();
              float y1 = ((Double)l.get(2)).floatValue();
              float k1 = ((Double)l.get(3)).floatValue();
              Main.eColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
              Main.saveFile.set('e', l);
            } 
            evenPicker.setValue(test);
          }
        });
    theme.add((Node)themeTitle, 0, 0);
    Text cLabel = new Text("      c");
    cLabel.setStyle("-fx-font-weight: bold");
    theme.add((Node)cLabel, 2, 0);
    Text mLabel = new Text("      m");
    mLabel.setStyle("-fx-font-weight: bold");
    theme.add((Node)mLabel, 3, 0);
    Text yLabel = new Text("      y");
    yLabel.setStyle("-fx-font-weight: bold");
    theme.add((Node)yLabel, 4, 0);
    Text kLabel = new Text("      k");
    kLabel.setStyle("-fx-font-weight: bold");
    theme.add((Node)kLabel, 5, 0);
    theme.add((Node)dayHeader, 0, 1);
    theme.add((Node)dayPicker, 1, 1);
    theme.add((Node)dc, 2, 1);
    theme.add((Node)dm, 3, 1);
    theme.add((Node)dy, 4, 1);
    theme.add((Node)dk, 5, 1);
    theme.add((Node)dB, 6, 1);
    theme.add((Node)background, 0, 2);
    theme.add((Node)backPicker, 1, 2);
    theme.add((Node)bc, 2, 2);
    theme.add((Node)bm, 3, 2);
    theme.add((Node)by, 4, 2);
    theme.add((Node)bk, 5, 2);
    theme.add((Node)bB, 6, 2);
    theme.add((Node)venue, 0, 3);
    theme.add((Node)venuePicker, 1, 3);
    theme.add((Node)vc, 2, 3);
    theme.add((Node)vm, 3, 3);
    theme.add((Node)vy, 4, 3);
    theme.add((Node)vk, 5, 3);
    theme.add((Node)vB, 6, 3);
    theme.add((Node)screenTime, 0, 4);
    theme.add((Node)screenPicker, 1, 4);
    theme.add((Node)sc, 2, 4);
    theme.add((Node)sm, 3, 4);
    theme.add((Node)sy, 4, 4);
    theme.add((Node)sk, 5, 4);
    theme.add((Node)sB, 6, 4);
    theme.add((Node)dayHeaderText, 0, 5);
    theme.add((Node)dayTextPicker, 1, 5);
    theme.add((Node)hc, 2, 5);
    theme.add((Node)hm, 3, 5);
    theme.add((Node)hy, 4, 5);
    theme.add((Node)hk, 5, 5);
    theme.add((Node)hB, 6, 5);
    theme.add((Node)font, 0, 6);
    theme.add((Node)fontPicker, 1, 6);
    theme.add((Node)fc, 2, 6);
    theme.add((Node)fm, 3, 6);
    theme.add((Node)fy, 4, 6);
    theme.add((Node)fk, 5, 6);
    theme.add((Node)fB, 6, 6);
    theme.add((Node)oddRow, 0, 7);
    theme.add((Node)oddPicker, 1, 7);
    theme.add((Node)oc, 2, 7);
    theme.add((Node)om, 3, 7);
    theme.add((Node)oy, 4, 7);
    theme.add((Node)ok, 5, 7);
    theme.add((Node)oB, 6, 7);
    theme.add((Node)evenRow, 0, 8);
    theme.add((Node)evenPicker, 1, 8);
    theme.add((Node)ec, 2, 8);
    theme.add((Node)em, 3, 8);
    theme.add((Node)ey, 4, 8);
    theme.add((Node)ek, 5, 8);
    theme.add((Node)eB, 6, 8);
    FlowPane log = new FlowPane();
    ScrollPane logWrapper = new ScrollPane();
    Text logTitle = new Text("Status: ");
    logTitle.setStyle("-fx-font-weight: bold");
    logArea = new TextFlow();
    logArea.setPrefHeight(355.0D);
    logArea.setPrefWidth(450.0D);
    logWrapper.setContent((Node)logArea);
    logWrapper.setBorder(new Border(new BorderStroke[] { new BorderStroke((Paint)Color.LIGHTGRAY, 
              BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT) }));
    log.getChildren().add(logTitle);
    log.getChildren().add(logWrapper);
    GridPane fontConfig = new GridPane();
    fontConfig.setHgap(10.0D);
    fontConfig.setVgap(10.0D);
    Text fontTitle = new Text("Font:");
    fontTitle.setStyle("-fx-font-weight: bold");
    Text fontFace = new Text("Font");
    final ObservableList<String> fonts = FXCollections.observableArrayList((Object[])new String[] { "Helvetica", "NeueHaas", "Calibri", "Arial", 
          "Garamond", "Geneva", "Verdana", "AvantGarde" });
    final ComboBox<String> fontBox = new ComboBox(fonts);
    fontBox.getSelectionModel().selectFirst();
    Text fontSize = new Text("Size");
    final ObservableList<Integer> sizes = FXCollections.observableArrayList();
    for (int i = 2; i < 33; i++)
      sizes.add(Integer.valueOf(i)); 
    final ComboBox<Integer> sizeBox = new ComboBox(sizes);
    sizeBox.getSelectionModel().select(5);
    sizeBox.setPrefWidth(100.0D);
    fontConfig.add((Node)fontTitle, 0, 0);
    fontConfig.add((Node)fontFace, 0, 1);
    fontConfig.add((Node)fontBox, 1, 1);
    fontConfig.add((Node)fontSize, 0, 2);
    fontConfig.add((Node)sizeBox, 1, 2);
    GridPane outPutGroup = new GridPane();
    Button save = new Button("Save Setting");
    Button load = new Button("Load Setting");
    Button export = new Button("Create PDF");
    export.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            ArrayList<VenueTable> VTList = new ArrayList<>();
            ArrayList<VenueDateTable> VDTList = new ArrayList<>();
            ArrayList<DayTable> DList = new ArrayList<>();
            ArrayList<PageTable> PList = new ArrayList<>();
            ArrayList<Date> dateList = new ArrayList<>();
            HashMap<String, VenueData> venueList = ((VenueParser)Main.this.parsers.get(2)).getVenueList();
            Main.this.colorList = ((ColorParser)Main.this.parsers.get(1)).getColorMap();
            Iterator<Map.Entry<String, ColorData>> itt = Main.this.colorList.entrySet().iterator();
            while (itt.hasNext()) {
              Map.Entry<String, ColorData> e = itt.next();
              System.out.println(((ColorData)e.getValue()).getColor());
            } 
            Main.this.sectionList = ((SectionParser)Main.this.parsers.get(0)).getSectionList();
            Iterator<Map.Entry<String, VenueData>> it = venueList.entrySet().iterator();
            while (it.hasNext()) {
              Map.Entry<String, VenueData> entry = it.next();
              VenueData venue = entry.getValue();
              VenueTable tableEntry = new VenueTable(venue, Main.this.parsers.get(3));
              VTList.add(tableEntry);
            } 
            for (VenueTable v : VTList) {
              for (ScreenTimeData s : v.thisTimeBlocks) {
                if (!dateList.contains(s.getDate()))
                  dateList.add(s.getDate()); 
              } 
            } 
            Collections.sort(dateList);
            for (VenueTable vt : VTList) {
              for (Date d : dateList) {
                VenueDateTable vdtEntry = new VenueDateTable(vt, d, 
                    Float.parseFloat(Main.rowHeightConfigInput.getText()));
                if (Main.checkEmpty.isSelected()) {
                  if (!vdtEntry.thisVDT.isEmpty())
                    VDTList.add(vdtEntry); 
                  continue;
                } 
                VDTList.add(vdtEntry);
              } 
            } 
            for (Date day : dateList) {
              DayTable temp = new DayTable(VDTList, day);
              DList.add(temp);
            } 
            int c = 1;
            int dayCounter = 0;
            byte b;
            int i, arrayOfInt[];
            for (i = (arrayOfInt = Main.pageLayoutSetting).length, b = 0; b < i; ) {
              int j = arrayOfInt[b];
              if (dayCounter < DList.size()) {
                PageTable pTable = new PageTable(DList, j, c++);
                PList.add(pTable);
                dayCounter += pTable.numOfDays;
                Status.print(String.valueOf(dayCounter) + " days successfully allocated.");
              } 
              b++;
            } 
            if (dayCounter <= dateList.size()) {
              int leftOver = dateList.size() - dayCounter;
              Status.print("After formatting, you have " + leftOver + " days left unallocated.");
            } 
            AllTable table = new AllTable(VTList, VDTList, DList, PList, dateList, Main.this.colorList, Main.this.sectionList);
            Main.masterFont = fonts.indexOf(fontBox.getValue());
            Main.config = new Configuration(((Integer)sizeBox.getValue()).intValue(), Main.dColorConfig, Main.bColorConfig, Main.vColorConfig, Main.sColorConfig, 
                Main.hColorConfig, Main.fColorConfig, Main.oColorConfig, Main.eColorConfig, Main.masterFont);
            try {
              FileChooser fileChooser = new FileChooser();
              File dest = fileChooser.showSaveDialog((Window)primaryStage);
              PDFGenerator pDFGenerator = new PDFGenerator(dest.getAbsolutePath(), 
                  table, Main.config);
            } catch (Exception e) {
              e.printStackTrace();
              Status.print("PDF creation failed. Please close opened PDF file if you're trying to override it.");
            } 
            PageTable.dayCount = 0;
            PageTable.pageCount = 1;
            DayTable.count = 0;
          }
        });
    save.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent arg0) {
            Main.saveFile.setFont(fonts.indexOf(fontBox.getValue()));
            Main.saveFile.setFontSize(((Integer)sizeBox.getValue()).intValue());
            try {
              FileChooser fileChooser = new FileChooser();
              File dest = fileChooser.showSaveDialog((Window)primaryStage);
              FileOutputStream file = new FileOutputStream(dest.getAbsolutePath());
              ObjectOutputStream out = new ObjectOutputStream(file);
              out.writeObject(Main.saveFile);
              out.close();
              file.close();
            } catch (IOException e) {
              return;
            } catch (NullPointerException e) {
              Status.printError("Saving cancelled.");
              return;
            } 
            Status.print("Setting saving successful.");
          }
        });
    load.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent arg0) {
            try {
              FileChooser fileChooser = new FileChooser();
              File dest = fileChooser.showOpenDialog((Window)primaryStage);
              FileInputStream file = new FileInputStream(dest.getAbsolutePath());
              ObjectInputStream in = new ObjectInputStream(file);
              configSave temp = (configSave)in.readObject();
              sizeBox.getSelectionModel().select(sizes.indexOf(Integer.valueOf(temp.vfs)));
              fontBox.getSelectionModel().select(temp.mf);
              ArrayList<Double> d = temp.getD();
              dayPicker.setValue(Main.cmykToRgb(d));
              float c1 = ((Double)d.get(0)).floatValue();
              float m1 = ((Double)d.get(1)).floatValue();
              float y1 = ((Double)d.get(2)).floatValue();
              float k1 = ((Double)d.get(3)).floatValue();
              Main.dColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
              Main.saveFile.set('d', d);
              ArrayList<Double> b = temp.getB();
              backPicker.setValue(Main.cmykToRgb(b));
              c1 = ((Double)b.get(0)).floatValue();
              m1 = ((Double)b.get(1)).floatValue();
              y1 = ((Double)b.get(2)).floatValue();
              k1 = ((Double)b.get(3)).floatValue();
              Main.bColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
              Main.saveFile.set('b', b);
              ArrayList<Double> v = temp.getV();
              venuePicker.setValue(Main.cmykToRgb(v));
              c1 = ((Double)v.get(0)).floatValue();
              m1 = ((Double)v.get(1)).floatValue();
              y1 = ((Double)v.get(2)).floatValue();
              k1 = ((Double)v.get(3)).floatValue();
              Main.vColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
              Main.saveFile.set('v', v);
              ArrayList<Double> s = temp.getS();
              screenPicker.setValue(Main.cmykToRgb(s));
              c1 = ((Double)s.get(0)).floatValue();
              m1 = ((Double)s.get(1)).floatValue();
              y1 = ((Double)s.get(2)).floatValue();
              k1 = ((Double)s.get(3)).floatValue();
              Main.sColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
              Main.saveFile.set('s', s);
              ArrayList<Double> h = temp.getH();
              dayTextPicker.setValue(Main.cmykToRgb(h));
              c1 = ((Double)h.get(0)).floatValue();
              m1 = ((Double)h.get(1)).floatValue();
              y1 = ((Double)h.get(2)).floatValue();
              k1 = ((Double)h.get(3)).floatValue();
              Main.hColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
              Main.saveFile.set('h', h);
              ArrayList<Double> f = temp.getF();
              fontPicker.setValue(Main.cmykToRgb(f));
              c1 = ((Double)f.get(0)).floatValue();
              m1 = ((Double)f.get(1)).floatValue();
              y1 = ((Double)f.get(2)).floatValue();
              k1 = ((Double)f.get(3)).floatValue();
              Main.fColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
              Main.saveFile.set('f', f);
              ArrayList<Double> o = temp.getO();
              oddPicker.setValue(Main.cmykToRgb(o));
              c1 = ((Double)o.get(0)).floatValue();
              m1 = ((Double)o.get(1)).floatValue();
              y1 = ((Double)o.get(2)).floatValue();
              k1 = ((Double)o.get(3)).floatValue();
              Main.oColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
              Main.saveFile.set('o', o);
              ArrayList<Double> e = temp.getE();
              evenPicker.setValue(Main.cmykToRgb(e));
              c1 = ((Double)e.get(0)).floatValue();
              m1 = ((Double)e.get(1)).floatValue();
              y1 = ((Double)e.get(2)).floatValue();
              k1 = ((Double)e.get(3)).floatValue();
              Main.eColorConfig = (Color)new DeviceCmyk(c1, m1, y1, k1);
              Main.saveFile.set('e', e);
            } catch (ClassNotFoundException e) {
              return;
            } catch (IOException e) {
              return;
            } catch (NullPointerException e) {
              Status.printError("Loading cancelled.");
              return;
            } 
            Status.print("Setting loading successful.");
          }
        });
    Button pageSetting = new Button("Page Setting");
    pageSetting.setOnAction(new EventHandler<ActionEvent>() {
          public void handle(ActionEvent arg0) {
            Main.pageSettingUI(primaryStage);
          }
        });
    outPutGroup.add((Node)export, 0, 0);
    outPutGroup.add((Node)pageSetting, 1, 0);
    outPutGroup.add((Node)save, 2, 0);
    outPutGroup.add((Node)load, 3, 0);
    GridPane timeBlockConfig = new GridPane();
    timeBlockConfig.setHgap(10.0D);
    timeBlockConfig.setVgap(10.0D);
    Text rowHeightConfig = new Text("Max Row Height:\n(13 recommended) ");
    rowHeightConfigInput.setPrefWidth(150.0D);
    Text timeBlockTitle = new Text("Line:");
    timeBlockTitle.setStyle("-fx-font-weight: bold");
    checkEmpty = new RadioButton("Clear Empty Rows");
    timeBlockConfig.add((Node)checkEmpty, 0, 2);
    timeBlockConfig.add((Node)rowHeightConfig, 0, 3);
    timeBlockConfig.add((Node)rowHeightConfigInput, 1, 3);
    timeBlockConfig.add((Node)timeBlockTitle, 0, 0);
    try {
      Group root = new Group();
      root.getChildren().add(loaderGroup);
      root.getChildren().add(theme);
      root.getChildren().add(log);
      root.getChildren().add(fontConfig);
      root.getChildren().add(outPutGroup);
      root.getChildren().add(timeBlockConfig);
      Scene scene = new Scene((Parent)root, 1000.0D, 500.0D);
      primaryStage.show();
      loaderGroup.setLayoutX(primaryStage.getWidth() - 950.0D);
      loaderGroup.setLayoutY(primaryStage.getHeight() - 590.0D);
      log.setLayoutX(primaryStage.getWidth() - 940.0D);
      log.setLayoutY(primaryStage.getHeight() - 500.0D);
      theme.setLayoutX(primaryStage.getWidth() - 400.0D);
      theme.setLayoutY(primaryStage.getHeight() - 590.0D);
      fontConfig.setLayoutX(primaryStage.getWidth() - 400.0D);
      fontConfig.setLayoutY(primaryStage.getHeight() - 280.0D);
      outPutGroup.setHgap(10.0D);
      outPutGroup.setLayoutX(primaryStage.getWidth() - 400.0D);
      outPutGroup.setLayoutY(primaryStage.getHeight() - 150.0D);
      timeBlockConfig.setLayoutX(primaryStage.getWidth() - 200.0D);
      timeBlockConfig.setLayoutY(primaryStage.getHeight() - 280.0D);
      primaryStage.setScene(scene);
      primaryStage.setTitle("VIFF-PDF Generator");
    } catch (Exception e) {
      e.printStackTrace();
      return;
    } 
  }
  
  static Color cmykToRgb(ArrayList<Double> list) {
    double c = ((Double)list.get(0)).doubleValue();
    double m = ((Double)list.get(1)).doubleValue();
    double y = ((Double)list.get(2)).doubleValue();
    double k = ((Double)list.get(3)).doubleValue();
    double red = 255.0D * (1.0D - c) * (1.0D - k);
    double green = 255.0D * (1.0D - m) * (1.0D - k);
    double blue = 255.0D * (1.0D - y) * (1.0D - k);
    int r = (int)red;
    int g = (int)green;
    int b = (int)blue;
    Color output = Color.rgb(r, g, b);
    return output;
  }
  
  static Color cmykToRgb(double c, double m, double y, double k) {
    if (c > 1.0D || m > 1.0D || y > 1.0D || k > 1.0D) {
      Status.printError("Please only use value between 0 and 100 for cmyk.");
      return null;
    } 
    double red = 255.0D * (1.0D - c) * (1.0D - k);
    double green = 255.0D * (1.0D - m) * (1.0D - k);
    double blue = 255.0D * (1.0D - y) * (1.0D - k);
    int r = (int)red;
    int g = (int)green;
    int b = (int)blue;
    Color output = Color.rgb(r, g, b);
    return output;
  }
  
  static ArrayList<Double> rgbToCmyk(Color color) {
    ArrayList<Double> list = new ArrayList<>();
    double r = color.getRed();
    double g = color.getGreen();
    double b = color.getBlue();
    double max = Math.max(Math.max(r, g), b);
    double k = 1.0D - max;
    double c = (1.0D - r - k) / (1.0D - k);
    double m = (1.0D - g - k) / (1.0D - k);
    double y = (1.0D - b - k) / (1.0D - k);
    list.add(Double.valueOf(c));
    list.add(Double.valueOf(m));
    list.add(Double.valueOf(y));
    list.add(Double.valueOf(k));
    return list;
  }
  
  public static void main(String[] args) {
    launch(args);
  }
}

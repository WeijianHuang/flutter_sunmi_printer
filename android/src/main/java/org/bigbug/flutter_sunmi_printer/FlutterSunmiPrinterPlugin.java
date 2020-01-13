package org.bigbug.flutter_sunmi_printer;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

import static org.bigbug.flutter_sunmi_printer.FlutterSunmiPrinterModule.DEFAULT_FONT_SIZE;

/**
 * FlutterSunmiPrinterPlugin
 */
public class FlutterSunmiPrinterPlugin implements FlutterPlugin, MethodCallHandler {

    private static FlutterSunmiPrinterModule flutterSunmiPrinterModule;

    private String RESET = "reset";
    private String START_PRINT = "startPrint";
    private String STOP_PRINT = "stopPrint";
    private String IS_RUN_PRINT = "isRunPrint";
    private String SET_FONT_SIZE = "setFontSize";
    private String SET_BOLD = "setBold";
    private String CANCEL_BOLD = "cancelBold";
    private String SET_ONE_UNDER_LINE = "setOneUnderLine";
    private String SET_TWO_UNDER_LINE = "setTwoUnderLine";
    private String CANCEL_UNDER_LINE = "cancelUnderLine";
    private String LINE_WRAP = "lineWrap";
    private String PRINT_TEXT = "printText";
    private String PRINT_BAR_CODE = "printBarCode";
    private String PRINT_QRCODE = "printQrcode";
    private String PRINT_TWO_COL_TEXT = "printTwoColText";
    private String print_Avg_Two_Col_Text = "printAvgTwoColText";
    private String PRINT_IMAGE = "printImage";

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        final MethodChannel channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "flutter_sunmi_printer");
        channel.setMethodCallHandler(new FlutterSunmiPrinterPlugin());
        flutterSunmiPrinterModule = new FlutterSunmiPrinterModule();
        flutterSunmiPrinterModule.initAidl(flutterPluginBinding.getApplicationContext());
    }

    // This static function is optional and equivalent to onAttachedToEngine. It supports the old
    // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
    // plugin registration via this function while apps migrate to use the new Android APIs
    // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
    //
    // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
    // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
    // depending on the user's project. onAttachedToEngine or registerWith must both be defined
    // in the same class.
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_sunmi_printer");
        channel.setMethodCallHandler(new FlutterSunmiPrinterPlugin());
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method.equals(RESET)){
            flutterSunmiPrinterModule.reset();
            result.success(null);
        }else if (call.method.equals(START_PRINT)){
            flutterSunmiPrinterModule.startPrint();
            result.success(null);
        }else if (call.method.equals(STOP_PRINT)){
            flutterSunmiPrinterModule.stopPrint();
            result.success(null);
        }else if (call.method.equals(IS_RUN_PRINT)){
            result.success(flutterSunmiPrinterModule.isRunPrint());
        }else if (call.method.equals(SET_FONT_SIZE)){
            flutterSunmiPrinterModule.setFontSize(getFontSizeType(call),getFontSize(call));
            result.success(null);
        }else if (call.method.equals(SET_BOLD)){
            flutterSunmiPrinterModule.setBold();
            result.success(null);
        }else if (call.method.equals(CANCEL_BOLD)){
            flutterSunmiPrinterModule.cancelBold();
            result.success(null);
        }else if (call.method.equals(SET_ONE_UNDER_LINE)){
            flutterSunmiPrinterModule.setOneUnderLine();
            result.success(null);
        }else if (call.method.equals(SET_TWO_UNDER_LINE)){
            flutterSunmiPrinterModule.setTwoUnderLine();
            result.success(null);
        }else if (call.method.equals(CANCEL_UNDER_LINE)){
            flutterSunmiPrinterModule.cancelUnderLine();
            result.success(null);
        }else if (call.method.equals(LINE_WRAP)){
            flutterSunmiPrinterModule.lineWrap(getLine(call));
            result.success(null);
        }else if (call.method.equals(PRINT_TEXT)){
            flutterSunmiPrinterModule.printText(getText(call),getAlign(call));
            result.success(null);
        }else if (call.method.equals(PRINT_BAR_CODE)){
            flutterSunmiPrinterModule.printBarCode(getText(call));
            result.success(null);
        }else if (call.method.equals(PRINT_QRCODE)){
            flutterSunmiPrinterModule.printQrcode(getText(call));
            result.success(null);
        }else if (call.method.equals(PRINT_TWO_COL_TEXT)){
            flutterSunmiPrinterModule.printTwoColText(getFirstAlign(call),getFirstTxt(call),getSecondAlign(call),getSecondText(call));
            result.success(null);
        }else if (call.method.equals(print_Avg_Two_Col_Text)){
            flutterSunmiPrinterModule.printAvgTwoColText(getFirstAlign(call),getFirstTxt(call),getSecondAlign(call),getSecondText(call));
            result.success(null);
        }else if (call.method.equals(PRINT_IMAGE)){
            flutterSunmiPrinterModule.printImage(getText(call));
            result.success(null);
        }else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    }


    private String getFontSizeType(MethodCall call) {
        String fontSizeType = call.argument("fontSizeType");
        if (fontSizeType == null || fontSizeType.equals("")) {
            return "DEFAULT";
        }
        return fontSizeType;
    }


    private Integer getFontSize(MethodCall call) {
        Integer fontSize = call.argument("fontSize");
        if (fontSize == null || fontSize <= 0) {
            return null;
        }
        return fontSize;
    }

    private Integer getLine(MethodCall call) {
        Integer line = call.argument("line");
        if (line == null || line <= 1) {
            return 1;
        }
        return line;
    }

    private String getAlign(MethodCall call) {
        String align = call.argument("align");
        if (align == null || align.equals("")) {
            return "left";
        }
        return align;
    }

    private String getText(MethodCall call) {
        String text = call.argument("text");
        if (text == null || text.equals("")) {
            return "";
        }
        return text;
    }

    private String getFirstAlign(MethodCall call) {
        String firstAlign = call.argument("firstAlign");
        if (firstAlign == null || firstAlign.equals("")) {
            return "left";
        }
        return firstAlign;
    }

    private String getFirstTxt(MethodCall call) {
        String firstText = call.argument("firstText");
        if (firstText == null || firstText.equals("")) {
            return "";
        }
        return firstText;
    }

    private String getSecondAlign(MethodCall call) {
        String secondAlign = call.argument("secondAlign");
        if (secondAlign == null || secondAlign.equals("")) {
            return "left";
        }
        return secondAlign;
    }

    private String getSecondText(MethodCall call) {
        String secondText = call.argument("secondText");
        if (secondText == null || secondText.equals("")) {
            return "";
        }
        return secondText;
    }


}

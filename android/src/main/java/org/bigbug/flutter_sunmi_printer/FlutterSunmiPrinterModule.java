package org.bigbug.flutter_sunmi_printer;

import android.content.Context;

import org.bigbug.flutter_sunmi_printer.utils.AidlUtil;
import org.bigbug.flutter_sunmi_printer.utils.Base64Utils;
import org.bigbug.flutter_sunmi_printer.utils.BitmapUtil;
import org.bigbug.flutter_sunmi_printer.utils.ESCUtil;

public class FlutterSunmiPrinterModule {

    private static boolean runPrint = false;
    public static int SMALL_FONT_SIZE = 18;
    public static int DEFAULT_FONT_SIZE = 24;
    public static int LARGE_FONT_SIZE = 36;

    public void initAidl(Context context) {
        AidlUtil.getInstance().connectPrinterService(context);
        AidlUtil.getInstance().initPrinter();
    }

    public void reset() {
        AidlUtil.getInstance().initPrinter();
    }

    public void startPrint() {
        runPrint = true;
    }

    public void stopPrint() {
        runPrint = false;
    }

    public boolean isRunPrint() {
        return runPrint;
    }

    public void setFontSize(String type, Integer fontSize) {
        if (fontSize != null) {
            AidlUtil.getInstance().setFontSize(fontSize);
        } else {
            switch (type) {
                case "SMALL":
                    AidlUtil.getInstance().setFontSize(SMALL_FONT_SIZE);
                    break;
                case "LARGE":
                    AidlUtil.getInstance().setFontSize(LARGE_FONT_SIZE);
                    break;
                default:
                    AidlUtil.getInstance().setFontSize(DEFAULT_FONT_SIZE);
            }
        }
    }

    public void setBold() {
        AidlUtil.getInstance().sendRawData(ESCUtil.boldOn());
    }

    public void cancelBold() {
        AidlUtil.getInstance().sendRawData(ESCUtil.boldOff());
    }

    public void setUnderLine() {
        AidlUtil.getInstance().sendRawData(ESCUtil.underlineWithOneDotWidthOn());
    }

    public void cancelUnderLine() {
        AidlUtil.getInstance().sendRawData(ESCUtil.underlineOff());
    }

    public void lineWrap(int linewWrap) {
        AidlUtil.getInstance().lineWrap(linewWrap);
    }

    public void printText(String content, String alignStr) {
        int[] align = new int[]{0};
        if (alignStr.equals("center")) {
            align[0] = 1;
        } else if (alignStr.equals("right")) {
            align[0] = 2;
        }
        AidlUtil.getInstance().printTableItem(new String[]{content}, new int[]{32}, align);
    }

    public void printBarCode(String content, int symbology) {
        AidlUtil.getInstance().printBarCode(content, symbology, 60, 1, 0);
    }

    public void printQrcode(String content) {
        AidlUtil.getInstance().printQr(content, 7, 1);
    }

    public void printTwoColText(String firstAlign, String firstTxt, String secondAlign, String secondTxt) {
        int[] align = assembleAlign(firstAlign, secondAlign);
        AidlUtil.getInstance().printTableItem(new String[]{firstTxt, secondTxt}, new int[]{18, 14}, align);
    }

    public void printAvgTwoColText(String firstAlign, String firstTxt, String secondAlign, String secondTxt) {
        int[] align = assembleAlign(firstAlign, secondAlign);
        AidlUtil.getInstance().printTableItem(new String[]{firstTxt, secondTxt}, new int[]{16, 16}, align);
    }

    public void printImage(String imageData) {
        //Base64解码
        byte[] bytes = Base64Utils.decode(imageData);
        for (int i = 0; i < bytes.length; ++i) {
            if (bytes[i] < 0) {//调整异常数据
                bytes[i] += 256;
            }
        }

        AidlUtil.getInstance().printBitmap(BitmapUtil.convertToThumb(bytes, 280));
    }

    private int[] assembleAlign(String firstAlign, String secondAlign) {
        int[] align = new int[]{0, 0};
        if (firstAlign.equals("center")) {
            align[0] = 1;
        } else if (firstAlign.equals("right")) {
            align[0] = 2;
        }

        if (secondAlign.equals("center")) {
            align[1] = 1;
        } else if (secondAlign.equals("right")) {
            align[1] = 2;
        }
        return align;
    }

}

package org.bigbug.flutter_sunmi_printer;

import android.content.Context;
import android.os.RemoteException;

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
        try {
            AidlUtil.getInstance().initPrinter();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        try {
            AidlUtil.getInstance().initPrinter();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
//        String type = map.toHashMap().get("type") == null ? "DEFAULT" : (String) map.toHashMap().get("type");
//        Object fontSize = map.toHashMap().get("fontSize");
        try {
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
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setBold(){
        try {
            AidlUtil.getInstance().sendRawData(ESCUtil.boldOn());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void cancelBold() {
        try {
            AidlUtil.getInstance().sendRawData(ESCUtil.boldOff());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setOneUnderLine() {
        try {
            AidlUtil.getInstance().sendRawData(ESCUtil.underlineWithOneDotWidthOn());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setTwoUnderLine() {
        try {
            AidlUtil.getInstance().sendRawData(ESCUtil.underlineWithTwoDotWidthOn());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void cancelUnderLine() {
        try {
            AidlUtil.getInstance().sendRawData(ESCUtil.underlineOff());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void lineWrap(int linewWrap) {
//        int linewWrap = hashMap.get("line") == null ? 1 : ((Double) hashMap.get("line")).intValue();
        try {
            AidlUtil.getInstance().lineWrap(linewWrap);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printText(String content, String alignStr) {
//        HashMap hashMap = map.toHashMap();
//        String content = hashMap.get("text") == null ? "" : (String) hashMap.get("text");
//        String alignStr = hashMap.get("align") == null ? "left" : (String) hashMap.get("align");

        int[] align = new int[]{0};
        if (alignStr.equals("center")) {
            align[0] = 1;
        } else if (alignStr.equals("right")) {
            align[0] = 2;
        }
        try {
            AidlUtil.getInstance().printTableItem(new String[]{content}, new int[]{32}, align);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printBarCode(String content) {
//        HashMap hashMap = map.toHashMap();
//        String content = hashMap.get("text") == null ? "" : (String) hashMap.get("text");
        try {
            AidlUtil.getInstance().printBarCode(content, 5, 60, 1, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printQrcode(String content) {
//        HashMap hashMap = map.toHashMap();
//        String content = hashMap.get("text") == null ? "" : (String) hashMap.get("text");
        try {
            AidlUtil.getInstance().printQr(content, 7, 1);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printTwoColText(String firstAlign, String firstTxt, String secondAlign, String secondTxt) {
//        HashMap hashMap = map.toHashMap();
//        String firstAlign = hashMap.get("firstAlign") == null ? "left" : (String) hashMap.get("firstAlign");
//        String firstTxt = hashMap.get("firstText") == null ? "" : (String) hashMap.get("firstText");
//        String secondAlign = hashMap.get("secondAlign") == null ? "left" : (String) hashMap.get("secondAlign");
//        String secondTxt = hashMap.get("secondText") == null ? "" : (String) hashMap.get("secondText");

        int[] align = assembleAlign(firstAlign, secondAlign);
        try {
            AidlUtil.getInstance().printTableItem(new String[]{firstTxt, secondTxt}, new int[]{18, 14}, align);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printAvgTwoColText(String firstAlign, String firstTxt, String secondAlign, String secondTxt) {
//        String firstAlign = hashMap.get("firstAlign") == null ? "left" : (String) hashMap.get("firstAlign");
//        String firstTxt = hashMap.get("firstText") == null ? "" : (String) hashMap.get("firstText");
//        String secondAlign = hashMap.get("secondAlign") == null ? "left" : (String) hashMap.get("secondAlign");
//        String secondTxt = hashMap.get("secondText") == null ? "" : (String) hashMap.get("secondText");

        int[] align = assembleAlign(firstAlign, secondAlign);

        try {
            AidlUtil.getInstance().printTableItem(new String[]{firstTxt, secondTxt}, new int[]{16, 16}, align);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void printImage(String imageData) {
        //Base64解码
        byte[] bytes = Base64Utils.decode(imageData);
        for (int i = 0; i < bytes.length; ++i) {
            if (bytes[i] < 0) {//调整异常数据
                bytes[i] += 256;
            }
        }

        try {
            AidlUtil.getInstance().printBitmap(BitmapUtil.convertToThumb(bytes, 280));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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

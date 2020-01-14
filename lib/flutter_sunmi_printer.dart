import 'dart:async';

import 'package:flutter/services.dart';

class FlutterSunmiPrinter {
  static final String RESET = "reset";
  static final String START_PRINT = "startPrint";
  static final String STOP_PRINT = "stopPrint";
  static final String IS_RUN_PRINT = "isRunPrint";
  static final String SET_FONT_SIZE = "setFontSize";
  static final String SET_BOLD = "setBold";
  static final String CANCEL_BOLD = "cancelBold";
  static final String SET_UNDER_LINE = "setUnderLine";
  static final String CANCEL_UNDER_LINE = "cancelUnderLine";
  static final String LINE_WRAP = "lineWrap";
  static final String PRINT_TEXT = "printText";
  static final String PRINT_BAR_CODE = "printBarCode";
  static final String PRINT_QRCODE = "printQrcode";
  static final String PRINT_TWO_COL_TEXT = "printTwoColText";
  static final String PRINT_AVG_TWO_COL_TEXT = "printAvgTwoColText";
  static final String PRINT_IMAGE = "printImage";

  static const MethodChannel _channel = const MethodChannel('flutter_sunmi_printer');

  static Future<void> reset() async {
    await _channel.invokeMethod(RESET);
  }

  static Future<void> startPrint() async {
    await _channel.invokeMethod(START_PRINT);
  }

  static Future<void> stopPrint() async {
    await _channel.invokeMethod(STOP_PRINT);
  }

  static Future<void> isRunPrint() async {
    await _channel.invokeMethod(IS_RUN_PRINT);
  }

  // fontSizeType {DEFAULT,SMALL,LARGE}
  static Future<void> setFontSize({String fontSizeType, int fontSize}) async {
    await _channel.invokeMethod(SET_FONT_SIZE, {"fontSizeType": fontSizeType, "fontSize": fontSize});
  }

  static Future<void> setBold() async {
    await _channel.invokeMethod(SET_BOLD);
  }

  static Future<void> cancelBold() async {
    await _channel.invokeMethod(CANCEL_BOLD);
  }

  static Future<void> setUnderLine() async {
    await _channel.invokeMethod(SET_UNDER_LINE);
  }

  static Future<void> cancelUnderLine() async {
    await _channel.invokeMethod(CANCEL_UNDER_LINE);
  }

  static Future<void> lineWrap(int line) async {
    await _channel.invokeMethod(LINE_WRAP, {"line": line});
  }

  static Future<void> printText(String text, {String align}) async {
    await _channel.invokeMethod(PRINT_TEXT, {"text": text, "align": align});
  }

  /// 打印一维条码
  /// symbology: 	条码类型
  ///    0 -- UPC-A，		要求12位数字（最后一位校验位必须正确），但受限于打印机的宽度及条码宽度
  ///    1 -- UPC-E，		要求8位数字（最后一位校验位必须正确），但受限于打印机的宽度及条码宽度
  ///    2 -- JAN13(EAN13)，  要求13位数字（最后一位校验位必须正确），但受限于打印机的宽度及条码宽度
  ///    3 -- JAN8(EAN8)，	要求8位数字（最后一位校验位必须正确），但受限于打印机的宽度及条码宽度
  ///    4 -- CODE39，		数字英文及8个特殊符号且首尾为*号，但受限于打印机的宽度及条码宽度
  ///    5 -- ITF，			字符为数字且小于14位，但受限于打印机的宽度及条码宽度
  ///    6 -- CODABAR，		起始和终止必须为A-D，数据为0-9及6个特殊字符，长度任意但受限于打印机的宽度及条码宽度
  ///    7 -- CODE93，		字符任意，长度任意但受限于打印机的宽度及条码宽度
  ///    8 -- CODE128		字符任意，长度任意但受限于打印机的宽度及条码宽度
  static Future<void> printBarCode(String content, {int symbology}) async {
    await _channel.invokeMethod(PRINT_BAR_CODE, {"text": content, "symbology": symbology});
  }

  static Future<void> printQrcode(String content) async {
    await _channel.invokeMethod(PRINT_QRCODE, {"text": content});
  }

  static Future<void> printTwoColText(
      {String firstAlign, String firstText, String secondAlign, String secondText}) async {
    await _channel.invokeMethod(PRINT_TWO_COL_TEXT,
        {"firstAlign": firstAlign, "firstText": firstText, "secondAlign": secondAlign, "secondText": secondText});
  }

  static Future<void> printAvgTwoColText(
      {String firstAlign, String firstText, String secondAlign, String secondText}) async {
    await _channel.invokeMethod(PRINT_AVG_TWO_COL_TEXT,
        {"firstAlign": firstAlign, "firstText": firstText, "secondAlign": secondAlign, "secondText": secondText});
  }

  static Future<void> printImage(String base64Text) async {
    await _channel.invokeMethod(PRINT_IMAGE, {"text": base64Text});
  }
}

enum FontSizeType { DEFAULT, SMALL, LARGE }

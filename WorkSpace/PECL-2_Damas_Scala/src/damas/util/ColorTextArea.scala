package damas.util;
// Import
import scala.swing._;
import javax.swing.text._
import java.awt.Color;

/**
 * @author david
 */
class ColorTextArea extends TextArea {
     val D_Black = Color.getHSBColor(0.000f, 0.000f, 0.000f);
     val D_Red = Color.getHSBColor(0.000f, 1.000f, 0.502f);
     val D_Blue = Color.getHSBColor(0.667f, 1.000f, 0.502f);
     val D_Magenta = Color.getHSBColor(0.833f, 1.000f, 0.502f);
     val D_Green = Color.getHSBColor(0.333f, 1.000f, 0.502f);
     val D_Yellow = Color.getHSBColor(0.167f, 1.000f, 0.502f);
     val D_Cyan = Color.getHSBColor(0.500f, 1.000f, 0.502f);
     val D_White = Color.getHSBColor(0.000f, 0.000f, 0.753f);
     val B_Black = Color.getHSBColor(0.000f, 0.000f, 0.502f);
     val B_Red = Color.getHSBColor(0.000f, 1.000f, 1.000f);
     val B_Blue = Color.getHSBColor(0.667f, 1.000f, 1.000f);
     val B_Magenta = Color.getHSBColor(0.833f, 1.000f, 1.000f);
     val B_Green = Color.getHSBColor(0.333f, 1.000f, 1.000f);
     val B_Yellow = Color.getHSBColor(0.167f, 1.000f, 1.000f);
     val B_Cyan = Color.getHSBColor(0.500f, 1.000f, 1.000f);
     val B_White = Color.getHSBColor(0.000f, 0.000f, 1.000f);
     val cReset = Color.getHSBColor(0.000f, 0.000f, 1.000f);
     var colorCurrent = cReset;
     var remaining = "";

     def append(c: Color, s: String): Unit = {
          val sc = StyleContext.getDefaultStyleContext();
          val aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
          val len = s.length(); //getDocument().getLength(); // same value as getText().length();
          
          //setCaretPosition(len); // place caret at the end (with no selection)
          //setCharacterAttributes(aset, false);
          //replaceSelection(s); // there is no selection, so inserts at caret
     }

     def appendANSI(s: String): Unit = { // convert ANSI color codes first
          var aPos = 0; // current char position in addString
          var aIndex = 0; // index of next Escape sequence
          var mIndex = 0; // index of "m" terminating Escape sequence
          var tmpString = "";
          var stillSearching = true; // true until no more Escape sequences
          var addString = remaining + s;
          remaining = "";

          if (addString.length() > 0) {
               aIndex = addString.indexOf("\u001B"); // find first escape
               if (aIndex == -1) { // no escape/color change in this string, so just send it with current color
                    append(colorCurrent, addString);
                    return ;
               }
               // otherwise There is an escape character in the string, so we must process it

               if (aIndex > 0) { // Escape is not first char, so send text up to first escape
                    tmpString = addString.substring(0, aIndex);
                    append(colorCurrent, tmpString);
                    aPos = aIndex;
               }
               // aPos is now at the beginning of the first escape sequence

               stillSearching = true;
               while (stillSearching) {
                    mIndex = addString.indexOf("m", aPos); // find the end of the escape sequence
                    if (mIndex < 0) { // the buffer ends halfway through the ansi string!
                         remaining = addString.substring(aPos, addString.length());
                         stillSearching = false;
                         //continue;
                    }
                    else {
                         tmpString = addString.substring(aPos, mIndex + 1);
                         colorCurrent = getANSIColor(tmpString);
                    }
                    aPos = mIndex + 1;
                    // now we have the color, send text that is in that color (up to next escape)

                    aIndex = addString.indexOf("\u001B", aPos);

                    if (aIndex == -1) { // if that was the last sequence of the input, send remaining text
                         tmpString = addString.substring(aPos, addString.length());
                         append(colorCurrent, tmpString);
                         stillSearching = false;
                         //continue; // jump out of loop early, as the whole string has been sent now
                    }

                    // there is another escape sequence, so send part of the string and prepare for the next
                    tmpString = addString.substring(aPos, aIndex);
                    aPos = aIndex;
                    append(colorCurrent, tmpString);

               } // while there's text in the input buffer
          }
     }

     def getANSIColor(ANSIColor: String): Color = {
          if (ANSIColor.equals("\u001B[30m")) { return D_Black; }
          else if (ANSIColor.equals("\u001B[31m")) { return D_Red; }
          else if (ANSIColor.equals("\u001B[32m")) { return D_Green; }
          else if (ANSIColor.equals("\u001B[33m")) { return D_Yellow; }
          else if (ANSIColor.equals("\u001B[34m")) { return D_Blue; }
          else if (ANSIColor.equals("\u001B[35m")) { return D_Magenta; }
          else if (ANSIColor.equals("\u001B[36m")) { return D_Cyan; }
          else if (ANSIColor.equals("\u001B[37m")) { return D_White; }
          else if (ANSIColor.equals("\u001B[0;30m")) { return D_Black; }
          else if (ANSIColor.equals("\u001B[0;31m")) { return D_Red; }
          else if (ANSIColor.equals("\u001B[0;32m")) { return D_Green; }
          else if (ANSIColor.equals("\u001B[0;33m")) { return D_Yellow; }
          else if (ANSIColor.equals("\u001B[0;34m")) { return D_Blue; }
          else if (ANSIColor.equals("\u001B[0;35m")) { return D_Magenta; }
          else if (ANSIColor.equals("\u001B[0;36m")) { return D_Cyan; }
          else if (ANSIColor.equals("\u001B[0;37m")) { return D_White; }
          else if (ANSIColor.equals("\u001B[1;30m")) { return B_Black; }
          else if (ANSIColor.equals("\u001B[1;31m")) { return B_Red; }
          else if (ANSIColor.equals("\u001B[1;32m")) { return B_Green; }
          else if (ANSIColor.equals("\u001B[1;33m")) { return B_Yellow; }
          else if (ANSIColor.equals("\u001B[1;34m")) { return B_Blue; }
          else if (ANSIColor.equals("\u001B[1;35m")) { return B_Magenta; }
          else if (ANSIColor.equals("\u001B[1;36m")) { return B_Cyan; }
          else if (ANSIColor.equals("\u001B[1;37m")) { return B_White; }
          else if (ANSIColor.equals("\u001B[0m")) { return cReset; }
          else { return B_White; }
     }
}
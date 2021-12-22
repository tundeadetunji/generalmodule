package com.inovationware.generalmodule;

import java.util.ArrayList;

public class NFunctions {
    enum SideToReturn {
        Left, Middle, Right // , AsArray, AsListOfString, AsListToString
    }

    public static ArrayList<String> splitTextInSplits(String string_to_split, String separator) {
        if (string_to_split.trim().length() < 1 || separator.length() < 1) {
            return new ArrayList<String>();
        }
        String[] s = string_to_split.split(separator);
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < s.length; i++) {
            arrayList.add(s[i]);
        }
        return arrayList;
    }

    public static String splitTextInThree(String string_to_split, String separator, SideToReturn side_to_return) {
        if (string_to_split.trim().length() < 1 || separator.length() < 1) {
            return "";
        }

        String r = "";

        ArrayList<String> s = splitTextInSplits(string_to_split, separator);

        switch (side_to_return) {
            case Left:
                r = s.get(0);
                break;
            case Middle:
                r = s.get(1);
                break;
            case Right:
                r = s.get(2);
                break;
        }
        return r;
    }

    public static String splitTextInTwo(String string_to_split, String separator, SideToReturn side_to_return) {
        if (string_to_split.trim().length() < 1 || separator.length() < 1) {
            return "";
        }

        String r = "";

        ArrayList<String> s = splitTextInSplits(string_to_split, separator);

        switch (side_to_return) {
            case Left:
                r = s.get(0);
                break;
            case Middle:
            case Right:
                if (s.size() == 2) {
                    r = s.get(1);
                } else {
                    ArrayList<String> l = new ArrayList<String>();
                    for (int i = 1; i < s.size(); i++) {
                        l.add(s.get(i));
                    }
                    r = joinTextFromSplits(l, separator);
                }
                break;
        }
        return r;
    }

    public static String splitText(String string_to_split, String separator, SideToReturn side_to_return) {
        if (string_to_split.trim().length() < 1 || separator.length() < 1) {
            return "";
        }

        String r = "";

        ArrayList<String> s = splitTextInSplits(string_to_split, separator);

        switch (side_to_return) {
            case Left:
                r = s.get(0);
                break;
            case Middle:
            case Right:
                ArrayList<String> l = new ArrayList<String>();
                for (int i = 1; i < s.size(); i++) {
                    l.add(s.get(i));
                }
                r = joinTextFromSplits(l, separator);
                break;
        }
        return r;
    }

    public static String joinTextFromSplits(ArrayList<String> splits, String separator) {
        if (splits.size() < 1 || separator.length() < 1) {
            return "";
        }
        //return String.join(separator, splits);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < splits.size(); i++) {
            s.append(splits.get(i));
            if (i < splits.size() - 1) {
                s.append(separator);
            }
        }
        return s.toString();
    }

    public static String firstWord(String text) {
        if (text.trim().length() < 1) {
            return "";
        }

        return splitTextInSplits(text, " ").get(0);
    }

    public static String otherWords(String text) {
        if (text.trim().length() < 1) {
            return "";
        }

        return splitTextInTwo(text, " ", SideToReturn.Right);
    }

    public static String[] lastThreeLetters(String text) {
        if (text.trim().length() < 1) {
            return new String[]{};
        }

        // Return {text.Chars(text.Length - 3), text.Chars(text.Length - 2),
        // text.Chars(text.Length - 1)}
        return new String[]{String.valueOf(text.charAt(text.length() - 3)),
                String.valueOf(text.charAt(text.length() - 2)), String.valueOf(text.charAt(text.length() - 1))};
    }

    public static boolean isConsonant(String text) {
        boolean isConsonant = false;

        if (text.equals("b") || text.equals("c") || text.equals("d") || text.equals("f") || text.equals("g") || text.equals("h") || text.equals("j")
                || text.equals("k") || text.equals("l") || text.equals("m") || text.equals("n") || text.equals("p") || text.equals("q") || text.equals("r")
                || text.equals("s") || text.equals("t") || text.equals("v") || text.equals("w") || text.equals("x") || text.equals("y") || text.equals("z")
                || text.equals("B") || text.equals("C") || text.equals("D") || text.equals("F") || text.equals("G") || text.equals("H") || text.equals("J")
                || text.equals("K") || text.equals("L") || text.equals("M") || text.equals("N") || text.equals("P") || text.equals("Q") || text.equals("R")
                || text.equals("S") || text.equals("T") || text.equals("V") || text.equals("W") || text.equals("X") || text.equals("Y")
                || text.equals("Z")) {
            isConsonant = true;
        }
        return isConsonant;
    }

    public static boolean isVowel(String text) {
        boolean isVowel = false;
        if (text.equals("a") || text.equals("e") || text.equals("i") || text.equals("o") || text.equals("u") || text.equals("A") || text.equals("E")
                || text.equals("I") || text.equals("O") || text.equals("U")) {
            isVowel = true;
        }
        return isVowel;
    }

    public static boolean isAlphabet(String text) {
        boolean isAlphabet = false;

        if (isVowel(text) || isConsonant(text)) {
            isAlphabet = true;
        }
        return isAlphabet;
    }

    public static String ToContinuous(String text, String suffx) {
        if (text.trim().length() < 1) {
            return "";
        }

        String prefx = "";
        String[] lastThree = lastThreeLetters(text);

        if (!isAlphabet(lastThree[0]) || !isAlphabet(lastThree[1])
                || !isAlphabet(lastThree[2])) {
            return "";
        }

        String a = lastThree[0].toLowerCase();
        String b = lastThree[1].toLowerCase();
        String c = lastThree[2].toLowerCase();


        if (a.equals("i") && b.equals("n") && c.equals("g")) {
            return text;
        }

        // If IsConsonant(a) And IsVowel(b) And IsConsonant(c) Then
        // prefx = text & Mid(text.Trim, text.Length, 1).Trim & "ing"
        // ElseIf b = "i" And c = "e" Then
        // prefx = Mid(text.Trim, 1, text.Length - 2).Trim & "ying"
        // ElseIf IsVowel(a) And IsConsonant(b) And c = "e" Then
        // prefx = Mid(text.Trim, 1, text.Length - 1).Trim & "ing"
        // Else
        // prefx = text.Trim & "ing"
        // End If

        if (isConsonant(a) && isVowel(b) && isConsonant(c)) {
            // swim, stop, run, begin
            prefx = text + text.substring(text.length() - 1).trim() + "ing";
        }
        else if (b.equals("i") && c.equals("e")){
            //lie, die
            prefx = text.substring(0, text.length()-2).trim() + "ying";
        }
        else if (isVowel(a) && isConsonant(b) && c.equals("e")){
            //come, mistake
            prefx = text.substring(0,text.length() - 1).trim() + "ing";
        }
        else{
            prefx = text.trim() + "ing";
        }
        //mix, deliver

        // Return RTrim(prefx) & " " & LTrim(suffx)
        return prefx + " " + suffx;

    }

    public static String ToContinuous(String text) {
        if (text.trim().length() < 1) {
            return "";
        }

        String prefx = "";
        String[] lastThree = lastThreeLetters(text);

        if (!isAlphabet(lastThree[0]) || !isAlphabet(lastThree[1])
                || !isAlphabet(lastThree[2])) {
            return "";
        }

        String a = lastThree[0].toLowerCase();
        String b = lastThree[1].toLowerCase();
        String c = lastThree[2].toLowerCase();


        if (a.equals("i") && b.equals("n") && c.equals("g")) {
            return text;
        }

        // If IsConsonant(a) And IsVowel(b) And IsConsonant(c) Then
        // prefx = text & Mid(text.Trim, text.Length, 1).Trim & "ing"
        // ElseIf b = "i" And c = "e" Then
        // prefx = Mid(text.Trim, 1, text.Length - 2).Trim & "ying"
        // ElseIf IsVowel(a) And IsConsonant(b) And c = "e" Then
        // prefx = Mid(text.Trim, 1, text.Length - 1).Trim & "ing"
        // Else
        // prefx = text.Trim & "ing"
        // End If

        if (isConsonant(a) && isVowel(b) && isConsonant(c)) {
            // swim, stop, run, begin
            prefx = text + text.substring(text.length() - 1).trim() + "ing";
        }
        else if (b.equals("i") && c.equals("e")){
            //lie, die
            prefx = text.substring(0, text.length()-2).trim() + "ying";
        }
        else if (isVowel(a) && isConsonant(b) && c.equals("e")){
            //come, mistake
            prefx = text.substring(0,text.length() - 1).trim() + "ing";
        }
        else{
            prefx = text.trim() + "ing";
        }
        //mix, deliver, cradle, juggle

        // Return RTrim(prefx) & " " & LTrim(suffx)
        return prefx;
    }

    /**
     * Checks if string is empty
     * @param text text to check
     * @return true if text.trim() is empty
     */
    public static boolean isEmptyString(String text){
        return text.trim().length() < 1;
    }

    /**
     * Checks if string is empty
     * @param text text to check
     * @param should_trim check text.trim() instead
     * @return true if it's eventually empty
     */
    public static boolean isEmptyString(String text, boolean should_trim){
        boolean r;
        if(should_trim){
            r = text.trim().length() < 1;
        }
        else{
            r = text.length()< 1;
        }
        return r;
    }

    public static boolean isPhraseOrSentence(String text){
/*
        If text.Trim.Length < 1 Then Return False
        If firstWord(text).Length > 0 And otherWords(text).Length > 0 Then
        Return True
        Else
        Return False
        End If
*/
        boolean isPhraseOrSentence = false;

        if (!isEmptyString(text) && (!isEmptyString(firstWord(text))) && !isEmptyString(otherWords(text))){
            isPhraseOrSentence = true;
        }
        return isPhraseOrSentence;
    }


}

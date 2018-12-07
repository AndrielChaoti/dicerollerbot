package net.vandesdelca32.dicerollerbot.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    public static List<String> readFile(String filepath) {
        List<String> ret = new ArrayList<>();
        try {
            BufferedReader input = new BufferedReader(new java.io.FileReader(filepath));
            String str;
            while ((str = input.readLine()) != null) {
                ret.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return ret;
    }

}

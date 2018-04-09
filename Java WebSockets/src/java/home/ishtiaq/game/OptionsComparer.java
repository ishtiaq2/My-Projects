/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.ishtiaq.game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author ishtiaq
 */
public class OptionsComparer {
    
    HashMap<String, String> options = new HashMap<>();
    
    public int[] makeDecision(HashMap options) {
        this.options = options;
        
        String[] compare = new String[options.size()];
        int i = 0;
        Set set = options.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry me = (Map.Entry)iterator.next();
                String name = (String)me.getKey();
                String option = (String) me.getValue();
                compare[i] = name + "," + option;
                i++;
        }
        return compare(compare);
    }
    
    public int[] compare(String[] compare) {
        
        int[] score = new int[compare.length];
        for (int i = 0; i< compare.length; i++) {
            for (int j=0; j< compare.length; j++) {
                String[] temp = compare[i].split(",");
                String name = temp[0];
                String toCompare = temp[1];
                
                String[] temp2 = compare[j].split(",");
                String name2 = temp2[0];
                String withCompare = temp2[1];
                
                if (toCompare.equalsIgnoreCase("R") && withCompare.equalsIgnoreCase("S")) {
                    score[i] += 1;
                } else if (toCompare.equalsIgnoreCase("R") && withCompare.equalsIgnoreCase("P")) {
                    score[i] -= 0;
                } else if (toCompare.equalsIgnoreCase("S") && withCompare.equalsIgnoreCase("R")) {
                    score[i] -= 0;                    
                } else if (toCompare.equalsIgnoreCase("S") && withCompare.equalsIgnoreCase("P")) {
                    score[i] += 1;
                } else if (toCompare.equalsIgnoreCase("P") && withCompare.equalsIgnoreCase("S")) {
                    score[i] -= 0;
                } else if (toCompare.equalsIgnoreCase("P") && withCompare.equalsIgnoreCase("R")) {
                    score[i] += 1;
                } else if (toCompare.equalsIgnoreCase(withCompare)) {
                    score[i] += 0;
                }
            }   
        }
        return score;
    }
    
}

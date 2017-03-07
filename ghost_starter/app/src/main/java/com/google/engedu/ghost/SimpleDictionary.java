/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class SimpleDictionary implements GhostDictionary {
    public ArrayList<String> words;

    public SimpleDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }




     int binarySearch(ArrayList<String> words, int min, int max,String value) {
        if (min > max) {
            return -1;
        }

        int mid = (max + min) / 2;

        if (words.get(mid).contains(value)) {
            return mid;
        } else if(words.get(mid).compareTo(value) > 0) {
            return binarySearch(words,  min, mid - 1,value);
        } else {
            return binarySearch(words,  mid + 1, max,value);
        }
    }

    int binarySearch2(ArrayList<String> words, int min, int max,String value) {
        if (min > max) {
            return -1;
        }

        int mid = (max + min) / 2;

        if (words.get(mid).contains(value)&&(words.get(mid).length()-value.length())%2==0) {
            return mid;
        } else if(words.get(mid).compareTo(value) > 0) {
            return binarySearch(words,  min, mid - 1,value);
        } else {
            return binarySearch(words,  mid + 1, max,value);
        }
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {

       int ans=binarySearch(words,0,words.size(),prefix);
        //int ans=0;
        if(ans!=-1)
        {

                return words.get(ans);
        }


        return null;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        String selected=null;

        int ans=binarySearch2(words,0,words.size(),prefix);
        if(ans!=-1)
        {



            selected=words.get(ans);
            return selected;
        }

        else
            selected=getAnyWordStartingWith(prefix);

        return selected;
    }
}

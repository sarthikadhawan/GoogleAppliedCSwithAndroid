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

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    int count=0;
    private Random random = new Random();
    HashSet wordlist=new HashSet();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;int i=0;
        while((line = in.readLine()) != null) {
            String word = line.trim();

            wordlist.add(word);

        }
        //wordlist2=wordlist;
    }

    public boolean isGoodWord(String word, String base)
    {



        if(wordlist.contains(word)&&!word.toLowerCase().contains(base.toLowerCase()))
            return true;
        return false;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        int i;
        Iterator iterator=wordlist.iterator();
        char[] chars2 = targetWord.toCharArray();
        Arrays.sort(chars2);
        String sorted2 = new String(chars2);
        while (iterator.hasNext())
        {
            String ss=(String)(iterator.next());
            char[] chars = (ss).toCharArray();
            Arrays.sort(chars);
            String sorted = new String(chars);



            if (sorted.equals(sorted2))
                result.add(ss);...0

        }



        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        int i;int c[]=new int[1000];int cc[]=new int[1000],ccc=0;
        for(i=0;i<word.length();i++)
        {
            c[word.charAt(i)]++;
        }
        Iterator iterator=wordlist.iterator();
        while (iterator.hasNext())
        {
            String ss=(String)(iterator.next());
            if(ss.length()+1==word.length())
            {
                int j;
                for(j=0;j<ss.length();j++)
                {
                    cc[ss.charAt(j)]++;
                }

                for(i=0;i<word.length();i++)
                {
                    if(c[word.charAt(i)]==cc[ss.charAt(i)]+1)
                    {
                        ccc++;
                    }
                }
                if(ccc==0||ccc==1)
                    result.add(ss);
                ccc=0;
            }
        }
        return result;
    }

    public String pickGoodStarterWord()
    {
        int c=0;

        int i= random.nextInt(wordlist.size())+1;
        Iterator iterator=wordlist.iterator();
        while (iterator.hasNext())
        {
            String ss=(String)(iterator.next());
            if(c==i)
                return ss;
            c++;
        }
        return "stop";
    }
}

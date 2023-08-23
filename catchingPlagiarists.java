import java.util.*;
import java.io.File;

/**
 * Catching Plagiarists
 *
 * @author Nikash Chhadia
 * @version 4/19/22
 */

public class catchingPlagiarists
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);

        File dir = new File(".");
        ArrayList<File> directories = new ArrayList<File>();
        for(File folder : dir.listFiles())
            if(folder.isDirectory())
                directories.add(folder);

        System.out.println("Welcome! This program is intented to detect plagiarism between");      
        System.out.println("different text documents by identifying the ones with a large");
        System.out.println("amount of similar text. It accomplishes this by finding the");
        System.out.println("amount of common n-contiguous-word sequences among documents");
        System.out.println("with a given n. Please select one of the following document");
        System.out.println("folders by entering the corresponding number.\n");
        System.out.println("Small number of documents      ->     [0]");
        System.out.println("Medium number of documents     ->     [1]");
        System.out.println("Large number of documents      ->     [2]\n");

        ArrayList<String> files = new ArrayList<String>();
        int fileGroup = 0;
        boolean successful = false;
        while(successful == false)
        {
            try
            {
                fileGroup = input.nextInt();
                String[] temp = directories.get(fileGroup).list();
                for(int i = 0; i < temp.length; i++)
                {
                    if(temp[i].endsWith(".txt"))
                        files.add(temp[i]);
                }
                successful = true;
            }
            catch(Exception ex)
            {
                System.out.println("Please try again.");
                System.out.println("Small number of documents      ->     [0]");
                System.out.println("Medium number of documents     ->     [1]");
                System.out.println("Large number of documents      ->     [2]\n");
            }
        }

        if(fileGroup == 0)
            System.out.println("> Small number of documents\n");
        if(fileGroup == 1)
            System.out.println("> Medium number of documents\n");
        if(fileGroup == 2)
            System.out.println("> Large number of documents\n");

        System.out.print("Please enter the number of words in a phrase (n-value): ");
        int numWords = input.nextInt();
        System.out.print("Please enter the cutoff threshold for showing hits: ");
        int minHits = input.nextInt();
        System.out.println();

        ArrayList<filePair> pairs = new ArrayList<filePair>();
        double totalComparisons = files.size() * (files.size() - 1) / 2;
        double count = 0;
        double progress = 0;
        System.out.println("                 SEARCH PROGRESS                  ");
        System.out.println("--------------------------------------------------|100%");
        for(int a = 0; a < files.size() - 1; a++)
        {
            for(int b = a + 1; b < files.size(); b++)
            {
                String f1 = directories.get(fileGroup) + "/" + files.get(a);
                String f2 = directories.get(fileGroup) + "/" + files.get(b);
                int val = hitCounter(sequences(f1,numWords),sequences(f2,numWords));
                if(val >= minHits)
                    pairs.add(new filePair(files.get(a),files.get(b),val));
                count++;
                if(count / totalComparisons - progress >= 0.02)
                {
                    System.out.print(">");
                    progress += 0.02;
                }
            }
        }
        System.out.println(">\n");
        Collections.sort(pairs,Collections.reverseOrder());
        System.out.println("HIT LIST:");
        for(filePair f : pairs)
            System.out.println(f);
    }

    private static ArrayList<String> sequences(String pathName, int numWords)
    {
        ArrayList<String> sequences = new ArrayList<String>();
        for(int i = 0; i < numWords; i++)
        {
            Scanner file = null;
            try
            {
                file = new Scanner(new File(pathName));
            }
            catch(Exception ex)
            {
                System.out.print("FILE NOT FOUND");
                System.exit(0);
            }
            for(int t = 0; t < i; t++)
            {
                if(file.hasNext())
                    file.next();
            }
            while(file.hasNext())
            {
                String phrase = "";
                for(int j = 0; j < numWords; j++)
                {
                    if(file.hasNext())
                        phrase += file.next().replaceAll("\\p{Punct}", "").toLowerCase();
                    else
                        phrase = null;
                }
                if(phrase != null && !sequences.contains(phrase))
                    sequences.add(phrase);
            }
        }
        return sequences;
    }

    private static int hitCounter(ArrayList<String> first, ArrayList<String> second)
    {
        int hitCount = 0;
        for(String s : first)
        {
            for(String t : second)
            {
                if(s.equals(t))
                {
                    hitCount++;
                    break;
                }
            }
        }
        return hitCount;
    }
}
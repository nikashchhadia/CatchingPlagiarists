/**
 * Custom class that holds two file names and a hit count.
 * Implements comparable based on greater # of hits.
 *
 * @author Nikash Chhadia
 * @version 4/19/22
 */
public class filePair implements Comparable<filePair>
{
    private String file1;
    private String file2;
    private int hits;

    public filePair(String f1, String f2, int h)
    {
        file1 = f1;
        file2 = f2;
        hits = h;
    }
    
    public int compareTo(filePair p)
    {
        if(this.hits > p.hits)
            return 1;
        else if (this.hits < p.hits)
            return -1;
        return 0;
    }

    public String toString() {
        return "[" + file1 + ", " + file2 + "]\t" + "  ->  " + hits;
    }
}
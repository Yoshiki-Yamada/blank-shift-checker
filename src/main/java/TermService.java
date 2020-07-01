import java.util.ArrayList;
import java.util.List;

public class TermService {

    private static List<Term> diff(Term extent, Term comparison) {
        var extentStartTime = extent.getStart();
        var extentEndTime = extent.getEnd();
        var comparisonStartTime = comparison.getStart();
        var comparisonEndTime = comparison.getEnd();

        var diffs = new ArrayList<Term>();
        // calc blank and in the diffs.
        if (extentStartTime.isEqual(comparisonStartTime)) {
            diffs.add(null);
        } else {
            diffs.add(new Term(extentStartTime, comparisonStartTime));
        }
        // calc next extent start time and in the diffs.
        if (comparisonEndTime.isEqual(extentEndTime)) {
            diffs.add(null);
        } else {
            diffs.add(new Term(comparisonEndTime, extentEndTime));
        }
        return diffs; // {0: blank, 1: next-extent}
    }

    public static List<Term> findBlanks(Term extent, List<Term> comparisons) {
        var blanks = new ArrayList<Term>();
        for (var comparison : comparisons) {
            var diffs = diff(extent, comparison);
            if (diffs.get(0) != null) {
                blanks.add(diffs.get(0));
            }
            extent = diffs.get(1);
        }
        blanks.add(extent);
        return blanks;
    }
}
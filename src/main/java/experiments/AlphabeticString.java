package experiments;

import process.interfaces.worklists.FixedSizeFIFOWorkList;
import process.types.BString;

public class AlphabeticString extends BString<Character> {
    public AlphabeticString(String s) {
        super(BString.wrap(s.toCharArray()));
    }

    public AlphabeticString(FixedSizeFIFOWorkList<Character> q) {
        super(q);
    }

    public AlphabeticString(Character[] s) {
        super(s);
    }

    public static Class<Character> getLetterType() {
        return Character.class;
    }
}

public class Rule {
    private Clothing clothing1;
    private Clothing clothing2;
    private boolean pair;

    public Rule(Clothing clothing1, Clothing clothing2, boolean pair) {
        this.clothing1 = clothing1;
        this.clothing2 = clothing2;
        this.pair = pair;
    }

    public Clothing getClothing1() {
        return clothing1;
    }

    public Clothing getClothing2() {
        return clothing2;
    }

    public boolean pairOrDontPair() {
        return pair;
    }

    public String toString() {
        if (pair == false) {
            return "Don't pair: " + clothing1.getCategory() + " and " + clothing2.getCategory();
        } else {
            return "Always pair: " + clothing1.getCategory() + " and " + clothing2.getCategory();
        }
    }
}

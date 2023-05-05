public class Outfit {
    private Clothing top;
    private Clothing bottom;
    private Clothing shoes;

    public Outfit() {
        this.top = null;
        this.bottom = null;
        this.shoes = null;
    }

    public Outfit(Clothing top, Clothing bottom, Clothing shoes) {
        this.top = top;
        this.bottom = bottom;
        this.shoes = shoes;
    }

    public boolean contains(Clothing clothing) {
        if (top.equals(clothing) || bottom.equals(clothing) || shoes.equals(clothing)) {
            return true;
        } else {
            return false;
        }
    }

    public void change (Clothing newClothes) {
        if (newClothes.getType().equals("Top")) {
            this.top = newClothes;
        } else if (newClothes.getType().equals("Bottom")) {
            this.bottom = newClothes;
        } else {
            this.shoes = newClothes;
        }
    }
    
    public String toString() {
        return top.getCategory() + ", " + bottom.getCategory() + ", " + shoes.getCategory();
    }
}

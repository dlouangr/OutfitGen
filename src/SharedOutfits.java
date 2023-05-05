import java.util.ArrayList;
import java.util.List;

public class SharedOutfits {
    private static List<Outfit> outfits;

    public SharedOutfits() {
        outfits = new ArrayList<>();
    }

    public static void addOutfit(Outfit outfit) {
        outfits.add(outfit);
    }

    public static List<Outfit> getOutfits() {
        return outfits;
    }

    public void clearOutfits() {
        outfits.clear();
    }
}

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Clothing {
    private String type;
    private String category;

    public Clothing(String type, String category) {
        if (type.isBlank()) {
            if (Arrays.asList(getCategoryTops()).contains(category)) {
                this.type = "Top";
            } else if (Arrays.asList(getCategoryBottoms()).contains(category)) {
                this.type = "Bottom";
            } else {
                this.type = "Shoes";
            }
        } else {
            this.type = type;
        }
        this.category = category;
    }
    

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static String[] getCategoryTops() {
        return new String[] {"T-Shirt", "Button-Down Shirt", "Tank Top", "Polo Shirt", "Sweatshirt", "Blouse", "Dress Shirt"};
    }
    
    public static String[] getCategoryBottoms() {
        return new String[] {"Jeans", "Cargos", "Leggings", "Chinos", "Sweatpants", "Shorts", "Skirt"};
    }
    
    public static String[] getCategoryShoes() {
        return new String[] {"Sneakers", "Boots", "Sandals", "Loafers", "Dress Shoes", "Flats", "High Heels"};
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Clothing)) {
            return false;
        }
        Clothing other = (Clothing) obj;
        return this.type.equals(other.getType()) && this.category.equals(other.getCategory());
    }
    
    public void writeToFile (File filename) {
        
        try {
            FileWriter writer = new FileWriter(filename);
            BufferedWriter buffer = new BufferedWriter(writer);
            
            // Write the clothing item to the file
            buffer.write(type + "," + category);
            buffer.newLine();
            
            // Close the buffer and writer
            buffer.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String toString() {
        return category + "," + type;
    }
}

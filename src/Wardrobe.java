import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Wardrobe {
    private List<Clothing> clothesArray;

    public Wardrobe() {
        clothesArray = new ArrayList<>();
    }

    public void addClothing(Clothing clothing) {
        clothesArray.add(clothing);
    }

    public List<Clothing> getClothes() {
        return clothesArray;
    }

    public List<Clothing> getTops() {
        List<Clothing> topsList = new ArrayList<>();
        
        for (Clothing clothing : this.clothesArray) {
            if (clothing.getType().equals("Top")) {
                topsList.add(clothing);
            }
        }
        return topsList;
    }

    public List<Clothing> getBottoms() {
        List<Clothing> bottomsList = new ArrayList<>();

        for (Clothing clothing : this.clothesArray) {
            if (clothing.getType().equals("Bottom")) {
                bottomsList.add(clothing);
            }
        }
        return bottomsList;
    }

    public List<Clothing> getShoes() {
        List<Clothing> shoesList = new ArrayList<>();
        for (Clothing clothing : this.clothesArray) {
            if (clothing.getType().equals("Shoes")) {
                shoesList.add(clothing);
            }
        }
        return shoesList;
    }
    

    public Outfit generateOutfit(Rulebook rulebook) {

        List<Clothing> tops = new ArrayList<>();
        List<Clothing> bottoms = new ArrayList<>();
        List<Clothing> shoes = new ArrayList<>();
    
        for (Clothing clothing : clothesArray) {
            if (clothing.getType().equals("Top")) {
                tops.add(clothing);
            } else if (clothing.getType().equals("Bottom")) {
                bottoms.add(clothing);
            } else if (clothing.getType().equals("Shoes")) {
                shoes.add(clothing);
            }
        }
    
        if (clothesArray.isEmpty()) {
            System.out.println("Wardrobe is empty!");
            return null;
        } else if (tops.isEmpty() || bottoms.isEmpty() || shoes.isEmpty()) {
            System.out.println("Wardrobe is missing items!");
            return null;
        }
    
        // Check if the rules forbid any clothing combination
        int attempts = 0;
        while (attempts < 40) {
            Random random = new Random();
            Clothing top = tops.get(random.nextInt(tops.size()));
            Clothing bottom = bottoms.get(random.nextInt(bottoms.size()));
            Clothing shoe = shoes.get(random.nextInt(shoes.size()));
            Outfit outfit = new Outfit(top, bottom, shoe);
    
            // Check if the outfit violates any of the rules
            boolean validOutfit = true;
            for (Rule rule : rulebook.getRules()) {
                if (rule.pairOrDontPair() == true && outfit.contains(rule.getClothing1())) {
                    outfit.change(rule.getClothing2());
                } else if (rule.pairOrDontPair() == false && outfit.contains(rule.getClothing1()) && outfit.contains(rule.getClothing2())) {
                    validOutfit = false;
                }
            }
    
            if (validOutfit) {
                return outfit;
            }
            attempts++;
        }
        System.out.println("Can't make an outfit with the current rules.");
        return null;
    }

    public boolean writeToFile() {

        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            if (!file.getName().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }

            try {
                FileWriter writer = new FileWriter(file);
                BufferedWriter buffer = new BufferedWriter(writer);
                for (Clothing clothing : clothesArray) {
                    buffer.write(clothing.toString());
                    buffer.newLine();
                }
                buffer.close();
                writer.close();
                System.out.println("Successfully wrote clothes to file.");
                return true;
            } catch (IOException e) {
                System.out.println("Error writing clothes to file.");
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
    

    public boolean loadFromFile() {
        JFileChooser fileChooser = new JFileChooser(new File("."));
        fileChooser.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        String category = parts[0];
                        String type = parts[1];
                        Clothing clothing = new Clothing(type, category);
                        addClothing(clothing);
                    }
                }                
                reader.close();
                System.out.println("Successfully loaded clothes from file.");
                return true;
            } catch (IOException e) {
                System.out.println("Error loading clothes from file.");
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
    
    public void clear () {
        clothesArray.clear();
    }

    public void delete() {
        try {
            java.io.FileWriter writer = new java.io.FileWriter("clothes.txt");
            java.io.BufferedWriter buffer = new java.io.BufferedWriter(writer);
    
            // write nothing to the file (deletes contents)
            buffer.write("");
            buffer.close();
            writer.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}

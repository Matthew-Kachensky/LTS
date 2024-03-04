package lts.utils;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {
    // TODO: change this as needed depending on root directory
    private static final String relativePath = ".\\LTS\\src\\lts\\utils\\data\\";
    //maps card name to description
    public static Map<String, String> descriptions = new HashMap<>();
    // stores modifier names which include roll modifier values
    public static List<String> modNames = List.of("+2/-2", "+1/-3", "+3/-1", "+4", "-4");
    // stores positive and negative roll modifiers respectively
    public static Map<String, Integer> buffs = new HashMap<>();
    public static Map<String, Integer> nerfs = new HashMap<>();
    // list of champion names
    public static List<String> champNames;
    //map of name to passives (both champ and boss), hero abilities, spell effects, and item effects
    public static Map<String, String> abilities = new HashMap<>();
    // list of hero names
    public static List<String> heroNames;
    // map of hero or champion name to their region,
    // also a map of boss name to region required to fight them
    public static Map<String, RegionType> regions = new HashMap<>();
    // map of hero name to minimum roll to activate their ability
    public static Map<String, Integer> minRolls = new HashMap<>();
    // lists of spell names, item names, and boss names
    public static List<String> spellNames;
    public static List<String> itemNames;
    public static List<String> bossNames;
    // Maps of boss names to positive and negative roll thresholds to either slay or defeat them.
    public static Map<String, Integer> posRolls = new HashMap<>();
    public static Map<String, Integer> negRolls = new HashMap<>();
    public static Map<String, Integer> minHeroes = new HashMap<>();

    /**
     * Updates the class data structures by populating them with data (from various text files)
     */
    public static void updateData(){
        populateChampions();
        populateHeroes();
        populateMods();
        populateChampions();
        populateBosses();
        populateItems();
        populateSpells();
        populateDesc();
    }

    /**
     * Populates card descriptions
     */
    private static void populateDesc(){

        descriptions = new HashMap<>();

        // Specify the relative path to champion data
        String filePath = relativePath + "descriptions.txt";

        // File path is passed as parameter
        File file = new File(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Read the file line by line
            String st;
            String[] parts;
            while ((st = br.readLine()) != null) {
                parts = st.split(": ");

                if(parts.length == 2){
                    String name = parts[0];
                    String description = parts[1];
                    descriptions.put(name, description);
                }else{
                    System.out.println("Invalid description data line format");
                }
            }
        } catch (IOException e) {
            // Handle the exception (e.g., print an error message)
            e.printStackTrace();
        }
    }

    /**
     * Populates Modifier names and related values
     */
    private static void populateMods(){

        Integer buff;
        Integer nerf;

        for (String name : modNames) {
            if(name.contains("/")){
                String[] parts = name.split("/");
                buff = Integer.parseInt(parts[0].substring(1));
                nerf = Integer.parseInt(parts[1]);
                buffs.put(name,buff);
                nerfs.put(name,nerf);
            }else{
                if (name.charAt(0) == '+'){
                    buff = Integer.parseInt(name.substring(1,2));
                    buffs.put(name, buff);
                    nerfs.put(name, 0);
                }else{
                    nerf = Integer.parseInt(name.substring(0,2));
                    nerfs.put(name, nerf);
                    buffs.put(name, 0);
                }
            }
        }
    }

    /**
     * Populates champion names and related passives and regions
     */
    private static void populateChampions(){

        champNames = new ArrayList<String>();

        // Specify the relative path to champion data
        String filePath = relativePath + "champions.txt";

        // File path is passed as parameter
        File file = new File(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Read the file line by line
            String st;
            String[] parts;
            while ((st = br.readLine()) != null) {
                parts = st.split(",");

                if(parts.length == 3){
                    String name = parts[0];
                    String region = parts[1];
                    String passive = parts[2];
                    champNames.add(name);
                    regions.put(name, RegionType.fromString(region));
                    abilities.put(name, passive);
                }else{
                    System.out.println("Invalid champion data line format");
                }
            }
        } catch (IOException e) {
            // Handle the exception (e.g., print an error message)
            e.printStackTrace();
        }
    }

    /**
     * populates hero names and related abilities, region, and minimum ability rolls
     */
    private static void populateHeroes(){
        heroNames = new ArrayList<String>();

        // Specify the relative path to champion data
        String filePath = relativePath + "heroes.txt";

        // File path is passed as parameter
        File file = new File(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Read the file line by line
            String st;
            String[] parts;
            while ((st = br.readLine()) != null) {
                parts = st.split(",");

                if(parts.length == 4){
                    String name = parts[0];
                    String region = parts[1];
                    String ability = parts[2];
                    String minRoll = parts[3];
                    heroNames.add(name);
                    regions.put(name, RegionType.fromString(region));
                    abilities.put(name, ability);
                    minRolls.put(name, Integer.parseInt(minRoll));
                }else{
                    System.out.println("Invalid hero data line format");
                }
            }
        } catch (IOException e) {
            // Handle the exception (e.g., print an error message)
            e.printStackTrace();
        }
    }

    /**
     * populates boss names and related passives, regions, positive roll values (value required to slay),
     * negative roll values (defeat), required party hero count, and required party regions
     */
    private static void populateBosses(){
        bossNames = new ArrayList<String>();

        // Specify the relative path to champion data
        String filePath = relativePath + "bosses.txt";

        // File path is passed as parameter
        File file = new File(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Read the file line by line
            String st;
            String[] parts;
            while ((st = br.readLine()) != null) {
                parts = st.split(",");

                if(parts.length == 6){
                    String name = parts[0];
                    String ability = parts[1];
                    String posRoll = parts[2];
                    String negRoll = parts[3];
                    String reqHeroCount = parts[4];
                    String reqRegionType = parts[5];
                    bossNames.add(name);
                    abilities.put(name, ability);
                    posRolls.put(name, Integer.parseInt(posRoll));
                    negRolls.put(name, Integer.parseInt(negRoll));
                    minHeroes.put(name, Integer.parseInt(reqHeroCount));
                    regions.put(name, RegionType.fromString(reqRegionType));
                }else{
                    System.out.println("Invalid boss data line format");
                }
            }
        } catch (IOException e) {
            // Handle the exception (e.g., print an error message)
            e.printStackTrace();
        }
    }

    public static void populateSpells(){
        spellNames = new ArrayList<String>();

        // Specify the relative path to champion data
        String filePath = relativePath + "spells.txt";

        // File path is passed as parameter
        File file = new File(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Read the file line by line
            String st;
            String[] parts;
            while ((st = br.readLine()) != null) {
                parts = st.split(",");

                if(parts.length == 2){
                    String name = parts[0];
                    String effect = parts[1];
                    spellNames.add(name);
                    abilities.put(name, effect);
                }else{
                    System.out.println("Invalid spell data line format");
                }
            }
        } catch (IOException e) {
            // Handle the exception (e.g., print an error message)
            e.printStackTrace();
        }
    }

    public static void populateItems(){
        itemNames = new ArrayList<String>();

        // Specify the relative path to champion data
        String filePath = relativePath + "items.txt";

        // File path is passed as parameter
        File file = new File(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // Read the file line by line
            String st;
            String[] parts;
            while ((st = br.readLine()) != null) {
                parts = st.split(",");

                if(parts.length == 2){
                    String name = parts[0];
                    String effect = parts[1];
                    itemNames.add(name);
                    abilities.put(name, effect);
                }else{
                    System.out.println("Invalid item data line format");
                }
            }
        } catch (IOException e) {
            // Handle the exception (e.g., print an error message)
            e.printStackTrace();
        }
    }

    public static Integer getMinRoll(String name){
        return minRolls.get(name);
    }

    public static RegionType getRegion(String name){
        return regions.get(name);
    }

    public static String getAbility(String name){
        return abilities.get(name);
    }

    public static Integer getNegRoll(String name){
        return negRolls.get(name);
    }

    public static Integer getPosRoll(String name){
        return posRolls.get(name);
    }
    public static Integer getMinHeroes(String name){
        return minHeroes.get(name);
    }

}

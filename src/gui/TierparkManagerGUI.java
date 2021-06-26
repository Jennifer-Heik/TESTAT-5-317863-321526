/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Checkbox;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import logic.Animal;
import logic.Food;
import logic.Settings;

/**
 *
 * @author TT
 */
public class TierparkManagerGUI extends javax.swing.JFrame {

    String AgeError = "";
    String NameError = "";
    String GenderError = "";
    String TypeError = "";
    String foodnameError = "";
    String foodquantityError = "";
    String foodtypeError = "";
    static int rows = 0;
    static int rowsFood = 0;

    static String TitleText = "Tierpark Manager 2021";
    static String ProgramVersion = "Version 1 2021";
    static String Licensee = "Created by Jen and Judith - jen.judith@web.de";

    Boolean deleted = false;
    Boolean updated = false;

    private static ArrayList<Animal> Animals = new ArrayList<Animal>();
    private static ArrayList<Food> Foods = new ArrayList<Food>();
    JTextField field1 = new JTextField();
    JTextField field2 = new JTextField();
    Checkbox health = new Checkbox();
    Checkbox fed = new Checkbox();
    JTextField field5 = new JTextField();
    JTextField field6 = new JTextField();
    JTextField foodfield1 = new JTextField();
    JTextField foodfield2 = new JTextField();
    JTextField foodfield3 = new JTextField();
    JTextField SettingField1 = new JTextField();
    JTextField SettingField2 = new JTextField();
    JTextField SettingField3 = new JTextField();
    Object[] fields = {
        "Name", field1, "Age", field2, "Healthy?", health, "Fed?", fed, "Gender", field5, "Type", field6

    };
    Object[] settingFields = {
        "Program Name: ", SettingField1, "Program Version: ", SettingField2, "Licensee and Contact Info: ", SettingField3

    };

    Object[] fields2 = {
        "Name", foodfield1, "Quantity", foodfield2, "Type", foodfield3

    };

    Object[] options = {"Save",
        "Cancel", "Clear all"};
    Object[] options2 = {"Save",
        "Cancel"};

    /**
     * Creates new form TierparkManager
     */
    public TierparkManagerGUI() {

        initComponents();
        NewName();
        this.setLocationRelativeTo(null);
        AnimalPanel.setVisible(false);
        FoodPanel.setVisible(false);
        WelcomeLabel.setVisible(true);
        populateTable();
        populateTableFood();
        AnimalTable.getModel().addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {
                if (!AnimalTable.getSelectionModel().isSelectionEmpty() && deleted == false && updated == false) {
                    DefaultTableModel model = (DefaultTableModel) AnimalTable.getModel();
                    int row = AnimalTable.getSelectedRow();
                    int id = (int) model.getValueAt(row, 0);

                    String name = (String) model.getValueAt(row, 1);
                    int age = (int) model.getValueAt(row, 2);
                    boolean health_status = (boolean) model.getValueAt(row, 3);
                    boolean feeding_status = (boolean) model.getValueAt(row, 4);
                    String gender = (String) model.getValueAt(row, 5);
                    String specific_type = (String) model.getValueAt(row, 6);
                    Animal animal = getAnimal(id);
                    animal.setName(name);
                    animal.setAge(age);
                    animal.setHealth(health_status);
                    animal.setFeeding(feeding_status);
                    animal.setGender(gender);
                    animal.setType(specific_type);
                }
            }
        });
        FoodTable.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                if (!FoodTable.getSelectionModel().isSelectionEmpty() && deleted == false && updated == false) {
                    DefaultTableModel model = (DefaultTableModel) FoodTable.getModel();
                    int row = FoodTable.getSelectedRow();
                    int id = (int) model.getValueAt(row, 0);

                    String name = (String) model.getValueAt(row, 1);
                    int quantity = (int) model.getValueAt(row, 2);
                    String type = (String) model.getValueAt(row, 3);
                    Food food = getFood(id);
                    food.setName(name);
                    food.setQuantity(quantity);
                    food.setType(type);

                }
            }
        });
    }

    private static void serializeSettings(Settings settings) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("settings.ser"));
            out.writeObject(settings);
        } catch (IOException exception) {
            System.out.println("There was an error during the serialization.");
            System.out.println(exception);
        }
    }

    private static Settings deserializeSettings(String fileName) {
        Settings settings = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            settings = (Settings) in.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            System.out.println("There was an error during the deserialization.");
            System.out.println(exception);
        }
        return settings;
    }

    private void NewName() {
        this.setTitle(TitleText);
        SettingField1.setText(TitleText);
        SettingField2.setText(ProgramVersion);
        SettingField3.setText(Licensee);
    }

    public static void serializeAnimals(ArrayList<Animal> animals) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("animals2.ser"));
            out.writeObject(animals);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

    public static ArrayList<Animal> deserializeAnimals(String fileName) {
        ArrayList<Animal> animals = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            animals = (ArrayList<Animal>) in.readObject();
        } catch (FileNotFoundException ex) {
            System.out.println("No animals found in file");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return animals;
    }

    public static void serializeFoods(ArrayList<Food> foods) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("food2.ser"));
            out.writeObject(foods);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

    public static ArrayList<Food> deserializeFoods(String fileName) {
        ArrayList<Food> foods = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            foods = (ArrayList<Food>) in.readObject();
        } catch (FileNotFoundException ex) {
            System.out.println("No foods found in file");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return foods;
    }

    public static ArrayList<Food> getFoods() {
        return Foods;
    }

    public static void finalIDFood() {
        try {
            Food food = Foods.get(Foods.size() - 1);
            rowsFood = food.getFoodID();
        } catch (IndexOutOfBoundsException e) {
            rowsFood = 0;
        }

    }

    public static void addFood(Food food) {
        getFoods();
        try {
            finalIDFood();
            food.setFoodID(rowsFood + 1);
            Foods.add(food);
        } catch (NullPointerException e) {
            food.setFoodID(1);
            Foods.add(food);
        }

    }

    public static void deleteFood(int id) {
        Food food = getFood(id);
        Foods.remove(food);

    }

    public static Food getFood(int id) {
        for (Food food : Foods) {
            if (food.getFoodID() == id) {
                return food;
            }
        }

        return null;
    }

    public static ArrayList<Animal> getAnimals() {
        return Animals;
    }

    public static void finalID() {
        try {
            Animal animal = Animals.get(Animals.size() - 1);
            rows = animal.getAnimalID();
        } catch (IndexOutOfBoundsException e) {
            rows = 0;
        }

    }

    public static void addAnimal(Animal animal) {
        getAnimals();
        try {
            finalID();
            animal.setAnimalID(rows + 1);
            Animals.add(animal);
        } catch (NullPointerException e) {
            animal.setAnimalID(1);
            Animals.add(animal);
        }

    }

    public static void deleteAnimal(int id) {
        Animal animal = getAnimal(id);

        Animals.remove(animal);

    }

    public static Animal getAnimal(int id) {
        for (Animal animal : Animals) {
            if (animal.getAnimalID() == id) {
                return animal;
            }
        }

        return null;
    }

    public void populateTable() {

        DefaultTableModel model = (DefaultTableModel) AnimalTable.getModel();
        ArrayList<Animal> localAnimals = getAnimals();

        //remove all old rows
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }

        //add all animals from localAnimals into the table
        Object rowData[] = new Object[7];
        for (int i = 0; i < localAnimals.size(); i++) {
            rowData[0] = localAnimals.get(i).getAnimalID();
            rowData[1] = localAnimals.get(i).getName();
            rowData[2] = localAnimals.get(i).getAge();
            rowData[3] = localAnimals.get(i).getHealth();
            rowData[4] = localAnimals.get(i).getfeeding();
            rowData[5] = localAnimals.get(i).getGender();
            rowData[6] = localAnimals.get(i).getType();
            model.addRow(rowData);
        }

    }

    public void populateTableFood() {
        DefaultTableModel model = (DefaultTableModel) FoodTable.getModel();
        ArrayList<Food> localFoods = getFoods();

        //remove all old rows
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }

        Object rowData[] = new Object[7];
        for (int i = 0; i < localFoods.size(); i++) {
            rowData[0] = localFoods.get(i).getFoodID();
            rowData[1] = localFoods.get(i).getName();
            rowData[2] = localFoods.get(i).getQuantity();
            rowData[3] = localFoods.get(i).getType();
            model.addRow(rowData);
        }

    }

    private boolean validInput() {

        try {
            int age = Integer.parseInt(field2.getText());
            if (age < 0) {
                AgeError = "Age has to be bigger or equal to 0.\n";
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            if (field2.getText().equals("")) {
                AgeError = "Age is empty.\n";
                return false;
            } else {
                AgeError = "Age can contain only numbers.\n";
            }
            return false;
        }
    }

    private boolean validInput2() {

        try {
            int quantity = Integer.parseInt(foodfield2.getText());
            if (quantity < 0) {
                foodquantityError = "Quantity has to be bigger or equal to 0.\n";
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            if (foodfield2.getText().equals("")) {
                foodquantityError = "Quantity is empty.\n";
                return false;
            } else {
                foodquantityError = "Quantity can contain only numbers.\n";
            }

            return false;

        }
    }

    public boolean containsOnlyLetters1(JTextField field1) {//checks if string contains only letters
        String string = field1.getText();
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if (Character.isLetter(c) || Character.isWhitespace(c) || Character.isISOControl(c)) {

                return true;
            }
        }
        if (field1.getText().equals("")) {
            NameError = "Name is empty.\n";
        } else {
            NameError = "Name can contain only letters.\n";

        }
        return false;
    }

    public boolean containsOnlyLetters5(JTextField field5) {//checks if string contains only letters
        if (field5.getText().equals("Male")) {

            return true;
        }
        if (field5.getText().equals("male")) {
            field5.setText("Male");
            return true;
        }
        if (field5.getText().equals("Female")) {

            return true;
        }
        if (field5.getText().equals("female")) {
            field5.setText("Female");
            return true;
        }

        if (field5.getText().equals("")) {
            GenderError = "Empty Gender Input! Please enter Male or Female!\n";
        } else {
            GenderError = "Invalid Gender Input.\nPlease enter either Male or Female!\n";

        }
        return false;
    }

    public boolean containsOnlyLetters6(JTextField field6) {//checks if string contains only letters
        String string = field6.getText();
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if (Character.isLetter(c) || Character.isWhitespace(c) || Character.isISOControl(c)) {

                return true;
            }
        }
        if (field6.getText().equals("")) {
            TypeError = "Type Field is empty.\n";
        } else {
            TypeError = "Type can contain only letters.\n";

        }
        return false;
    }

    public boolean containsOnlyLettersname(JTextField foodfield1) {//checks if string contains only letters
        String string = foodfield1.getText();
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if (Character.isLetter(c) || Character.isWhitespace(c) || Character.isISOControl(c)) {

                return true;
            }
        }
        if (foodfield1.getText().equals("")) {
            foodnameError = "Name is empty.\n";
        } else {
            foodnameError = "Name can contain only numbers.\n";

        }
        return false;
    }

    public boolean containsOnlyLetterstype(JTextField foodfield3) {//checks if string contains only letters
        String string = foodfield3.getText();
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if (Character.isLetter(c) || Character.isWhitespace(c) || Character.isISOControl(c)) {

                return true;
            }
        }
        if (foodfield3.getText().equals("")) {
            foodtypeError = "Type is empty.\n";
        } else {
            foodtypeError = "Type can contain only numbers.\n";

        }
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AnimalPanel = new javax.swing.JPanel();
        AnimalLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        AnimalTable = new javax.swing.JTable();
        CreateNewAnimalButton = new javax.swing.JButton();
        DeleteAnimalButton = new javax.swing.JButton();
        FoodPanel = new javax.swing.JPanel();
        FoodLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        FoodTable = new javax.swing.JTable();
        CreateNewFoodButton1 = new javax.swing.JButton();
        DeleteFoodButton1 = new javax.swing.JButton();
        WelcomeLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        StartingPage = new javax.swing.JMenuItem();
        SettingsMenu = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        BasicDataMenu = new javax.swing.JMenu();
        ManageAnimals = new javax.swing.JMenuItem();
        ManageFood = new javax.swing.JMenuItem();
        ManageStaff = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        HelpMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Tierpark Manager 2021");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        AnimalLabel.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        AnimalLabel.setText("Animals at TierparkPforzheim");

        AnimalTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Animal ID", "Name", "Age", "Healthy", "Fed", "Gender", "Type"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        AnimalTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(AnimalTable);

        CreateNewAnimalButton.setText("NEW ANIMAL");
        CreateNewAnimalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateNewAnimalButtonActionPerformed(evt);
            }
        });

        DeleteAnimalButton.setText("DELETE ANIMAL");
        DeleteAnimalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteAnimalButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AnimalPanelLayout = new javax.swing.GroupLayout(AnimalPanel);
        AnimalPanel.setLayout(AnimalPanelLayout);
        AnimalPanelLayout.setHorizontalGroup(
            AnimalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AnimalPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(CreateNewAnimalButton)
                .addGap(12, 12, 12)
                .addComponent(DeleteAnimalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(AnimalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AnimalLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AnimalPanelLayout.setVerticalGroup(
            AnimalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AnimalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AnimalLabel)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AnimalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CreateNewAnimalButton)
                    .addComponent(DeleteAnimalButton))
                .addGap(0, 44, Short.MAX_VALUE))
        );

        FoodLabel.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        FoodLabel.setText("Food at TierparkPforzheim");

        FoodTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Food ID", "Name", "Quantity", "Type"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(FoodTable);

        CreateNewFoodButton1.setText("NEW FOOD");
        CreateNewFoodButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateNewFoodButton1ActionPerformed(evt);
            }
        });

        DeleteFoodButton1.setText("DELETE FOOD");
        DeleteFoodButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteFoodButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout FoodPanelLayout = new javax.swing.GroupLayout(FoodPanel);
        FoodPanel.setLayout(FoodPanelLayout);
        FoodPanelLayout.setHorizontalGroup(
            FoodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FoodPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(CreateNewFoodButton1)
                .addGap(12, 12, 12)
                .addComponent(DeleteFoodButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(FoodPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(FoodLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        FoodPanelLayout.setVerticalGroup(
            FoodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FoodPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(FoodLabel)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(FoodPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CreateNewFoodButton1)
                    .addComponent(DeleteFoodButton1))
                .addGap(0, 45, Short.MAX_VALUE))
        );

        WelcomeLabel.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        WelcomeLabel.setText("Welcome to Tierpark Pforzheim Management System!");

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        StartingPage.setText("Starting Page");
        StartingPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartingPageActionPerformed(evt);
            }
        });
        fileMenu.add(StartingPage);

        SettingsMenu.setText("Settings");
        SettingsMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SettingsMenuActionPerformed(evt);
            }
        });
        fileMenu.add(SettingsMenu);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        BasicDataMenu.setMnemonic('e');
        BasicDataMenu.setText("BASIC DATA");

        ManageAnimals.setMnemonic('t');
        ManageAnimals.setText("Manage Animals");
        ManageAnimals.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManageAnimalsActionPerformed(evt);
            }
        });
        BasicDataMenu.add(ManageAnimals);

        ManageFood.setMnemonic('y');
        ManageFood.setText("Manage Food");
        ManageFood.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManageFoodActionPerformed(evt);
            }
        });
        BasicDataMenu.add(ManageFood);

        ManageStaff.setMnemonic('p');
        ManageStaff.setText("Manage Staff");
        ManageStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ManageStaffActionPerformed(evt);
            }
        });
        BasicDataMenu.add(ManageStaff);

        menuBar.add(BasicDataMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        HelpMenuItem.setMnemonic('c');
        HelpMenuItem.setText("Help");
        HelpMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HelpMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(HelpMenuItem);

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(AnimalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(FoodPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(70, 70, 70)
                    .addComponent(WelcomeLabel)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(AnimalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(FoodPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 21, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(173, 173, 173)
                    .addComponent(WelcomeLabel)
                    .addContainerGap(173, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        int answer; // What is the click of the user? Yes or NO?
        answer = JOptionPane.showConfirmDialog(this, "Closing the App?", "Please confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (answer == JOptionPane.NO_OPTION) {
        }
        if (answer == JOptionPane.YES_OPTION) {
            serializeSettings(new Settings(TitleText, ProgramVersion, Licensee));
            serializeAnimals(Animals);
            serializeFoods(Foods);
            System.exit(0);
        }
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void ManageFoodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManageFoodActionPerformed
        WelcomeLabel.setVisible(false);
        AnimalPanel.setVisible(false);
        FoodPanel.setVisible(true);
    }//GEN-LAST:event_ManageFoodActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int answer; // What is the click of the user? Yes or NO?
        answer = JOptionPane.showConfirmDialog(this, "Closing the App?", "Please confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (answer == JOptionPane.YES_OPTION) {
            serializeSettings(new Settings(TitleText, ProgramVersion, Licensee));
            serializeAnimals(Animals);
            serializeFoods(Foods);
            System.exit(0);
        }

    }//GEN-LAST:event_formWindowClosing

    private void ManageAnimalsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManageAnimalsActionPerformed
        WelcomeLabel.setVisible(false);
        AnimalPanel.setVisible(true);
    }//GEN-LAST:event_ManageAnimalsActionPerformed

    private void CreateNewAnimalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateNewAnimalButtonActionPerformed

        boolean choice = true;
        while (choice) {
            int n = JOptionPane.showOptionDialog(this,
                    fields,
                    "Create new animal",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, //do not use a custom Icon
                    options, //the titles of buttons
                    options[0]); //default button title
            if (n == JOptionPane.YES_OPTION) {
                if (validInput() & containsOnlyLetters1(field1) & containsOnlyLetters5(field5) & containsOnlyLetters6(field6)) {
                    AgeError = "";
                    NameError = "";
                    GenderError = "";
                    TypeError = "";

                    String name = field1.getText();
                    int age = Integer.parseInt(field2.getText());
                    boolean health_status = health.getState();
                    boolean feeding_status = fed.getState();
                    String gender = field5.getText();
                    String specific_type = field6.getText();
                    updated = true;
                    addAnimal(new Animal(name, age, health_status, feeding_status, gender, specific_type));
                    for (int i = 0; i < Animals.size(); i++) {

                        System.out.println(Animals.get(i));
                        populateTable();
                        updated = false;
                        field1.setText("");
                        field2.setText("");
                        health.setState(false);
                        fed.setState(false);
                        field5.setText("");
                        field6.setText("");

                    }
                } else {
                    JOptionPane.showMessageDialog(this, AgeError + NameError + TypeError + "Please reenter!\n" + GenderError, "Input Error", JOptionPane.WARNING_MESSAGE);

                }
            }
            if (n == JOptionPane.NO_OPTION) {
                field1.setText("");
                field2.setText("");
                health.setState(false);
                fed.setState(false);
                field5.setText("");
                field6.setText("");
                choice = false;
            }
            if (n == JOptionPane.CANCEL_OPTION) {
                field1.setText("");
                field2.setText("");
                health.setState(false);
                fed.setState(false);
                field5.setText("");
                field6.setText("");

            }
            if (n == JOptionPane.CLOSED_OPTION) {
                field1.setText("");
                field2.setText("");
                health.setState(false);
                fed.setState(false);
                field5.setText("");
                field6.setText("");
                choice = false;
            }
        }
    }//GEN-LAST:event_CreateNewAnimalButtonActionPerformed

    private void DeleteAnimalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteAnimalButtonActionPerformed

        if (!AnimalTable.getSelectionModel().isSelectionEmpty()) {
            DefaultTableModel model = (DefaultTableModel) AnimalTable.getModel();
            int row = AnimalTable.getSelectedRow();
            int id = (int) model.getValueAt(row, 0);
            String animalName = (String) model.getValueAt(row, 1);
            int answer; // What is the click of the user? Yes or NO?
            answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete animal " + animalName + "?", "Deleting an animal?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer == JOptionPane.YES_OPTION) {
                deleted = true;
                deleteAnimal(id);
                populateTable();
                deleted = false;
            }
            if (answer == JOptionPane.NO_OPTION) {
            }
        } else {
            JOptionPane.showMessageDialog(this, "You have not selected an animal to delete! Please select one.", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_DeleteAnimalButtonActionPerformed

    private void ManageStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ManageStaffActionPerformed
        JOptionPane.showMessageDialog(this, "This section has not yet been implemented!", "Managing Staff", JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_ManageStaffActionPerformed

    private void CreateNewFoodButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateNewFoodButton1ActionPerformed
        boolean choice = true;
        while (choice) {
            int n = JOptionPane.showOptionDialog(this,
                    fields2,
                    "Create new food",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, //do not use a custom Icon
                    options, //the titles of buttons
                    options[0]); //default button title
            if (n == JOptionPane.YES_OPTION) {
                if (validInput2() & containsOnlyLettersname(foodfield1) & containsOnlyLetterstype(foodfield3)) {
                    String name = foodfield1.getText();
                    int quantity = Integer.parseInt(foodfield2.getText());
                    String type = foodfield3.getText();
                    updated = true;
                    addFood(new Food(name, quantity, type));
                    populateTableFood();
                    updated = false;
                    foodfield1.setText("");
                    foodfield2.setText("");
                    foodfield3.setText("");

                } else {
                    JOptionPane.showMessageDialog(this, foodnameError + foodquantityError + foodtypeError + "Please reenter!", "Input Error", JOptionPane.WARNING_MESSAGE);
                }
            }
            if (n == JOptionPane.NO_OPTION) {
                foodfield1.setText("");
                foodfield2.setText("");
                foodfield3.setText("");
                choice = false;
            }
            if (n == JOptionPane.CANCEL_OPTION) {
                foodfield1.setText("");
                foodfield2.setText("");
                foodfield3.setText("");
            }
            if (n == JOptionPane.CLOSED_OPTION) {
                foodfield1.setText("");
                foodfield2.setText("");
                foodfield3.setText("");
                choice = false;
            }
        }
    }//GEN-LAST:event_CreateNewFoodButton1ActionPerformed

    private void DeleteFoodButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteFoodButton1ActionPerformed
        if (!FoodTable.getSelectionModel().isSelectionEmpty()) {
            DefaultTableModel model = (DefaultTableModel) FoodTable.getModel();
            int row = FoodTable.getSelectedRow();
            int id = (int) model.getValueAt(row, 0);
            String foodName = (String) model.getValueAt(row, 1);
            String foodType = (String) model.getValueAt(row, 3);
            int answer; // What is the click of the user? Yes or NO?
            answer = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + foodType + " " + foodName + "?", "Deleting a food?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (answer == JOptionPane.YES_OPTION) {
                deleted = true;
                deleteFood(id);
                populateTableFood();
                deleted = false;
            }
            if (answer == JOptionPane.NO_OPTION) {
            }
        } else {
            JOptionPane.showMessageDialog(this, "You have not selected a food to delete! Please select one.", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_DeleteFoodButton1ActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        JOptionPane.showMessageDialog(this, ProgramVersion, "Information", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void HelpMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HelpMenuItemActionPerformed
        JOptionPane.showMessageDialog(this, "Licensee and Contact Info: " + Licensee, "HELP", JOptionPane.QUESTION_MESSAGE);
    }//GEN-LAST:event_HelpMenuItemActionPerformed

    private void StartingPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartingPageActionPerformed
        AnimalPanel.setVisible(false);
        FoodPanel.setVisible(false);
        WelcomeLabel.setVisible(true);

    }//GEN-LAST:event_StartingPageActionPerformed

    private void SettingsMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SettingsMenuActionPerformed
        int n = JOptionPane.showOptionDialog(this,
                settingFields,
                "Change Settings",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, //do not use a custom Icon
                options2, //the titles of buttons
                options2[0]); //default button title

        if (n == JOptionPane.YES_OPTION) {
            TitleText = SettingField1.getText();
            ProgramVersion = SettingField2.getText();
            Licensee = SettingField3.getText();
            serializeSettings(new Settings(TitleText, ProgramVersion, Licensee));

            NewName();

        }

    }//GEN-LAST:event_SettingsMenuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TierparkManagerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TierparkManagerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TierparkManagerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TierparkManagerGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                Settings mySettings = deserializeSettings("settings.ser");
                TitleText = mySettings.getTitleText();
                ProgramVersion = mySettings.getProgramVersion();
                Licensee = mySettings.getLicensee();
                Foods = deserializeFoods("food2.ser");
                if (getFoods().isEmpty()) {
                    addFood(new Food("Meat", 40, "Chicken"));
                    addFood(new Food("Plant", 50, "Corn"));
                }
                Animals = deserializeAnimals("animals2.ser");
                if (getAnimals().isEmpty()) {
                    addAnimal(new Animal("Tina", 5, false, true, "Female", "Tiger"));
                    addAnimal(new Animal("Olli", 13, true, false, "Male", "Lion"));
                    addAnimal(new Animal("Momo", 4, true, true, "Male", "Giraffe"));
                    addAnimal(new Animal("Lana", 1, true, true, "Female", "Monkey"));
                }

                new TierparkManagerGUI().setVisible(true);

            }
        });

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AnimalLabel;
    private javax.swing.JPanel AnimalPanel;
    private javax.swing.JTable AnimalTable;
    private javax.swing.JMenu BasicDataMenu;
    private javax.swing.JButton CreateNewAnimalButton;
    private javax.swing.JButton CreateNewFoodButton1;
    private javax.swing.JButton DeleteAnimalButton;
    private javax.swing.JButton DeleteFoodButton1;
    private javax.swing.JLabel FoodLabel;
    private javax.swing.JPanel FoodPanel;
    private javax.swing.JTable FoodTable;
    private javax.swing.JMenuItem HelpMenuItem;
    private javax.swing.JMenuItem ManageAnimals;
    private javax.swing.JMenuItem ManageFood;
    private javax.swing.JMenuItem ManageStaff;
    private javax.swing.JMenuItem SettingsMenu;
    private javax.swing.JMenuItem StartingPage;
    private javax.swing.JLabel WelcomeLabel;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables

}

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PizzaGUIFrame extends JFrame
{
    private JRadioButton thinCrust;
    private JRadioButton regularCrust;
    private JRadioButton deepDishCrust;

    private JComboBox<String> sizeComboBox;

    private JCheckBox pepperoni;
    private JCheckBox sausage;
    private JCheckBox mushrooms;
    private JCheckBox onions;
    private JCheckBox greenPeppers;
    private JCheckBox extraCheese;

    private JTextArea receiptArea;

    private JButton orderButton;
    private JButton clearButton;
    private JButton quitButton;

    public PizzaGUIFrame()
    {
        setTitle("Pizza Order Form");
        setSize(750, 650);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();

        addWindowListener(new java.awt.event.WindowAdapter()
        {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e)
            {
                quitProgram();
            }
        });
    }

    private void createGUI()
    {
        setLayout(new BorderLayout(10, 10));

        JPanel optionsPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        optionsPanel.add(createCrustPanel());
        optionsPanel.add(createSizePanel());
        optionsPanel.add(createToppingsPanel());

        add(optionsPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        bottomPanel.add(createReceiptPanel(), BorderLayout.CENTER);
        bottomPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.CENTER);
    }

    private JPanel createCrustPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new TitledBorder("Crust"));

        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDishCrust = new JRadioButton("Deep-dish");

        ButtonGroup crustGroup = new ButtonGroup();

        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDishCrust);

        panel.add(thinCrust);
        panel.add(regularCrust);
        panel.add(deepDishCrust);

        return panel;
    }

    private JPanel createSizePanel()
    {
        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("Size"));

        String[] sizes =
                {
                        "Small",
                        "Medium",
                        "Large",
                        "Super"
                };

        sizeComboBox = new JComboBox<>(sizes);

        panel.add(sizeComboBox);

        return panel;
    }

    private JPanel createToppingsPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new TitledBorder("Toppings - $1.00 Each"));

        pepperoni = new JCheckBox("Pepperoni");
        sausage = new JCheckBox("Sausage");
        mushrooms = new JCheckBox("Mushrooms");
        onions = new JCheckBox("Onions");
        greenPeppers = new JCheckBox("Green Peppers");
        extraCheese = new JCheckBox("Extra Cheese");

        panel.add(pepperoni);
        panel.add(sausage);
        panel.add(mushrooms);
        panel.add(onions);
        panel.add(greenPeppers);
        panel.add(extraCheese);

        return panel;
    }

    private JPanel createReceiptPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Receipt"));

        receiptArea = new JTextArea(15, 50);
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(receiptArea);

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createButtonPanel()
    {
        JPanel panel = new JPanel();

        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");

        panel.add(orderButton);
        panel.add(clearButton);
        panel.add(quitButton);

        orderButton.addActionListener(e -> processOrder());
        clearButton.addActionListener(e -> clearForm());
        quitButton.addActionListener(e -> quitProgram());

        return panel;
    }

    private void processOrder()
    {
        String crust;

        if (thinCrust.isSelected())
        {
            crust = "Thin";
        }
        else if (regularCrust.isSelected())
        {
            crust = "Regular";
        }
        else if (deepDishCrust.isSelected())
        {
            crust = "Deep-dish";
        }
        else
        {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a crust.",
                    "Missing Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String size = (String) sizeComboBox.getSelectedItem();

        double basePrice;

        switch (size)
        {
            case "Small":
                basePrice = 8.00;
                break;

            case "Medium":
                basePrice = 12.00;
                break;

            case "Large":
                basePrice = 16.00;
                break;

            case "Super":
                basePrice = 20.00;
                break;

            default:
                basePrice = 0.00;
        }

        int toppingCount = 0;

        if (pepperoni.isSelected())
            toppingCount++;

        if (sausage.isSelected())
            toppingCount++;

        if (mushrooms.isSelected())
            toppingCount++;

        if (onions.isSelected())
            toppingCount++;

        if (greenPeppers.isSelected())
            toppingCount++;

        if (extraCheese.isSelected())
            toppingCount++;

        if (toppingCount == 0)
        {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select at least one topping.",
                    "Missing Selection",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        double toppingCost = toppingCount * 1.00;
        double subtotal = basePrice + toppingCost;
        double tax = subtotal * 0.07;
        double total = subtotal + tax;

        StringBuilder receipt = new StringBuilder();

        receipt.append("=========================================\n");

        receipt.append(String.format(
                "%-28s %10s%n",
                "Type of Crust & Size",
                "Price"
        ));

        receipt.append("-----------------------------------------\n");

        receipt.append(String.format(
                "%-28s $%9.2f%n",
                crust + " " + size,
                basePrice
        ));

        receipt.append("\n");

        receipt.append(String.format(
                "%-28s %10s%n",
                "Ingredient",
                "Price"
        ));

        receipt.append("-----------------------------------------\n");

        if (pepperoni.isSelected())
        {
            receipt.append(String.format(
                    "%-28s $%9.2f%n", "Pepperoni", 1.00));
        }

        if (sausage.isSelected())
        {
            receipt.append(String.format(
                    "%-28s $%9.2f%n", "Sausage", 1.00));
        }

        if (mushrooms.isSelected())
        {
            receipt.append(String.format(
                    "%-28s $%9.2f%n", "Mushrooms", 1.00));
        }

        if (onions.isSelected())
        {
            receipt.append(String.format(
                    "%-28s $%9.2f%n", "Onions", 1.00));
        }

        if (greenPeppers.isSelected())
        {
            receipt.append(String.format(
                    "%-28s $%9.2f%n", "Green Peppers", 1.00));
        }

        if (extraCheese.isSelected())
        {
            receipt.append(String.format(
                    "%-28s $%9.2f%n", "Extra Cheese", 1.00));
        }

        receipt.append("\n");

        receipt.append(String.format(
                "%-28s $%9.2f%n", "Sub-total:", subtotal));

        receipt.append(String.format(
                "%-28s $%9.2f%n", "Tax:", tax));

        receipt.append("-----------------------------------------\n");

        receipt.append(String.format(
                "%-28s $%9.2f%n", "Total:", total));

        receipt.append("=========================================\n");

        receiptArea.setText(receipt.toString());
    }

    private void clearForm()
    {
        thinCrust.setSelected(false);
        regularCrust.setSelected(false);
        deepDishCrust.setSelected(false);

        sizeComboBox.setSelectedIndex(0);

        pepperoni.setSelected(false);
        sausage.setSelected(false);
        mushrooms.setSelected(false);
        onions.setSelected(false);
        greenPeppers.setSelected(false);
        extraCheese.setSelected(false);

        receiptArea.setText("");
    }

    private void quitProgram()
    {
        int response = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to quit?",
                "Confirm Quit",
                JOptionPane.YES_NO_OPTION
        );

        if (response == JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }
}
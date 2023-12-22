import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverterGUI extends JFrame {

    private JComboBox<String> baseCurrencyComboBox;
    private JComboBox<String> targetCurrencyComboBox;
    private JTextField amountField;
    private JLabel resultLabel;

    // Fixed exchange rates for demonstration purposes
    private Map<String, Double> exchangeRates;

    public CurrencyConverterGUI() {
        super("Currency Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        // Initialize exchange rates
        initializeExchangeRates();

        initializeUI();

        setVisible(true);
    }

    private void initializeExchangeRates() {
        exchangeRates = new HashMap<>();
        exchangeRates.put("USD", 1.0);  // Base currency is USD
        exchangeRates.put("EUR", 0.85);
        exchangeRates.put("GBP", 0.75);
        exchangeRates.put("JPY", 110.0);
        // Add more currencies here
        exchangeRates.put("AUD", 1.35);
        exchangeRates.put("CAD", 1.25);
        exchangeRates.put("INR", 73.5);
        // ... and so on
    }

    private void initializeUI() {
        setLayout(new GridLayout(0, 2, 10, 10));

        createCurrencySelection();
        createAmountInput();
        createConvertButton();
        createResultLabel();
    }

    private void createCurrencySelection() {
        JPanel currencyPanel = new JPanel();

        // Base currency selection
        String[] currencies = exchangeRates.keySet().toArray(new String[0]);
        baseCurrencyComboBox = new JComboBox<>(currencies);
        baseCurrencyComboBox.setSelectedIndex(0);

        // Target currency selection
        targetCurrencyComboBox = new JComboBox<>(currencies);
        targetCurrencyComboBox.setSelectedIndex(1);

        currencyPanel.add(new JLabel("Base Currency: "));
        currencyPanel.add(baseCurrencyComboBox);
        currencyPanel.add(new JLabel("Target Currency: "));
        currencyPanel.add(targetCurrencyComboBox);

        add(currencyPanel);
    }

    private void createAmountInput() {
        JPanel amountPanel = new JPanel();
        amountField = new JTextField(10);

        amountPanel.add(new JLabel("Amount to Convert: "));
        amountPanel.add(amountField);

        add(amountPanel);
    }

    private void createConvertButton() {
        JButton convertButton = new JButton("Convert");
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertCurrency();
            }
        });

        add(convertButton);
    }

    private void createResultLabel() {
        resultLabel = new JLabel("Converted Amount: ");
        add(resultLabel);
    }

    private void convertCurrency() {
        try {
            // Get selected currencies
            String baseCurrency = (String) baseCurrencyComboBox.getSelectedItem();
            String targetCurrency = (String) targetCurrencyComboBox.getSelectedItem();

            // Get amount to convert
            double amount = Double.parseDouble(amountField.getText());

            // Get exchange rate
            double exchangeRate = exchangeRates.get(targetCurrency) / exchangeRates.get(baseCurrency);

            // Perform conversion
            double convertedAmount = amount * exchangeRate;

            // Display result
            DecimalFormat df = new DecimalFormat("#.##");
            resultLabel.setText("Converted Amount: " + df.format(convertedAmount) + " " + targetCurrency);
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input. Please enter a valid number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CurrencyConverterGUI();
            }
        });
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

// Product class to store details about products
class Product {
    String name;
    double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

// ShoppingCart class to manage products in the cart
class ShoppingCart {
    List<Product> cartItems;

    public ShoppingCart() {
        cartItems = new ArrayList<>();
    }

    public void addProduct(Product product) {
        cartItems.add(product);
    }

    public void clearCart() {
        cartItems.clear();
    }

    public List<Product> getCartItems() {
        return cartItems;
    }

    public double calculateTotal() {
        double total = 0;
        for (Product product : cartItems) {
            total += product.getPrice();
        }
        return total;
    }
}

// ECommerce class to simulate the platform functionality
public class ECommercePlatform extends JFrame {
    private static final long serialVersionUID = 1L;

    private List<Product> productList;
    private ShoppingCart cart;
    private JTextArea cartArea;
    private JLabel totalLabel;
    private DefaultListModel<String> productListModel;

    public ECommercePlatform() {
        // Initialize the product list and cart
        productList = new ArrayList<>();
        productList.add(new Product("Laptop", 800.00));
        productList.add(new Product("Smartphone", 400.00));
        productList.add(new Product("Headphones", 50.00));
        cart = new ShoppingCart();

        // Set up the frame
        setTitle("E-Commerce Platform");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for product list
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BorderLayout());

        productListModel = new DefaultListModel<>();
        for (Product product : productList) {
            productListModel.addElement(product.getName() + " - $" + product.getPrice());
        }
        JList<String> productJList = new JList<>(productListModel);
        JScrollPane scrollPane = new JScrollPane(productJList);
        productPanel.add(new JLabel("Available Products:"), BorderLayout.NORTH);
        productPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel for cart and checkout
        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new BorderLayout());
        cartArea = new JTextArea(10, 30);
        cartArea.setEditable(true); // Make cart editable
        JScrollPane cartScrollPane = new JScrollPane(cartArea);
        cartPanel.add(new JLabel("Shopping Cart:"), BorderLayout.NORTH);
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);

        // Total price label and checkout button
        totalLabel = new JLabel("Total: $0.00");
        JButton checkoutButton = new JButton("Checkout");

        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double total = cart.calculateTotal();
                if (total > 0) {
                    int response = JOptionPane.showConfirmDialog(ECommercePlatform.this,
                            "Your total is: $" + total + "\nProceed with checkout?", "Checkout", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(ECommercePlatform.this, "Thank you for your purchase!");
                        cart.clearCart();
                        updateCart();
                    }
                } else {
                    JOptionPane.showMessageDialog(ECommercePlatform.this, "Your cart is empty. Add products first.");
                }
            }
        });

        JPanel checkoutPanel = new JPanel();
        checkoutPanel.setLayout(new FlowLayout());
        checkoutPanel.add(totalLabel);
        checkoutPanel.add(checkoutButton);

        // Panel for adding products to cart
        JPanel addToCartPanel = new JPanel();
        addToCartPanel.setLayout(new FlowLayout());
        JButton addButton = new JButton("Add to Cart");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = productJList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Product selectedProduct = productList.get(selectedIndex);
                    cart.addProduct(selectedProduct);
                    updateCart();
                } else {
                    JOptionPane.showMessageDialog(ECommercePlatform.this, "Please select a product to add.");
                }
            }
        });
        addToCartPanel.add(addButton);

        // Adding components to the frame
        add(productPanel, BorderLayout.NORTH);
        add(cartPanel, BorderLayout.CENTER);
        add(addToCartPanel, BorderLayout.SOUTH);
        add(checkoutPanel, BorderLayout.SOUTH);
    }

    private void updateCart() {
        // Update cart display
        List<Product> cartItems = cart.getCartItems();
        cartArea.setText("");
        if (cartItems.isEmpty()) {
            cartArea.append("Your cart is empty.");
        } else {
            for (Product product : cartItems) {
                cartArea.append(product.getName() + " - $" + product.getPrice() + "\n");
            }
        }
        totalLabel.setText("Total: $" + cart.calculateTotal());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ECommercePlatform platform = new ECommercePlatform();
                platform.setVisible(true);
            }
        });
    }
}

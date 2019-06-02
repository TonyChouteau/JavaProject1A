import javax.swing.*;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.border.Border;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class TFrame extends JFrame implements Definition
{
    //=============
    // VARIABLES
    //=============

    static int contractID = 0;
    static int vehicleID = 0;
    static int clientID = 0;

    // Main
    private TFrame frame;
    private TPanel mainPanel;

    // Menu & Menu Buttons
    private TMotionPanel pTop;
    private TPanel MenuLeft;
    private TPanel buttonLeftSpace;
    private TMenuButton[] buttons;

    // Frame buttons
    private TPanel MenuRight1;
    private TPanel MenuRight2;
    private TFrameButton reduceButton;
    private TFrameButton closeButton;

    // Content
    private TPanel contentPanel;
    private CardLayout cardLayout;
    private TPanel[] cards;

    // Contract Panel : Card[0]
    // List
    private TPanel contractListPanel;
    private TPanel contractListContent;
    private TPanel contractListTitlePanel;
    private TLabel contractListTitleLabel;
    private TPanel contractVerticalSeparator;
    private TContentButton contractNewContractButton;
    private TList contractList;
    private TSearchBar contractSearchBar;
    private TScrollPane contractScrollPane;
    // Contract Area To Fill
    private TPanel contractAreaToFillPanel;
    private TTextField contractClientField;
    private TTextField contractVehicleField;
    private TConfirmButton contractConfirmButton;
    private TConfirmButton contractCancelButton;

    // Contract Panel : Card[1]
    // List
    private TPanel vehicleListPanel;
    private TPanel vehicleListContent;
    private TPanel vehicleListTitlePanel;
    private TLabel vehicleListTitleLabel;
    private TPanel vehicleVerticalSeparator;
    private TContentButton vehicleNewvehicleButton;
    private TList vehicleList;
    private TSearchBar vehicleSearchBar;
    private TScrollPane vehicleScrollPane;
    // vehicle Area To Fill
    private TPanel vehicleAreaToFillPanel;
    private TTextField vehicleBrandField;
    private TTextField vehicleModelField;
    private TTextField vehicleDailyPriceField;
    private TTextField vehicleMaxSpeedField;
    private TTextField vehicleStateField;
    private TTextField vehicleOdometerField;
    private TTextField vehiclePowerField;
    private TTextField vehicleNbSeatField;
    private TConfirmButton vehicleConfirmButton;
    private TConfirmButton vehicleCancelButton;

    // Client Panel : Card[2]
    // List
    private TPanel clientListPanel;
    private TPanel clientListContent;
    private TPanel clientListTitlePanel;
    private TLabel clientListTitleLabel;
    private TPanel clientVerticalSeparator;
    private TContentButton clientNewclientButton;
    private TList clientList;
    private TSearchBar clientSearchBar;
    private TScrollPane clientScrollPane;
    // client Area To Fill
    private TPanel clientAreaToFillPanel;
    private TTextField clientSurnameField;
    private TTextField clientNameField;
    private TTextField clientPhoneField;
    private TTextField clientMailField;
    private TTextField clientAdressField;
    private TConfirmButton clientConfirmButton;
    private TConfirmButton clientCancelButton;

    private ArrayList<Client> clients;
    private ArrayList<Vehicule> vehicles;
    private ArrayList<Contrat> contracts;

    //=============
    // CONSTRUCTOR
    //=============

    public TFrame()
    {
        super();

        initFrame();

        initFramePanel();

        initMenu();

        initArrayLists();
        initContent();

        this.setVisible(true);
        this.pack();
    }

    private void initFrame()
    {
        frame = this;

        this.setSize(1000,700);
        this.setTitle("JavaParkTest");
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println(new File("images/logo.png").getAbsolutePath());
        try {
            BufferedImage image = ImageIO.read(new File("images/logo.png"));
            frame.setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) (dim.getWidth()/2-this.getSize().width/2), (int) (dim.getHeight()/2-this.getSize().height/2));
        this.setBackground(new Color(0));
    }

    //=============
    // PRIVATE FUNCTIONS
    //=============

    private void initFramePanel()
    {
        mainPanel = new TPanel(1000,700, null, null, new BorderLayout(0,0), false);
        this.setContentPane(mainPanel);

        TPanel pRightBorder = new TPanel(5, 615, InterfaceMainColor, null, null, true);
        mainPanel.add(pRightBorder, BorderLayout.EAST);

        TPanel pLeftBorder = new TPanel(5, 615, InterfaceMainColor, null, null, true);
        mainPanel.add(pLeftBorder, BorderLayout.WEST);

        TPanel pBottomBorder = new TPanel(600, 5, InterfaceMainColor, null, null, true);
        mainPanel.add(pBottomBorder, BorderLayout.PAGE_END);
    }

    private void initMenu()
    {
        pTop = new TMotionPanel(this, 1000,80, InterfaceMainColor, null,
                new FlowLayout(FlowLayout.CENTER,0,0), true);
        mainPanel.add(pTop, BorderLayout.PAGE_START);

        MenuLeft = new TPanel(940, 80, null, null, new FlowLayout(FlowLayout.CENTER,0,0), false);
        pTop.add(MenuLeft);

        buttonLeftSpace = new TPanel(60, 0, null, null, null, false);
        MenuLeft.add(buttonLeftSpace);

        initMenuButtons();
        initFrameButtons();
    }

    private void initMenuButtons()
    {
        buttons = new TMenuButton[3];

        for (int i=0; i<3; i++)
        {
            buttons[i] = new TMenuButton(frame, buttonsName[i]);
        }

        for (int i=0; i<3; i++)
        {
            buttons[i].setOtherButtons(buttons[(i + 1) % 3], buttons[(i + 2) % 3]);
            MenuLeft.add(buttons[i]);
        }

        buttons[0].setIsPressed(true);
    }

    private void initFrameButtons()
    {
        MenuRight1 = new TPanel(30, 80, null, null, new BorderLayout(0,0),false);
        pTop.add(MenuRight1);

        reduceButton = new TFrameButton(frame, "Reduce");
        MenuRight1.add(reduceButton, BorderLayout.PAGE_START);

        MenuRight2 = new TPanel(30, 80, null, null, new BorderLayout(0,0),false);
        pTop.add(MenuRight2);

        closeButton = new TFrameButton(frame, "Close");
        MenuRight2.add(closeButton, BorderLayout.PAGE_START);
    }

    private void initContent()
    {
        cardLayout = new CardLayout();
        contentPanel = new TPanel(1000, 615, null, null, cardLayout, false);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        initCards();

        fillContractCard();
        fillVehiculeCard();
        fillClientCard();

        cardLayout.show(contentPanel, buttonsName[0]);
    }

    //=============
    // INIT CARDS
    //=============

    private void initCards()
    {
        cards = new TPanel[3];

        for (int i=0; i<3; i++)
        {
            cards[i] = new TPanel(-1, -1, new Color(255<<16>>(i*8)), null, null, true);
            cards[i].setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
            contentPanel.add(cards[i], buttonsName[i]);
        }
    }

    //=============
    // INIT CARD 0
    //=============

    private void fillContractCard()
    {
        contractListPanel = new TPanel(300, 615, null, null, new FlowLayout(FlowLayout.LEFT, 0, 0), false);
        cards[0].add(contractListPanel);

        initContractLists();

        contractAreaToFillPanel = new TPanel(690, 616, InterfaceLightColor, null, new FlowLayout(FlowLayout.CENTER, getWidth()/2, 30), true);
        cards[0].add(contractAreaToFillPanel);

        initContractAreasToFill();

    }

    private void initContractLists()
    {
        // Card0
        contractListContent = new TPanel(295, 615, null, null, new FlowLayout(FlowLayout.LEFT, 0, 0), false);
        contractListPanel.add(contractListContent);

        contractVerticalSeparator = new TPanel(5, 615, InterfaceMainColor, null, null, true);
        contractListPanel.add(contractVerticalSeparator);

        contractListTitlePanel = new TPanel(295, 30, InterfaceLightColor, WHITE, new FlowLayout(FlowLayout.CENTER), true);
        contractListContent.add(contractListTitlePanel);
        contractListTitleLabel = new TLabel("Historique des contrats", WHITE);
        contractListTitlePanel.add(contractListTitleLabel);


        contractList = new TList(frame, 0);
        contractScrollPane = new TScrollPane(this, 0, contractList, 295,525);

        contractSearchBar = new TSearchBar(frame, 0, "Rechercher un contrat", 295, 30, LIGHTGREY, DARKGREY);
        contractListContent.add(contractSearchBar);

        contractListContent.add(contractScrollPane);

        contractNewContractButton = new TContentButton(frame, "Nouveau contrat", 295, 30);
        contractListContent.add(contractNewContractButton);
    }

    private void initContractAreasToFill()
    {
        contractClientField = new TTextField(frame, "Client", 250, 30, WHITE, BLACK);
        contractAreaToFillPanel.add(contractClientField);

        contractVehicleField = new TTextField(frame, "Vehicle", 250, 30, WHITE, BLACK);
        contractAreaToFillPanel.add(contractVehicleField);

        contractConfirmButton = new TConfirmButton(frame, 0, "Confirmer", 250, 30, contractClientField, contractVehicleField);
        contractAreaToFillPanel.add(contractConfirmButton);
        contractCancelButton = new TConfirmButton(frame, 0, "Annuler", 250, 30);
        contractAreaToFillPanel.add(contractCancelButton);
    }

    //=============
    // INIT CARD 2
    //=============

    private void fillVehiculeCard()
    {
        vehicleListPanel = new TPanel(300, 615, null, null, new FlowLayout(FlowLayout.LEFT, 0, 0), false);
        cards[1].add(vehicleListPanel);

        initVehicleLists();

        vehicleAreaToFillPanel = new TPanel(690, 616, InterfaceLightColor, null, new FlowLayout(FlowLayout.CENTER, getWidth()/2, 30), true);
        cards[1].add(vehicleAreaToFillPanel);

        initVehicleAreasToFill();

        cardLayout.show(contentPanel, "Mes contrats");
    }

    private void initVehicleLists()
    {
        // Card0
        vehicleListContent = new TPanel(295, 615, null, null, new FlowLayout(FlowLayout.LEFT, 0, 0), false);
        vehicleListPanel.add(vehicleListContent);

        vehicleVerticalSeparator = new TPanel(5, 615, InterfaceMainColor, null, null, true);
        vehicleListPanel.add(vehicleVerticalSeparator);

        vehicleListTitlePanel = new TPanel(295, 30, InterfaceLightColor, WHITE, new FlowLayout(FlowLayout.CENTER), true);
        vehicleListContent.add(vehicleListTitlePanel);
        vehicleListTitleLabel = new TLabel("Historique des véhicules", WHITE);
        vehicleListTitlePanel.add(vehicleListTitleLabel);


        vehicleList = new TList(frame, 1);
        vehicleScrollPane = new TScrollPane(this, 1, vehicleList, 295,525);

        vehicleSearchBar = new TSearchBar(frame, 1, "Rechercher un véhicule", 295, 30, LIGHTGREY, DARKGREY);
        vehicleListContent.add(vehicleSearchBar);

        vehicleListContent.add(vehicleScrollPane);

        vehicleNewvehicleButton = new TContentButton(frame, "Nouveau véhicule", 295, 30);
        vehicleListContent.add(vehicleNewvehicleButton);

    }

    private void initVehicleAreasToFill()
    {
        /*vehicleSurnameLabel = new TLabel("Nom : ", WHITE);
        vehicleAreaToFillPanel.add(vehicleSurnameLabel);*/
        vehicleBrandField = new TTextField(frame, "Marque", 250, 30, WHITE, BLACK);
        vehicleAreaToFillPanel.add(vehicleBrandField);

        /*vehicleNameLabel = new TLabel("Prenom : ", WHITE);
        vehicleAreaToFillPanel.add(vehicleNameLabel);*/
        vehicleModelField = new TTextField(frame, "Modèle", 250, 30, WHITE, BLACK);
        vehicleAreaToFillPanel.add(vehicleModelField);

        vehicleDailyPriceField = new TTextField(frame, "Prix journalier", 250, 30, WHITE, BLACK);
        vehicleAreaToFillPanel.add(vehicleDailyPriceField);

        vehicleMaxSpeedField = new TTextField(frame, "Vitesse maximale", 250, 30, WHITE, BLACK);
        vehicleAreaToFillPanel.add(vehicleMaxSpeedField);

        vehicleStateField = new TTextField(frame, "Etat du véhicule", 250, 30, WHITE, BLACK);
        vehicleAreaToFillPanel.add(vehicleStateField);

        vehicleOdometerField = new TTextField(frame, "Distance déjà parcourue", 250, 30, WHITE, BLACK);
        vehicleAreaToFillPanel.add(vehicleOdometerField);

        vehiclePowerField = new TTextField(frame, "Puissance", 250, 30, WHITE, BLACK);
        vehicleAreaToFillPanel.add(vehiclePowerField);

        vehicleNbSeatField = new TTextField(frame, "Nombre de places", 250, 30, WHITE, BLACK);
        vehicleAreaToFillPanel.add(vehicleNbSeatField);

        vehicleConfirmButton = new TConfirmButton(frame, 1, "Confirmer", 250, 30, vehicleBrandField, vehicleModelField, vehicleDailyPriceField);
        vehicleAreaToFillPanel.add(vehicleConfirmButton);
        vehicleCancelButton = new TConfirmButton(frame, 1, "Annuler", 250, 30);
        vehicleAreaToFillPanel.add(vehicleCancelButton);
    }

    //=============
    // INIT CARD 3
    //=============

    private void fillClientCard()
    {
        clientListPanel = new TPanel(300, 615, null, null, new FlowLayout(FlowLayout.LEFT, 0, 0), false);
        cards[2].add(clientListPanel);

        initClientLists();

        clientAreaToFillPanel = new TPanel(690, 616, InterfaceLightColor, null, new FlowLayout(FlowLayout.CENTER, getWidth()/2, 30), true);
        cards[2].add(clientAreaToFillPanel);

        initClientAreasToFill();
    }

    private void initClientLists()
    {
        // Card0
        clientListContent = new TPanel(295, 615, null, null, new FlowLayout(FlowLayout.LEFT, 0, 0), false);
        clientListPanel.add(clientListContent);

        clientVerticalSeparator = new TPanel(5, 615, InterfaceMainColor, null, null, true);
        clientListPanel.add(clientVerticalSeparator);

        clientListTitlePanel = new TPanel(295, 30, InterfaceLightColor, WHITE, new FlowLayout(FlowLayout.CENTER), true);
        clientListContent.add(clientListTitlePanel);
        clientListTitleLabel = new TLabel("Liste des clients", WHITE);
        clientListTitlePanel.add(clientListTitleLabel);

        clientList = new TList(frame, 2);
        clientScrollPane = new TScrollPane(this, 2, clientList, 295,525);

        clientSearchBar = new TSearchBar(frame, 2, "Rechercher un client", 295, 30, LIGHTGREY, DARKGREY);
        clientListContent.add(clientSearchBar);

        clientListContent.add(clientScrollPane);

        clientNewclientButton = new TContentButton(frame, "Nouveau client", 295, 30);
        clientListContent.add(clientNewclientButton);
    }

    private void initClientAreasToFill()
    {
        /*clientSurnameLabel = new TLabel("Nom : ", WHITE);
        clientAreaToFillPanel.add(clientSurnameLabel);*/
        clientSurnameField = new TTextField(frame, "Nom", 250, 30, WHITE, BLACK);
        clientAreaToFillPanel.add(clientSurnameField);

        /*clientNameLabel = new TLabel("Prenom : ", WHITE);
        clientAreaToFillPanel.add(clientNameLabel);*/
        clientNameField = new TTextField(frame, "Prenom", 250, 30, WHITE, BLACK);
        clientAreaToFillPanel.add(clientNameField);

        clientPhoneField = new TTextField(frame, "Telephone", 250, 30, WHITE, BLACK);
        clientAreaToFillPanel.add(clientPhoneField);

        clientMailField = new TTextField(frame, "E-mail", 250, 30, WHITE, BLACK);
        clientAreaToFillPanel.add(clientMailField);

        clientAdressField = new TTextField(frame, "Adresse", 250, 30, WHITE, BLACK);
        clientAreaToFillPanel.add(clientAdressField);

        clientConfirmButton = new TConfirmButton(frame, 2, "Confirmer", 250, 30, clientSurnameField, clientNameField, clientPhoneField, clientMailField, clientAdressField);
        clientAreaToFillPanel.add(clientConfirmButton);
        clientCancelButton = new TConfirmButton(frame, 2, "Annuler", 250, 30);
        clientAreaToFillPanel.add(clientCancelButton);
    }

    private void initArrayLists()
    {
        clients = new ArrayList<Client>();
        vehicles = new ArrayList<Vehicule>();
        contracts = new ArrayList<Contrat>();
    }

    //=============
    // GETTERS
    //=============

    public TFrame getFrame() {
        return frame;
    }
    public TPanel getContentPanel() {
        return contentPanel;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    // TLIST GETTERS

    public TList getContractTList()
    {
        return contractList;
    }
    public TList getVehicleTList()
    {
        return vehicleList;
    }
    public TList getClientTList()
    {
        return clientList;
    }

    // SCROLLPAN GETTERS

    public TScrollPane getContractScrollPane()
    {
        return contractScrollPane;
    }

    public TScrollPane getVehicleScrollPane()
    {
        return vehicleScrollPane;
    }

    public TScrollPane getClientScrollPane()
    {
        return clientScrollPane;
    }

    // ARRAYLIST GETTERS

    public ArrayList getContractArrayList()
    {
        return contracts;
    }

    public ArrayList getVehicleArrayList()
    {
        return vehicles;
    }

    public ArrayList getClientArrayList()
    {
        return clients;
    }

    // TEXTFIELD ADD NEW CLIENT

    public TTextField getClientSurnameField() {
        return clientSurnameField;
    }

    public TTextField getClientNameField() {
        return clientNameField;
    }
}
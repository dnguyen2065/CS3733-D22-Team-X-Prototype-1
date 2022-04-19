package edu.wpi.cs3733.D22.teamX;

import edu.wpi.cs3733.D22.teamX.entity.*;
import edu.wpi.cs3733.D22.teamX.exceptions.loadSaveFromCSVException;
import java.sql.*;

public class DatabaseCreator {
  // Create impls in order to avoid FK errors
  private static LocationDAO locDAO;
  private static EmployeeDAO emplDAO;
  private static EquipmentTypeDAO eqtDAO;
  private static MedicalEquipmentServiceRequestDAO mesrDAO;
  private static LabServiceRequestDAO labDAO;
  private static GiftDeliveryRequestDAO giftDAO;
  private static EquipmentUnitDAO equDAO;
  private static MealServiceRequestDAO pmsrDAO;
  private static LangServiceRequestDAO langDAO;
  private static MedicineDeliverServiceRequestDAO MDSDAO;
  private static JanitorServiceRequestDAO janitorDAO;
  private static LaundryServiceRequestDAO laundryDAO;

  /** Initializes the database with tables and establishes a connection */
  public static void initializeDB()
      throws
          loadSaveFromCSVException { // method must be called after app startup, so user can choose
    // server type
    System.out.println("-----Testing Apache Derby Embedded Connection-----");
    // checks whether the driver is working
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      Class.forName("org.apache.derby.jdbc.ClientDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Apache Derby Driver not found.");
      e.printStackTrace();
      System.exit(1);
    }
    System.out.println("Apache Derby driver registered!");
  }

  /** Saves the data from the database to the appropriate CSV files and closes the database. */
  public static boolean closeDB() throws loadSaveFromCSVException {
    try {
      ConnectionSingleton.getConnectionSingleton().getConnection().close();
      System.out.println("Connection closed successfully");
    } catch (Exception e) {
      System.out.println("No connection to close");
      return false;
    }
    return true;
  }

  /** Drops all database tables */
  public static void dropAllTables() {
    laundryDAO.dropTable();
    MDSDAO.dropTable();
    janitorDAO.dropTable();
    pmsrDAO.dropTable();
    giftDAO.dropTable();
    langDAO.dropTable();
    labDAO.dropTable();
    mesrDAO.dropTable();
    equDAO.dropTable();
    eqtDAO.dropTable();
    emplDAO.dropTable();
    locDAO.dropTable();
  }

  /** Creates all database tables */
  public static void createAllTables() {
    locDAO.createTable();
    emplDAO.createTable();
    eqtDAO.createTable();
    equDAO.createTable();
    mesrDAO.createTable();
    labDAO.createTable();
    langDAO.createTable();
    giftDAO.createTable();
    pmsrDAO.createTable();
    janitorDAO.createTable();
    MDSDAO.createTable();
    laundryDAO.createTable();
  }

  /**
   * Reads data from all resource csv files and loads them into the database tables and DAO
   * Implementations
   */
  public static boolean loadAllCSV() throws loadSaveFromCSVException {
    if (!locDAO.loadCSV()
        || !emplDAO.loadCSV()
        || !eqtDAO.loadCSV()
        || !equDAO.loadCSV()
        || !mesrDAO.loadCSV()
        || !labDAO.loadCSV()
        || !langDAO.loadCSV()
        || !giftDAO.loadCSV()
        || !pmsrDAO.loadCSV()
        || !janitorDAO.loadCSV()
        || !MDSDAO.loadCSV()
        || !laundryDAO.loadCSV()) {
      throw new loadSaveFromCSVException("Error when writing to CSV file.");
    }
    return true;
  }

  /**
   * Saves all CSV files to the specified directory
   *
   * @param dirPath path to folder on local machine
   * @return true if all CSV's are successfully saved
   * @throws loadSaveFromCSVException if save does not execute
   */
  public static boolean saveAllCSV(String dirPath) throws loadSaveFromCSVException {
    if (!locDAO.saveCSV(dirPath)
        || !emplDAO.saveCSV(dirPath)
        || !eqtDAO.saveCSV(dirPath)
        || !equDAO.saveCSV(dirPath)
        || !mesrDAO.saveCSV(dirPath)
        || !labDAO.saveCSV(dirPath)
        || !langDAO.saveCSV(dirPath)
        || !giftDAO.saveCSV(dirPath)
        || !pmsrDAO.saveCSV(dirPath)
        || !janitorDAO.saveCSV(dirPath)
        || !MDSDAO.saveCSV(dirPath)
        || !laundryDAO.saveCSV(dirPath)) {
      throw new loadSaveFromCSVException("Error when writing to CSV file.");
    }
    return true;
  }

  /** Set all DAO variables declared in DatabaseCreator */
  public static void setAllDAOVars() {
    locDAO = LocationDAO.getDAO();
    emplDAO = EmployeeDAO.getDAO();
    eqtDAO = EquipmentTypeDAO.getDAO();
    mesrDAO = MedicalEquipmentServiceRequestDAO.getDAO();
    labDAO = LabServiceRequestDAO.getDAO();
    giftDAO = GiftDeliveryRequestDAO.getDAO();
    equDAO = EquipmentUnitDAO.getDAO();
    pmsrDAO = MealServiceRequestDAO.getDAO();
    langDAO = LangServiceRequestDAO.getDAO();
    MDSDAO = MedicineDeliverServiceRequestDAO.getDAO();
    janitorDAO = JanitorServiceRequestDAO.getDAO();
    laundryDAO = LaundryServiceRequestDAO.getDAO();
  }
}

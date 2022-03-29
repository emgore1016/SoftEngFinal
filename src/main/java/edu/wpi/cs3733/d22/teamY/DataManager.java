package edu.wpi.cs3733.d22.teamY;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/** This class manages the location data so that data is mirrored in memory and in the database. */
public class DataManager {
  private static final HashMap<String, HashMap<String, DBObject>> data =
      new HashMap<>(); // all data stored in the database
  private static Connection dbConnection; // database connection

  /**
   * Initializes the data manager.
   *
   * @param connection the database connection
   * @param tables the table names to initialize
   */
  public static void init(Connection connection, String... tables) {
    dbConnection = connection;
    for (String table : tables) {
      data.put(table, new HashMap<>());
    }
  }

  private static HashMap<String, DBObject> getTable(String tableName) {
    HashMap<String, DBObject> list = data.get(tableName);
    if (list == null) {
      System.out.println("No table '" + tableName + "' found");
      return null;
    }
    return list;
  }
  /**
   * Adds a location to the list of locations
   *
   * @param object the DBObject to add to the Database
   * @return true if successful, false otherwise
   */
  public static boolean add(DBObject object) {
    HashMap<String, DBObject> table = getTable(object.tableName);
    if (table == null) {
      return false;
    }
    // table has been found
    // now try adding the object to the database
    try {
      Statement stmt = dbConnection.createStatement();
      stmt.executeUpdate("INSERT INTO " + object.tableName + " " + object.getInsertQuery());
    } catch (SQLException e) {
      System.out.println("Adding Location " + object.getKey() + " Failed, check console");
      e.printStackTrace();
      return false;
    }
    // at this point, the table has been found and the object has been added to the database
    // can safely add to the hashmap
    table.put(object.getKey(), object);

    return true;
  }

  /**
   * Removes a location from the list of locations
   *
   * @param key the key attribute of the DBObject to remove
   * @return true if successful, false otherwise
   */
  public static boolean remove(String tableName, String key) {
    HashMap<String, DBObject> table = getTable(tableName);
    if (table == null) {
      return false;
    }
    // table has been found
    DBObject toRemove = table.get(key);
    if (toRemove == null) {
      System.out.println("Object of key: '" + key + "' was not found in table '" + tableName + "'");
      return false;
    }

    String sql_string = toRemove.getRemoveQuery();

    try {
      Statement stmt = dbConnection.createStatement();
      stmt.executeUpdate(sql_string);
    } catch (SQLException e) {
      System.out.println("Removing Location Failed, check console");
      e.printStackTrace();
      return false;
    }

    table.remove(
        key); // this should never fail because we already got the 'toRemove' object in the hashmap

    return true;
  }

  /** Updates the local copy of the location list from the database */
  public static void updateLocationsFromDB() {
    // erases the current maps
    for (String table : data.keySet()) {
      data.get(table).clear();
    }
    // TODO pull stuff from db and update hashmap
  }

  /**
   * Adds a list of DBObjects to the database
   *
   * @param objects the list of DBObjects to add
   * @return true if successful with entire list, false otherwise
   */
  public static boolean addObjects(DBObject... objects) {
    boolean ok = true;
    for (DBObject obj : objects) {
      if (!add(obj)) // uses the addLocation method to avoid redundancy
      {
        ok = false;
      }
    }

    return ok;
  }

  /**
   * Adds a list of locations to the current list of locations
   *
   * @param objects the arraylist of DBObjects to add
   * @return true if successful with entire list, false otherwise
   */
  public static <T extends DBObject> boolean addObjects(ArrayList<T> objects) {
    boolean ok = true;
    for (DBObject obj : objects) {
      if (!add(obj)) // uses the addLocation method to avoid redundancy
      {
        ok = false;
      }
    }

    return ok;
  }

  /**
   * Gets a location from the list of locations
   *
   * @param key the key attribute of the DBObject to get
   * @return the DBObject object with the given key or null if no such location exists
   */
  private static DBObject getDBObject(String tableName, String key) {
    HashMap<String, DBObject> table = getTable(tableName);
    if (table == null) {
      return null;
    }
    return table.get(key);
  }

  /**
   * Returns a copy of a location from the list of locations
   *
   * @param tableName the tableName attribute of the DBObject to get
   * @param key the key attribute of the DBObject to get
   * @return a copy of the DB object with the given key or null if no such location exists
   */
  @SuppressWarnings("unchecked")
  public static <T extends DBObject> T get(String tableName, String key) {
    DBObject object = getDBObject(tableName, key);
    if (object != null) {
      return (T) object.getClone();
    }
    return null;
  }

  /**
   * Replaces an object with a new one based on matching keys
   *
   * @param newObject the object to replace the old one with
   * @return true if successful, false otherwise
   */
  public static boolean replace(DBObject newObject) {
    if (newObject == null) {
      return false;
    }
    DBObject oldObject = getDBObject(newObject.tableName, newObject.getKey());
    if (oldObject == null) {
      return false;
    }
    // object has been found
    if (!remove(newObject.tableName, newObject.getKey())) {
      return false;
    }
    // remove was successful
    if (!add(newObject)) {
      return false;
    }

    return true;
  }

  /**
   * Returns a deep copy list from a table in the database
   *
   * @return return an arraylist of elements in the table cast to the type of the table
   */
  @SuppressWarnings("unchecked")
  public static <T extends DBObject> ArrayList<T> getAll(String tableName) {
    ArrayList<T> locationsCopy = new ArrayList<>();
    HashMap<String, DBObject> table = getTable(tableName);
    if (table == null) {
      return null;
    }
    // here we have a valid table
    for (DBObject obj : table.values()) {
      locationsCopy.add(
          (T) obj.getClone()); // casts the object to an extension DBObject which can be implicitly
      // cast
    }
    // return a list of elements in the table cast to the type of the table

    return locationsCopy;
  }

  /**
   * Clears all data from Connected DB + inputted tableName
   *
   * @param tableName String of table name to clear
   */
  public static void cleanTable(String tableName) {
    String sql_string = "DELETE FROM " + tableName;

    try {
      Statement stmt = dbConnection.createStatement();
      stmt.executeUpdate(sql_string);
    } catch (SQLException e) {
      System.out.println("Clearing table failed, check console");
      e.printStackTrace();
    }
  }

  public static void cleanAll() {
    for (String table : data.keySet()) {
      cleanTable(table);
    }
  }
}

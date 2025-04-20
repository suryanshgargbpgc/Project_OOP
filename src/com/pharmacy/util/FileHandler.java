package com.pharmacy.util;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for file operations
 */
public class FileHandler {
    
    private static final Logger logger = Logger.getLogger(FileHandler.class.getName());
    
    /**
     * Save an object to a file using serialization
     * 
     * @param <T> Type of object to save
     * @param object The object to save
     * @param filePath The path to save the file to
     * @return true if saved successfully, false otherwise
     */
    public static <T extends Serializable> boolean saveObjectToFile(T object, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(object);
            logger.info("Object saved to file: " + filePath);
            return true;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving object to file: " + e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Load an object from a file using deserialization
     * 
     * @param <T> Type of object to load
     * @param filePath The path to load the file from
     * @return The loaded object, or null if loading failed
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T loadObjectFromFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            T object = (T) ois.readObject();
            logger.info("Object loaded from file: " + filePath);
            return object;
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error loading object from file: " + e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Save a list of objects to a file using serialization
     * 
     * @param <T> Type of objects in the list
     * @param objects The list of objects to save
     * @param filePath The path to save the file to
     * @return true if saved successfully, false otherwise
     */
    public static <T extends Serializable> boolean saveListToFile(List<T> objects, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(objects);
            logger.info("List saved to file: " + filePath);
            return true;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving list to file: " + e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Load a list of objects from a file using deserialization
     * 
     * @param <T> Type of objects in the list
     * @param filePath The path to load the file from
     * @return The loaded list, or an empty list if loading failed
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> List<T> loadListFromFile(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            List<T> objects = (List<T>) ois.readObject();
            logger.info("List loaded from file: " + filePath);
            return objects;
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error loading list from file: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }
    
    /**
     * Save text to a file
     * 
     * @param text The text to save
     * @param filePath The path to save the file to
     * @param append Whether to append to the file or overwrite it
     * @return true if saved successfully, false otherwise
     */
    public static boolean saveTextToFile(String text, String filePath, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append))) {
            writer.write(text);
            logger.info("Text saved to file: " + filePath);
            return true;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving text to file: " + e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Load text from a file
     * 
     * @param filePath The path to load the file from
     * @return The loaded text, or null if loading failed
     */
    public static String loadTextFromFile(String filePath) {
        try {
            String content = Files.readString(Paths.get(filePath));
            logger.info("Text loaded from file: " + filePath);
            return content;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading text from file: " + e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Save an image file to a destination path
     * 
     * @param sourceFile The source image file
     * @param destinationPath The destination path to save the image to
     * @return The path to the saved image, or null if saving failed
     */
    public static String saveImageFile(File sourceFile, String destinationPath) {
        try {
            // Create destination directory if it doesn't exist
            File destDir = new File(destinationPath).getParentFile();
            if (destDir != null && !destDir.exists()) {
                destDir.mkdirs();
            }
            
            // Copy the file
            Files.copy(
                sourceFile.toPath(),
                Paths.get(destinationPath),
                StandardCopyOption.REPLACE_EXISTING
            );
            
            logger.info("Image saved to: " + destinationPath);
            return destinationPath;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving image file: " + e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Delete a file
     * 
     * @param filePath The path of the file to delete
     * @return true if deleted successfully, false otherwise
     */
    public static boolean deleteFile(String filePath) {
        try {
            Files.delete(Paths.get(filePath));
            logger.info("File deleted: " + filePath);
            return true;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error deleting file: " + e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Create a directory if it doesn't exist
     * 
     * @param directoryPath The path of the directory to create
     * @return true if the directory exists or was created successfully, false otherwise
     */
    public static boolean createDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists()) {
            return true;
        }
        
        boolean created = directory.mkdirs();
        if (created) {
            logger.info("Directory created: " + directoryPath);
        } else {
            logger.warning("Failed to create directory: " + directoryPath);
        }
        
        return created;
    }
    
    /**
     * Check if a file exists
     * 
     * @param filePath The path of the file to check
     * @return true if the file exists, false otherwise
     */
    public static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }
} 
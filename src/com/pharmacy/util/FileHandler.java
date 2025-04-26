package com.pharmacy.util;

import java.io.*;
import java.nio.file.*;
import java.util.Arrays;
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
     * Save an array of objects to a file using serialization
     * 
     * @param <T> Type of objects in the array
     * @param objects The array of objects to save
     * @param filePath The path to save the file to
     * @return true if saved successfully, false otherwise
     */
    public static <T extends Serializable> boolean saveArrayToFile(T[] objects, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(objects);
            logger.info("Array saved to file: " + filePath);
            return true;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving array to file: " + e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Load an array of objects from a file using deserialization
     * 
     * @param <T> Type of objects in the array
     * @param filePath The path to load the file from
     * @param componentType The class of the component type
     * @return The loaded array, or an empty array if loading failed
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T[] loadArrayFromFile(String filePath, Class<T> componentType) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            T[] objects = (T[]) ois.readObject();
            logger.info("Array loaded from file: " + filePath);
            return objects;
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error loading array from file: " + e.getMessage(), e);
            // Create empty array of the right type
            return (T[]) java.lang.reflect.Array.newInstance(componentType, 0);
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
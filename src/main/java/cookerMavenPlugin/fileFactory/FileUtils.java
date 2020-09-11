/*
 * Copyright (c) 2020.  Manjunath Prabhakar manjunath189@gmail.com
 */

package cookerMavenPlugin.fileFactory;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * This Class Contains several methods to perform Files Operations
 * Initilize this Class with the Constructor passing file path as parameter
 */

public class FileUtils {

    private File fileFolder = null;
    private final List<File> res = new ArrayList<File>();

    /**
     * Empty Constructor
     */
    public FileUtils() {
    }

    /**
     * Parameterized Constructor to initlize File Path
     * <h5> Author : Manjunath Prabhakar </h5>
     *
     * @param Folder Folder Name
     */
    public FileUtils(String Folder) {
        //File Path to perform Operations
        this.fileFolder = new File(Folder);
    }

    /**
     * This Method is used to read the file content and pass the content as return value
     * (This needs Empty Constructor to be used)
     * <h5> Author : Manjunath Prabhakar </h5>
     *
     * @param filePathToRead fullfilepath
     * @return string
     * @throws IOException error
     */
    public static String readAndGetFileContent(String filePathToRead) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePathToRead));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    /**
     * This Method is used to write the content and create a file
     * (This needs Empty Constructor to be used)
     * <h5> Author : Manjunath Prabhakar </h5>
     *
     * @param filecontent    Contents to be Saved in the File
     * @param filePathToSave Full Path including filename to save the file
     */
    public static void writeAndCreateFile(String filecontent, String filePathToSave) {
        try {
            File file = new File(filePathToSave);
            file.getParentFile().mkdirs();
            FileWriter fw = new FileWriter(filePathToSave);
            fw.write(filecontent);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to Delete a Folder and its Sub Folders and files in it
     * Here it is Used to Delete Generated Test Runners and Generated Feature Files Directory
     * <h5> Author : Manjunath Prabhakar </h5>
     *
     * @param file filepath
     */
    public static void deleteRunnerandFeatureDir(File file) {

        try {
            if (file.isDirectory()) {
                //directory is empty, then delete it
                if (file.list().length == 0) {
                    file.delete();
                    //System.out.println("Directory is deleted : " + file.getAbsolutePath());
                } else {
                    //list all the directory contents
                    String[] files = file.list();
                    for (String temp : files) {
                        //construct the file structure
                        File fileDelete = new File(file, temp);
                        //recursive delete
                        deleteRunnerandFeatureDir(fileDelete);
                    }
                    //check the directory again, if empty then delete it
                    if (file.list().length == 0) {
                        file.delete();
                        // System.out.println("Directory is deleted : " + file.getAbsolutePath());
                    }
                }
            } else if (file.isFile()) {
                //if file, then delete it
                file.delete();
                // System.out.println("File is deleted : " + file.getAbsolutePath());
            } else {
                // System.out.println("Doesnt Exist");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * This method is used to Create a Folder
     * Here it is Used to Create Generated Test Runners and Generated Feature Files Directory
     * <h5> Author : Manjunath Prabhakar </h5>
     *
     * @param file filepath
     */
    public static void createRunnerandFeatureDir(File file) {

        try {
            file.mkdirs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to read a yaml file
     * <h5> Author : Manjunath Prabhakar </h5>
     *
     * @param file yamlfilepath
     * @return yaml data in MAP
     */
    public static Map<String, Object> readYmlFile(File file) {
        Map<String, Object> configurations = new LinkedHashMap<>();
        try {
            InputStream configStream = new FileInputStream(file);
            Yaml yaml = new Yaml();
            configurations = yaml.load(configStream);
        } catch (Exception e) {
            System.out.println("Exception is :" + e.getLocalizedMessage());
        }
        return configurations;
    }

    /**
     * Get List of Files with certain extension (this needs parameterized constructor to be initilized)
     * <h5> Author : Manjunath Prabhakar </h5>
     *
     * @param extension File Extenstion
     * @return String
     * @throws Exception error
     */
    public List<File> getFiles(String extension) throws Exception {
        File[] listFilesFolders = fileFolder.listFiles();
        for (File file : listFilesFolders) {
            if (file.isDirectory()) {
                fileFolder = file;
                getFiles(extension);
            }
            if (file.isFile() && file.getName().toLowerCase().endsWith(extension)) {
                res.add(file);
            }
        }
        return res;
    }

}
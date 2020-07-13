package cookerMavenPlugin;

import cookerMavenPlugin.compiler.Compiler;
import cookerMavenPlugin.fileFactory.FileUtils;
import cookerMavenPlugin.fileGenFactory.GenMain;
import cookerMavenPlugin.kitchen.Ingredients;
import io.cucumber.gherkin.GherkinDocumentBuilder;
import io.cucumber.gherkin.Parser;
import io.cucumber.gherkin.TokenMatcher;
import io.cucumber.messages.IdGenerator;
import io.cucumber.messages.Messages.GherkinDocument;
import io.cucumber.messages.Messages.GherkinDocument.Builder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This Class is the Trigger Point to parse feature files and filter as per user tags
 * and create *.feature (Feature File)  & *.java (Test Runner File)
 * <p>
 * Usage: The Mojo Class get the parameters from the POM File and does below
 * 1. sets parameter data to Kitchen.Ingredients POJO Class.
 * 2. And Calls the Method in cookerMavenPlugin.CookerTrigger.
 * 2a. cookerMavenPlugin.CookerTrigger Methods gets the data from Kitchen.Ingredients POJO Class.
 * 2b. And Parses each *.feature file with user needed tags and appends to a List.
 * 2c. At the end Feature & Runner File are created by reading each entry in the List.
 *
 * <h5> Author : Manjunath Prabhakar (manjunath189@gmail.com) </h5>
 */
public class CookerTrigger {

    static IdGenerator idGenerator = new IdGenerator.Incrementing();
    static Parser<Builder> parser = new Parser<>(new GherkinDocumentBuilder(idGenerator));
    static TokenMatcher matcher = new TokenMatcher();
    static GherkinDocument gherkinDocument = null;
    static List<String> filesToGenerate = new ArrayList<>();

    static List<String> tagsToCreate = new ArrayList<>();

    public static void cookFiles() throws Exception {

        System.out.println("kjdsvnjksnvkjsvkjsbvkjskjvbskjvb");

        System.out.println("Tag " + Ingredients.getUserTag());

        tagsToCreate.add(Ingredients.getUserTag());

        //Initilize FileUtils Class with exiting feature files directory
        FileUtils featurecontent = new FileUtils(Ingredients.getfExiFullPath());
        //Get all *.feature files from existing feature files directory
        List<File> featurefiles = featurecontent.getFiles(".feature");

        //If there are feature files in the directory
        if (featurefiles.size() > 0) {

            //Loop through each feature file
            for (File featureFile : featurefiles) {

                //Get the Content of the Feature File to String
                String featureFileContent = FileUtils.readAndGetFileContent(featureFile.getPath());

                //Check if Feature File is Empty
                if (featureFileContent.equals("")) {
                    //Display the feature file is empty and go to next Feature File
                    System.err.println(featureFile.getName() + " is Empty!");
                    continue;
                }

                //Parse the feature file content and build it to get an Object of GherkinDocument
                gherkinDocument = parser.parse(featureFileContent/*,matcher*/).build();

                //Declare & Initilize Compiler Object with an Object of IDGenerator & Tags that user needs
                Compiler compiler = new Compiler(idGenerator, tagsToCreate);
                //get the List of files to create by calling Compile Method with GherkinDocument Object
                List<String> filesFromCurrentFile = compiler.compile(gherkinDocument);

                //Append the files to create another global list
                filesToGenerate.addAll(filesFromCurrentFile);

            }
        }
        //If no feature files in the directory
        else {
            System.out.println("No Feature Files in the directory");
        }

        //Loop through each file that we need to generate *.Feature file and Generate Feature & Runner File
        for (int i = 0; i < filesToGenerate.size(); i++) {

            System.out.println( filesToGenerate.get(i));

            GenMain.genFiles("featureUtils.getsFeatureName()", filesToGenerate.get(i));
        }


    }

    public static void main(String[] args) throws Exception {
       // cookFiles();
    }
}

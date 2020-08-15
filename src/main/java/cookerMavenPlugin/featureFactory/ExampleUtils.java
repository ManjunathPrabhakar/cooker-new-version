package cookerMavenPlugin.featureFactory;

import cookerMavenPlugin.fileFactory.ExcelReader;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Scenario.Examples;
import io.cucumber.messages.Messages.GherkinDocument.Feature.TableRow;
import io.cucumber.messages.Messages.GherkinDocument.Feature.TableRow.TableCell;
import io.cucumber.messages.Messages.GherkinDocument.Feature.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Manjunath-PC
 * @created 24/04/2020
 * @project cooker-new-version
 */
public class ExampleUtils {

    /*
    EXAMPLES WILL BE UNDER SCENARIO OUTLINE
    EXAMPLES CAN HAVE KEYWORD, NAME & DESCRIPTION
     */

    private Examples examples = null;
    private StringBuilder examplesResult = new StringBuilder();

    public ExampleUtils(Examples prExamples) {
        this.examples = prExamples;
    }

    public String getExamplesData() {

        try {
            //Examples Information
            String examplesKeyword = this.examples.getKeyword();
            String examplesName = this.examples.getName() == null ? "" : this.examples.getName();
            String examplesDescription = this.examples.getDescription() == null ? "" : this.examples.getDescription();
            List<Tag> examplesTagsList = this.examples.getTagsList();

            //Examples Tags
            for (Tag tag : examplesTagsList) {
                TagUtils tagUtils = new TagUtils(tag);
                String tagData = tagUtils.getTagData();
                this.examplesResult.append(tagData);
            }

            this.examplesResult.append(examplesKeyword).append(": ").append(examplesName);
            this.examplesResult.append(System.getProperty("line.separator"));
            this.examplesResult.append(examplesDescription);
            this.examplesResult.append(System.getProperty("line.separator"));

            //Check if Example Tags has @excel, if yes then extract path, filename, sheetName
            //And Mark NeedExcel as True and NeedExample as False so we can replace exmaples with excel data
            //Else
            List<String> path = new ArrayList<>();
            boolean needExcel = false;
            boolean needExamples = true;
            for (Tag exTag : examplesTagsList) {
                if (exTag.getName().contains("@excel")) {
                    try {
                        path.add(exTag.getName().split("=")[1]); //Path
                        path.add(exTag.getName().split("=")[2]); //filename
                        path.add(exTag.getName().split("=")[3]); //SheetName
                        needExcel = true;
                        needExamples = false;
                        break;
                    } catch (Exception e) {
                        //If error in extracting then log warning message and set res = false
//                        MojoLogger.getLogger().error("Issue in prasing Excel for Scenario Outline " + sSoName +
//                                "\nExcel Tag format must be @excel=folderpath=filename.fileextension=sheetname\n" +
//                                "if folderpath is root then project path is taken");
                        needExcel = false;
                        needExamples = true;
                        break;
                    }
                }
            }

            if (needExcel) {
                StringBuilder excelExamples = new StringBuilder();
                try {

                    //Prepare the path of excel file
                    String filePath = null;
                    if (path.get(0).equalsIgnoreCase("root")) {
                        filePath = System.getProperty("user.dir");
                    } else {
                        filePath = path.get(0);
                    }

                    //Call read file method of the class to read data
                    String z = ExcelReader.readExcel(filePath, path.get(1), path.get(2));
                    excelExamples.append(z);

                    this.examplesResult.append(excelExamples);
                } catch (Exception e) {
                    //If error in extracting then log warning message and set res = false
//                    MojoLogger.getLogger().error("Issue in prasing Excel for Scenario Outline " + sSoName +
//                            "\nExcel Tag format must be @excel=folderpath=filename.fileextension=sheetname\n" +
//                            "if folderpath is root then project path is taken");
                    needExamples = true;
                }
            }

            if (needExamples) {
                List<TableCell> headerCells = this.examples.getTableHeader().getCellsList();
                this.examplesResult.append("|");
                for (TableCell headerCell : headerCells) {
                    //exampleMap.put("<" + headerCell.getValue() + ">", new ArrayList<String>());
                    this.examplesResult.append(headerCell.getValue()).append("|");
                }
                //Object[] columnKeys = exampleMap.keySet().toArray();
                this.examplesResult.append(System.getProperty("line.separator"));

                List<TableRow> tableBody = this.examples.getTableBodyList();
                for (TableRow tableRow : tableBody) {
                    List<TableRow.TableCell> cells = tableRow.getCellsList();
                    this.examplesResult.append("|");
                    for (TableCell tableCell : cells) {
                        // String columnKey = (String) columnKeys[i];
                        // List<String> values = exampleMap.get(columnKey);
                        //values.add(cells.get(i).getValue());

                        String cell = tableCell.getValue();
                        this.examplesResult.append(cell).append("|");
                    }
                    this.examplesResult.append(System.getProperty("line.separator"));
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return (System.getProperty("line.separator") + String.valueOf(this.examplesResult));
    }

    public List<String> getExamplesTagsList() {
        List<String> res = new ArrayList<String>();

        try {
            for (Tag tag : this.examples.getTagsList()) {
                TagUtils tagUtils = new TagUtils(tag);
                res.add(tagUtils.getTagData().trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
}

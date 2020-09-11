/*
 * Copyright (c) 2020.  Manjunath Prabhakar manjunath189@gmail.com
 */

package cookerMavenPlugin.featureFactory;

import io.cucumber.messages.Messages.GherkinDocument.Feature.Step.DataTable;
import io.cucumber.messages.Messages.GherkinDocument.Feature.TableRow;
import io.cucumber.messages.Messages.GherkinDocument.Feature.TableRow.TableCell;

import java.util.List;

/**
 * @author Manjunath Prabhakar
 * @created 24/04/2020
 * @project cooker-new-version
 */
public class DataTableUtils {

       /*
       DATATABLE WILL BE UNDER STEP
     */


    private DataTable dataTable = null;
    private final StringBuilder dataTableResult = new StringBuilder();

    public DataTableUtils(DataTable prDataTable) {
        this.dataTable = prDataTable;
    }

    public String getDataTableData() {

        try {
            List<TableRow> listrows = this.dataTable.getRowsList();

            for (TableRow row : listrows) {
                List<TableCell> cells = row.getCellsList();
                dataTableResult.append("|");
                for (TableCell cell : cells) {
                    String sCellVal = cell.getValue();
                    dataTableResult.append(sCellVal).append("|");

                }
                dataTableResult.append(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(dataTableResult);

    }
}

/*
RETURN ===

| CELLVALUE |
\n
| CELLVALUE |

 */

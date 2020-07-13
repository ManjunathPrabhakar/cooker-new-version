package backYard;

import cookerMavenPlugin.featureFactory.BackgroundUtils;
import cookerMavenPlugin.featureFactory.FeatureUtils;
import io.cucumber.gherkin.Gherkin;
import io.cucumber.messages.IdGenerator;
import io.cucumber.messages.Messages;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

/**
 * @author Manjunath-PC
 * @created 24/04/2020
 * @project cooker-new-version
 */
public class TriggerCopyBKP {

    public static String userTags = "@need";
    private static IdGenerator idGenerator = new IdGenerator.Incrementing();

    public static void provides_access_to_the_ast() {
        List<String> paths = singletonList("testdata/backYard.test.feature");
        boolean includeSource = false;
        boolean includeAst = true;
        boolean includePickles = false;
        List<Object> envelopes = Gherkin.fromPaths(paths, includeSource, includeAst, includePickles, idGenerator).collect(Collectors.toList());

//        Parser<Messages.GherkinDocument.Builder> parser = new Parser<>(new GherkinDocumentBuilder(idGenerator));
//        TokenMatcher matcher = new TokenMatcher();
//        Messages.GherkinDocument gherkinDocument = parser.parse("Feature: Some name").build();

        // Get the AST
        Messages.GherkinDocument gherkinDocument = ((Messages.Envelope) envelopes.get(0)).getGherkinDocument();


        //TODO FEATURE LEVEL

        // Get the Feature node of the AST
        Messages.GherkinDocument.Feature feature = gherkinDocument.getFeature();

        FeatureUtils featureUtils = new FeatureUtils(feature);
        String featureData = featureUtils.getFeatureData();

        System.out.println("featureData = \n" + featureData);
        System.exit(0);

        //assertEquals("Minimal", feature.getNam
        System.out.println(feature.getName());

        List<Messages.GherkinDocument.Feature.Tag> tags = feature.getTagsList();
        for (Messages.GherkinDocument.Feature.Tag t : tags
        ) {
            System.out.println(t.getName());
        }


        //TODO GET FEATURE CHILDRENS

        //Get all the scenariodefinations from the feature files as list
        List<Messages.GherkinDocument.Feature.FeatureChild> featurescenarios = feature.getChildrenList();
        System.out.println(featurescenarios.size());

        for (Messages.GherkinDocument.Feature.FeatureChild featureChild : featurescenarios) {

            if (featureChild.hasBackground()) {
                Messages.GherkinDocument.Feature.Background bg = featureChild.getBackground();
                BackgroundUtils backgroundUtils = new BackgroundUtils(bg);
                String bgData = backgroundUtils.getBackgroundData();
            }

            if (featureChild.hasRule()) {
                Messages.GherkinDocument.Feature.FeatureChild.Rule r = featureChild.getRule();
                System.out.println(r.getName());
                System.out.println(r.getDescription());
                System.out.println(r.getChildrenList().size());
                List<Messages.GherkinDocument.Feature.FeatureChild.RuleChild> sc = r.getChildrenList();
                for (Messages.GherkinDocument.Feature.FeatureChild.RuleChild rr : sc) {
                    if (rr.hasBackground()) {
                        System.out.println("Rule has BG");
                    }
                    if (rr.hasScenario()) {
                        System.out.println("Rule had Scenario");
                    }
                }
            }

            if (featureChild.hasScenario()) {
                Messages.GherkinDocument.Feature.Scenario sc = featureChild.getScenario();
                System.out.println(sc.getKeyword() + sc.getName());
                List<Messages.GherkinDocument.Feature.Step> step = sc.getStepsList();
                for (Messages.GherkinDocument.Feature.Step s : step) {
                    System.out.println(s.getKeyword());
                    System.out.println(s.getText());
                    if (s.hasDataTable()) {
                        Messages.GherkinDocument.Feature.Step.DataTable dt = s.getDataTable();
                        List<Messages.GherkinDocument.Feature.TableRow> listrows = dt.getRowsList();

                        for (Messages.GherkinDocument.Feature.TableRow row : listrows) {
                            List<Messages.GherkinDocument.Feature.TableRow.TableCell> cells = row.getCellsList();
                            System.out.print("|");
                            for (Messages.GherkinDocument.Feature.TableRow.TableCell cell : cells) {
                                String sCellVal = cell.getValue();
                                System.out.print(sCellVal + "|");

                            }
                            System.out.print(System.getProperty("line.separator"));

                        }

                    }
                    if (s.hasDocString()) {
                        System.out.println("has DocString");
                        Messages.GherkinDocument.Feature.Step.DocString ds = s.getDocString();
                        System.out.println("Content " + ds.getContent());
                        System.out.println("MediaType " + ds.getMediaType());
                        System.out.println("Delimiter " + ds.getDelimiter());

                    }
                }

                List<Messages.GherkinDocument.Feature.Scenario.Examples> ex = sc.getExamplesList();
                for (Messages.GherkinDocument.Feature.Scenario.Examples e : ex) {
                    System.out.println("Get Keyword" + e.getKeyword());
                    System.out.println("Get Name" + e.getName());
                    System.out.println("Get Tags" + e.getTagsList());
                    List<Messages.GherkinDocument.Feature.TableRow.TableCell> headerCells = e.getTableHeader().getCellsList();
                    System.out.print("|");
                    for (Messages.GherkinDocument.Feature.TableRow.TableCell headerCell : headerCells) {
                        //exampleMap.put("<" + headerCell.getValue() + ">", new ArrayList<String>());
                        System.out.print((headerCell.getValue()) + "|");
                    }
                    //Object[] columnKeys = exampleMap.keySet().toArray();
                    System.out.print(System.getProperty("line.separator"));

                    List<Messages.GherkinDocument.Feature.TableRow> tableBody = e.getTableBodyList();
                    for (Messages.GherkinDocument.Feature.TableRow tableRow : tableBody) {
                        List<Messages.GherkinDocument.Feature.TableRow.TableCell> cells = tableRow.getCellsList();
                        System.out.print("|");
                        for (Messages.GherkinDocument.Feature.TableRow.TableCell tableCell : cells) {
                            // String columnKey = (String) columnKeys[i];
                            // List<String> values = exampleMap.get(columnKey);
                            //values.add(cells.get(i).getValue());

                            String cell = tableCell.getValue();
                            System.out.print(cell + "|");
                        }
                        System.out.print(System.getProperty("line.separator"));
                    }
                }


            }

            // Get the first Scenario node of the Feature node
//        GherkinDocument.Feature.Scenario scenario = feature.getChildren(0).getScenario();
//        assertEquals("minimalistic", scenario.getName());
//        System.out.println(scenario.getName());
            System.out.println("===========================================");
        }
    }

    public static void main(String[] args) {
        provides_access_to_the_ast();
    }

}

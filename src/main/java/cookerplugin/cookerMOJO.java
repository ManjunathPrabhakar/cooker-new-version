package cookerplugin;

import cookerMavenPlugin.CookerTrigger;
import cookerMavenPlugin.kitchen.Ingredients;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.Map;


@Mojo(name = "cook", threadSafe = true, defaultPhase = LifecyclePhase.INITIALIZE)
public class cookerMOJO extends AbstractMojo {

    @Parameter(property = "tag", required = true, defaultValue = "")
    private String tag;

    @Parameter(property = "templatePath", required = true, defaultValue = "")
    private String templatePath;

    @Parameter(property = "featuresPath", required = true, defaultValue = "")
    private String featuresPath;

    @Parameter(property = "stepDefPackage", required = true, defaultValue = "")
    private String stepDefPackage;

    @Parameter(property = "testRunnersGenDir", required = true, defaultValue = "")
    private String testRunnersGenDir;

    @Parameter(property = "featureFilesGenDir", required = true, defaultValue = "")
    private String featureFilesGenDir;

    @Parameter(property = "customPlaceHolder", defaultValue = "")
    private Map<String, String> customPlaceHolders;

    private Log LOGGER = getLog();

    /**
     * This Method is First Executed during INITILIZE (cook) LifeCycle of Maven, Its Thread Safe
     * <h5> Author : Manjunath Prabhakar (manjunath189@gmail.com) </h5>
     *
     * @throws MojoExecutionException If Any
     * @throws MojoFailureException   If Any
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        // The logic of our plugin will go here
        try {

            MojoLogger.setLogger(LOGGER);

            LOGGER.info("================ COOKER CUCUMBER MAVEN PLUGIN STARTED ==========================");
            LOGGER.info("========================== By Manjunath Prabhakar ==============================");
            LOGGER.info("========================= ++++ cooking started ++++ ============================");

            //Get Ingredients
            getAndMapParameters();

            //Show Ingredients
            showParameters();

            //Start Cooking
            LOGGER.info("======================== ++++ cooking started ++++ =============================");
//            CookerTrigger cookIt = new CookerTrigger();
//            cookIt.cookFiles();
            CookerTrigger.cookFiles();
            LOGGER.info("======================== ++++ cooking complete ++++ ============================");

        } catch (Exception e) {

            LOGGER.error("===================== ++++ oh no! cooking aborted ++++ =========================");
            e.printStackTrace();
            LOGGER.error("================================================================================");

        } finally {
            LOGGER.info("==================== COOKER CUCUMBER MAVEN PLUGIN ENDED ========================");
        }
    }

    /**
     * This Method Reads the parameters from POM File and stores it in the Kitchen.Ingredients(PoJo Class)
     * <h5> Author : Manjunath Prabhakar (manjunath189@gmail.com) </h5>
     */
    private void getAndMapParameters() {
        try {
            LOGGER.info("============================== Preparing Ingredients ===========================");
            Ingredients.setUserTag(this.tag);
            Ingredients.setTrFullTempPath(this.templatePath);
            Ingredients.setfExiFullPath(this.featuresPath);
            Ingredients.setStepDefPackage(this.stepDefPackage);
            Ingredients.setFgFullGenPath(this.featureFilesGenDir);
            Ingredients.setTrFullGenPath(this.testRunnersGenDir);
            Ingredients.setCustomPlaceHolders(this.customPlaceHolders);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This Method is used to display the Parameters mentioned in the POM during the Execution
     * <h5> Author : Manjunath Prabhakar (manjunath189@gmail.com) </h5>
     */
    private void showParameters() {
        try {
            LOGGER.info("============================ Selected Ingredients ==============================");
            LOGGER.info("== Selected Tags                     : " + Ingredients.getUserTag());
            LOGGER.info("== Test Runner Template              : " + Ingredients.getTrFullTempPath());
            LOGGER.info("== Feature Files Directory           : " + Ingredients.getfExiFullPath());
            LOGGER.info("== Step Definations Package          : " + Ingredients.getStepDefPackage());
            LOGGER.info("== Feature Files Generated Directory : " + Ingredients.getFgFullGenPath());
            LOGGER.info("== Test Runners Generated Directory  : " + Ingredients.getTrFullGenPath());
            LOGGER.info("== Custom Placeholders               : " + Ingredients.getCustomPlaceHolders());
            LOGGER.info("================================================================================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
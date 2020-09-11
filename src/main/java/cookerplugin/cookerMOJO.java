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

    @Parameter(property = "tags", required = true, defaultValue = "")
    private String tags;

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

    private Log MOJO_LOGGER = getLog();

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

            MojoLogger.setLogger(MOJO_LOGGER);

            MOJO_LOGGER.info("================ COOKER CUCUMBER MAVEN PLUGIN STARTED ==========================");
            MOJO_LOGGER.info("========================== By Manjunath Prabhakar ==============================");
            MOJO_LOGGER.info("========================= ++++ cooking started ++++ ============================");

            //Get Ingredients
            getAndMapParameters();

            //Show Ingredients
            showParameters();

            //Start Cooking
            MOJO_LOGGER.info("======================== ++++ cooking started ++++ =============================");
//            CookerTrigger cookIt = new CookerTrigger();
//            cookIt.cookFiles();
            CookerTrigger.cookFiles();
            MOJO_LOGGER.info("======================== ++++ cooking complete ++++ ============================");

        } catch (Exception e) {

            MOJO_LOGGER.error("===================== ++++ oh no! cooking aborted ++++ =========================");
            e.printStackTrace();
            MOJO_LOGGER.error("================================================================================");

        } finally {
            MOJO_LOGGER.info("==================== COOKER CUCUMBER MAVEN PLUGIN ENDED ========================");
        }
    }

    /**
     * This Method Reads the parameters from POM File and stores it in the Kitchen.Ingredients(PoJo Class)
     * <h5> Author : Manjunath Prabhakar (manjunath189@gmail.com) </h5>
     */
    private void getAndMapParameters() {
        try {
            MOJO_LOGGER.info("============================== Preparing Ingredients ===========================");
            Ingredients.setUserTag(this.tags);
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
            MOJO_LOGGER.info("============================ Selected Ingredients ==============================");
            MOJO_LOGGER.info("== Selected Tags                     : " + Ingredients.getUserTag());
            MOJO_LOGGER.info("== Test Runner Template              : " + Ingredients.getTrFullTempPath());
            MOJO_LOGGER.info("== Feature Files Directory           : " + Ingredients.getfExiFullPath());
            MOJO_LOGGER.info("== Step Definations Package          : " + Ingredients.getStepDefPackage());
            MOJO_LOGGER.info("== Feature Files Generated Directory : " + Ingredients.getFgFullGenPath());
            MOJO_LOGGER.info("== Test Runners Generated Directory  : " + Ingredients.getTrFullGenPath());
            MOJO_LOGGER.info("== Custom Placeholders               : " + Ingredients.getCustomPlaceHolders());
            MOJO_LOGGER.info("================================================================================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
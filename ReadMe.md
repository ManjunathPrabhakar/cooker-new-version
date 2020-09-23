## Cucumber Cooker Plugin

Refer Change Log to [here](CHANGE_LOG.md).

Developed in Supported Gherkin(io.cucumber.gherkin)
From v9.0.0

Developed in Supported Cucumber Java(io.cucumber.cucumber-java)
From v9.0.0

**CURRENTLY THIS PLUGIN IS NOT AVAILABLE IN MAVEN CENTRAL**
Please clone , and run `MVN INSTALL` to install in your machine

Example Project code : [https://github.com/ManjunathPrabhakar/cooker-new-version-example-project](https://github.com/ManjunathPrabhakar/cooker-new-version-example-project)

> Features : 
> 1. Now Supports Excel Data for Cucumber Feature Files
> 2. Usage with and without Maven
> 
> Also coming soon (Utils which doesnt alter the current functionality)
> 1. Email Feature to send reports
> 2. Custom Reporters using cucumber
> 3. Custom Reports which uses HTML, CSS, JS etc
> 4. Cucumber JSON Listener support

*THIS PLUGIN IS TESTED WITH VERSIONS MENTIONED IN THE CODE.
And Tested in TESTNG Framework. Its works with JUNIT as well.*


**FOR USAGE IN YOUR PROJECT (Refer Below Usage)**

### USAGE # 1 : FROM MAVEN

    <plugin>
                    <groupId>com.github.cooker-new-version</groupId>
                    <artifactId>cooker-new-version</artifactId>
                    <version>1.5.1-SNAPSHOT</version>
                    <configuration>
                        <!--suppress UnresolvedMavenProperty -->
                        <tags>@E2E</tags>
                        <stepDefPackage>stepDefs</stepDefPackage>
                        <templatePath>${project.basedir}\src\main\resources\templates\TestRunnerTemplate.template
                        </templatePath>
                        <featuresPath>${project.basedir}\src\test\features</featuresPath>
                        <featureFilesGenDir>${project.basedir}\src\test\resources\generated\featureFiles\
                        </featureFilesGenDir>
                        <testRunnersGenDir>${project.basedir}\src\test\java\generated\testRunners\</testRunnersGenDir>
                        <customPlaceHolders>
                            <progAuthor>Manjunath</progAuthor>
                        </customPlaceHolders>
                    </configuration>
                    <executions>
                        <execution>
                            <goals>
                                <goal>cook</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
            
            
            
### USAGE # 2 : WITHOUT MAVEN BUT WITH TEST NG

Create YAML File with properties for Plugin.

    ################## MANDATORY DATA START
    userTag: "@E2E2"
    templatePath: "\\src\\main\\resources\\templates\\TestRunnerTemplate.template"
    featuresPath: "\\src\\test\\features"
    stepDefsPackage: "stepDefs"
    generatedFeaturesPath: "\\src\\test\\resources\\generated\\featureFiles\\"
    generatedRunnerPath: "\\src\\test\\java\\generated\\testRunners\\"
    ################## MANDATORY DATA END
    customPlaceHolders:
      progAuthor: "Manjunath"

  

Add this Annotation in the Testing class where TestNG Hooks are Run

    @initCucumber(propertiesPath = "data.yml")

Add below static block to trigger the plugin in runtime

    static {
            try {
                Perfrom.now();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

Example : 

    @initCucumber(propertiesPath = "data.yml")  
    public class Testing {  
        static {  
            try {  
                Perfrom.now();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        @BeforeSuite  
      public void beforeSuite(){  
            System.out.println("Testing.beforeSuite");  
        }  
        @BeforeMethod  
      public void beforeMethod(){  
            System.out.println("Testing.beforeMethod");  
        }  
        @Test  
      public void test(){  
            System.out.println("Testing.test");  
        }  
        @AfterMethod  
      public void afterMethod(){  
            System.out.println("Testing.afterMethod");  
        }  
        @AfterSuite  
      public void afterSuite(){  
            System.out.println("Testing.afterSuite");  
        }  
    }

##**EXCEL DATA USAGE IN YOUR PROJECT (Refer Below Usage)**

Supports Excel XLSX & XLS format only

    @E2E2
    Scenario Outline: Test Scenario New One
      Given Some Given
      When Some When "<param>"
      Then Some Then
      And Some And
      
      @excel=root=book.xlsx=Sheet1
      Examples:
        | param |
        | data  |


the @excel tag is written above Examples. so that example will be replaced with data from Excel
FORMAT : @excel=<FOLDER_PATH>=<EXCELFILENAME>.<EXTENSION>=<SHEETNAME>
 example:
  1. `@excel=root=book.xlsx=Sheet1`
    - since FOLDER_PATH here is "root" -> project root directory is considered
    - book.xlsx - is the FIle name with its extension
    - Sheet1 - sheet name within the Excel sheet
  2. `@excel=C:\Users\Manjunath-PC\Documents=book.xlsx=Sheet1`
    - since FOLDER_PATH here is "C:\Users\Manjunath-PC\Documents" -> given path is considered
  
 Once the plugin is executed, the Example data is replaced with data from Excel
 
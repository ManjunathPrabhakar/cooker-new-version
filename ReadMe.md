## Cucumber Cooker Plugin

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

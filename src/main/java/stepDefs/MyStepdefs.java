package stepDefs;

import io.cucumber.datatable.DataTable;

/**
 * @author Manjunath-PC
 * @created 25/04/2020
 * @project cooker-new-version
 */
public class MyStepdefs {
    @io.cucumber.java.en.Given("^no rule$")
    public void noRule() {
        System.out.println("stepDefs.MyStepdefs.noRule");
    }

    @io.cucumber.java.en.Then("^no need rule$")
    public void noNeedRule() {
        System.out.println("stepDefs.MyStepdefs.noNeedRule");
    }

    @io.cucumber.java.en.Given("^Test$")
    public void test() {
        System.out.println("stepDefs.MyStepdefs.backYard.test");
    }

    @io.cucumber.java.en.Given("^some give$")
    public void someGive(String docString) {
        System.out.println("stepDefs.MyStepdefs.someGive - docstring - " + docString);
    }

    @io.cucumber.java.en.When("^some when \"([^\"]*)\"$")
    public void someWhen(String arg0) throws Throwable {
        System.out.println("stepDefs.MyStepdefs.someWhen - arg0 - " + arg0);
    }

    @io.cucumber.java.en.Given("^When bg step$")
    public void whenBgStep() {
        System.out.println("stepDefs.MyStepdefs.whenBgStep");
    }

    @io.cucumber.java.en.When("^Bg Step$")
    public void bgStep(DataTable dtb) {
        System.out.println("stepDefs.MyStepdefs.bgStep - dtb - " + dtb);
    }

    @io.cucumber.java.en.Then("^bg then$")
    public void bgThen() {
    }

    @io.cucumber.java.en.Given("^some fiven$")
    public void someFiven() {
        System.out.println("stepDefs.MyStepdefs.someFiven");
    }

    @io.cucumber.java.en.Then("^some then$")
    public void someThen(DataTable dtb) {
        System.out.println("stepDefs.MyStepdefs.someThen- dtb - " + dtb);
    }

    @io.cucumber.java.en.And("^some and$")
    public void someAnd() {
        System.out.println("stepDefs.MyStepdefs.someAnd");
    }

    @io.cucumber.java.en.But("^some but$")
    public void someBut() {
        System.out.println("stepDefs.MyStepdefs.someBut");
    }

    @io.cucumber.java.en.Given("^some data$")
    public void someData() {
        System.out.println("stepDefs.MyStepdefs.someData");
    }

    @io.cucumber.java.en.Given("^nn$")
    public void nn() {
        System.out.println("stepDefs.MyStepdefs.nn");
    }

    @io.cucumber.java.en.Then("^tt \"([^\"]*)\"$")
    public void tt(String arg0) throws Throwable {
        System.out.println("stepDefs.MyStepdefs.tt - args0 - " + arg0);
    }
}

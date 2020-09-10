package nl.jonathandegier.bank;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.test.context.jdbc.Sql;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features")
@Sql("accounts.sql")
public class CucumberRunner {
}

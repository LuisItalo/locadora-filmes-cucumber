package Runner;


import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/fetures/alugar_filme.feature",  //procura local das features
        glue = "Steps",                                       //procura local dos steps
        //tags = "~@ignore",                                  //escolhe ou ignora cenario a executar teste (@esse / ~@ignore)
        plugin = {"pretty"},// "html:target/report-html", "json:target/report.json"},               // relatorio
        monochrome = true,                      //retira as cores mvn test
        snippets = SnippetType.CAMELCASE,
        strict = false,
        dryRun = false
)

public class RunnerTest {
}

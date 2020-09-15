package nl.jonathandegier.bank.definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import nl.jonathandegier.bank.SpringIntegrationTest;
import nl.jonathandegier.bank.domain.Account;
import nl.jonathandegier.bank.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountStepDef extends SpringIntegrationTest {

    @Autowired
    private AccountRepository repository;

    @Given("^5 accounts in the database$")
    public void fill_database_with_accounts() {
        repository.save(new Account("12345"));
        repository.save(new Account("23456"));
        repository.save(new Account("34567"));
        repository.save(new Account("45678"));
        repository.save(new Account("56789"));
    }

    @When("^client makes a post on (.+) with iban (.+)$")
    public void client_makes_account(String uri, String iban) {
        post(uri, null);
    }

    @When("^client makes a get on (.+)$")
    public void client_makes_account(String uri) {
        get(uri);
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
        assertEquals(statusCode, currentStatusCode.value());
//        System.out.println("check status");
    }

    @And("^the client receives a bank account$")
    public void the_client_receives_a_bank_account() {

        System.out.println("Response body: " + latestResponse.getBody());
    }


}

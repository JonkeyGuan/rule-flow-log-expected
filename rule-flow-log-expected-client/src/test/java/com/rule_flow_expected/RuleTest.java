package com.rule_flow_expected;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.rule_flow_log_expected.Condition;
import com.example.rule_flow_log_expected.Fact;
import com.example.rule_flow_log_expected.Result;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.KieServiceResponse.ResponseType;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuleTest {

    static final Logger log = LoggerFactory.getLogger(RuleTest.class);

    private static final String droolsUrl = "http://jonkey.51vip.biz:18180/kie-server/services/rest/server";
    private static final String username = "admin";
    private static final String password = "admin";

    private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;

    private static KieServicesConfiguration kieServicesConfig;
    private static KieServicesClient kieServicesClient;

    @Before
    public void setup() {
        kieServicesConfig = KieServicesFactory.newRestConfiguration(droolsUrl, username, password);
        kieServicesConfig.setMarshallingFormat(FORMAT);

        Set<Class<?>> allClasses = new HashSet<Class<?>>();
        allClasses.add(Condition.class);
        allClasses.add(Fact.class);
        kieServicesConfig.addExtraClasses(allClasses);

        kieServicesClient = KieServicesFactory.newKieServicesClient(kieServicesConfig);
    }

    @After
    public void teardown() {

    }

    @Test
    public void test_yes_flow() {

        RuleServicesClient rulesClient = kieServicesClient.getServicesClient(RuleServicesClient.class);

        KieCommands commandFactory = KieServices.Factory.get().getCommands();

        List<Command<?>> commands = new ArrayList<>();

        Condition condition = new Condition("yes");
        commands.add(commandFactory.newInsert(condition));

        Fact fact = new Fact("field1", "field2", "field3", "field4");
        commands.add(commandFactory.newInsert(fact));

        Command<?> fireAllRules = commandFactory.newFireAllRules();
        Command<?> process = commandFactory.newStartProcess("RuleFlow.Flow");
        Command<?> getObjectsResult = commandFactory.newGetObjects(new ClassObjectFilter(Result.class),
                "Result");
        Command<?> getObjectsFact = commandFactory.newGetObjects(new ClassObjectFilter(Fact.class), "Fact");
        Command<?> dispose = commandFactory.newDispose();
        commands.addAll(Arrays.asList(fireAllRules, process, getObjectsResult, getObjectsFact, dispose));

        Command<?> batchCommand = commandFactory.newBatchExecution(commands);
        ServiceResponse<ExecutionResults> executeResponse = rulesClient.executeCommandsWithResults(
                "rule-flow-log-expected",
                batchCommand);

        if (executeResponse.getType() == ResponseType.SUCCESS) {
            @SuppressWarnings("unchecked")

            List<Result> results = (List<Result>) executeResponse.getResult().getValue("Result");
            for (Result r : results) {
                log.info("" + r);
            }

            @SuppressWarnings("unchecked")
            List<Fact> facts = (List<Fact>) executeResponse.getResult().getValue("Fact");
            for (Fact f : facts) {
                log.info("" + f);
            }
        }
    }

    @Test
    public void test_no_flow() {

        RuleServicesClient rulesClient = kieServicesClient.getServicesClient(RuleServicesClient.class);

        KieCommands commandFactory = KieServices.Factory.get().getCommands();

        List<Command<?>> commands = new ArrayList<>();

        Condition condition = new Condition("no");
        commands.add(commandFactory.newInsert(condition));

        Fact fact = new Fact("field1", "field2", "field3", "field4");
        commands.add(commandFactory.newInsert(fact));

        Command<?> fireAllRules = commandFactory.newFireAllRules();
        Command<?> process = commandFactory.newStartProcess("RuleFlow.Flow");
        Command<?> getObjectsResult = commandFactory.newGetObjects(new ClassObjectFilter(Result.class),
                "Result");
        Command<?> getObjectsFact = commandFactory.newGetObjects(new ClassObjectFilter(Fact.class), "Fact");
        Command<?> dispose = commandFactory.newDispose();
        commands.addAll(Arrays.asList(fireAllRules, process, getObjectsResult, getObjectsFact, dispose));

        Command<?> batchCommand = commandFactory.newBatchExecution(commands);
        ServiceResponse<ExecutionResults> executeResponse = rulesClient.executeCommandsWithResults(
                "rule-flow-log-expected",
                batchCommand);

        if (executeResponse.getType() == ResponseType.SUCCESS) {
            @SuppressWarnings("unchecked")

            List<Result> results = (List<Result>) executeResponse.getResult().getValue("Result");
            for (Result r : results) {
                log.info("" + r);
            }

            @SuppressWarnings("unchecked")
            List<Fact> facts = (List<Fact>) executeResponse.getResult().getValue("Fact");
            for (Fact f : facts) {
                log.info("" + f);
            }
        }
    }
}
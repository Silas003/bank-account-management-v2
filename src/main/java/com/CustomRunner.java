package com;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;


public class CustomRunner {

    private static void runTests(LauncherDiscoveryRequest request) {
        Launcher launcher = LauncherFactory.create();
        SummaryGeneratingListener summarylistener = new SummaryGeneratingListener();

        TestExecutionListener listener = new TestExecutionListener() {
            @Override
            public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult){
                if(testIdentifier.isTest()){
                    String testName = testIdentifier.getDisplayName();

                    System.out.printf("TEST: %s" ,testName);

                    if(testExecutionResult.getStatus() == TestExecutionResult.Status.SUCCESSFUL){
                        System.out.println("...\tPASSED");
                    }else{
                        System.out.println(("...\tFAILED"));
                        testExecutionResult.getThrowable().ifPresent(t -> System.out.println("  Error: " + t.getMessage()));
                    }

                }
            }
        };

        launcher.registerTestExecutionListeners(summarylistener, listener);
        launcher.execute(request);

        TestExecutionSummary summary = summarylistener.getSummary();

        System.out.println("\n========== Test Results ==========");
        System.out.println("Tests found: " + summary.getTestsFoundCount());
        System.out.println("Tests started: " + summary.getTestsStartedCount());
        System.out.println("Tests successful: " + summary.getTestsSucceededCount());
        System.out.println("Tests failed: " + summary.getTestsFailedCount());
        System.out.println("Tests skipped: " + summary.getTestsSkippedCount());
        System.out.println("==================================\n");


        if (!summary.getFailures().isEmpty()) {
            System.out.println("Failed tests:");
            summary.getFailures().forEach(failure -> {
                System.out.println("  - " + failure.getTestIdentifier().getDisplayName());
                System.out.println("    " + failure.getException());
            });
        }
    }
    public static void runAllTestsInPackage() {
        System.out.println("Searching for tests...\n");

        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectPackage("com"))
                .build();
        runTests(request);
    }


}
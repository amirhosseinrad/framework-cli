package ir.ipaam.ipaamcli;


import picocli.CommandLine;

@CommandLine.Command(name = "ipaam-cli", mixinStandardHelpOptions = true, version = "1.0",
        description = "Scaffolding CLI for DDD + CQRS/ES + Camunda microservices",
        subcommands = {
                GenerateServiceCommand.class,
                GenerateWizardCommand.class,
                GenerateEntityCommand.class
        })
public class IpaamCliApplication implements Runnable {

    @Override
    public void run() {
        System.out.println("Welcome to IPPAM CLI 🚀");
        System.out.println("Next step: ask user for service info...");
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new IpaamCliApplication()).execute(args);
        System.exit(exitCode);
    }
}

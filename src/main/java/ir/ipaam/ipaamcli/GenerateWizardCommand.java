package ir.ipaam.ipaamcli;

import picocli.CommandLine.Command;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;


@Command(name = "wizard", description = "Step-by-step wizard to generate a microservice")
public class GenerateWizardCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("📦 Service Generator Wizard 🚀");

        System.out.print("👉 Service name: ");
        String serviceName = scanner.nextLine();

        System.out.print("👉 Base package (e.g., ir.ipaam."+serviceName+"): ");
        String basePackage = scanner.nextLine().trim();

        String defaultBasePackage = "ir.ipaam." + serviceName.toLowerCase();
        basePackage = basePackage.isEmpty() ? defaultBasePackage : basePackage;
        String baseDir = "./generated/" + serviceName;
        System.out.print("👉 Enable CQRS/ES with Axon? (y/n): ");
        boolean cqrs = scanner.nextLine().trim().equalsIgnoreCase("y");

        System.out.print("👉 Add Camunda 8 workers? (y/n): ");
        boolean camunda = scanner.nextLine().trim().equalsIgnoreCase("y");

        System.out.print("👉 Projection database [postgres/oracle/h2]: ");
        String database = scanner.nextLine().trim();
        if (database.isEmpty()) {
            database = "postgres";
        }

        System.out.print("📦 Do you want to create entities now? (y/n): ");
        boolean createEntities = scanner.nextLine().trim().equalsIgnoreCase("y");
        boolean entitiesGenerated = false;

        while (createEntities) {
            System.out.print("📦 Entity name: ");
            String entityName = scanner.nextLine();

            // Ask fields
            List<Map<String, String>> fields = new ArrayList<>();
            boolean addMore = true;
            while (addMore) {
                System.out.print("📦 Add field? (y/n): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    addMore = false;
                    break;
                }
                System.out.print("📦 Field name: ");
                String fieldName = scanner.nextLine();

                System.out.print("📦 Field type [String,int,boolean,LocalDateTime,BigDecimal]: ");
                String fieldType = scanner.nextLine();

                System.out.print("📦 Validation (nullable, unique, length=, min=, max=): ");
                String validation = scanner.nextLine();

                fields.add(Map.of(
                        "name", fieldName,
                        "type", fieldType,
                        "validation", validation
                ));
            }

            Map<String, Object> model = Map.of(
                    "entityName", capitalize(entityName),
                    "aggregateName", capitalize(entityName),
                    "basePackage", basePackage,
                    "serviceName", serviceName.toLowerCase(),
                    "fields", fields

            );

            try {
                TemplateGenerator generator = new TemplateGenerator();
                if (cqrs) {
                    // === Domain Layer (pure Axon artifacts) ===
                    generator.generate("info/package-info.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/domain/command/" + entityName + "/package-info.java");

                    generator.generate("info/package-info.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/domain/event/" + "package-info.java");

                    generator.generate("aggregate/Aggregate.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/domain/model/aggregate/" + entityName + "Aggregate.java");

                    generator.generate("entity/Entity.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/domain/model/entity/" + entityName + "Entity.java");

                    generator.generate("info/package-info.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/domain/model/valueobject/" + "package-info.java");

                    // === Application Layer ===
                    generator.generate("service/CommandService.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/application/service/command/" + entityName + "CommandService.java");

                    generator.generate("service/CommandServiceImpl.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/application/service/command/" + entityName + "CommandServiceImpl.java");

                    generator.generate("service/QueryService.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/application/service/query/" + entityName + "QueryService.java");

                    generator.generate("service/QueryServiceImpl.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/application/service/query/" + entityName + "QueryServiceImpl.java");

                    generator.generate("query/QueryHandler.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/application/service/" + entityName + "QueryHandler.java");

                    generator.generate("info/package-info.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/application/workflow/" + entityName + "/package-info.java");

                    // === API Layer ===
                    generator.generate("commands/CommandController.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/api/controller/" + entityName + "CommandController.java");

                    generator.generate("query/QueryController.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/api/controller/" + entityName + "QueryController.java");

                    generator.generate("dto/Dto.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/api/dto/" + entityName + "Dto.java");

                    // === Event Handlers ===
                    generator.generate("projection/Projection.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/eventHandler/projection/" + entityName + "ProjectionEventHandler.java");

                    generator.generate("integration/IntegrationHandler.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/eventHandler/integration/" + entityName + "IntegrationEventHandler.java");

                    // === Infrastructure Layer ===
                    generator.generate("repository/Repository.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/infrastructure/repository/" + entityName + "Repository.java");

                }
                else {
                    generator.generate("entity/Entity.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/model/entity/" + capitalize(entityName) + ".java");

                    generator.generate("dto/Dto.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/model/dto/" + capitalize(entityName) + "Dto.java");

                    generator.generate("mapper/Mapper.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/mapper/" + capitalize(entityName) + "Mapper.java");

                    generator.generate("repository/Repository.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/repository/" + capitalize(entityName) + "Repository.java");

                    generator.generate("service/Service.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/service/" + capitalize(entityName) + "Service.java");

                    generator.generate("service/ServiceImpl.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/service/impl/" + capitalize(entityName) + "ServiceImpl.java");

                    generator.generate("controller/Controller.java.ftl", model,
                            baseDir + "/src/main/java/" + basePackage + "/controller/" + capitalize(entityName) + "Controller.java");

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            entitiesGenerated = true;

            System.out.print("📦 Add another entity? (y/n): ");
            createEntities = scanner.nextLine().trim().equalsIgnoreCase("y");
        }
        // ===== BASE PROJECT FILES =====
        Map<String, Object> model = Map.of(
                "serviceName", serviceName,
                "basePackage", basePackage,
                "aggregateName", capitalize(serviceName),
                "cqrs", cqrs,
                "camunda", camunda,
                "database", database,
                "javaVersion", "21"
        );

        try {
            TemplateGenerator generator = new TemplateGenerator();
            // pom.xml
            generator.generate("application/pom.xml.ftl", model,
                    baseDir + "/pom.xml");

            // application.properties
            generator.generate("application/application.properties.ftl", model,
                    baseDir + "/src/main/resources/application.properties");
            // dev application.properties
            generator.generate("application/application-dev.properties.ftl", model,
                    baseDir + "/src/main/resources/application-dev.properties");
            // prod application.properties
            generator.generate("application/application-prod.properties.ftl", model,
                    baseDir + "/src/main/resources/application-prod.properties");
            // Dockerfile
            generator.generate("application/Dockerfile.ftl", model,
                    baseDir + "/Dockerfile");
            // dev Dockerfile
            generator.generate("application/Dockerfile-dev.ftl", model,
                    baseDir + "/Dockerfile-dev");
            // prod Dockerfile
            generator.generate("application/Dockerfile-prod.ftl", model,
                    baseDir + "/Dockerfile-prod");
            // Main class
            generator.generate("application/Application.java.ftl", model,
                    baseDir + "/src/main/java/" + basePackage + "/" + capitalize(serviceName) + "Application.java");

            // Camunda worker if enabled
            if (camunda) {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }



        System.out.println("\n🎉 Project scaffold generated in ./generated/" + serviceName);

        if (cqrs && !camunda && !entitiesGenerated) {
            try {
                String packagePath = basePackage.replace('.', '/');
                Path projectBase = Paths.get(baseDir, "src/main/java", packagePath);
                Files.createDirectories(projectBase);
                String[] packages = {
                        "api",
                        "api/controller",
                        "api/dto",
                        "application",
                        "application/service",
                        "application/service/command",
                        "application/service/query",
                        "application/workflow",
                        "domain",
                        "domain/command",
                        "domain/event",
                        "domain/model",
                        "domain/model/aggregate",
                        "domain/model/entity",
                        "domain/model/valueobject",
                        "infrastructure",
                        "infrastructure/repository",
                        "bootstrap",
                        "bootstrap/config"
                };
                for (String pkg : packages) {
                    Files.createDirectories(projectBase.resolve(pkg));
                }
                System.out.println("📁 Created base CQRS package structure (no entities).");
            } catch (Exception e) {
                System.err.println("⚠️  Failed to create base CQRS package structure: " + e.getMessage());
            }
        }
        return 0;
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}

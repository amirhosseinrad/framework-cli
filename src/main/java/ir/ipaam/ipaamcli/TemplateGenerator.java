package ir.ipaam.ipaamcli;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

public class TemplateGenerator {

    private final Configuration cfg;

    public TemplateGenerator() throws Exception {
        cfg = new Configuration(Configuration.VERSION_2_3_33);
        cfg.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public void generate(String templateName, Map<String, Object> dataModel, String outputPath) throws Exception {
        Template template = cfg.getTemplate(templateName);
        File outFile = new File(outputPath);
        outFile.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(outFile)) {
            template.process(dataModel, writer);
        }
        System.out.println(iconForFile(outputPath) + "  Generated " + outputPath);
    }

    private String iconForFile(String filePath) {
        String lower = filePath.toLowerCase();

        if (lower.endsWith(".properties") || lower.endsWith(".yml")) return "⚙️";
        if (filePath.endsWith("Dockerfile")) return "🐳";
        if (lower.endsWith("pom.xml")) return "📄";
        if (lower.endsWith(".java") && filePath.contains("Application")) return "☕";
        if (lower.endsWith(".java") && filePath.contains("controller")) return "🌐";
        if (lower.endsWith(".java") && filePath.contains("repository")) return "🗄️";
        if (lower.endsWith(".java") && filePath.contains("service")) return "🔧";
        if (lower.endsWith(".java") && filePath.contains("mapper")) return "🧩";
        if (lower.endsWith(".java") && filePath.contains("dto")) return "📦";
        if (lower.endsWith(".java")) return "📦"; // fallback for other Java files

        return "📦"; // default for everything else
    }
}

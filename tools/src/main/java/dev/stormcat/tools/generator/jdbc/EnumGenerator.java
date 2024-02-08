package dev.stormcat.tools.generator.jdbc;

import lombok.RequiredArgsConstructor;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.Writer;

@RequiredArgsConstructor
public class EnumGenerator implements CodeGenerator {

    private final VelocityEngine velocityEngine;

    private final EnumMetaData enumMetaData;

    private final String packageName;

    @Override
    public void write(Writer writer) {
        Template template = velocityEngine.getTemplate("templates/Enum.java.vm");
        VelocityContext context = new VelocityContext();
        context.put("packageName", packageName);
        context.put("enumMetaData", enumMetaData);
        template.merge(context, writer);
    }
}

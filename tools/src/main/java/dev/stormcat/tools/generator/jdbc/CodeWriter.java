package dev.stormcat.tools.generator.jdbc;

import lombok.AllArgsConstructor;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.Writer;

@AllArgsConstructor
public class CodeWriter {

    private VelocityEngine velocityEngine;

    private TableMetaData tableMetaData;

    private String entityPackageName;

    private String repositoryPackageName;

    private Writer entityWriter;

    private Writer repositoryWriter;

    public void writeAll() {
        writeEntity();
        writeRepository();
    }

    public void writeEntity() {
        Template template = velocityEngine.getTemplate("templates/Entity.java.vm");
        VelocityContext context = new VelocityContext();
        context.put("packageName", entityPackageName);
        context.put("tableMetaData", tableMetaData);
        template.merge(context, entityWriter);
    }

    public void writeRepository() {
        Template template = velocityEngine.getTemplate("templates/Repository.java.vm");
        VelocityContext context = new VelocityContext();
        context.put("packageName", repositoryPackageName);
        context.put("entityPackageName", entityPackageName);
        context.put("tableMetaData", tableMetaData);
        template.merge(context, repositoryWriter);
    }
}

package dev.stormcat.tools.generator.jdbc;

import org.apache.velocity.app.VelocityEngine;

public final class VelocityEngineBuilder {

    public static VelocityEngine build() {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "class");
        velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.init();
        return velocityEngine;
    }
}

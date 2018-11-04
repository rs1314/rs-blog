package cn.rs.blog.core.directive;

import java.io.IOException;
import java.util.Map;

import cn.rs.blog.core.handler.DirectiveHandler;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * Created by rs
 */
public abstract class BaseDirective implements TemplateDirectiveModel {

    @Override
    public void execute(Environment environment, @SuppressWarnings("rawtypes") Map parameters, TemplateModel[] loopVars,
                        TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {

        execute(new DirectiveHandler(environment, parameters,loopVars, templateDirectiveBody));
    }

    public abstract void execute(DirectiveHandler handler) throws TemplateException, IOException;
}
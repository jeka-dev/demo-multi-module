import dev.jeka.core.api.project.JkProject;
import dev.jeka.core.tool.KBean;
import dev.jeka.core.tool.builtins.project.ProjectKBean;

class ParentCustom extends KBean {

    // Apply configuration to all child modules
    @Override
    protected void init() {
        getRunbase().findChildren(ProjectKBean.class).forEach(this::config);
    }

    // Infer the moduleId according to its root folder name.
    private void config(ProjectKBean projectKBean) {
        JkProject project = projectKBean.project;
        project.setModuleId("demo-multi-module:" + project.getBaseDir().getFileName());
    }

}

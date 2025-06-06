# Multi-modules Project Example

## Project Structure

This project is organized as a multi-module build with the following structure:

```
.
├── core
│   ├── src    
│   ├── jeka.properties        <- Configuration specific to core module
│   └── dependencies.txt       <- Dependencies specific to core module   
├── plugins   
│   ├── jeka.properties        <- Configuration shared with all plugins
│   ├── dependencies.txt       <- Dependency versions shared with all plugins           
│   ├── bar                      
│   │   ├── src             
│   │   ├── jeka.properties    <- Configuration specific to bar plugin
│   │   └── dependencies.txt   <- Dependencies specific to bar plugin
│   │   
│   └── foo             
│       ├── src
│       ├── jeka.properties
│       └── dependencies.txt
│       └── resources 
├── jeka-src             
│   └── ParentCustom.java      <- Programmatic configuration to apply to all modules      
├── jeka.properties            <- Configuration for multi-modules
├── dependencies.txt           <- Dependencies versions shared with all modules              
└── README.md  

```

There is one *core* module and two *plugin* modules, each depending on the *core* module.  
Additionally, the plugin *foo* depends on the plugin *bar*.

In this example, a specific *plugin* folder has been added to the folder hierarchy.  
However, other structures, such as a flat structure, work equally well.

## Declaring Module Locations

The root `./jeka.properties` file must declare the locations of all modules involved in the project:
```properties
_jeka.child-bases=core, plugins/*
```
The `plugins/*` item refers to *"All direct child directories of 'plugins' that contain either a 'jeka.property' file or a 'dependencies.txt' file."*  
This approach makes it convenient to manage flat structures using:
```properties
_jeka.child-bases=*
```
The `_` prefix means this property doesn't apply to child modules.

## Configure Modules
Each `jeka.properties` file inherits properties from the `jeka.properties` file in its parent directory.  
This inheritance continues recursively until no parent file is found.  
This allows for flexible and centralized configuration.

To prevent a property from being inherited by child directories, prefix it with an underscore (`_`).  
In the following example, the `project` kbean is activated in child directories but not in the parent directory.
```properties
_@project=off
@project=on
```

Child modules can define properties as usual. Example from the *plugin/bar* module:
```properties
@project.layout.style=SIMPLE
@project.layout.mixSourcesAndResources=true
```

The parent module can configure all child modules programmatically. For example, see *./jeka-src/ParentCustom.java*.
```java
    @Override
    protected void init() {
        getRunbase().findChildren(ProjectKBean.class).forEach(this::config);
    }

    // Infer the moduleId according to its root folder name.
    private void config(ProjectKBean projectKBean) {
        JkProject project = projectKBean.project;
        project.setModuleId("demo-multi-module:" + project.getBaseDir().getFileName());
    }
```

## Configure Dependencies
Dependencies can be declared in the *dependencies.txt* file. The dependencies file inherits from its parent in the same way as *jeka.properties* files.

This allows you to centrally declare dependency versions in `./dependencies.txt`. Example:
```ini
[version]
org.junit:junit-bom:5.12.0@pom
com.google.guava.guava:34-jre
```
Caution: Only the `[version]` section is inherited.  
Modules can specify dependencies on each other by using relative directory paths.  
For example, the contents of *./plugins/foo/dependencies.txt* might look like this:
```ini
[compile]
../../core
../bar
com.fasterxml.jackson.core:jackson-core:2.19.0
```

## Build all Modules

```shell
jeka build
```

## Build a Single Module

```shell
jeka build -cb=plugin 
```
# core module

Library module of the [multi-module example](../README.md). The `bar` and `foo` plugins depend on it.
Its dependencies are in [jeka.project.deps](jeka.project.deps); the JUnit version comes from the root one.

Build this module only, from the root directory:
```shell
jeka build -cb=core
```

Or run its tests and create its jar from this directory:
```shell
jeka project: test pack
```

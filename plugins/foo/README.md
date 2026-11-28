# foo plugin module

Plugin module of the [multi-module example](../../README.md). It depends on the [core](../../core) module and
on the [bar](../bar) plugin, as declared in [jeka.project.deps](jeka.project.deps).

Build this module only, from the root directory:
```shell
jeka build -cb=plugins/foo
```

From this directory, build a runnable jar and run its `Main` class (it prints `Hello World!Hello World!`, then `hello`):
```shell
jeka -c -p
```

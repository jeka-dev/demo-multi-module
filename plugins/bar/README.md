# bar plugin module

Plugin module of the [multi-module example](../../README.md). It depends on the [core](../../core) module,
and uses the `SIMPLE` layout (sources in `src`, tests in `test`), set in [jeka.properties](jeka.properties).

Build this module only, from the root directory:
```shell
jeka build -cb=plugins/bar
```

From this directory, build a runnable jar and run its `Main` class (it prints `Hello World!Hello World!`):
```shell
jeka -c -p
```

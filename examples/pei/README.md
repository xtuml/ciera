# PEI

Single domain example with demonstration of SQL instance loading and
BridgePoint pre-build.

## To build the project:

Assure that the `BPHOME` environment variable is set to your BridgePoint
install directory.

Prepare a BridgePoint workspace
```
bash create-workspace.sh
```

Once the workspace is created, run the `export` command output by the script.

Build with maven:
```
mvn install
```

## To run the project

```
bash run.sh
```

When promted that the model is reading instances from standard in, enter
`Ctl-D` to signal end of input.

A new file `out.sql` will have been created with the instance population. Rename
this file `in.sql`

Run again and pipe `in.sql` to the model:
```
bash run.sh < in.sql
```

Verify that the dumped instance population matches what was loaded:
```
diff in.sql out.sql
```

## To turn of loading/dumping architectural IDs

Add the mark to the `application.mark` file

```diff
 *,RootPackage,*,pei::pei
+*,NonPersistentInstanceIds,*,true
 pei::pei,InitFunction,Component,start_test
 pei::pei,InstanceLoading,Component,Sql
```

Build and run:
```
mvn install
bash run.sh < /dev/null
```

Observe the difference in the `out.sql` file contents.

## To browse/edit the project in BridgePoint

1. Launch a recent version of BridgePoint (download nightly build
   [here](https://s3.amazonaws.com/xtuml-releases/nightly-build/buildfiles.html))
2. Import the `runtime` project from the
   `$HOME/.m2/repository/io/ciera/runtime/2.6.2/runtime-2.6.2.jar` file.
3. Import the `pei` from this folder (diasable "Search for nested
   projects")

Alternatively, simply open BridgePoint on the workspace created during the build
step.

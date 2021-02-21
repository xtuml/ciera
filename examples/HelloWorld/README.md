# Hello World

Test area for MASL development

## To build the project (MASL CPP compiler):

Build the project with a bash script
```
bash cpp-gen/build-masl.sh
```

## To run the project

```
docker run -it -v $PWD:/workspace levistarrett/masl-exe cpp-gen/bin/HelloWorld_proc_transient
```

### Shell aliases

For ease of use, add the following to your `.bashrc` or `.zshrc`:

```
alias masl-exe="docker run -it -v $PWD:/workspace levistarrett/masl-exe"
```

Once sourced, you can simply run:

```
masl-exe cpp-gen/bin/HelloWorld_proc_transient
```

to launch the application.

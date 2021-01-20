## To build and run examples docker image:

### Dependencies

- [Docker](https://www.docker.com)

### Build

```
docker build -t ciera-examples .
```

### Run

```
docker run -it ciera-examples
```

### Download/run prebuilt version

```
docker run -it levistarrett:ciera-examples
```

## Updating projects to use the latest Ciera version

```
mvn versions:use-latest-versions
```

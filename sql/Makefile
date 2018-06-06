CC=gcc
CFLAGS=-Wno-pointer-to-int-cast -g
SOURCES := $(shell ls src/*.c)
OBJECTS=$(SOURCES:.c=.o)
OUTDIR=bin
EXECUTABLE=$(OUTDIR)/sql

all:  $(SOURCES) $(EXECUTABLE)

$(EXECUTABLE): $(OBJECTS)
	@$(CC) $(CFLAGS) $^ -o $@

.o:
	$(CC) $(CFLAGS) -c $<

clean:
	rm -f $(OBJECTS) $(EXECUTABLE)

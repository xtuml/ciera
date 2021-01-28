---

This work is licensed under the Creative Commons CC0 License

---

# Generation of Java classes for Web frameworks.
### xtUML Project Analysis Note


### 1. Abstract

This note describes the requirements and analysis for automatically generating Java classes for marked interfaces.
These classes are needed to support Web-based messaging such as clent-server communication. 

### 2. Document References

<a id="2.1"></a>2.1 https://support.onefact.net/issues/11996

### 3. Background

Two applications, CarPark and PilotPayroll, have been developed using Spring framework support for client-server.
This required some measure of hand-written code. One of the more tedious tasks was the creation of a Java class to convey each 
interface message. This is especially tedious because Spring requires that each data member have separate 'get' and 'set' methods 
and a constructor that takes no arguments.
Clearly, a capability could be developed which would selectively generate message classes for selected port messages.

### 4. Requirements

1. Provision of a means to identify those messages for which a Java message class be generated.
2. For each suych message, a separate file shall be generated in a suitable package.
3. Each generated class shall be given a 'messageName' data member of type String.
4. Each message parameter shall be represented by a corresponding data member of type String.
5. Each message data member shall be 'camelCase' formatted with a lower case initial letter.

### 5. Analysis

1. It must be possible for an Interface message to be associated with an output file; a change to tool-core::architecture::file. 
2. Marking may be used to determine which interface messages are associated with an appropriately named file.
3. Message rendering will require templates to guide message class generation.
4. Message rendering should generate an appropriately formatted messageName attribute, derived from each parameter name.

### Testing

It should be simple to replace the hand-coded message classes in the existing applications and validate behavior.


### End

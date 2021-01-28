---

This work is licensed under the Creative Commons CC0 License

---

# Generation of Java classes for Web frameworks.
### xtUML Project Design Note


### 1. Abstract

This note describes the design approach for automatically generating Java classes for marked interfaces.
These classes are needed to support Web-based messaging such as clent-server communication. 

### 2. Document References

<a id="2.1"></a>2.1 https://github.com/amullarney/ciera/blob/master/11969_message_classes_ant.md

### 3. Background

See the analysis note referenced above.

### 4. Requirements

See the analysis note referenced above.

### 5. Analysis

See the analysis note referenced above.

### 6. Design

1. Create a mark feature "MsgClasses" which can be applied to a named Port.
2. Add Port Message class to the subtypes (R401) of File; this provides for emitting a file if marked.
3. At Port Message transform, create a file instance for each Port Message on a marked Port.
4. Copy the Port package information to the Port Message file instance, locating the emitted file.
5. Develop templates for generation of a Java class corresponding to a message
5. At Port render, for each rendered message apply newly developed templates to emit a Java class file.

### Testing

It should be simple to replace the hand-coded message classes in the existing applications and validate behavior.


### End

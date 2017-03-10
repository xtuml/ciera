.select many o_objs from instances of O_OBJ;
.for each o_obj in o_objs
// Class: ${o_obj.Key_Lett}
.end for
.emit to file "../src/lib/POPULATE.java"